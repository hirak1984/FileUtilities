package pvt.hrk.fileutilities.filesearch.core;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pvt.hrk.fileutilities.utils.ObjectUtils;

public class MyFileSearcher {
	private static Logger LOGGER = LoggerFactory.getLogger(MyFileSearcher.class);
	public static void search(File source, String searchStr, FileFilter filter, FileSearchResultContainer results) {
		search(source, searchStr, filter, results,false);
	}

	public static void search(File source, String searchStr, FileFilter filter, FileSearchResultContainer results,boolean searchInNameOnly) {
		boolean hasBeenVisitedbefore = results.hasBeenVisitedBefore(source);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Now processing : " + source);
			LOGGER.debug("Has been visited before? " + hasBeenVisitedbefore);
		}
		if(hasBeenVisitedbefore) {
			return;
		}
		SearchHandler handler = SearchHandlerFactory.Handler(source);
		if(handler.matchInName(searchStr) || (!searchInNameOnly &&handler.matchInContent(searchStr))) {
			results.addToResults(source);
		}else {
			List<File> children = handler.getChildren(filter);
			if(!ObjectUtils.isNullOrEmpty(children)) {
				children.parallelStream().forEach(child-> search(child, searchStr, filter, results));
			}
		}

	}

}
