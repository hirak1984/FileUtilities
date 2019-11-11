package pvt.hrk.fileutilities.difffinder.api;

import static pvt.hrk.fileutilities.difffinder.api.other.Businessutils._fileNameFileMap;

import java.io.File;
import java.io.FileFilter;
import java.util.Map;
import java.util.Set;

import pvt.hrk.fileutilities.difffinder.api.handlers.ResultHandler;
import pvt.hrk.fileutilities.utils.ObjectUtils;
public class FindDiffRecursive {

	/**
	 * Initial directory names may not match, that's okay. Comparison starts from
	 * the children. No null check/validation done here, it expects that the input
	 * is sanitized. That's why this method must remain private.
	 * 
	 * @param dir1
	 * @param dir2
	 */
	public static void findDiff(File file1, File file2, FileFilter filter,ResultHandler handler) {
		// Initial directory names may not match, that's okay.

		if (file1.isDirectory() && file2.isDirectory()) {
			Map<String, File> fileMap1 = _fileNameFileMap(file1.listFiles(filter));
			Map<String, File> fileMap2 =_fileNameFileMap(file2.listFiles(filter));

			Set<String> commonFilesByNames = ObjectUtils.findIntersection(fileMap1.keySet(), fileMap2.keySet());
			commonFilesByNames.parallelStream().forEach(commonFileName -> {
				File f1 = fileMap1.get(commonFileName);
				File f2 = fileMap2.get(commonFileName);
				findDiff(f1, f2,filter, handler);
			});

			fileMap1.keySet().removeIf(commonFilesByNames::contains);
			fileMap2.keySet().removeIf(commonFilesByNames::contains);

			fileMap1.forEach((k, v) -> handler.addMissingInTarget(v));
			fileMap2.forEach((k, v) -> handler.addMissingInSource(v));

		} else if (file1.isFile() && file2.isFile()) {
			if (!ObjectUtils.areContentsEqual(file1, file2)) {
				handler.addContentMismatch(file1,file2);
			} else {
				// do nothing. the file paths and contents are both matching.
			}

		} else {
			handler.addTypeMismatch(file1,file2);
		}

	}
}
