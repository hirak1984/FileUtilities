package pvt.hrk.fileutilities.utils;

import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pvt.hrk.fileutilities.filesearch.config.ConfigHolderSingleton;

public class MyFileFilterBuilder {
	private static Logger LOGGER = LoggerFactory.getLogger(MyFileFilterBuilder.class);
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
			boolean retVal = false;
			String fileName = file.getName();

			if (ConfigHolderSingleton.INSTANCE.searchInNameOnly()) {
				retVal = true;
			} else if (isPartOfExcludedList(fileName)) {
				retVal = false;
			} else if (isPartOfIncludedList(fileName)) {
				retVal = true;
			} else if (file.isDirectory()) {
				retVal = true; // dir not in excluded/included list, so give the files inside this dir a chance
			} else {
				retVal = false;
			}
			LOGGER.trace("File= {} , retVal={}", fileName,retVal);
			return retVal;
		};
	}

	boolean isPartOfExcludedList(String fileName) {
		if (ObjectUtils.isNullOrEmpty(excludeFileNamesContaining)) {
			return false;
		} else {
			return excludeFileNamesContaining.parallelStream().anyMatch(fileName::contains);
		}
	}

	boolean isPartOfIncludedList(String fileName) {
		if (ObjectUtils.isNullOrEmpty(includeOnlyFileNamesContaining)) {
			// if included list is empty, that means everything is included.
			return true;
		} else {
			return includeOnlyFileNamesContaining.parallelStream().anyMatch(fileName::contains);
		}
	}
}
