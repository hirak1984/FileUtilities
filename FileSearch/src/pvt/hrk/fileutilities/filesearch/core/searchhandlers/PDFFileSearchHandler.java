package pvt.hrk.fileutilities.filesearch.core.searchhandlers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import pvt.hrk.fileutilities.utils.ObjectUtils;
import pvt.hrk.fileutilities.utils.StringUtilities;

public class PDFFileSearchHandler extends FileSearchHandler{

	public PDFFileSearchHandler(File file) {
		super(file);
	}

	@Override
	public boolean matchInContent(String searchString) {
		try (PDDocument document = PDDocument.load(file) ) {
			GetLinesFromPDF stripper = new GetLinesFromPDF(searchString);
	            stripper.setSortByPosition( true );
	            stripper.setStartPage( 0 );
	            stripper.setEndPage( document.getNumberOfPages() );
	            Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
	            stripper.writeText(document, dummy); 
	            return stripper.matchInContent;
		} catch (IOException  e) {
			ObjectUtils.handleException(file, e);
		} 
		return false;
	}

	private class GetLinesFromPDF extends PDFTextStripper {
		String searchString; 
		boolean matchInContent=false;
		public GetLinesFromPDF(String searchString) throws IOException {
			super();
			this.searchString=searchString;
		}
		@Override
	    protected void writeString(String str, List<TextPosition> textPositions) throws IOException {
	      if(!matchInContent) {
	    	  if(StringUtilities.containsIgnoreCase.test(str, searchString)) {
	    		  matchInContent=true;
	    	  }
	      }
	    }
		
	}
	
}
