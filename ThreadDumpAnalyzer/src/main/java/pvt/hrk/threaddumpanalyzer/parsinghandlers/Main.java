package main.java.pvt.hrk.threaddumpanalyzer.parsinghandlers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {

	public static void main(String[] args) {
	LocalDateTime dt=LocalDateTime.now();
	System.out.println(dt.format(DateTimeFormatter.ofPattern("EEE")));
	}

}
