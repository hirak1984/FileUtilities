package pvt.hrk.fileutilities.filesearch.core;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

public interface SearchHandler {

	boolean matchInName(String searchString);

	boolean matchInContent(String searchString);

	List<File> getChildren(FileFilter filter);

	default public boolean containsIgnoreCase(String line, String keyword) {
		if(line==null) {
			return false;
		}
		if(keyword==null) {
			return false;
		}
		return line.toLowerCase().contains(keyword.toLowerCase());
	}
}
