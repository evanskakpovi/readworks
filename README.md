readworks
=========

Caculate math expressions

To use, simply import the code into your project, and make export it as a library.

To use it, simple create an instance of Reader.

Example:

Reader myReader = new Reader();
		String data = "5-sin(90)";
		String answer = myReader.calculateToString(data, 0); // use 0 for radians, and 1 for degrees.
		
		
		There are other methods as well, return float, double, or int.
