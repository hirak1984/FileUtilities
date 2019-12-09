package main.java.pvt.hrk.threaddumpanalyzer.reportinghandlers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Consumer;

import main.java.pvt.hrk.threaddumpanalyzer.model.DumpFile;
import main.java.pvt.hrk.threaddumpanalyzer.propertyhandlers.AvailableProperties;

public abstract class AbstractAnalyzer implements Consumer<List<DumpFile>> {

	private boolean isEnabled;
	private String analyzerName;

	public AbstractAnalyzer(boolean isEnabled, String analyzerName) {
		super();
		this.isEnabled = isEnabled;
		this.analyzerName = analyzerName;

	}

	protected abstract void doWork(List<DumpFile> dumpFiles, OutputStream os) throws Exception;

	@Override
	public void accept(List<DumpFile> dumpFiles) {
		if (isEnabled) {
			File file = new File(AvailableProperties.INSTANCE.REPORTS_DIRECTORY(), analyzerName + ".log");
			System.out.println("Analyzer : " + analyzerName + ", location: "+file.getAbsolutePath());
			try (FileOutputStream fos = new FileOutputStream(file)) {
				doWork(dumpFiles, fos);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Skipping : " + analyzerName + " as it's disabled");
		}

	}

	public void writeln(OutputStream os, String line) throws UnsupportedEncodingException, IOException {
		os.write(line.getBytes(StandardCharsets.UTF_8));
		os.write(System.lineSeparator().getBytes(StandardCharsets.UTF_8));
	}

}
