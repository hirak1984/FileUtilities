package pvt.hrk.fileutilities.filesearch.config;

import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import pvt.hrk.fileutilities.utils.ObjectUtils;

public class MyFileFilterBuilder {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private List<String> includeOnlyFileNamesContaining;
	private List<String> excludeFileNamesContaining;

	public MyFileFilterBuilder() {

	}

	public MyFileFilterBuilder excludeFileNamesContaining(List<String> keywords) {
		if (keywords != null && excludeFileNamesContaining == null) {
			excludeFileNamesContaining = new ArrayList<String>(keywords);
		}
		return this;
	}

	public MyFileFilterBuilder includeOnlyFileNamesContaining(List<String> keywords) {
		if (keywords != null && includeOnlyFileNamesContaining == null) {
			includeOnlyFileNamesContaining = new ArrayList<String>(keywords);
		}
		return this;
	}

	public MyFileFilterBuilder includeOnlyFileNamesContaining(String[] keywords) {
		return includeOnlyFileNamesContaining(Arrays.<String>asList(keywords));
	}

	public MyFileFilterBuilder excludeFileNamesContaining(String[] keywords) {
		return excludeFileNamesContaining(Arrays.<String>asList(keywords));
	}

	public FileFilter build() {
		return (file) -> {
			String fileName = file.getName();

			if (isPartOfExcludedList(fileName)) {
				return false;
			}
			if (isPartOfIncludedList(fileName)) {
				return true;
			}
			if (file.isDirectory()) {
				return true; // to give the files inside this dir a chance
			}
			return false;
		};
	}

	boolean isPartOfExcludedList(String fileName) {
		if (ObjectUtils.isNullOrEmpty(excludeFileNamesContaining)) {
			return false;
		} else {
			return excludeFileNamesContaining.stream().anyMatch(name -> fileName.contains(name));
		}
	}

	boolean isPartOfIncludedList(String fileName) {
		if (ObjectUtils.isNullOrEmpty(includeOnlyFileNamesContaining)) {
			//if included list is empty, that means everything is included.
			return true;
		} else {
			return includeOnlyFileNamesContaining.stream().anyMatch(name -> fileName.contains(name));
		}
	}
}