package pvt.hrk.fileutilities.filesearch.config;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import pvt.hrk.fileutilities.utils.ObjectUtils;

public enum ConfigHolderSingleton {
	INSTANCE;
	private static Logger LOGGER = LoggerFactory.getLogger(ConfigHolderSingleton.class);
	private Metadata metadata;
	private FileFilter filter;
	private Path tempDirectory =null;
	private static class Metadata {
		@SerializedName("Search_String_IgnoreCase")
		String searchString;
		@SerializedName("Search_Locations_CSV")
		String[] searchLocations;
		@SerializedName("Exclude_Files_CSV")
		String[] exclusions;
	}

	public void handleException(File f, Throwable t) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.warn("Error reading file:" + f.toPath()+" Error Message : "+t.getLocalizedMessage());
		}
	}

	public FileFilter getFileFilter() {
		if (filter == null) {
			filter = new MyFileFilterBuilder().excludeFileNamesContaining(metadata.exclusions).build();
		}
		return filter;
	}

	public String[] searchLocations() {
		return metadata.searchLocations;
	}

	public String searchString() {
		return metadata.searchString;
	}
public Path getTempDirectoryPath() {
	return tempDirectory;
}
	public void init(File propertiesFilePath) throws IOException {

		InputStream is = null;
		try {
			if (propertiesFilePath != null) {
				is = new FileInputStream(propertiesFilePath);
			} else {
				is = getClass().getResourceAsStream("filesearchconfig.json");
			}

			metadata = new Gson().fromJson(new InputStreamReader(is), Metadata.class);
			tempDirectory = Files.createTempDirectory("FileSearch");
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
		} catch (IOException e) {
			throw e;
		}
	}


	private	void deleteRecursively(File f) throws IOException {
		if(f==null) {
			return;
		}
			if(f.isDirectory()) {
				File[] files = f.listFiles();
				if(!ObjectUtils.isNullOrEmpty(files)) {
					for(File file : files) {
						deleteRecursively(file);
					}
				}
			}else {
				Files.deleteIfExists(f.toPath());
			}
		}
	}
	

