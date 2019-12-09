package main.java.pvt.hrk.threaddumpanalyzer.model;

import java.util.List;
import java.util.Optional;

import main.java.pvt.hrk.threaddumpanalyzer.utils.UtilityMethods;

public class DumpThread {

	/**
	 * Mandatory properties
	 */
	private List<String> stackTrace;
	private String stackTraceAsString;
	private String threadName;
	private Pair<String, String> state;

	/**
	 * properties that could vary by jvm
	 */
	private Optional<Boolean> daemon;
	private Optional<Integer> priority;
	private Optional<Long> tid;
	private Optional<Long> nid;
	private Optional<String> message;
	private Optional<String> identifier;

	public DumpThread(List<String> stackTrace) {
		super();
		this.stackTrace = stackTrace;
	}

	public List<String> getStackTrace() {
		return stackTrace;
	}

	public String getStackTraceAsString() {
		if (stackTraceAsString == null) {
			stackTraceAsString = UtilityMethods.flatten.apply(stackTrace);
		}
		return stackTraceAsString;
	}

	public void setStackTraceAsString(String stackTraceAsString) {
		this.stackTraceAsString = stackTraceAsString;
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public Pair<String, String> getState() {
		return state;
	}

	public void setState(Pair<String, String> state) {
		this.state = state;
	}

	public Optional<Boolean> getDaemon() {
		return daemon;
	}

	public void setDaemon(Optional<Boolean> daemon) {
		this.daemon = daemon;
	}

	public Optional<Integer> getPriority() {
		return priority;
	}

	public void setPriority(Optional<Integer> priority) {
		this.priority = priority;
	}

	public Optional<Long> getTid() {
		return tid;
	}

	public void setTid(Optional<Long> tid) {
		this.tid = tid;
	}

	public Optional<Long> getNid() {
		return nid;
	}

	public void setNid(Optional<Long> nid) {
		this.nid = nid;
	}

	public Optional<String> getMessage() {
		return message;
	}

	public void setMessage(Optional<String> message) {
		this.message = message;
	}

	public Optional<String> getIdentifier() {
		return identifier;
	}

	public void setIdentifier(Optional<String> identifier) {
		this.identifier = identifier;
	}

}
