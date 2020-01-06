package pvt.hrk.fileutilities.filesearch.core.resulthandlers;

import java.io.File;

public interface ResultHandler {

	void init() throws Exception;

	void handle(File file) throws Exception;

	void finish() throws Exception;
}
