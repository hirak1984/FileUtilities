package main.java.pvt.hrk.threaddumpanalyzer.jstack.main;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import main.java.pvt.hrk.threaddumpanalyzer.database.TransactionAwareDatabaseServiceImpl;
import main.java.pvt.hrk.threaddumpanalyzer.jstack.core.Analyzer;
import main.java.pvt.hrk.threaddumpanalyzer.jstack.core.Parser;
import main.java.pvt.hrk.threaddumpanalyzer.model.DumpFile;
import main.java.pvt.hrk.threaddumpanalyzer.propertyhandlers.AvailableProperties;
import main.java.pvt.hrk.threaddumpanalyzer.propertyhandlers.PropertyHolder;

public class Main {

	public static void main(String[] args){
		try(ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("appcontext.xml")){
			PropertyHolder propertyHolder = (PropertyHolder) ctx.getBean("propertyHolder");
			AvailableProperties.INSTANCE.load(propertyHolder);
			List<DumpFile> dumpFiles = Parser.parseFiles();
			Analyzer.analyze(dumpFiles);
			TransactionAwareDatabaseServiceImpl dbService = (TransactionAwareDatabaseServiceImpl) ctx.getBean("dbService");
			int transactionId = dbService.insertRecordsIntoDatabase(dumpFiles);
			System.out.println("**************************************************************************************************************");
			System.out.println("Transaction id : "+transactionId);
			System.out.println("Query : ");
			System.out.println("select * from Dump_File dfile,Dump_Thread dthread where dfile.id=dthread.dumpfileid and dfile.transactionid="+transactionId);
			System.out.println("**************************************************************************************************************");
			
		}catch (Exception e) {
		e.printStackTrace();
		}
	}

}
