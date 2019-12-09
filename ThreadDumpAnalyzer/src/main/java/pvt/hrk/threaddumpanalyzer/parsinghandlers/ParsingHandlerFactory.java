package main.java.pvt.hrk.threaddumpanalyzer.parsinghandlers;

public class ParsingHandlerFactory {

	public static ParsingHandler Handler(String identifier) {
		switch(identifier) {
		case "jstack": return new JStackParsingHandler();
		}
		return null;
	}
}
