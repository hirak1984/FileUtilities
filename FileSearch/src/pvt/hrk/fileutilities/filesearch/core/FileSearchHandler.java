package pvt.hrk.fileutilities.filesearch.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import pvt.hrk.fileutilities.filesearch.config.ConfigHolderSingleton;

public class FileSearchHandler implements SearchHandler{

	File file;

	public FileSearchHandler(File file) {
		super();
		this.file = file;
	}

	@Override
	public boolean matchInName(String searchString) {
		return containsIgnoreCase(file.getName(), searchString);
	}

	@Override
	public boolean matchInContent(String searchString) {
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			return bufferedReader.lines().parallel().anyMatch(line->containsIgnoreCase(line, searchString));
		} catch (IOException  e) {
			ConfigHolderSingleton.INSTANCE.handleException(file, e);
		} 
		return false;
	}

	@Override
	public List<File> getChildren(FileFilter filter) {
		return Collections.<File>emptyList();
	}
	
	
}