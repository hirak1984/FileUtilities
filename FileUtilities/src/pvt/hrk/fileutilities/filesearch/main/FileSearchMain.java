package pvt.hrk.fileutilities.filesearch.main;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import pvt.hrk.fileutilities.difffinder.api.other.MyFileFilterBuilder;
import pvt.hrk.fileutilities.filesearch.other.FileSearchResultContainer;

public class FileSearchMain {

	private static final Logger LOGGER = Logger.getLogger(FileSearchMain.class.getClass().getName());
	/**
	 * Input section
	 */
	private static final String searchString = "apache-ant-1.7";
	private static final String[] searchLocations = { "C:\\" };
	private static final FileFilter filter = new MyFileFilterBuilder()
			//.includeOnlyFileNamesContaining(new String[] { ".gs"})
			.excludeFileNamesContaining(Arrays.asList(new String[] { ".class", ".png", ".jpg", ".jpeg", ".pcfc", ".gif",
							".ico", ".dll", ".lock", ".mp4", "node", ".gz", "DS_Store", "un~", "node_modules","ISBTF","PolicyCenter900-SmartCommunications-4.0.2" }))
					.build();
	private static final BiConsumer<File, Throwable> logAndIgnoreExceptions = (f, t) -> {
		if (LOGGER.isLoggable(Level.FINE)) {
			LOGGER.log(Level.SEVERE, "Error reading file:" + f.toPath(), t);
		}
	};

	public static void main(String[] args) {
		Stream.of(searchLocations).forEach(searchLocation -> {
			File searchIn = new File(searchLocation);
			if (LOGGER.isLoggable(Level.INFO)) {
				LOGGER.info("=====Searching in :-" + searchLocation + "=====");
			}
			try {
				FileSearchResultContainer results = new FileSearchResultContainer();
				FileSearcher.search(searchIn, searchString, filter, results, logAndIgnoreExceptions);
				results.handleResults(f -> System.out.println(f.getAbsolutePath()));
			} catch (Exception e) {
				logAndIgnoreExceptions.accept(searchIn, e);
			}
		});
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info("Done");
		}
	}
}
