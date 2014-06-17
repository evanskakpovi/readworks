package Works;

import java.text.DecimalFormat;
import java.util.ArrayList;
import org.apache.commons.lang3.*;

public class calc {
	// functions fWork = new functions();
	final tools t = new tools();
	public static ArrayList<String> errorLog=new ArrayList<String>();
	public final String opslist = "*/+-~`^#%";
	final DecimalFormat f = new DecimalFormat("#.###############");

	private ArrayList<String> getData(String data) {
		ArrayList<String> numL = new ArrayList<String>();
		numL.add("");
		int n = 0;
		int iM = 0;
		String wData = data;
		if (wData.equals("")) {
			wData = "NaN";
		} else {
			// System.out.println(wData);
			wData = StringUtils.replace(wData, "--", "+");
			wData = StringUtils.replace(wData, "+-", "-");
			wData = StringUtils.replace(wData, "-+", "-");
			wData = StringUtils.replace(wData, "++", "+");
			wData = StringUtils.replace(wData, "*-", "~");
			wData = StringUtils.replace(wData, "/-", "`");
			wData = StringUtils.replace(wData, "^-", "#");
			wData = StringUtils.replace(wData, "E-", "@");
		}
		// System.out.println("Data: " + wData);
		String temp = "";

		if (wData.startsWith("-")) {
			temp = "-";
			iM = 1;
		}

		for (; iM < wData.length(); iM++) {
			if (StringUtils.containsNone(wData.substring(iM, iM + 1), opslist)) {
				temp += wData.substring(iM, iM + 1);
				if (temp.endsWith("!")) {
					double newTemp = t.factorial(t.parse(temp.substring(0,
							temp.length() - 1)));
					temp = newTemp + "";
				}
				if (temp.contains("@")) {
					temp = StringUtils.replace(temp, "@", "E-");
				}
				// numList[n] = temp;
				numL.set(n, temp);
			} else if (!temp.equals("")) {
				temp = "";
				n++;
				numL.add("");
			}
		}
		return numL;
	}

	private ArrayList<String> getOperations(String data) {
		ArrayList<String> opList = new ArrayList<String>();
		// opList.add("");
		int iM = 0;
		String wData = data;

		if (StringUtils.containsNone(StringUtils.substring(data, 1), opslist)) {
			opList.add("none");
			return opList;
		} else {
			wData = StringUtils.replace(wData, "--", "+");
			wData = StringUtils.replace(wData, "+-", "-");
			wData = StringUtils.replace(wData, "-+", "-");
			wData = StringUtils.replace(wData, "++", "+");
			wData = StringUtils.replace(wData, "*-", "~");
			wData = StringUtils.replace(wData, "/-", "`");
			wData = StringUtils.replace(wData, "^-", "#");
			wData = StringUtils.replace(wData, "E-", "@");
			// System.out.println("op@" + wData);

			if (StringUtils.startsWith(wData, "-")) {
				iM = 1;
			}
			for (; iM < wData.length(); iM++) {
				if (t.stringSearch(wData.substring(iM, iM + 1), opslist)) {
					opList.add(wData.substring(iM, iM + 1));
				}
			}
			return opList;
		}

	}

	final String primCode = "^#";

	private String primaryCalculs(String data) {

		ArrayList<String> numList = getData(data);
		ArrayList<String> opList = getOperations(data);
		String temp = "";
		if (opList.get(0).equals("none")) {
			temp = numList.get(0);
			return temp;
		} else {

			/**
			 * We go backward
			 */

			// Step 1: Build temp string
			for (int i = opList.size() - 1; i > -1; i--) {

				temp = opList.get(i) + numList.get(i + 1) + "" + temp;
				//
				if (i - 1 == -1) {
					temp = numList.get(Math.min(0, i + 1)) + temp;
				}
			}
			// ///////////////////////////////////////////////////
			int isDone = 0;
			while (!StringUtils.containsNone(temp, primCode)) {

				isDone = 0;
				temp = "";
				// mark("newrun");
				for (int i = opList.size() - 1; i > -1; i--) {
					boolean end = (i - 1 == -1);

					if (opList.get(i).equals("^") && isDone == 0) {
						double a = Math.pow(t.parse(numList.get(i)),
								t.parse(numList.get(i + 1)));
						if (!end) {
							temp = opList.get(i - 1)
									+ f.format(t.parse(a + "")) + "" + temp;
							// t.mark(temp+" first temp");
						} else {
							temp = "" + f.format(t.parse(a + "")) + temp;
							// t.mark(temp+ " 1else "+i);
						}
						i--;
						isDone = 1;
					} else if (opList.get(i).equals("#") && isDone == 0) {
						double a = Math.pow(t.parse(numList.get(i)),
								-t.parse(numList.get(i + 1)));
						if (!end) {
							temp = opList.get(i - 1)
									+ f.format(t.parse(a + "")) + "" + temp;
							// t.mark(temp+" first temp");
						} else {
							temp = "" + f.format(t.parse(a + "")) + temp;
							// t.mark(temp+ " 1else "+i);
						}
						i--;
						isDone = 1;
					} else {
						temp = opList.get(i) + numList.get(i + 1) + "" + temp;
						// t.mark(temp+" else "+i);
					}
					if (i - 1 == -1) {
						temp = numList.get(Math.min(0, i + 1)) + temp;
						// t.mark(temp+" last "+i);
					}
				}
				numList = getData(temp);
				opList = getOperations(temp);

			}
		}
		return temp;

	}

