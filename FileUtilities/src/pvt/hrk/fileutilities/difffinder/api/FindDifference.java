package pvt.hrk.fileutilities.difffinder.api;

import java.io.File;
import java.io.FileFilter;

import pvt.hrk.fileutilities.difffinder.api.handlers.ResultHandler;
import pvt.hrk.fileutilities.difffinder.api.other.MyFileFilterBuilder;

public class FindDifference {

	public static void findDifference(File dir1, File dir2,ResultHandler handler) {
		findDifference(dir1, dir2,new MyFileFilterBuilder().build(), handler);
	}
	public static void findDifference(File dir1, File dir2, FileFilter filter,ResultHandler handler) {
		validateInput(dir1, dir2);
		handler.init();
		//FindDiffRecursive.findDiff(dir1, dir2, handler);
		FindDiffIterative.findDiff(dir1, dir2,filter, handler);
		handler.finish();
	}

	private static void validateInput(File dir1, File dir2) {
		if (dir1 == null || !dir1.isDirectory())
			throw new IllegalArgumentException("input directory not valid -" + dir1);
		else if (dir2 == null || !dir2.isDirectory())
			throw new IllegalArgumentException("input directory not valid -" + dir1);
		else if (dir1.getAbsolutePath().equalsIgnoreCase(dir2.getAbsolutePath()))
			throw new IllegalArgumentException("both directories selected are same");
	}

	

}
