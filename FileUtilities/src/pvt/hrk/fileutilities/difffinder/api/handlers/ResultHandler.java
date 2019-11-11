package pvt.hrk.fileutilities.difffinder.api.handlers;

import java.io.File;

public interface ResultHandler {

	void init();
	void addMissingInSource(File sourceFile);
	void addMissingInTarget(File targetFile);
	void addTypeMismatch(File sourceFile, File targetFile);
	void addContentMismatch(File sourceFile, File targetFile);
	void finish();
}

