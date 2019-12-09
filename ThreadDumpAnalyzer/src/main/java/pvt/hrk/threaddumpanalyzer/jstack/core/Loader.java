package main.java.pvt.hrk.threaddumpanalyzer.jstack.core;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;

import main.java.pvt.hrk.threaddumpanalyzer.model.DumpFile;
import main.java.pvt.hrk.threaddumpanalyzer.model.DumpThread;
import main.java.pvt.hrk.threaddumpanalyzer.parsinghandlers.JStackParsingHandler;
import main.java.pvt.hrk.threaddumpanalyzer.parsinghandlers.ParsingHandler;
import main.java.pvt.hrk.threaddumpanalyzer.propertyhandlers.AvailableProperties;
import main.java.pvt.hrk.threaddumpanalyzer.utils.UtilityMethods;

public class Loader {

	public static List<DumpFile> load() {
		File source = AvailableProperties.INSTANCE.SOURCE();
		File[] threadDumpFiles = null;
		if (source.isFile()) {
			threadDumpFiles = new File[] { source };
		} else {
			threadDumpFiles = source.listFiles(file -> file.isFile());
		}

		List<DumpFile> dumpFiles = Arrays.stream(threadDumpFiles).parallel().map(Loader::parseFile)
				.collect(Collectors.toList());
		return dumpFiles;
	}

	private static DumpFile parseFile(File file) {
		String dumpName = file.getName();
		List<String> lines = UtilityMethods.readAllNonEmptyLines.apply(file.toPath());
		ParsingHandler handler =AvailableProperties.INSTANCE.PARSER();
		return parse(dumpName, lines, handler);
	}

	private static DumpFile parse(String dumpName, List<String> lines, ParsingHandler handler) {
		DumpFile dump = new DumpFile(dumpName);
		dump.setDateTime(Optional.ofNullable(handler.extractDateIfPossible(lines)));
		dump.setDescription(Optional.ofNullable(handler.extractDescriptionIfPossible(lines)));

		Queue<String> stackTrace = null;
		int totalLines = lines.size();
		for (int i = 0; i < totalLines; i++) {
			String line = lines.get(i);
			if (UtilityMethods.isEmpty.test(line)) {
				continue;
			}

			if (handler.isStartOfStackTrace(line)) {
				if (stackTrace == null || stackTrace.isEmpty()) {
					stackTrace = new LinkedList<>();
				} else {
					// load the previous stacktrace
					DumpThread thread = handler.ummarshall(stackTrace.toArray(new String[0]));
					dump.addTo(thread);
					stackTrace.clear();
				}
			}

			if (stackTrace != null) {
				stackTrace.offer(line);
			} else {
				dump.addToLinesIgnored(line);
			}

		}

		return dump;
	}
}
