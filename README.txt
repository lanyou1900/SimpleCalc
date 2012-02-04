This is my Simple Calculator. A silly and simple project to learn Java coding and Github usage.
The calculator was initially an integer calculator, but changed using the BigDecimal-class. So now it also handles floats, doubles, etc.

Usage: "java SimpleCalc [math expression] (precision)"
   - [math expression] is the mathematical expression such as "98*4+2.2"
   - (precision) is the number of digits you would like to get in the final result. Default is 10.
     All precision during the calculations are done with precision*100. Only the last result is rounded to the value of precision.


Changelog:

Version 1.10:
- completely new parser
- new usage: "java SimpleCalc [math expression] (precision)"
   - [math expression] is the mathematical expression such as "98*4+2.2"
   - (precision) is the number of digits you would like to get in the final result. Default is 10.
     All precision during the calculations is precision*100. Only the last result is rounded to the value of precision.
- one bug persists: you can't use "/-" in the calculation, for example "2/-2" will crash. Use "-2/2" instead.
  I will fix it ASAP.
- another limitation: no parenthesises are allowed yet, nor are sinus, pi, etc...
- and last but not least: I need to fix the error handling of the code.

Version 1.03:
- usage: "java SimpleCalc number1 (+-*/) number2."
- better source code - simplified by Daniel
- same functionality

Version 1.02:
- usage: "java SimpleCalc number1 (+-*/) number2."
- fixed basic bug (crashed when no args was used)
- better error handling when numbers are invalid.

Version 1.01:
- new usage: "java SimpleCalc number1 (+-*/) number2. You might have to use \* instead of *.
- new structure in the code.

Version 1.00:
- added this file - README.txt


