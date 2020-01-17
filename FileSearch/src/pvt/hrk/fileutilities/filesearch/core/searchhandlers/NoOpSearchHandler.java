package pvt.hrk.fileutilities.filesearch.core.searchhandlers;

import java.io.File;
import java.io.FileFilter;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoOpSearchHandler implements SearchHandler {
File file;
private static Logger LOGGER = LoggerFactory.getLogger(NoOpSearchHandler.class);

	public NoOpSearchHandler(File file) {
	super();
	this.file = file;
	LOGGER.trace("File {} went through NoOpHandler",file.getAbsoluteFile());
	
}

	@Override
	public boolean matchInName(String searchString) {
		return contains(file.getName(), searchString);
	}

	@Override
	public boolean matchInContent(String searchString) {
		return false;
	}

	@Override
	public List<File> getChildren(FileFilter filter) {

		return Collections.emptyList();
	}

}
