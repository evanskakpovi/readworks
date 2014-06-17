package Works;

import java.util.ArrayList;
import java.util.Arrays;


public class ReaderSK {
	tools t = new tools();
	calc c = new calc();
	functions f = new functions();
	final ArrayList<String> ops = new ArrayList<String>(Arrays.asList("arccos*", "arcsin*", "arctan*", "arccot*",
			"arccsc*", "arcsec*", "cos*", "sin*", "tan*", "cot*", "csc*",
			"sec*", "sinh*", "cosh*", "tanh*", "coth*", "csch*", "sech*",
			"sqrt*", "log*", "ln*", "exp*" ));


	String calc(String data, int i, int mode) {
		double datap = this.t.parse(data);
		if (mode==1){
			datap = Math.toRadians(datap);
		}
		String result = "";
		switch (i) {

		case 6: 
			result = Math.cos(datap) + "";
			break;
		case 7:
			result = Math.sin(datap) + "";
			break;
		case 8:
			result = Math.tan(datap) + "";
			break;
		case 9:
			result = 1 / (Math.tan(datap)) + "";
			break;
		case 10:
			result = 1 / (Math.sin(datap)) + "";
			break;
		case 11:
			result = 1 / (Math.cos(datap)) + "";
			break;
		/** !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! */
		case 0:
			result = Math.acos(datap) + "";
			break;
		case 1:
			result = Math.asin(datap) + "";
			break;
		case 2:
			result = Math.atan(datap) + "";
			break;
		case 3:
			result = 1 / (Math.atan(datap)) + "";
			break;
		case 4:
			result = 1 / Math.asin(datap) + "";
			break;
		case 5:
			result = 1 / Math.acos(datap) + "";
			break;
		/** !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! */
		case 12:
			result = Math.sinh(datap) + "";
			break;
		case 13:
			result = Math.cosh(datap) + "";
			break;
		case 14:
			result = Math.tanh(datap) + "";
			break;
		case 15:
			result = 1 / Math.tanh(datap) + "";
			break;
		case 16:
			result = 1 / Math.sinh(datap) + "";
			break;
		case 17:
			result = 1 / Math.cosh(datap) + "";
			break;
		/** !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! */
		case 18:
			result = Math.sqrt(this.t.parse(data)) + "";
			break;
		/** !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! */
		case 19:
			result = Math.log10(datap) + "";
			break;
		case 20:
			result = Math.log(datap) + "";
			break;
		case 21:
			result = Math.exp(datap) + "";
			break;
		default:
			break;
		}
		return result;
	}

	//
	//
	//
	/**
	 * 
	 * @param trailing
	 * @param preData
	 * @param mode 1=degrees 0=rad
	 * @return
	 */
	public String sinus(String trailing, String preData, int mode) {
		String result = "";
		// if (trailing)
		for (int i = 0; i < this.ops.size(); i++) {
			if (trailing.endsWith(this.ops.get(i))) {
				int p = trailing.indexOf(this.ops.get(i));
				String addSt = trailing.substring(0, p);
				String total = calc(preData, i, mode);
				// System.out.println("cacop  "+f.polishTrailer(addSt) +
				// total+"---"+i);
				result = this.c.Calculate(this.f.polishTrailer(addSt) + total) + "";
				break;
			}
		}
		// System.out.println("PERFORMING SINNUS");
		return result;
	}

	public String[][] trimCalc(String operator) {
		String[][] result = new String[2][1];
		// deal with sin in operator
		result[0][0] = operator;
		result[1][0] = "";
		for (int i = 0; i < this.ops.size(); i++) {
			if (operator.endsWith(this.ops.get(i))) {
				result[0][0] = this.f.cut(operator, 0,
						operator.length() - this.ops.get(i).length());
				result[1][0] = i + "";
				break;
			}
		}
		// System.out.println("PERFMING TRIMCALC   "+result[1][0]);
		return result;
	}

	/**
	 * 
	 * @param data
	 * @param store
	 * @param mode  1=deg 0=rad
	 * @return
	 */
	public String finnish(String data, String store, int mode) {
		// System.out.println("PERFORMING FINNISH  "+result);
		return calc(data,Integer.parseInt(store), mode);
	}
	//
	//
	//
}
