package Works;

import java.io.FileNotFoundException;

public class body {

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Reader h = new Reader();
		String data = "5/son0";
		String hf = h.calculateToString(data, 0);
		System.out.println(hf);

		for (String i:calc.errorLog) {
			System.out.println(i);
		}
	}

}