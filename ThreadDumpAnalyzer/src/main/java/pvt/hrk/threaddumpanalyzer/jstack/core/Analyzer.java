package main.java.pvt.hrk.threaddumpanalyzer.jstack.core;

import java.util.List;
import java.util.function.Consumer;

import main.java.pvt.hrk.threaddumpanalyzer.model.DumpFile;
import main.java.pvt.hrk.threaddumpanalyzer.reportinghandlers.BlockedThreads;
import main.java.pvt.hrk.threaddumpanalyzer.reportinghandlers.CommonThreadInAllDumpFiles;
import main.java.pvt.hrk.threaddumpanalyzer.reportinghandlers.PrintApplicationThreads;

public class Analyzer {

	private static final Consumer<List<DumpFile>> consumers() {
		return new BlockedThreads().andThen(new PrintApplicationThreads())
				.andThen(new CommonThreadInAllDumpFiles());
	}

	public static void analyze(List<DumpFile> dumpFiles) {
		consumers().accept(dumpFiles);
	}
}
