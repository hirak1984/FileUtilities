package pvt.hrk.fileutilities.filesearch.core.resulthandlers;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ConsolidatedResultHandler implements ResultHandler{

	private OutputStream os;
	private Set<String> results;
	public ConsolidatedResultHandler(OutputStream os) {
		this.os = os;
		
	}
	@Override
	public void init() {
		results = new HashSet<>();
		
	}

	@Override
	public void handle(File file) throws IOException {
		results.add(file.getAbsolutePath());
		
	}

	@Override
	public void finish() throws IOException {
		os.write("============ Results============\n".getBytes());
		Iterator<String> it = results.iterator();
		while(it.hasNext()) {
			String line = it.next();
			os.write(line.getBytes());
			os.write("\n".getBytes());
		}
		os.write("================================\n".getBytes());
	}




}
