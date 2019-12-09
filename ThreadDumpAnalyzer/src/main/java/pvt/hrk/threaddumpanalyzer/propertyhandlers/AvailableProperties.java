package main.java.pvt.hrk.threaddumpanalyzer.propertyhandlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Properties;

import main.java.pvt.hrk.threaddumpanalyzer.parsinghandlers.ParsingHandler;
import main.java.pvt.hrk.threaddumpanalyzer.parsinghandlers.ParsingHandlerFactory;

public enum AvailableProperties {
	INSTANCE;
	Properties properties;

	public void init(String propertiesFilePath) throws IOException {
		properties = new Properties();
		try {
			if (propertiesFilePath != null) {
				properties.load(new FileInputStream(propertiesFilePath));
			} else {
				InputStream is = getClass().getResourceAsStream("threaddumpanalyzer.properties");
				properties.load(is);
			}
		} catch (IOException e) {
			throw e;
		}
	}

	private String getProperty(String key) {
		if (properties == null) {
			throw new IllegalStateException(
					"Properties not initialized. \"init\" method must be invoked before using the properties.");
		}
		return Optional.ofNullable(properties.getProperty(key))
				.orElseThrow(() -> new IllegalArgumentException("Propery not found : " + key));

	}

	public File SOURCE() {
		String sourceFileOrDir = getProperty("source_file_or_dir");
		return new File(sourceFileOrDir);
	}

	public File REPORTS_DIRECTORY() {
		String reportsDir = getProperty("reports_dir");
		File reportsDirectory = new File(reportsDir);
		reportsDirectory.mkdirs();
		return reportsDirectory;
	}

	public ParsingHandler PARSER() {
		String parserName = getProperty("parser");
		return ParsingHandlerFactory.Handler(parserName);
	}

}
