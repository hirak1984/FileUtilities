package main.java.pvt.hrk.threaddumpanalyzer.database;

import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import main.java.pvt.hrk.threaddumpanalyzer.model.DumpFile;
import main.java.pvt.hrk.threaddumpanalyzer.model.DumpThread;
import main.java.pvt.hrk.threaddumpanalyzer.utils.UtilityMethods;

public class DatabaseHandler {
	private static final String DumpFile_Insert = "insert into Dump_File (fileName,dateTime,description,linesIgnored,transactionid,id) values (?,?,?,?,?,?)";
	private static final String DumpThread_Insert = "insert into Dump_Thread (stackTrace,threadName,state,daemon,priority,tid,nid,message,identifier,id,dumpfileid) values (?,?,?,?,?,?,?,?,?,?,?)";
	private static final String SELECT_MAX = "select max(COLUMN_NAME) from TABLE_NAME";
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private void insert(DumpFile record, int transactionId) {
		List<DumpThread> threads = record.getThreads();
		if (threads == null || threads.size() == 0) {
			throw new IllegalStateException("DumpThreads list is empty for this DumpFile");
		}
		String fileName = record.getFileName();
		java.sql.Timestamp dateTime = record.getDateTime().map(UtilityMethods.convertToSQLDateTime).orElse(null);
		String description = record.getDescription().orElse(null);
		String linesIgnored = UtilityMethods.flatten.apply(record.getLinesIgnored());
		Integer transactionid = transactionId;
		Integer dumpFileId = getNext("Dump_File", "id");

		jdbcTemplate.update(DumpFile_Insert, fileName, dateTime, description, linesIgnored, transactionid, dumpFileId);
		int threadid = getNext("Dump_Thread", "id");
		for (DumpThread thread : threads) {
			if (thread.getState() == null) {
				System.out.println(thread);
			}
			String stackTrace = UtilityMethods.flatten.apply(thread.getStackTrace());
			String threadName = thread.getThreadName();
			String state = thread.getState().map(pair -> pair.getKey()).orElse(null);
			Boolean daemon = thread.getDaemon().orElse(null);
			Integer priority = thread.getPriority().orElse(null);
			Long tid = thread.getTid().orElse(null);
			Long nid = thread.getNid().orElse(null);
			String message = thread.getMessage().orElse(null);
			String identifier = thread.getIdentifier().orElse(null);
			jdbcTemplate.update(DumpThread_Insert, stackTrace, threadName, state, daemon, priority, tid, nid, message,
					identifier, threadid++, dumpFileId);
		}
	}

	public int insertAll(List<DumpFile> records) {
		int transactionId = getNext("Dump_File", "transactionid");
		records.forEach(record -> insert(record, transactionId));
		return transactionId;
	}

	private static String createMaxQuery(String tableName, String columnName) {
		return SELECT_MAX.replace("TABLE_NAME", tableName).replace("COLUMN_NAME", columnName);
	}

	private int getNext(String tableName, String columnName) {
		int id = Optional
				.ofNullable(jdbcTemplate.<Integer>queryForObject(createMaxQuery(tableName, columnName), Integer.class))
				.map(r -> r + 1).orElse(0);
		return id;
	}

}
