package main.java.pvt.hrk.threaddumpanalyzer.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

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
				 List<String> contents = FileUtils.readLines(p.toFile(), "UTF-8").stream().filter(isNotEmpty).collect(Collectors.toList());
				 return contents;
			} catch (IOException e) {
				e.printStackTrace();
				return Collections.<String>emptyList();
			}
		};
		BiConsumer<String, File> writeStringToFile= (data,file)->{
			try {
				FileUtils.writeStringToFile(file, data, "UTF-8", false);
			} catch (IOException e) {
				e.printStackTrace();
			}
		};
	
}
