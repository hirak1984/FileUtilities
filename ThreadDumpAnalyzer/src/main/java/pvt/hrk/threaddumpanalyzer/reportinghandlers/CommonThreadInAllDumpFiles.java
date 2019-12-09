package main.java.pvt.hrk.threaddumpanalyzer.reportinghandlers;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.java.pvt.hrk.threaddumpanalyzer.model.DumpFile;
import main.java.pvt.hrk.threaddumpanalyzer.model.DumpThread;

public class CommonThreadInAllDumpFiles extends AbstractAnalyzer {

	public CommonThreadInAllDumpFiles() {
		this(true);
	}

	public CommonThreadInAllDumpFiles(boolean isEnabled) {
		super(isEnabled, "CommonThreadInAllDumpFiles");
	}

	@Override
	protected void doWork(List<DumpFile> dumpFiles, OutputStream os) throws Exception {
		Map<String, List<String>> commonThreadMap = new HashMap<>();

		dumpFiles.forEach(dumpFile -> {
			dumpFile.getThreads().forEach(thread -> {
				List<String> fileNameList = commonThreadMap.get(thread.getThreadName());
				if (fileNameList == null) {
					fileNameList = new ArrayList<>();
				}
				fileNameList.add(dumpFile.getFileName());
				commonThreadMap.put(thread.getThreadName(), fileNameList);
			});
		});

		Iterator<String> it = commonThreadMap.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			List<String> values = commonThreadMap.get(key);
			if(values==null || values.size()==1) {
				continue;
			}
			writeln(os, "Thread = " + key + " found in files : " + values);
		}
	}

}
