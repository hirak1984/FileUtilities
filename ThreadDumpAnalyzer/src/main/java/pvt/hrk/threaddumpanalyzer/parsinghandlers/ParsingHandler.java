package main.java.pvt.hrk.threaddumpanalyzer.parsinghandlers;

import java.time.LocalDateTime;
import java.util.List;

import main.java.pvt.hrk.threaddumpanalyzer.model.DumpThread;

public interface ParsingHandler {
	
	boolean isStartOfStackTrace(String line);
	DumpThread ummarshall(String[] stacktrace);
	
	LocalDateTime extractDateIfPossible(List<String> lines);
	String extractDescriptionIfPossible(List<String> lines);
}
