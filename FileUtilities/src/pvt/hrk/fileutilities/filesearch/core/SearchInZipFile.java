package pvt.hrk.fileutilities.filesearch.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class SearchInZipFile extends SearchInFileBase {

	public SearchInZipFile(File file, String searchString) {
		super(file, searchString);
	}

	@Override
	public boolean foundInContents() throws IOException {
		try (ZipFile zf = new ZipFile(file)) {
			Enumeration<? extends ZipEntry> e = zf.entries();
			while (e.hasMoreElements()) {
				ZipEntry entry = e.nextElement();
				if (!entry.isDirectory()) {
					if (match.test(entry.getName())) {
						return true;
					} else {
						try (InputStream inputStream = zf.getInputStream(entry);
								BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
							return reader.lines().parallel().anyMatch(match);
						}
					}
				}
			}
		}
		return false;
	}

}
