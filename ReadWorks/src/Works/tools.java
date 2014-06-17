package Works;

import org.apache.commons.lang3.*;

public class tools {

	public boolean stringSearch(String data, String comparable) {
		if (StringUtils.containsNone(data, comparable)) {
			return false;
		} else {
			return true;
		}
	}

	public boolean wordSearch(String... words) {
		String temp = words[0];
		boolean result = false;
		for (int i = 1; i < words.length; i++) {
			if (StringUtils.equals(temp, words[i]+"*")) {
				result = true;
				break;
			}
		}
		return result;
	}

	// public boolean wordSearch(String...words) {
	// String temp = words[0];
	// boolean result = false;
	// for ( int i=1; i<words.length; i++) {
	// if (temp.contains(words[i])) {
	// result = true;
	// break;
	// }
	// }
	// return result;
	// }
	// public String wordSearchSt(String...words) {
	// String temp = words[0];
	// String result = "none";
	// for ( int i=1; i<words.length; i++) {
	// if (temp.contains(words[i])) {
	// result = words[i];
	// break;
	// }
	// }
	// // System.out.println(result);
	// return result;
	// }

	// public String getLetter(String data, int Position) {
	// String result = "Unsolved";
	// if (Position < data.length() && Position > -1) {
	// result = data.substring(Position, Position + 1);
	// } else {
	// result = "The position " + Position
	// + " is not valid for the String '" + data + "'";
	// }
	// return result;
	// }

	public String endOf(String data) {
		return data.substring(data.length() - 1);
	}

	public String StartOf(String data) {
		return data.substring(0, 1);
	}

	String cutOf(String data) {
		return data.substring(0, data.length() - 1);
	}

	double factorial(double n) {
		double temp = 1;
		for (int i = 1; i < n + 1; i++) {
			temp *= i;
		}
		return temp;
	}

	void mark(String a) {
		System.out.println(a);
	}

	void mark(double a) {
		System.out.println(a);
	}

	public String answer(CharSequence dat) {
		String data = (String) dat;
		return data.substring(data.indexOf("=") + 2, data.length());
	}

	public String question(CharSequence dat) {
		String data = (String) dat;
		return data.substring(0, data.indexOf("="));
	}

//	boolean isNumber(String p) {
//		try {
//			Double.parseDouble(p);
//			return true;
//		} catch (Exception e) {
//			return false;
//		}
//	}

	double parse(String p) {
		try  {
			return Double.parseDouble(p);
			} catch ( Exception e) {
			calc.errorLog.add("Error: " + p + " is invalid");
			return Float.NaN;
			}
	}
	
	int parseInt(String p) {
		try  {
			return Integer.parseInt(p);
			} catch ( Exception e) {
				calc.errorLog.add("Error: " + p + " is invalid");
				return (int) Float.NaN;
			}
	}

	float parsef(String p) {
		try  {
			return Float.parseFloat(p);
		} catch ( Exception e) {
			calc.errorLog.add("Error: " + p + " is invalid");	
			return Float.NaN;
		}
	}
}
