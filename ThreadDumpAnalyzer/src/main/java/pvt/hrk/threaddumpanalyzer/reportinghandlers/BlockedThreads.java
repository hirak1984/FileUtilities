package main.java.pvt.hrk.threaddumpanalyzer.reportinghandlers;

import java.io.OutputStream;
import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import main.java.pvt.hrk.threaddumpanalyzer.model.DumpFile;
import main.java.pvt.hrk.threaddumpanalyzer.model.Pair;

public class BlockedThreads extends AbstractAnalyzer {

	public BlockedThreads() {
		this(true);
	}

	public BlockedThreads(boolean isEnabled) {
		super(isEnabled, "BlockedThreadsAnalyzer");
	}

	@Override
	protected void doWork(List<DumpFile> dumpFiles, OutputStream os) throws Exception {
		Map<String, List<String>> blockedThreadFileNameMap = new HashMap<>();
		dumpFiles.forEach(dumpFile -> {
			dumpFile.getThreads().forEach(dumpThread -> {
				Pair<String,String> state = dumpThread.getState().orElse(null);
				if (state!=null && state.getKey().equals(State.BLOCKED.name()) ){
					List<String> values = blockedThreadFileNameMap.get(dumpThread.getStackTraceAsString());
					if (values == null) {
						values = new ArrayList<>();
					}
					values.add(dumpFile.getFileName());
					blockedThreadFileNameMap.put(dumpThread.getStackTraceAsString(), values);
				}
			});
		});
		Iterator<String> it = blockedThreadFileNameMap.keySet().iterator();
		while(it.hasNext()) {
			String stackTraceAsString = it.next();
			writeln(os, stackTraceAsString);
			writeln(os, "In files::"+blockedThreadFileNameMap.get(stackTraceAsString));
			writeln(os, "*********************************");
		}
	}

}
