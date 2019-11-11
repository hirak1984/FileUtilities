package pvt.hrk.fileutilities.difffinder.api.other;

import static pvt.hrk.fileutilities.utils.ObjectUtils.MD5HashFile;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class Businessutils {

	public static boolean areContentsEqual(File file1, File file2) {
		try {
			// return Arrays.equals(Files.readAllBytes(file1.toPath()),
			// Files.readAllBytes(file2.toPath()));
			return MD5HashFile(file1).equals(MD5HashFile(file2));
		} catch (IOException | NoSuchAlgorithmException e) {
			e.printStackTrace();
			return false;
		}
	}

	
	
	public static Map<String, File> _fileNameFileMap(File[] files) {
		
		return Arrays.stream(files).collect(Collector.of((Supplier<Map<String, File>>) HashMap::new,
		(BiConsumer<Map<String, File>, File>) (map, file) -> map.put(file.getName(), file), (left, right) -> {
			left.putAll(right);
			return left;
		}, Collector.Characteristics.CONCURRENT, Collector.Characteristics.UNORDERED));
		/*if(!DiffFinderUtils.isNullOrEmpty(files)) {
			Map<String, File> map = new HashMap<>(files.length);
			Arrays.stream(files).map(f -> map.put(f.getName(), f));
			return map;
		}
		return Collections.<String,File>emptyMap();
		*/
	}
	
}
