package pvt.hrk.fileutilities.filesearch.core.resulthandlers;

import java.io.File;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class PrintDistinctParentDirectories implements ResultHandler {

	Set<String> directories;
	@Override
	public void init() throws Exception {
		directories = new HashSet<>();
		
	}

	@Override
	public void handle(File file) throws Exception {
		if(file!=null) {
			String parentPath = file.getParentFile().getAbsolutePath();
			directories.add(parentPath);
		}
		
	}

	@Override
	public void finish() throws Exception {
		OutputStream os = System.out;
		os.write("============ Results============\n".getBytes());
		Iterator<String> it = directories.iterator();
		while(it.hasNext()) {
			String line = it.next();
			os.write(line.getBytes());
			os.write("\n".getBytes());
		}
		os.write("================================\n".getBytes());
		
	}

}
