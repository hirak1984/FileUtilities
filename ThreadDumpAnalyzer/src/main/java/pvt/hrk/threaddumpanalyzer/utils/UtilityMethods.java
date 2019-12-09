package main.java.pvt.hrk.threaddumpanalyzer.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface UtilityMethods {

	Predicate<String> isNotEmpty = line-> line!=null && line.replaceAll("\n", "").replaceAll("\r", "").trim().length()>0;
	Predicate<String> isEmpty = line-> !isNotEmpty.test(line);
	Function<Collection<String>,String> flatten= (c)->{
			StringBuilder sb = new StringBuilder(c.size());
			c.forEach(s -> {
				sb.append(s).append(System.lineSeparator());
			});
			return sb.toString();
		};
		
		Function<Path, List<String>> readAllNonEmptyLines = p->{
			try {
				return Files.lines(p).filter(isNotEmpty).collect(Collectors.toList());
			} catch (IOException e) {
				e.printStackTrace();
				return Collections.<String>emptyList();
			}
		};
	
}
