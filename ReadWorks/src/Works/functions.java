package Works;

public class functions {
	
	String cut(String data, int a, int b) {
		return data.substring(a,b);
	}
	
	int Open(String data) {
		return data.indexOf("(") ;
	}

	int Open(String data, int a) {
		return data.indexOf("(", a);
	}

	int OpenEnd(String data) {
		return data.lastIndexOf("(");
	}

	int OpenEnd(String data, int a) {
		return data.lastIndexOf("(", a);
	}

	int Close(String data) {
		return data.indexOf(")");
	}

	int Close(String data, int b) {
		return data.indexOf(")", b);
	}

	int CloseEnd(String data) {
		return data.lastIndexOf(")");
	}

	int CloseEnd(String data, int b) {
		return data.lastIndexOf(")", b);
	}

	int n(String data, String loc) {
		return data.indexOf(loc);
	}

	int n(String data, String loc, int b) {
		return data.indexOf(loc, b);
	}
	boolean startWo(String data, int loc) {
		boolean result = false;
		if (data.substring(loc).startsWith("(")) 
			result = true;
		else
			result = false;
		return result;
	}
	boolean startWc(String data, int loc) {
		boolean result = false;
		if (data.substring(loc).startsWith(")")) 
			result = true;
		else
			result = false;
		return result;
	}
	tools t = new tools();
	String polishTrailer(String data) {
		String result = data;
		if (data.equals("")) {
			
		}
		else if (!t.stringSearch(t.endOf(data), "/*-+^!~`#")) {
			result+="*";
		}
		return result;
	}
	String polishPostTrailer(String data) {
		String result = data;
		if (data.equals("")) {
			
		}
		else if (!t.stringSearch(t.StartOf(data), "/*-+^!~`#")) {
			result="*"+result;
		}
		return result;
	}

	
}
