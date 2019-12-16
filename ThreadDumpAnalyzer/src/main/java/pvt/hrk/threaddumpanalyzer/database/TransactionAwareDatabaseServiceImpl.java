package main.java.pvt.hrk.threaddumpanalyzer.database;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import main.java.pvt.hrk.threaddumpanalyzer.model.DumpFile;

public class TransactionAwareDatabaseServiceImpl{

	private DatabaseHandler databaseHandler;

	public void setDatabaseHandler(DatabaseHandler databaseHandler) {
		this.databaseHandler = databaseHandler;
	}

	@Transactional
	public int insertAll(List<DumpFile> dumpFiles) {
		int transactionId = databaseHandler.insertAll(dumpFiles);
		return transactionId;
		
	}

}
