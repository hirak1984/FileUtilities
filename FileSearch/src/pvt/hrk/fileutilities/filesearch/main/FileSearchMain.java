package pvt.hrk.fileutilities.filesearch.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pvt.hrk.fileutilities.filesearch.config.ConfigHolderSingleton;
import pvt.hrk.fileutilities.filesearch.core.MyFileSearcher;
import pvt.hrk.fileutilities.filesearch.core.resulthandlers.ConsolidatedResultHandler;
import pvt.hrk.fileutilities.filesearch.core.resulthandlers.ResultHandler;
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
			logger.info("Searching in [ {} ] ", searchLocation);
			try {
				ResultHandler results = new ConsolidatedResultHandler(System.out);
				results.init();
				new MyFileSearcher().searchRecursively(searchIn, results);
				results.finish();
			} catch (Exception e) {
				ObjectUtils.handleException(searchIn, e);
			}
		});
		logger.info("Done");
	}
}
