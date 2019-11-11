package pvt.hrk.fileutilities.filesearch.other;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

public class MyResultHandler implements FileSearchResultHandler {
	Set<String> filePaths;

	public MyResultHandler() {
		init();
	}
	@Override
	public void init() {
		if (filePaths == null) {
			filePaths = new HashSet<>();
		}
	}

	@Override
	public boolean addToResults(File f) {
		return filePaths.add(f.getAbsolutePath());
	}

	@Override
	public void handle() {
		filePaths.forEach(System.out::println);
	}

	@Override
	public boolean hasBeenVisitedBefore(File f) {
		return filePaths.contains(f.getAbsolutePath());
	}
	@Override
	public Set<File> visitedFilesSoFar() {
		throw new UnsupportedOperationException("Please use 'visitedFileNamesSoFar' function");
	}
	@Override
	public Set<String> visitedFileNamesSoFar() {
		return Collections.unmodifiableSet(filePaths);
	}

}
