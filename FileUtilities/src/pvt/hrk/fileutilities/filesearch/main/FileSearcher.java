package pvt.hrk.fileutilities.filesearch.main;

import static pvt.hrk.fileutilities.utils.ObjectUtils.isNullOrEmpty;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import pvt.hrk.fileutilities.filesearch.core.SearchInFileBase;
import pvt.hrk.fileutilities.filesearch.core.SearchInFileFactory;
import pvt.hrk.fileutilities.filesearch.other.FileSearchResultContainer;

public class FileSearcher {

	public static void search(File source, String searchStr, FileFilter filter, FileSearchResultContainer results,
			BiConsumer<File, Throwable> handleIgnorableExceptions) {
		boolean hasBeenVisitedbefore = results.hasBeenVisitedBefore(source);
		if (LOGGER.isLoggable(Level.FINE)) {
			LOGGER.fine("Now processing : " + source);
			LOGGER.fine("Has been visited before? " + hasBeenVisitedbefore);
		}
		if (!hasBeenVisitedbefore) {
			if (source.isDirectory()) {
				// If directory, traverse recursively
				List<File> files = Arrays.asList(Optional.ofNullable(source.listFiles(filter)).orElse(new File[0]));

				if (!isNullOrEmpty(files)) {
					files.parallelStream().forEach(file -> {
						search(file, searchStr, filter, results, handleIgnorableExceptions);
					});
				}
			} else if (source.isFile()) {
				try {
					SearchInFileBase searchInFile = new SearchInFileFactory(source, searchStr).get();
					if (searchInFile.foundInName() || searchInFile.foundInContents()) {
						results.addToResults(source);
					} else {
						// this file doesn't contain the search string in name or contents
					}
				} catch (Exception e) {
					handleIgnorableExceptions.accept(source, e);
				}
			} else {
				if (LOGGER.isLoggable(Level.FINE)) {
					LOGGER.fine("File type ot supported. Ignoring." + source);
				}
			}
		}
	}

	// Input section end
	private static final Logger LOGGER = Logger.getLogger(FileSearcher.class.getClass().getName());

}