	final String secCode = "*/~`%";

	private String secondaryCalculs(String data) {
		String tp = data;
		if (!StringUtils.containsNone(data, primCode)) {
			tp = primaryCalculs(data);
		}
		ArrayList<String> numList = getData(tp);
		ArrayList<String> opList = getOperations(tp);
		String temp = "";
		if (opList.get(0).equals("none")) {
			temp = numList.get(0);
			return temp;
		}
		// Step 1: Build temp string

		for (int i = 0; i < opList.size(); i++) {
			temp += numList.get(i) + "" + opList.get(i) + "";
			if (i == opList.size() - 1 && !opList.get(i).equals("")) {
				temp += numList.get(Math.min(i + 1, numList.size() - 1));
			}
			// System.out.println("Temp is: "+temp);
		}
		// ///////////////////////////////////////////////////
		int isDone = 0;
		while (t.stringSearch(temp, secCode)) {
			isDone = 0;
			temp = "";
			for (int i = 0; i < opList.size(); i++) {
				boolean end = (i == opList.size() - 1);

				if (opList.get(i).equals("%") && isDone == 0) {
					double a = t.parse(numList.get(i))
							% t.parse(numList.get(i + 1));
					if (!end) {
						temp += f.format(t.parse(a + "")) + ""
								+ opList.get(i + 1) + "";
					} else {
						temp += "" + f.format(t.parse(a + ""));
					}
					i++;
					isDone = 1;
				} else if (opList.get(i).equals("*") && isDone == 0) {
					double a = t.parse(numList.get(i))
							* t.parse(numList.get(i + 1));
					if (!end) {
						temp += f.format(t.parse(a + "")) + ""
								+ opList.get(i + 1) + "";
					} else {
						temp += "" + f.format(t.parse(a + ""));
					}
					i++;
					isDone = 1;
				} else if (opList.get(i).equals("/") && isDone == 0) {
					double a = t.parse(numList.get(i))
							/ t.parse(numList.get(i + 1));
					if (Double.isInfinite(a)) {
						errorLog.add("Division by 0");
						System.out.println("x/0");
						break;
					}
					if (!end) {
						temp += f.format(t.parse(a + "")) + ""
								+ opList.get(i + 1) + "";
					} else {
						temp += "" + f.format(t.parse(a + ""));
						
					}
					i++;
					isDone = 1;
				} else if (opList.get(i).equals("~") && isDone == 0) {
					double a = 0;
					if (opList.get(Math.max(i - 1, 0)).equals("-") && i > 0) {
						opList.set(i - 1, "+");
						a = t.parse(numList.get(i))
								* t.parse(numList.get(i + 1));
					} else {
						a = t.parse(numList.get(i))
								* -(t.parse(numList.get(i + 1)));
					}

					if (!end) {
						temp += f.format(t.parse(a + "")) + ""
								+ opList.get(i + 1) + "";
					} else {
						temp += "" + f.format(t.parse(a + ""));
					}
					i++;
					isDone = 1;
				} else if (opList.get(i).equals("`") && isDone == 0) {
					double a = 0;
					if (opList.get(Math.max(i - 1, 0)).equals("-") && i > 0) {
						opList.set(i - 1, "+");
						a = t.parse(numList.get(i))
								/ t.parse(numList.get(i + 1));
					} else {
						a = t.parse(numList.get(i))
								/ -(t.parse(numList.get(i + 1)));
					}

					if (!end) {
						temp += f.format(t.parse(a + "")) + ""
								+ opList.get(i + 1) + "";
					} else {
						temp += "" + f.format(t.parse(a + ""));
					}
					i++;
					isDone = 1;
				} else {
					if (t.stringSearch(
							opList.get(Math.min(i + 1, opList.size() - 1)),
							"`~")) {
						temp += numList.get(i) + "" + "+" + "";
					} else {
						temp += numList.get(i) + "" + opList.get(i) + "";

					}
					// mark(temp+">>>>>"+i);
				}
				if (i + 1 == opList.size()) {
					temp += numList.get(Math.min(i + 1, numList.size() - 1));
				}
			}
			// Step 2: reset Strings

			numList = getData(temp);
			opList = getOperations(temp);

		}

		return temp;

	}

	protected double Calculate(String data) {

		if (StringUtils.containsNone(data, opslist)) {
			return t.parse(data);
		} else {
			String tp = data;
			if (!StringUtils.containsNone(data, secCode + primCode)) {
				tp = secondaryCalculs(data);
			}
			ArrayList<String> numList = getData(tp);
			ArrayList<String> opList = getOperations(tp);
			double temp = t.parse(numList.get(0));
			for (int i = 0; i < opList.size(); i++) {

				if (opList.get(i).equals("+")) {
					temp += t.parse(numList.get(i + 1));
				} else if (opList.get(i).equals("-")) {
					temp -= t.parse(numList.get(i + 1));
				}
			}
			return temp;
		}
		// System.out.println("finnish with "+temp);

	}

}
