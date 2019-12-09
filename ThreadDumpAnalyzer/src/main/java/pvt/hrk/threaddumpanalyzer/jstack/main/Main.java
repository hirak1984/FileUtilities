package main.java.pvt.hrk.threaddumpanalyzer.jstack.main;

import java.io.IOException;
import java.util.List;

import main.java.pvt.hrk.threaddumpanalyzer.jstack.core.Loader;
import main.java.pvt.hrk.threaddumpanalyzer.jstack.core.Analyzer;
import main.java.pvt.hrk.threaddumpanalyzer.model.DumpFile;
import main.java.pvt.hrk.threaddumpanalyzer.propertyhandlers.AvailableProperties;

public class Main {
	//public static final File ThreadDumpSourceFileOrDir = new File("C:\\tmp\\performance_analysis\\Thread_0404_7PM");
	//public static final File AnalysisReportsFolder = new File(ThreadDumpSourceFileOrDir,"reports");

	public static void main(String[] args) throws IOException {
		if(args==null || args.length==0) {
			AvailableProperties.INSTANCE.init(null);
		}else {
			AvailableProperties.INSTANCE.init(args[0]);	
		}
		
		List<DumpFile> dumpFiles = Loader.load();
		Analyzer.analyze(dumpFiles);
	}

}
