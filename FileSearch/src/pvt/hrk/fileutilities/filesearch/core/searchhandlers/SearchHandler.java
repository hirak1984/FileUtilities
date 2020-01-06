package pvt.hrk.fileutilities.filesearch.core.searchhandlers;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

import pvt.hrk.fileutilities.filesearch.config.ConfigHolderSingleton;

public interface SearchHandler {

	boolean matchInName(String searchString);

	boolean matchInContent(String searchString);

	List<File> getChildren(FileFilter filter);

	default public boolean contains(String line, String keyword) {
		if(line==null) {
			return false;
		}
		if(keyword==null) {
			return false;
		}
		if(ConfigHolderSingleton.INSTANCE.ignoreCase()) {
			line = line.toLowerCase();
			keyword = keyword.toLowerCase();
		}
		return line.contains(keyword);
	}
}
