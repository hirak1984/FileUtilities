package pvt.hrk.fileutilities.difffinder.api.handlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CSVResultHandler implements ResultHandler {

	FileOutputStream fos;
	File outputFile;
	private static final String SEPARATOR = ",";
	private static final String Header = new StringBuilder().append("Source File Path").append(SEPARATOR)
			.append("Target File Path").append(SEPARATOR).append("Conflict Type").toString();

	private enum CONFLICT_TYPE {
		MISSING_IN_SOURCE("No such file in source(left) directory"), MISSING_IN_TARGET(
				"No such file in target(right) directory"), TYPE_MISMATCH(
						"Same name but file type is different (maybe one is file and another dir)"), CONTENT_MISMATCH(
								"File aboslute paths match but contents don't match");

		private String desc;

		CONFLICT_TYPE(String desc) {
			this.desc = desc;
		}

		public String DisplayName() {
			return new StringBuilder(name()).append("(").append(desc).append(")").toString();
		}
	}

	public CSVResultHandler(File resultsFile) throws FileNotFoundException {
		resultsFile.getParentFile().mkdirs();
		if (resultsFile.isDirectory()) {
			outputFile = new File(resultsFile, "results.csv");
		} else {
			outputFile = resultsFile;
		}
		fos = new FileOutputStream(outputFile);
	}

	@Override
	public void init() {
		writeToFile(Header);
		writeToFile(System.lineSeparator());
	}

	@Override
	public void addMissingInSource(File file) {
		String line = generateOutputLine(null, file, CONFLICT_TYPE.MISSING_IN_SOURCE);
		writeToFile(line);
	}

	@Override
	public void addMissingInTarget(File file) {
		String line = generateOutputLine(file, null, CONFLICT_TYPE.MISSING_IN_TARGET);
		writeToFile(line);
	}

	@Override
	public void addTypeMismatch(File sourceFile, File targetFile) {
		String line = generateOutputLine(sourceFile, targetFile, CONFLICT_TYPE.TYPE_MISMATCH);
		writeToFile(line);
	}

	@Override
	public void addContentMismatch(File sourceFile, File targetFile) {
		String line = generateOutputLine(sourceFile, targetFile, CONFLICT_TYPE.CONTENT_MISMATCH);
		writeToFile(line);

	}

	private void writeToFile(String line) {
		if (line == null) {
			return;
		}
		try {
			fos.write(line.getBytes());
			fos.flush();
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e.getLocalizedMessage());
		}
	}

	private String generateOutputLine(File sourceFile, File targetFile, CONFLICT_TYPE type) {
		StringBuilder sb = new StringBuilder();
		if (sourceFile != null) {
			sb.append(sourceFile.getAbsolutePath());
		} else {
			sb.append("");
		}
		sb.append(SEPARATOR);
		if (targetFile != null) {
			sb.append(targetFile.getAbsolutePath());
		} else {
			sb.append("");
		}
		sb.append(SEPARATOR);
		if (type != null) {
			sb.append(type.DisplayName());
		} else {
			sb.append("");
		}
		sb.append(System.lineSeparator());

		return sb.toString();
	}

	@Override
	public void finish() {
		try {
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e.getLocalizedMessage());
		}

	}

	public String getOutputFileName() {
		return outputFile.getAbsolutePath();
	}
}
