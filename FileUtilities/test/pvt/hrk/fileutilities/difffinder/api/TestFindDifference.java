package pvt.hrk.fileutilities.difffinder.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pvt.hrk.fileutilities.difffinder.api.FindDifference;

class TestFindDifference {

	private static final File testFolder = new File("C:\\tmp\\test");
	private static final File srcDir = new File(testFolder, "dir1\\");
	private static final File tarDir = new File(testFolder, "dir2\\");
	private InMemoryResultHandler handler = new InMemoryResultHandler();

	@BeforeEach
	void setup() {
		testFolder.mkdirs();
		srcDir.mkdirs();
		tarDir.mkdirs();
	}
	@AfterEach
    void tearDown() throws IOException{
		Files.walk(srcDir.toPath())
        .map(Path::toFile)
        .sorted((o1, o2) -> -o1.compareTo(o2))
        .forEach(File::delete);
		
		Files.walk(tarDir.toPath())
        .map(Path::toFile)
        .sorted((o1, o2) -> -o1.compareTo(o2))
        .forEach(File::delete);

    }
	
	@Test
	void testMissingInSource() throws IOException {
		File file1 = new File(tarDir, "File1");
		Files.write(file1.toPath(), "Content1".getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
		FindDifference.findDifference(srcDir, tarDir, handler);
		Assertions.assertEquals(1, handler.missingInSrc.size());
	}

	@Test
	void testMissingInTarget() {
		File file1 = new File(srcDir, "File1");
		try {
			Files.write(file1.toPath(), "Content1".getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
			FindDifference.findDifference(srcDir, tarDir,handler);
			Assertions.assertEquals(1, handler.missingInTar.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testTypeMismatch() {
		File file1 = new File(srcDir, "File1");
		File dir2 = new File(tarDir, "File1");
		dir2.mkdir();
		try {
			Files.write(file1.toPath(), "Content1".getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
			FindDifference.findDifference(srcDir, tarDir,handler);
			Assertions.assertEquals(1, handler.typeMisMatch.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testContentMismatch() {
		File file1 = new File(srcDir, "File1");
		File file2 = new File(tarDir, "File1");

		try {
			Files.write(file1.toPath(), "Content1".getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
			Files.write(file2.toPath(), "Content2".getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
			FindDifference.findDifference(srcDir, tarDir, handler);
			Assertions.assertEquals(1, handler.contentMisMatch.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
