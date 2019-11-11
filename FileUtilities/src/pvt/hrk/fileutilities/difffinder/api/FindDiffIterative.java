package pvt.hrk.fileutilities.difffinder.api;

import static pvt.hrk.fileutilities.difffinder.api.other.Businessutils._fileNameFileMap;

import java.io.File;
import java.io.FileFilter;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import pvt.hrk.fileutilities.difffinder.api.handlers.ResultHandler;
import pvt.hrk.fileutilities.difffinder.api.other.Businessutils;
import pvt.hrk.fileutilities.difffinder.api.other.DiffFinderUtils;

public class FindDiffIterative {
	 private static final Logger LOGGER = Logger.getLogger(FindDiffIterative.class
	            .getClass().getName());

	public static void findDiff(File source, File target, FileFilter filter,ResultHandler handler) {

		FileIterator sourceIterator = new FileIterator(source);
		FileIterator targetIterator = new FileIterator(target);
		while (sourceIterator.hasNext() && targetIterator.hasNext()) {
			File src = sourceIterator.next();
			File tar = targetIterator.next();
			if(LOGGER.isLoggable(Level.FINE)) {
				LOGGER.fine("Now processing -  source = "+src.getAbsolutePath()+" , target="+tar.getAbsolutePath());
			}
			if (src.isDirectory() && tar.isDirectory()) {
				Map<String, File> fileMap1 = _fileNameFileMap(src.listFiles(filter));
				Map<String, File> fileMap2 = _fileNameFileMap(tar.listFiles(filter));

				Set<String> commonFilesByNames = DiffFinderUtils.findIntersection(fileMap1.keySet(), fileMap2.keySet());
				commonFilesByNames.stream().forEach(commonFileName -> {
					File f1 = fileMap1.get(commonFileName);
					File f2 = fileMap2.get(commonFileName);
					sourceIterator.addToIterator(f1);
					targetIterator.addToIterator(f2);
				});

				fileMap1.keySet().removeIf(commonFilesByNames::contains);
				fileMap2.keySet().removeIf(commonFilesByNames::contains);

				fileMap1.forEach((k, v) -> handler.addMissingInTarget(v));
				fileMap2.forEach((k, v) -> handler.addMissingInSource(v));

			} else if (src.isFile() && tar.isFile()) {
				if (!Businessutils.areContentsEqual(src, tar)) {
					handler.addContentMismatch(src,tar);
				} else {
					// do nothing. the file paths and contents are both matching.
				}

			} else {
				handler.addTypeMismatch(src,tar);
			}
		}
	}
}
