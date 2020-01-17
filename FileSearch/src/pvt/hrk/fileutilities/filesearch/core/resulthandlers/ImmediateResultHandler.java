package pvt.hrk.fileutilities.filesearch.core.resulthandlers;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class ImmediateResultHandler implements ResultHandler {

	private OutputStream os;

	public ImmediateResultHandler(OutputStream os) {
		this.os = os;
	}

	@Override
	public void init() {

		// nothing required
	}

	@Override
	public void handle(File file) throws IOException {
		os.write(">>".getBytes());
		os.write(file.getAbsolutePath().getBytes());
		os.write("\n".getBytes());
		os.flush();

	}

	@Override
	public void finish() {
		System.out.println("");
	}

}
