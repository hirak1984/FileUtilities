package pvt.hrk.fileutilities.filesearch.core;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SearchInTextFile extends SearchInFileBase {

	public SearchInTextFile(File file, String searchString) {
		super(file, searchString);
	}

	@Override
	public boolean foundInContents() throws IOException {
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			return bufferedReader.lines().parallel().anyMatch(match);
		}
	}
}
