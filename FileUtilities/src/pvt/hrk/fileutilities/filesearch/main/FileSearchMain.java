package pvt.hrk.fileutilities.filesearch.main;



import static pvt.hrk.fileutilities.utils.ObjectUtils.isNullOrEmpty;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import pvt.hrk.fileutilities.utils.StringSearchUtilities;

public class FileSearchMain {

	/**
	 * Input section
	 */
	private static final String searchString = "%VehicleIncident%";
	private static final String[] searchLocations = { "C:\\Users\\configuration" };
	private static final SearchModes searchMode = SearchModes.FileNameAndContent;
	private static final String[] ignorables = { "class", "png", "jpg", "jpeg", "pcfc", "gif", "ico", "dll", "lock",
			"mp4", "node", "gz", "DS_Store", "un~", "node_modules" };
	// Input section end

	public static void main(String[] args) {

		if (searchLocations != null) {
			Stream.of(searchLocations).forEach(searchLocation -> {
				File searchIn = new File(searchLocation);
				System.out.println("=====Searching in :-" + searchLocation + "=====");
				Set<String> results = new HashSet<>();
				try {
					search(searchIn, searchString, results);
				} catch (Exception e) {
					handleException(e, searchIn);
				}
				if (isNullOrEmpty(results)) {
					System.out.println("Nothing found");
				} else {
					results.forEach(System.out::println);
				}

			});
		} else {
			System.out.println("Nowhere to search");
		}
		System.out.println("Done");
	}

	private static void search(File source, String searchStr, Set<String> results) {
		if (source.isDirectory()) {
			// If directory, traverse recursively
			File[] files = source.listFiles((f) -> !(results.contains(f.getAbsolutePath()) || Stream.of(ignorables)
					.parallel().anyMatch(s -> StringSearchUtilities.endsWithIgnoreCase.test(f.getName(), s))));
			if (!isNullOrEmpty(files)) {
				Stream.of(files).parallel().forEach(file -> {
					search(file, searchStr, results);
				});
			}
		} else {
			if (!results.contains(source.getAbsolutePath())) {
				try {
					SearchInFileBase searchInFile = null;
					if (isZipFile.test(source.getAbsolutePath())) {
						searchInFile = new SearchInZipFile(source, searchStr);
					} else {
						searchInFile = new SearchInTextFile(source, searchStr);
					}
					switch (searchMode) {
					case FileNameOnly:
						if (searchInFile.searchInName()) {
							results.add(source.getAbsolutePath());
						}
						break;
					default:
						if (searchInFile.searchInName() || searchInFile.searchContents()) {
							results.add(source.getAbsolutePath());
						}
					}
				} catch (Exception e) {
					handleException(e, source);
				}
			}
		}
	}

	private static void handleException(Throwable t, File file) {
		 System.err.println("Error reading file:" + file.toPath());
		t.printStackTrace();
	}

	private static final Predicate<String> isZipFile = filePath -> {
		return Stream.of(new String[] { "zip", "jar" }).parallel()
				.anyMatch(s -> StringSearchUtilities.endsWithIgnoreCase.test(filePath, s));
	};

	enum SearchModes {
		FileNameOnly, FileNameAndContent;
	}
}
