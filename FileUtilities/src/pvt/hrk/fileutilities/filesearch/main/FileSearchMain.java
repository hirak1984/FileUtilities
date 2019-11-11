package pvt.hrk.fileutilities.filesearch.main;

import static pvt.hrk.fileutilities.utils.ObjectUtils.isNullOrEmpty;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pvt.hrk.fileutilities.difffinder.api.other.MyFileFilterBuilder;
import pvt.hrk.fileutilities.filesearch.core.SearchInFileBase;
import pvt.hrk.fileutilities.filesearch.core.SearchInFileFactory;
import pvt.hrk.fileutilities.filesearch.other.FileSearchResultHandler;
import pvt.hrk.fileutilities.filesearch.other.MyResultHandler;

public class FileSearchMain {

	/**
	 * Input section
	 */
	private static final String searchString = "%VehicleIncident%";
	private static final String[] searchLocations = {"C:\\Guidewire\\9\\ClaimCenter907"}; 

	public static void main(String[] args) {
		Stream.of(searchLocations).forEach(searchLocation -> {
			File searchIn = new File(searchLocation);
			if (LOGGER.isLoggable(Level.INFO)) {
				LOGGER.info("=====Searching in :-" + searchLocation + "=====");
			}
			try {
				FileSearchResultHandler handler = new MyResultHandler();
				search(searchIn, searchString, handler);
				handler.handle();
			} catch (Exception e) {
				logAndIgnoreExceptions(e, searchIn);
			}
		});
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info("Done");
		}
	}

	private static void search(File source, String searchStr, FileSearchResultHandler handler) {
		if (handler.hasBeenVisitedBefore(source)) {
			return;
		} else {
			if (source.isDirectory()) {
				// If directory, traverse recursively
				List<File> files = Arrays.stream(source.listFiles(filter)).collect(Collectors.toList());

				if (!isNullOrEmpty(files)) {
					files.parallelStream().forEach(file -> {
						search(file, searchStr, handler);
					});
				}
			} else if (source.isFile()) {
				try {
					SearchInFileBase searchInFile = new SearchInFileFactory(source, searchStr).get();
					if (searchInFile.foundInName() || searchInFile.foundInContents()) {
						handler.addToResults(source);
					} else {
						// this file doesn't contain the search string in name or contents
					}
				} catch (Exception e) {
					logAndIgnoreExceptions(e, source);
				}
			} else {
				// Do nothing. unsupported
			}
		}
	}

	private static void logAndIgnoreExceptions(Throwable t, File file) {
		if (LOGGER.isLoggable(Level.FINE)) {
			LOGGER.log(Level.FINE, "Error reading file:" + file.toPath(), t);
		}
	}

	private static final FileFilter filter = new MyFileFilterBuilder()
			.excludeFileNamesContaining(Arrays.asList(new String[] { "class", "png", "jpg", "jpeg", "pcfc", "gif",
					"ico", "dll", "lock", "mp4", "node", "gz", "DS_Store", "un~", "node_modules" }))
			.build();
	// Input section end
	private static final Logger LOGGER = Logger.getLogger(FileSearchMain.class.getClass().getName());

}
