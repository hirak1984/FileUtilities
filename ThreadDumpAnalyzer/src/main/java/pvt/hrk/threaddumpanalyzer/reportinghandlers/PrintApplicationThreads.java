package main.java.pvt.hrk.threaddumpanalyzer.reportinghandlers;

import java.io.OutputStream;
import java.util.List;

import main.java.pvt.hrk.threaddumpanalyzer.model.DumpFile;
import main.java.pvt.hrk.threaddumpanalyzer.model.DumpThread;

public class PrintApplicationThreads extends AbstractAnalyzer {

	public PrintApplicationThreads() {
		this(true);
	}

	public PrintApplicationThreads(boolean isEnabled) {
		super(isEnabled, "PrintApplicationThreadsAnalyzer");
	}

	@Override
	protected void doWork(List<DumpFile> dumpFiles, OutputStream os) throws Exception {
		for (DumpFile dumpFile : dumpFiles) {
			writeln(os, "Now parsing : " + dumpFile);
			List<DumpThread> threads = dumpFile.getThreads();
			for (DumpThread dumpThread : threads) {
				if (dumpThread.getStackTraceAsString().indexOf("com.guidewire") != -1) {
					writeln(os, "******************************");
					writeln(os, dumpThread.getStackTraceAsString());
					writeln(os, "******************************");
				}
			}
		}
	}

}
