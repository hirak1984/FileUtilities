package pvt.hrk.fileutilities.filesearch.core;

import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pvt.hrk.fileutilities.filesearch.config.ConfigHolderSingleton;
import pvt.hrk.fileutilities.filesearch.core.resulthandlers.ResultHandler;
import pvt.hrk.fileutilities.filesearch.core.searchhandlers.SearchHandler;
import pvt.hrk.fileutilities.filesearch.core.searchhandlers.SearchHandlerFactory;
import pvt.hrk.fileutilities.utils.ObjectUtils;

public class MyFileSearcher {
	private static Logger LOGGER = LoggerFactory.getLogger(MyFileSearcher.class);

	public void searchRecursively(File source, ResultHandler resultHandler) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Now processing : " + source);
		}
		SearchHandler handler = SearchHandlerFactory.Handler(source);
		String searchStr = ConfigHolderSingleton.INSTANCE.searchString();
		FileFilter filter = ConfigHolderSingleton.INSTANCE.getFileFilter();

		if (handler.matchInName(searchStr)) {
			handleResult(source, resultHandler);
		} else if ((!ConfigHolderSingleton.INSTANCE.searchInNameOnly()) && handler.matchInContent(searchStr)) {
			handleResult(source, resultHandler);
		}
		List<File> children = handler.getChildren(filter);
		if (!ObjectUtils.isNullOrEmpty(children)) {
			children.parallelStream().forEach(child -> searchRecursively(child, resultHandler));
		}
	}

	private void handleResult(File source, ResultHandler resultHandler) {
		try {
			resultHandler.handle(source);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void searchiteratively(File source, ResultHandler resultHandler) {
		Queue<File> queue = new LinkedList<File>();
		queue.offer(source);
		while (!queue.isEmpty()) {
			File file = queue.poll();
			if (file == null) {
				continue;
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Now processing : " + file);
			}
			SearchHandler handler = SearchHandlerFactory.Handler(file);
			String searchStr = ConfigHolderSingleton.INSTANCE.searchString();
			FileFilter filter = ConfigHolderSingleton.INSTANCE.getFileFilter();

			if (handler.matchInName(searchStr)) {
				handleResult(file, resultHandler);
			} else if ((!ConfigHolderSingleton.INSTANCE.searchInNameOnly()) && handler.matchInContent(searchStr)) {
				handleResult(file, resultHandler);
			}
			List<File> children = handler.getChildren(filter);
			for(File f:children) {
				queue.offer(f);
			}

		}
	}
}
