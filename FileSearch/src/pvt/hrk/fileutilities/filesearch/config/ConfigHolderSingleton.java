package pvt.hrk.fileutilities.filesearch.config;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import pvt.hrk.fileutilities.utils.MyFileFilterBuilder;
import pvt.hrk.fileutilities.utils.ObjectUtils;

public enum ConfigHolderSingleton {
	INSTANCE;
	private FileFilter filter;
	private Path tempDirectory = null;
	private boolean ignoreCase=true;
	private boolean searchInNameOnly=false;
	private boolean searchInZip=true;
	private String[] searchLocations;
	private String searchString;
	private static class Metadata {
		@SerializedName("Search_String")
		String searchString;
		@SerializedName("Search_Locations_CSV")
		String[] searchLocations;
		@SerializedName("Exclude_Files_CSV")
		String[] exclusions;
		@SerializedName("Include_Files_CSV")
		String[] inclusions;
		@SerializedName("Ignore_Case")
		boolean ignoreCase;
		@SerializedName("Search_In_Zip")
		boolean searchInZip;
		@SerializedName("Search_Name_Only")
		boolean searchInNameOnly;
	}

	public boolean ignoreCase() {
		return ignoreCase;
	}
	public boolean searchInNameOnly() {
		return searchInNameOnly;
	}
	public boolean searchInZip() {
		return searchInZip;
	}

	public FileFilter getFileFilter() {
		return filter;
	}

	public String[] searchLocations() {
		return searchLocations;
	}

	public String searchString() {
		return searchString;
	}

	public Path getTempDirectoryPath() {
		return tempDirectory;
	}

	public void init(InputStream fileSearchConfigInputStream) throws IOException {

		if (fileSearchConfigInputStream == null) {
			throw new RuntimeException("fileSearchConfigInputStream can't be null");
		}
		Metadata metadata = new Gson().fromJson(new InputStreamReader(fileSearchConfigInputStream), Metadata.class);
		tempDirectory = Files.createTempDirectory("FileSearch");
		filter = new MyFileFilterBuilder().excludeFileNamesContaining(metadata.exclusions).includeOnlyFileNamesContaining(metadata.inclusions).build();
		ignoreCase = metadata.ignoreCase;
		searchInNameOnly = metadata.searchInNameOnly;
		searchInZip = metadata.searchInZip;
		searchLocations = metadata.searchLocations;
		searchString = metadata.searchString;
		Runtime.getRuntime().addShutdownHook(new Thread() {

			@Override
			public void run() {
				try {
					deleteRecursively(tempDirectory.toFile());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	private void deleteRecursively(File f) throws IOException {
		if (f == null) {
			return;
		}
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			if (!ObjectUtils.isNullOrEmpty(files)) {
				for (File file : files) {
					deleteRecursively(file);
				}
			}
		} else {
			Files.deleteIfExists(f.toPath());
		}
	}
}
