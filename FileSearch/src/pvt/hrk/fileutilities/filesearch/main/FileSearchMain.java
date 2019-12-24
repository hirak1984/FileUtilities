package pvt.hrk.fileutilities.filesearch.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pvt.hrk.fileutilities.filesearch.config.ConfigHolderSingleton;
import pvt.hrk.fileutilities.filesearch.core.FileSearchResultContainer;
import pvt.hrk.fileutilities.filesearch.core.MyFileSearcher;
import pvt.hrk.fileutilities.utils.ObjectUtils;

public class FileSearchMain {

	private static final Logger logger = LoggerFactory.getLogger(FileSearchMain.class);

	public static void main(String[] args) throws IOException {

		InputStream is = null;
		if (ObjectUtils.isNullOrEmpty(args)) {
			is = FileSearchMain.class.getClassLoader().getResourceAsStream("filesearchconfig.json");
		} else {
			is = new FileInputStream(args[0]);
		}
		ConfigHolderSingleton.INSTANCE.init(is);
		is.close();
		Stream.of(ConfigHolderSingleton.INSTANCE.searchLocations()).forEach(searchLocation -> {
			File searchIn = new File(searchLocation);
			logger.info("=====Searching in [ {} ] =====", searchLocation);
			try {
				FileSearchResultContainer results = new FileSearchResultContainer();
				MyFileSearcher.search(searchIn, ConfigHolderSingleton.INSTANCE.searchString(),
						ConfigHolderSingleton.INSTANCE.getFileFilter(), results);
				results.handleResults(f -> {
					System.out.println(f.getAbsolutePath());
					// logger.error(f.getAbsolutePath());
				});
			} catch (Exception e) {
				ConfigHolderSingleton.INSTANCE.handleException(searchIn, e);
			}
		});
		logger.info("Done");
	}
}
