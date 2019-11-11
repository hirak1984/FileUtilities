package pvt.hrk.fileutilities.filesearch.main;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SearchInTextFile extends SearchInFileBase {

	public SearchInTextFile(File file, String searchString) {
		super(file, searchString);
	}

	@Override
	protected boolean searchContents() throws IOException {
		if (match.test(file.getAbsolutePath())) {
			return true;
		} else {
			try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
				if (bufferedReader.lines().parallel().anyMatch(match)) {
					return true;
				}
			}
		}
		return false;
	}
}
