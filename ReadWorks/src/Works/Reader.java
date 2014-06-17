package Works;

import java.text.DecimalFormat;

import org.apache.commons.lang3.*;

public class Reader {
	final static tools t = new tools();
	final static functions f = new functions();
	final static calc calc = new calc();

	private String prepare(String data) {
		String newData = data;
		newData = StringUtils.replace(newData," ", "");
		newData = StringUtils.replace(newData,"√", "sqrt");
		newData = StringUtils.replace(newData,"pi", "(" + Math.PI + ")");
		newData = StringUtils.replace(newData,"π", "(" + Math.PI + ")");
		newData = StringUtils.replace(newData,"exp", "x%%%p");
		newData = StringUtils.replace(newData,"sec", "s%%%c");
		newData = StringUtils.replace(newData,"e", "(" + Math.E + ")");
		newData = StringUtils.replace(newData,"x%%%p", "exp");
		newData = StringUtils.replace(newData,"s%%%c", "sec");
		newData = StringUtils.replace(newData,"²", "^2");
		newData = StringUtils.replace(newData,"³", "^3");

		newData = StringUtils.replace(newData,"÷", "/");
		newData = StringUtils.replace(newData,"×", "*");
		newData = StringUtils.replace(newData,")(", ")*(");

		return newData;
	}

