package pvt.hrk.fileutilities.filesearch.core;

import java.io.File;

import pvt.hrk.fileutilities.utils.ObjectUtils;

public class SearchHandlerFactory {

	public static SearchHandler Handler(File file){
		if(file.isDirectory()) {
			return new DirectorySearchHandler(file);
		}else if(file.isFile()) {
			if(ObjectUtils.isZipFile.test(file.getName())) {
				return new ZipFileSearchHandler(file);
			}else {
				return new FileSearchHandler(file);
			}
		}else {
			return new NoOpSearchHandler(file);
		}
	}
}
