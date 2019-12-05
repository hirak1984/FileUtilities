package pvt.hrk.fileutilities.filesearch.core;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import pvt.hrk.fileutilities.filesearch.config.ConfigHolderSingleton;

public class ZipFileSearchHandler extends FileSearchHandler {

	public ZipFileSearchHandler(File file) {
		super(file);
	}

	@Override
	public boolean matchInContent(String searchString) {
		return false;
	}

	@Override
	public List<File> getChildren(FileFilter filter) {
		
		try (ZipFile zf = new ZipFile(file);) {
			Path dest = Files.createTempDirectory(ConfigHolderSingleton.INSTANCE.getTempDirectoryPath(),file.getName());
			Enumeration e = zf.entries();
			while (e.hasMoreElements()) {
				ZipEntry ze = (ZipEntry) e.nextElement();
				copyFileEntry(dest.toFile().getCanonicalPath(), zf, ze);
			}
			return Files.list(dest).map(path -> path.toFile()).filter(file->filter.accept(file)).collect(Collectors.toList());

		} catch (IOException e) {
			ConfigHolderSingleton.INSTANCE.handleException(file, e);
		}
		return super.getChildren(filter);
	}

	/**
	 * copy a single entry from the archive
	 * 
	 * @param destDir
	 * @param zf
	 * @param ze
	 * @throws IOException
	 */
	public void copyFileEntry(String destDir, ZipFile zf, ZipEntry ze) throws IOException {
		DataInputStream dis = new DataInputStream(new BufferedInputStream(zf.getInputStream(ze)));
		try {
			copyFileEntry(destDir, ze.isDirectory(), ze.getName(), dis);
		} finally {
			try {
				dis.close();
			} catch (IOException ioe) {
			}
		}
	}

	private void copyFileEntry(String destDir, boolean destIsDir, String destFile, DataInputStream dis)
			throws IOException {
		byte[] bytes = readAllBytes(dis);
		File file = new File(destFile);
		String parent = file.getParent();
		if (parent != null && parent.length() > 0) {
			File dir = new File(destDir, parent);
			if (dir != null) {
				dir.mkdirs();
			}
		}
		File outFile = new File(destDir, destFile);
		if (destIsDir) {
			outFile.mkdir();
		} else {
			FileOutputStream fos = new FileOutputStream(outFile);
			try {
				fos.write(bytes, 0, bytes.length);
			} finally {
				try {
					fos.close();
				} catch (IOException ioe) {
				}
			}
		}
	}

	// *** below may be slow for large files ***
	/** Read all the bytes in a ZIPed file */
	protected byte[] readAllBytes(DataInputStream is) throws IOException {
		byte[] bytes = new byte[0];
		for (int len = is.available(); len > 0; len = is.available()) {
			byte[] xbytes = new byte[len];
			int count = is.read(xbytes);
			if (count > 0) {
				byte[] nbytes = new byte[bytes.length + count];
				System.arraycopy(bytes, 0, nbytes, 0, bytes.length);
				System.arraycopy(xbytes, 0, nbytes, bytes.length, count);
				bytes = nbytes;
			} else if (count < 0) {
				// accommodate apparent bug in IBM JVM where
				// available() always returns positive value on some files
				break;
			}
		}
		return bytes;
	}
}
