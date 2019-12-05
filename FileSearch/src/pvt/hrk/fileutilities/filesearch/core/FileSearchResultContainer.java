package pvt.hrk.fileutilities.filesearch.core;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class FileSearchResultContainer {

	Set<File> filesVisited;
	Set<String> filePaths;

	public FileSearchResultContainer() {
		init();
	}

	public void init() {
		if (filesVisited == null) {
			filesVisited = new HashSet<>();
		}
		if (filePaths == null) {
			filePaths = new HashSet<>();
		}
	}

	public boolean addToResults(File f) {
		boolean retVal = filesVisited.add(f);
		retVal = filePaths.add(f.getAbsolutePath());
		return retVal;
	}

	public void handleResults(Consumer<File> action) {
		filesVisited.forEach(action);
	}

	public boolean hasBeenVisitedBefore(File f) {
		return filePaths.contains(f.getAbsolutePath());
	}

	public Set<File> getVisitedFiles() {
		// defensive copy
		Set<File> retVal = new HashSet<>(filesVisited);
		return retVal;
	}
	public Set<String> getVisitedFileNames() {
		// defensive copy
		Set<String> retVal = new HashSet<>(filePaths);
		return retVal;
	}
}