	/**
	 * 
	 * @param lin = data
	 * @param mode 1=deg 0=rad
	 * @return
	 */
	private String findPar(String lin, int mode) {
		String line = prepare(lin);
		String store = "";
		if (!line.contains("(")) {
			return round(calc.Calculate(line));
		} else {
			String tmp = "";
			int lastInit = 0;
			int opennerInit = f.Open(line);
			int p = 0;
			int o = 0;
			int l = 0;
			try {
				for (int i = 0; i < line.length(); i++) {
					// t.mark("--------------------[ " + i +
					// " ]------------------");
					// ----------Equate parentheses-------//
					if (f.startWo(line, i)) {
						++p;
						// t.mark(p + " p " + i);
					} else if (f.startWc(line, i)) {
						++o;
						// t.mark(o + " o " + i);
					}
					// -----------------------------------//
					/**
					 * Everything Above should remain untouched for the duration
					 * of this code. It is the heart of the code.
					 */

					//
					//
					//
					//
					//
					//
					//

					// ----------logic starts-------------//
					if (p == o && p == 1) {
						// when a parentheses fully closes, increment l.
						++l;
						// locate position of openner and closer
						int openner = f.Open(line, i);
						int closer = f.Close(line, i);
						String operator = "";
						// decode data within data block
						if (openner > 0) {
							//System.out.println(openner
								//	+ "   "
								//	+ f.polishTrailer(line.substring(lastInit,
								//			opennerInit)));
							String trailing = f.polishTrailer(line.substring(
									lastInit, opennerInit));
							String preData = line.substring(opennerInit + 1, i);
							preData = calc.Calculate(preData) + "";
							operator = line.substring(closer + 1, openner);
							if (operator.equals("")) {
								operator = "*";
							}
							operator = f.polishPostTrailer(operator);
							ReaderSK r = new ReaderSK();
							boolean isRunnable = t.wordSearch(trailing, "sin",
									"cos", "tan", "cot", "csc", "sec", "sqrt",
									"log", "ln", "exp", "cosh", "sinh", "tanh",
									"coth", "sech", "csch", "arcsin", "arccos",
									"arctan", "arccot", "arccsc", "arcsec");
							// ***********************************************************//
							/**
							 * deal with trailing sin apply trailing to data
							 * inside parentheses
							 */
							if (isRunnable) {
								preData = r.sinus(trailing, preData, mode);
								trailing = "";
							}

							// ensures placement of op
							operator = f.polishTrailer(operator);
							/** deals with sin in operator */
							store = r.trimCalc(operator)[1][0];
							operator = r.trimCalc(operator)[0][0];
							/** <------------------------------> */

							// System.out.println("===============store: " +
							// store
							// + " op: " + operator);

							// ***********************************************************//
							trailing = f.polishTrailer(trailing);
							operator = f.polishTrailer(operator);

							// <This Block needs checking>//

							tmp += trailing + preData + "" + operator + "";

						} else {
							// trailing and postTrailing are proprer
							String trailing = "";
							if (l == 1) {
								trailing = f.polishTrailer(line.substring(
										lastInit, opennerInit));
							}
							String postTrailing = f.polishPostTrailer(line
									.substring(closer + 1));
							String postData = line.substring(opennerInit + 1,
									closer);

							// ***********************************************************//

							// calculate postData
							postData = calc.Calculate(postData) + "";

							// Start instance of ReaderSk
							ReaderSK r = new ReaderSK();
							// deal with trailing sin
							boolean isRunnable = t.wordSearch(trailing, "sin",
									"cos", "tan", "cot", "csc", "sec", "sqrt",
									"log", "ln", "exp", "cosh", "sinh", "tanh",
									"coth", "sech", "csch", "arcsin", "arccos",
									"arctan", "arccot", "arccsc", "arcsec");

							if (isRunnable) {
								postData = r.sinus(trailing, postData, mode);
								trailing = "";
							}
							// <------------------------------------------>//

							// fullfil postData conditions
							/** <Critical block for post calculations> */
							if (!store.equals("")) {
								postData = r.finnish(postData, store, mode);
								store = "";
								// t.mark(postData + "??");
							}
							/** ------------------------------------- */
							// ***********************************************************//
							trailing = f.polishTrailer(trailing);

							tmp += trailing + postData + postTrailing;
							// t.mark(tmp + "??/");
						}
						lastInit = closer + operator.length() + 1;
						// reset opennerInit to new openner
						opennerInit = f.Open(line, i);
						// reset equators
						p = 0;
						o = 0;
					}
					// ---------------------------------------//

					/**
					 * Nesting starts over here. Nesting takes care of extra
					 * parentheses
					 */
					// ---------nested parentheses only-------//
					else if (p == o && p > 1) {
						++l;
						int opener = f.Open(line, i);
						int closer = f.Close(line, i);
						String operator = "";

						if (opener > 0) {
							String trailing = f.polishTrailer(line.substring(
									lastInit, opennerInit));
							operator = line.substring(closer + 1, opener);
							String preData = line.substring(opennerInit + 1, i);
							if (line.substring(closer + 1, opener).equals("")) {
								operator = "*";
							}
							operator = f.polishPostTrailer(operator);
							// LOOP BACK CODE
							if (preData.contains("(")) {
								preData = findPar(preData, mode);
							}

							// ***********************************************************//
							ReaderSK r = new ReaderSK();
							boolean isRunnable = t.wordSearch(trailing, "sin",
									"cos", "tan", "cot", "csc", "sec", "sqrt",
									"log", "ln", "exp", "cosh", "sinh", "tanh",
									"coth", "sech", "csch", "arcsin", "arccos",
									"arctan", "arccot", "arccsc", "arcsec");
							// ***********************************************************//
							/**
							 * deal with trailing sin apply trailing to data
							 * inside parentheses
							 */
							if (isRunnable) {
								preData = r.sinus(trailing, preData, mode);
								trailing = "";
							}

							// ensures placement of op
							operator = f.polishTrailer(operator);
							/** deals with sin in operator */
							store = r.trimCalc(operator)[1][0];
							operator = r.trimCalc(operator)[0][0];
							/** <------------------------------> */

							trailing = f.polishTrailer(trailing);
							operator = f.polishTrailer(operator);

							tmp += trailing + preData + "" + operator + "";
						} else {
							String trailing = "";
							if (l == 1) {
								trailing = f.polishTrailer(line.substring(
										lastInit, opennerInit));

							}
							String postTrailing = f.polishPostTrailer(line
									.substring(closer + 1));
							String postData = line.substring(opennerInit + 1,
									closer);

							if (postData.contains("(")) {
								postData = findPar(postData, mode);
							}

							// ***********************************************************//
							// Start instance of ReaderSk
							ReaderSK r = new ReaderSK();
							// deal with trailing sin
							boolean isRunnable = t.wordSearch(trailing, "sin",
									"cos", "tan", "cot", "csc", "sec", "sqrt",
									"log", "ln", "exp", "cosh", "sinh", "tanh",
									"coth", "sech", "csch", "arcsin", "arccos",
									"arctan", "arccot", "arccsc", "arcsec");

							if (isRunnable) {
								postData = r.sinus(trailing, postData, mode);
								trailing = "";
							}
							// <------------------------------------------>//

							// fullfil postData conditions
							/** <Critical block for post calculations> */
							if (!store.equals("")) {
								postData = r.finnish(postData, store, mode);
								store = "";
								// t.mark(postData + "??");
							}
							/** ------------------------------------- */

							// ***********************************************************//
							trailing = f.polishTrailer(trailing);
							tmp += trailing + postData + postTrailing;

						}
						// reset opennerInit to new openner
						lastInit = closer + 1 + operator.length();
						opennerInit = f.Open(line, i);
						// reset equators
						p = 0;
						o = 0;
					}
				}
			} catch (Exception e) {
				System.out.println("PROBLEM FOUND IN READER.JAVA: " + e);
			}

			// complete parentheses
			if (p > o) {
				for (int i = 0; i < p - o; i++) {
					line += ")";
					// System.out.println(line);
					// o++;
				}

				tmp = findPar(line, mode);
				// System.out.println(tmp+":");
			}
			return round(calc.Calculate(tmp));
		}

		/**
		 * We now return the answer as a string
		 */
		// System.out.println(tmp + " = " + calc.Calculate(tmp));
		// System.out.println("fin");

		
	}

	final DecimalFormat form = new DecimalFormat("####E0");
	final DecimalFormat form2 = new DecimalFormat("####.###");
	public String round(double n) {
		System.out.println();
		if ((n+"").contains("E")) {
			return form.format(n);
		} else {
			return form2.format(n);
		}
		
	}
/**
 * 
 * @param data
 * @param mode deg=1, rad=0
 * @return string of ans
 */
	public String calculateToString(String data, int mode) {
		System.out.println("Data: " + prepare(data) + "\n");
		return findPar(data, mode);
	}
	/**
	 * 
	 * @param data
	 * @param mode deg=1, rad=0
	 * @return string of ans
	 */
	public double calculateToDouble(String data,int mode) {
		return t.parse(findPar(data,mode));
	}
	/**
	 * 
	 * @param data
	 * @param mode deg=1, rad=0
	 * @return string of ans
	 */
	public float calculateToFloat(String data, int mode) {
		return t.parsef(findPar(data, mode));
	}
	/**
	 * 
	 * @param data
	 * @param mode deg=1, rad=0
	 * @return string of ans
	 */
	public float calculateToInt(String data, int mode) {
		return t.parseInt(findPar(data, mode));
	}
}
