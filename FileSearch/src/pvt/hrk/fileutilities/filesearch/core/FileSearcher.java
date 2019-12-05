package pvt.hrk.fileutilities.filesearch.core;

import static pvt.hrk.fileutilities.utils.ObjectUtils.isNullOrEmpty;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import pvt.hrk.fileutilities.utils.ObjectUtils;

public class FileSearcher {
	public static void search(File source, String searchStr, FileFilter filter, FileSearchResultContainer results,
			BiConsumer<File, Throwable> handleIgnorableExceptions) {
		search(source, searchStr, filter, results, handleIgnorableExceptions,false);
	}
	

	public static void search(File source, String searchStr, FileFilter filter, FileSearchResultContainer results,
			BiConsumer<File, Throwable> handleIgnorableExceptions, boolean searchInNameOnly) {
		if(ObjectUtils.isZipFile.test(source.getName())) {
			searchInZipFile(source, searchStr, filter, results, handleIgnorableExceptions, searchInNameOnly);
		}else {
			searchInDirOrTextFile(source, searchStr, filter, results, handleIgnorableExceptions, searchInNameOnly);
		}
	}
	  private static Map getEntries(ZipFile zf) {
		    Enumeration e = zf.entries();
		    Map<String,ZipEntry> m = new HashMap<>();
		    while (e.hasMoreElements()) {
		      ZipEntry ze = (ZipEntry) e.nextElement();
		      m.put(ze.getName(), ze);
		    }
		    return m;
		  }
	public static void searchInZipFile(File source, String searchStr, FileFilter filter, FileSearchResultContainer results,
			BiConsumer<File, Throwable> handleIgnorableExceptions, boolean searchInNameOnly) {

		try (ZipFile f= new ZipFile(source) ){
			 Map fEntries = getEntries(f);
			 

		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	public static void searchInDirOrTextFile(File source, String searchStr, FileFilter filter, FileSearchResultContainer results,
			BiConsumer<File, Throwable> handleIgnorableExceptions, boolean searchInNameOnly) {
		boolean hasBeenVisitedbefore = results.hasBeenVisitedBefore(source);
		if (LOGGER.isLoggable(Level.FINE)) {
			LOGGER.fine("Now processing : " + source);
			LOGGER.fine("Has been visited before? " + hasBeenVisitedbefore);
		}
		if (!hasBeenVisitedbefore) {
			if (source.isDirectory()) {
				// If directory, traverse recursively
				List<File> files = Arrays.asList(Optional.ofNullable(source.listFiles(filter)).orElse(new File[0]));

				if (!isNullOrEmpty(files)) {
					files.parallelStream().forEach(file -> {
						search(file, searchStr, filter, results, handleIgnorableExceptions);
					});
				}
			} else if (source.isFile()) {
				try {
					SearchInFileBase searchInFile = new SearchInFileFactory(source, searchStr).get();
					if (searchInFile.foundInName()) {
						results.addToResults(source);
					}else if(!searchInNameOnly && searchInFile.foundInContents()) {
						results.addToResults(source);
					} else {
						// this file doesn't contain the search string in name or contents
					}
				} catch (Exception e) {
					handleIgnorableExceptions.accept(source, e);
				}
			} else {
				if (LOGGER.isLoggable(Level.FINE)) {
					LOGGER.fine("File type not supported. Ignoring." + source);
				}
			}
		}
	}

	// Input section end
	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

}
