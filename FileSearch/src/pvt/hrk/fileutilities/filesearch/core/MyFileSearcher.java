package pvt.hrk.fileutilities.filesearch.core;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pvt.hrk.fileutilities.filesearch.config.ConfigHolderSingleton;
import pvt.hrk.fileutilities.utils.ObjectUtils;

public class MyFileSearcher {
	private static Logger LOGGER = LoggerFactory.getLogger(MyFileSearcher.class);

	public static void search(File source, FileSearchResultContainer results) {
		boolean hasBeenVisitedbefore = results.hasBeenVisitedBefore(source);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Now processing : " + source);
			LOGGER.debug("Has been visited before? " + hasBeenVisitedbefore);
		}
		if (hasBeenVisitedbefore) {
			return;
		}
		SearchHandler handler = SearchHandlerFactory.Handler(source);
		String searchStr = ConfigHolderSingleton.INSTANCE.searchString();
		FileFilter filter = ConfigHolderSingleton.INSTANCE.getFileFilter();
		if (handler.matchInName(searchStr)) {
			results.addToResults(source);
		} else if (!ConfigHolderSingleton.INSTANCE.searchInNameOnly()) {
			if (handler.matchInContent(searchStr)) {
				results.addToResults(source);
			} else {
				List<File> children = handler.getChildren(filter);
				if (!ObjectUtils.isNullOrEmpty(children)) {
					children.parallelStream().forEach(child -> search(child, results));
				}
			}
		}

	}

}
