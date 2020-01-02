package pvt.hrk.fileutilities.filesearch.core;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class FileSearchResultContainer {

	Set<String> visitedFileAbsolutePath;

	public FileSearchResultContainer() {
		init();
	}

	public void init() {
		if (visitedFileAbsolutePath == null) {
			visitedFileAbsolutePath = new HashSet<>();
		}
	}

	public boolean addToResults(File f) {
		boolean retVal = visitedFileAbsolutePath.add(f.getAbsolutePath());
		return retVal;
	}

	public Set<String> getVisitedFilePaths() {
		return visitedFileAbsolutePath;
	}

	// prevents cycles
	public boolean hasBeenVisitedBefore(File f) {
		return visitedFileAbsolutePath.parallelStream()
				.anyMatch(visitedFilePath -> visitedFilePath.equalsIgnoreCase(f.getAbsolutePath()));
	}
}
