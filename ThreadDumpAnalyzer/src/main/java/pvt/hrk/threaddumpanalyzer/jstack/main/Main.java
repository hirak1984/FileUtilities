package main.java.pvt.hrk.threaddumpanalyzer.jstack.main;

import java.util.List;

import org.springframework.context.support.FileSystemXmlApplicationContext;

import main.java.pvt.hrk.threaddumpanalyzer.database.TransactionAwareDatabaseServiceImpl;
import main.java.pvt.hrk.threaddumpanalyzer.jstack.core.Analyzer;
import main.java.pvt.hrk.threaddumpanalyzer.jstack.core.Loader;
import main.java.pvt.hrk.threaddumpanalyzer.model.DumpFile;
import main.java.pvt.hrk.threaddumpanalyzer.propertyhandlers.AvailableProperties;
import main.java.pvt.hrk.threaddumpanalyzer.propertyhandlers.PropertyHolder;

public class Main {

	public static void main(String[] args){
		try(FileSystemXmlApplicationContext ctx = new FileSystemXmlApplicationContext("C:\\Users\\hchatterjee\\git\\FileUtilities\\ThreadDumpAnalyzer\\src\\main\\java\\pvt\\hrk\\threaddumpanalyzer\\jstack\\resources\\applicationContext.xml")){
			PropertyHolder propertyHolder = (PropertyHolder) ctx.getBean("propertyHolder");
			AvailableProperties.INSTANCE.load(propertyHolder);
			TransactionAwareDatabaseServiceImpl dbService = (TransactionAwareDatabaseServiceImpl) ctx.getBean("dbService");
			List<DumpFile> dumpFiles = Loader.load();
			int transactionId = dbService.insertAll(dumpFiles);
			System.out.println(transactionId);
			Analyzer.analyze(dumpFiles);
			
		}catch (Exception e) {
		e.printStackTrace();
		}
	}

}
