package pvt.hrk.fileutilities.filesearch.main;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pvt.hrk.fileutilities.filesearch.config.ConfigHolderSingleton;
import pvt.hrk.fileutilities.filesearch.core.FileSearchResultContainer;
import pvt.hrk.fileutilities.filesearch.core.MyFileSearcher;
import pvt.hrk.fileutilities.utils.ObjectUtils;

public class FileSearchMain {

	private static Logger LOGGER = LoggerFactory.getLogger(FileSearchMain.class);

	public static void main(String[] args) throws IOException {
		if (ObjectUtils.isNullOrEmpty(args)) {
			ConfigHolderSingleton.INSTANCE.init(null);
		} else {
			ConfigHolderSingleton.INSTANCE.init(new File(args[0]));
		}

		Stream.of(ConfigHolderSingleton.INSTANCE.searchLocations()).forEach(searchLocation -> {
			File searchIn = new File(searchLocation);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("=====Searching in :-" + searchLocation + "=====");
			}
			try {
				FileSearchResultContainer results = new FileSearchResultContainer();
				MyFileSearcher.search(searchIn, ConfigHolderSingleton.INSTANCE.searchString(),
						ConfigHolderSingleton.INSTANCE.getFileFilter(), results);
				results.handleResults(f -> System.out.println(f.getAbsolutePath()));
			} catch (Exception e) {
				ConfigHolderSingleton.INSTANCE.handleException(searchIn, e);
			}
		});
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Done");
		}
	}
}
