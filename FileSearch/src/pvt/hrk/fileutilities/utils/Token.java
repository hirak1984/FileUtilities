package pvt.hrk.fileutilities.utils;

public class Token {

	private String data;
	private String tag;
	public Token(String data, String tag) {
		super();
		this.data = data;
		this.tag = tag;
	}
	
@Override
public String toString() {
	if(tag.equals("same")) {
		return data;
	}else {
		return "~"+data+"~";	
	}
}	
	
}
