package main.java.pvt.hrk.threaddumpanalyzer.propertyhandlers;

import java.io.File;

import main.java.pvt.hrk.threaddumpanalyzer.parsinghandlers.ParsingHandler;
import main.java.pvt.hrk.threaddumpanalyzer.parsinghandlers.ParsingHandlerFactory;

public enum AvailableProperties {
	INSTANCE;
	private File sourceFileOrDir;
	private File reportsDir;
	private ParsingHandler parsingHandler;
	
public void load(PropertyHolder holder) {
	this.sourceFileOrDir = new File(holder.getSourceFileOrDir());
	if(!sourceFileOrDir.isFile() && !sourceFileOrDir.isDirectory()) {
		throw new IllegalArgumentException("Source not a valid file or directory : "+sourceFileOrDir.getAbsolutePath());
	}
	this.reportsDir = new File(holder.getReportsDir());
	this.reportsDir.mkdirs();
	if(!reportsDir.isDirectory()) {
		throw new IllegalArgumentException("Reports direcotry invalid : "+reportsDir.getAbsolutePath());
	}
	this.parsingHandler = ParsingHandlerFactory.Handler(holder.getParser());
	if(parsingHandler==null) {
		throw new IllegalArgumentException("No parsing handler found for :"+holder.getParser()+". Supported names are : "+ParsingHandlerFactory.getKeys());
	}
	
}

public File SOURCE() {
	return sourceFileOrDir;
}
public File REPORT_DIR() {
	return reportsDir;
}  
public ParsingHandler PARSER() {
	return parsingHandler;
}
}
