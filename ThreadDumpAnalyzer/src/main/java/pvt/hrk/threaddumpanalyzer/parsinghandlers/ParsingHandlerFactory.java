package main.java.pvt.hrk.threaddumpanalyzer.parsinghandlers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ParsingHandlerFactory {

	private static Map<String,ParsingHandler> handlerMap;
	static {
		handlerMap = new HashMap<>();
		handlerMap.put("jstack", new JStackParsingHandler());
	}
	
	public static ParsingHandler Handler(String identifier) {
		return handlerMap.get(identifier);
	}
	public static Set<String> getKeys(){
		return Collections.unmodifiableSet(handlerMap.keySet());
	}
}
