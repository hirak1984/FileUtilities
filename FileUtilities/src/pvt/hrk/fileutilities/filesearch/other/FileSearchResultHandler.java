package pvt.hrk.fileutilities.filesearch.other;

import java.io.File;
import java.util.Set;

public interface FileSearchResultHandler {

	void init();
	boolean hasBeenVisitedBefore(File f);
	boolean addToResults(File f);
	Set<File> visitedFilesSoFar();
	Set<String> visitedFileNamesSoFar();
	void handle();
}
