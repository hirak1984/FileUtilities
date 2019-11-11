package pvt.hrk.fileutilities.difffinder.api.other;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class DiffFinderUtils {

	public static <T> boolean isNullOrEmpty(Collection<T> col) {
		return col == null || col.isEmpty();
	}

	public static <T> boolean isNullOrEmpty(T[] arr) {
		return arr == null || arr.length == 0;
	}

	public static <T> Set<T> convertToSet(T[] array) {
		return new HashSet<T>(Arrays.asList(array));
	}

	public static <T> Set<T> findIntersection(Set<T> set1, Set<T> set2) {
		Set<T> commonFilesByName = new HashSet<>();
		Iterator<T> it = set1.iterator();
		while (it.hasNext()) {
			T t = it.next();
			if (set2.stream().anyMatch(s2 -> s2.equals(t))) {
				commonFilesByName.add(t);
			}
		}
		return commonFilesByName;
	}

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

	public static String MD5HashFile(File file) throws NoSuchAlgorithmException, IOException {
		byte[] buf = ChecksumFile(file);
		String res = "";
		for (int i = 0; i < buf.length; i++) {
			res += Integer.toString((buf[i] & 0xff) + 0x100, 16).substring(1);
		}
		return res;
	}

	public static byte[] ChecksumFile(File file) throws NoSuchAlgorithmException, IOException {
		InputStream fis = new FileInputStream(file);
		byte[] buf = new byte[1024];
		MessageDigest complete = MessageDigest.getInstance("MD5");
		int n;
		do {
			n = fis.read(buf);
			if (n > 0) {
				complete.update(buf, 0, n);
			}
		} while (n != -1);
		fis.close();
		return complete.digest();
	}
}
