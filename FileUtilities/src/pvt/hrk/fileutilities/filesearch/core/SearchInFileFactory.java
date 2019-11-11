package pvt.hrk.fileutilities.filesearch.core;

import java.io.File;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import pvt.hrk.fileutilities.utils.StringSearchUtilities;

public class SearchInFileFactory implements Supplier<SearchInFileBase> {

	File file;
	String searchString;
	public SearchInFileFactory(File file,String searchString) {
		this.file = file;
		this.searchString = searchString;
	}
	
	@Override
	public SearchInFileBase get() {
		if (isZipFile.test(file.getAbsolutePath())) {
			return new SearchInZipFile(file, searchString);
		} else {
			return new SearchInTextFile(file, searchString);
		}
	}
	private static final Predicate<String> isZipFile = filePath -> {
		return Stream.of(new String[] { "zip", "jar" }).parallel()
				.anyMatch(s -> StringSearchUtilities.endsWithIgnoreCase.test(filePath, s));
	};
}
