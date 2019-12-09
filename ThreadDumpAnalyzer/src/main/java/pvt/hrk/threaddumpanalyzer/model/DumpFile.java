package main.java.pvt.hrk.threaddumpanalyzer.model;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class DumpFile {

	private String fileName;
	private Optional<LocalDateTime> dateTime;
	private Optional<String> description;
	private List<DumpThread> threads;
	private List<String> linesIgnored;

	public Optional<LocalDateTime> getDateTime() {
		return dateTime;
	}

	public void setDateTime(Optional<LocalDateTime> dateTime) {
		this.dateTime = dateTime;
	}

	public Optional<String> getDescription() {
		return description;
	}

	public void setDescription(Optional<String> description) {
		this.description = description;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setThreads(List<DumpThread> threads) {
		this.threads = threads;
	}

	public String getFileName() {
		return fileName;
	}

	public DumpFile(String fileName) {
		super();
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "Dump [fileName=" + fileName + ", dateTime=" + dateTime + ", description=" + description + "]";
	}

	public List<DumpThread> getThreads() {
		return threads;
	}
	
	public void addTo(DumpThread thread) {
		if(threads==null) {
			threads = new LinkedList<>();
		}
		threads.add(thread);
	}
	public void addToLinesIgnored(String line) {
		if(linesIgnored==null) {
			linesIgnored = new LinkedList<>();
		}
		linesIgnored.add(line);
	}

	public List<String> getLinesIgnored() {
		return linesIgnored;
	}

	public void setLinesIgnored(List<String> linesIgnored) {
		this.linesIgnored = linesIgnored;
	}
	
}
