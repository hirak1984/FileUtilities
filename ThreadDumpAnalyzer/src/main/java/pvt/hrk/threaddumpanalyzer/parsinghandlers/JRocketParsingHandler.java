package main.java.pvt.hrk.threaddumpanalyzer.parsinghandlers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import main.java.pvt.hrk.threaddumpanalyzer.model.DumpThread;

public class JRocketParsingHandler implements ParsingHandler {

	private static final String dateFormatPattern = "EEE MMM dd HH:mm:ss yyyy";
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormatPattern);

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
		String datetime = lines.get(1);

		try {
			return LocalDateTime.parse(datetime, formatter);
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public String extractDescriptionIfPossible(List<String> lines) {
		return lines.get(2);

	}

}
