package main.java.pvt.hrk.threaddumpanalyzer.parsinghandlers;

import java.time.LocalDateTime;
import java.util.List;

import main.java.pvt.hrk.threaddumpanalyzer.model.DumpThread;

public class JBossParsingHandler implements ParsingHandler{
private static final String dateFormat = "2019-12-11 13:07:17";
private static final String threadStartIdentifier="";
	@Override
	public boolean isStartOfStackTrace(String line) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DumpThread ummarshall(String[] stacktrace) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalDateTime extractDateIfPossible(List<String> lines) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String extractDescriptionIfPossible(List<String> lines) {
		// TODO Auto-generated method stub
		return null;
	}

}
