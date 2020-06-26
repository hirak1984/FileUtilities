package pvt.hrk.fileutilities.filesearch.core.resulthandlers;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface ResultHandler {
	public static final Logger logger = LoggerFactory.getLogger(ResultHandler.class);
	void init() throws Exception;

	void handle(File file) throws Exception;

	void finish() throws Exception;
}
