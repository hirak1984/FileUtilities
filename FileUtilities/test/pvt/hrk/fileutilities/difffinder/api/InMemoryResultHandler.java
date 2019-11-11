package pvt.hrk.fileutilities.difffinder.api;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pvt.hrk.fileutilities.difffinder.api.handlers.ResultHandler;

public class InMemoryResultHandler implements ResultHandler{
public List<String> missingInSrc;
public List<String> missingInTar;
public List<String> contentMisMatch;
public List<String> typeMisMatch;
	@Override
	public void init() {
		missingInSrc = new ArrayList<>();
		missingInTar = new ArrayList<>();
		contentMisMatch = new ArrayList<>();
		typeMisMatch = new ArrayList<>();
		
	}

	@Override
	public void addMissingInSource(File file) {
		missingInSrc.add(file.getAbsolutePath());
		
	}

	@Override
	public void addMissingInTarget(File file) {
		missingInTar.add(file.getAbsolutePath());
		
	}

	@Override
	public void addTypeMismatch(File sourceFile,File targetFile) {
		typeMisMatch.add(sourceFile.getAbsolutePath());
		
	}

	@Override
	public void addContentMismatch(File sourceFile,File targetFile) {
		contentMisMatch.add(sourceFile.getAbsolutePath());
		
	}

	@Override
	public void finish() {

		
	}

}
