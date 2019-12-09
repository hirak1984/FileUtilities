package main.java.pvt.hrk.threaddumpanalyzer.parsinghandlers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.java.pvt.hrk.threaddumpanalyzer.model.DumpThread;
import main.java.pvt.hrk.threaddumpanalyzer.model.Pair;
import main.java.pvt.hrk.threaddumpanalyzer.utils.UtilityMethods;

public class JStackParsingHandler implements ParsingHandler {
	private static final String threadStateIdentifier = " +java\\.lang\\.Thread\\.State: ([^ ]+)(?: \\((.+)\\))?";
	private static final String threadStartIdentifier = "^\"(.*)\" (?:#[0-9]+ )?(daemon )?prio=([0-9]+) (?:os_prio=-?[0-9]+ )?tid=(0x[0-9a-f]+) nid=(0x[0-9a-f]+) (.*)$";
	private static final String dateFormatPattern = "yyyy-MM-dd HH:mm:ss";
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormatPattern);
	private static final Pattern threadStateIdentifierPattern = Pattern.compile(threadStateIdentifier);
	private static final Pattern threadStartIdentifierPattern = Pattern.compile(threadStartIdentifier);

	@Override
	public boolean isStartOfStackTrace(String line) {
		return line.matches(threadStartIdentifier);

	}

	@Override
	public DumpThread ummarshall(String[] stacktrace) {
		DumpThread thread = new DumpThread(Arrays.asList(stacktrace));
		Matcher threadStartMatcher = threadStartIdentifierPattern.matcher(stacktrace[0]);
		if (threadStartMatcher.matches()) {
			newDumpThread(thread, stacktrace[0], threadStartMatcher);
		} else {
			throw new RuntimeException(
					"Not a valid stacktrace : \n" + UtilityMethods.flatten.apply(Arrays.asList(stacktrace)));
		}
		Matcher stateMatcher = threadStateIdentifierPattern.matcher(stacktrace[1]);
		if (stateMatcher.matches()) {
			thread.setState(new Pair<String, String>(stateMatcher.group(1), stateMatcher.group(2)));
		} else {
			throw new RuntimeException(
					"Not a valid stacktrace : \n" + UtilityMethods.flatten.apply(Arrays.asList(stacktrace)));
		}
		return thread;
	}

	private static DumpThread newDumpThread(DumpThread thread, String line, Matcher matcher) {

		String threadName = matcher.group(1);
		thread.setThreadName(threadName);
		boolean daemon = matcher.group(2) != null;
		thread.setDaemon(Optional.ofNullable(daemon));
		int priority = Integer.parseInt(matcher.group(3));
		thread.setPriority(Optional.ofNullable(priority));
		long tid = Long.parseLong(matcher.group(4).substring(2), 16);
		thread.setTid(Optional.ofNullable(tid));
		long nid = Long.parseLong(matcher.group(5).substring(2), 16);
		thread.setNid(Optional.ofNullable(nid));
		String status = matcher.group(6);
		thread.setMessage(Optional.ofNullable(status));
		return thread;
	}

	@Override
	public LocalDateTime extractDateIfPossible(List<String> lines) {
		String firstLine = lines.get(0);
		try {
			return LocalDateTime.parse(firstLine, formatter);
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public String extractDescriptionIfPossible(List<String> lines) {
		if(extractDateIfPossible(lines)==null) {
			return lines.get(1);
		}else {
			return lines.get(0);
		}
	}

}
