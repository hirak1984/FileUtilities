package pvt.hrk.file;

import java.io.File;
import java.io.IOException;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

public class PDFMain {

	public static void main(String[] args) throws IOException {
		
		
		//splitPages("C:\\output\\titai_passport.pdf");
		
		
		String [] pdfFiles = new String[19];
		for(int i=0;i<19;i++){
			pdfFiles[i]= "C:\\output\\"+"titai_passport.pdf_"+i+".pdf";
		}
		merge(pdfFiles, "C:\\output\\titai_passport_updated.pdf");
	}

	private static void splitPages(String pdfFileToSplit) throws IOException {
		File inputFile = new File(pdfFileToSplit);
		PDDocument doc = PDDocument.load(inputFile);
		int pages = doc.getNumberOfPages();
		
		for(int i=0;i<pages;i++){
			PDPage page = doc.getPage(i);
			PDDocument doc1 = new PDDocument();
			doc1.addPage(page);
			doc1.save("C:\\output\\"+inputFile.getName()+"_"+i+".pdf");
			doc1.close();
		}
		doc.close();
		
	}
	

	interface PerformOperation {
		 boolean check(int a);
		}
	
	public static PerformOperation is_odd(){
        return (int num) -> num % 2 != 0;
    }
    
    public static PerformOperation is_prime(){
        return (int num) -> java.math.BigInteger.valueOf(num).isProbablePrime(100);
    }
    
    public static PerformOperation is_palindrome(){
        return (int num) -> { 
        	int sum=0;
    		int temp=num;
    		while(temp>0){
    			sum=sum*10+temp%10;
    			temp=temp/10;
    		}
    		return num==sum;
        	};
    }
    
	Function<Integer,Boolean> is_Palindrome = n -> {
		int sum=0;
		int temp=n;
		while(temp>0){
			sum+=temp%10;
			temp/=10;
		}
		return n==sum;
	};
	
	
	
	private static void merge(String [] pdfFiles,String outputFile) throws IOException{
		PDDocument doc =  new PDDocument();
		PDFMergerUtility merger = new PDFMergerUtility();

		for (String string : pdfFiles) {
			System.out.println(string);
			/*PDDocument doc1 = PDDocument.load(new File(string));
			for(PDPage page:doc1.getPages()){
				doc.addPage(page);
			}*/
			merger.addSource(string);
		}
		merger.setDestinationFileName(outputFile);
		 merger.mergeDocuments();
//		doc.save(outputFile);
	//	doc.close();
	}

}
