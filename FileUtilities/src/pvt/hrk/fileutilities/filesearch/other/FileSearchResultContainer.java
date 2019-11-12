package pvt.hrk.fileutilities.filesearch.other;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class FileSearchResultContainer {

	Set<File> filesVisited;

	public FileSearchResultContainer() {
		init();
	}

	public void init() {
		if (filesVisited == null) {
			filesVisited = new HashSet<>();
		}
	}

	public boolean addToResults(File f) {
		return filesVisited.add(f);
	}

	public void handleResults(Consumer<File> action) {
		filesVisited.forEach(action);
	}

	public boolean hasBeenVisitedBefore(File f) {
		return filesVisited.contains(f);
	}

	public Set<File> getVisitedFilesSoFar() {
		// defensive copy
		Set<File> retVal = new HashSet<>(filesVisited);
		return retVal;
	}
}
