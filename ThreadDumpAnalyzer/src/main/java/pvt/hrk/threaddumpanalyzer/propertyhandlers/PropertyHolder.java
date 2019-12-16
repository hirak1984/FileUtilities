package main.java.pvt.hrk.threaddumpanalyzer.propertyhandlers;

import java.io.File;

import main.java.pvt.hrk.threaddumpanalyzer.parsinghandlers.ParsingHandler;
import main.java.pvt.hrk.threaddumpanalyzer.parsinghandlers.ParsingHandlerFactory;

public class PropertyHolder {

	private String sourceFileOrDir;
	private String reportsDir;
	private String parser;

	public void setSourceFileOrDir(String sourceFileOrDir) {
		this.sourceFileOrDir = sourceFileOrDir;
	}

	public void setReportsDir(String reportsDir) {
		this.reportsDir = reportsDir;
	}

	public void setParser(String parser) {
		this.parser = parser;
	}

	public String getSourceFileOrDir() {
		return sourceFileOrDir;
	}

	public String getReportsDir() {
		return reportsDir;
	}

	public String getParser() {
		return parser;
	}

}
