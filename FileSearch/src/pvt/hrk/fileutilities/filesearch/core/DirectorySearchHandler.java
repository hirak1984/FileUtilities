package pvt.hrk.fileutilities.filesearch.core;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DirectorySearchHandler implements SearchHandler{

	File directory;
	
	public DirectorySearchHandler(File directory) {
		super();
		this.directory = directory;
	}

	@Override
	public boolean matchInName(String searchString) {
		return contains(this.directory.getName(), searchString);
	}

	@Override
	public boolean matchInContent(String searchString) {
		return false;
	}

	@Override
	public List<File> getChildren(FileFilter filter) {
		List<File> files = Arrays.asList(Optional.ofNullable(directory.listFiles(filter)).orElse(new File[0]));
		return files;
	}

}
