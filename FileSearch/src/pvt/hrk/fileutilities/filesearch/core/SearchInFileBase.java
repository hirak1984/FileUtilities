package pvt.hrk.fileutilities.filesearch.core;



import java.io.File;
import java.io.IOException;
import java.util.function.Predicate;


import pvt.hrk.fileutilities.utils.StringSearchUtilities.StringSearchModes;

public abstract class SearchInFileBase  {

	
	private String searchString;
	private StringSearchModes searchMode;
	protected File file;
	public SearchInFileBase(File file,String searchString) {
		super();
		this.searchString = searchString;
		this.searchMode = StringSearchModes.evaluate(searchString);
		this.file = file;
	}
	
	protected Predicate<String> match=line-> searchMode.doSearch(line.toLowerCase(), searchString.toLowerCase());
	
	public boolean foundInName() {
		return match.test(file.getAbsolutePath());
	}
	public abstract boolean foundInContents() throws IOException;

}
