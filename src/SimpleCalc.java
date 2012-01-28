/**
 * Created by IntelliJ IDEA.
 * User: skovmand
 * Date: 28/01/12
 * Time: 12.10
 */

import java.math.BigDecimal;

public class SimpleCalc {
    
    // Initialise public version number and usage info.
    public static String welcomeMsg = new String("SimpleCalc - The simple calculator - v1.02");
    public static String usage = new String("Usage: SimpleCalc number1 [+-*/] number2. To multiply, use \\* instead of *.");

    // Initialise variables to be used in calculations.
    public static BigDecimal number1bd = BigDecimal.ZERO;
    public static BigDecimal number2bd = BigDecimal.ZERO;

    // Initialise plain string-values for output (to make the code simpler later...)
    public static String number1s = new String("");
    public static String number2s = new String("");


    public static void fatalError(String exitMsg, int errorCode) {
        System.out.println("Fatal Error " + errorCode + ": " + exitMsg);
        System.out.println(usage);
        System.exit(errorCode);  
    }

    private static int initialiseModeOfOperation(String[] args) {
        // Check for arguments.
        if (args.length < 3)
            fatalError("No arguments given.", 1);

        // The first and third argument should be a number, the second a single char.
        // What is the mode of operation?
        int modeofoperation = 0;

        if (args[1].equalsIgnoreCase("+"))
            modeofoperation=1;
        else if (args[1].equalsIgnoreCase("-"))
            modeofoperation=2;
        else if (args[1].equalsIgnoreCase("*"))
            modeofoperation=3;
        else if (args[1].equalsIgnoreCase("/"))
            modeofoperation=4;
        else
            fatalError("Invalid operator '" + args[1] + "'. Use +, -, * or /", 1);

        return modeofoperation;
    }

    private static void initialiseNumbers(String[] args) {
        // The first and third argument should be a number, the second a single char.

        // Are the two numbers actually numbers?
        // Initialise the two numbers into number1bd and number2bd:

        // Initialise number1bd and number2bd from args[0] and args[2].
        number1bd = toBigDecimal(args[0]);
        number2bd = toBigDecimal(args[2]);

        // Strip trailing zeros from numbers:
        number1bd = number1bd.stripTrailingZeros();
        number2bd = number2bd.stripTrailingZeros();

        // Create plain text versions of BigDecimals
        number1s = number1bd.toPlainString();
        number2s = number2bd.toPlainString();

    }

    private static BigDecimal toBigDecimal(String num) throws NumberFormatException {
        // Returns a BigDecimal from a String or handles a NumberFormatException.

        BigDecimal numbd = new BigDecimal(0);

        try {
            numbd = new BigDecimal(num);
        } catch (NumberFormatException e) {
            fatalError("Invalid number '" + num + "'. Must be a numeric value. Use '.' for decimals.", 2);
        }

        return numbd;
    }


    // Functions for doing basic calculations:
    private static String addTwoNumbers(BigDecimal num1, BigDecimal num2) {
        BigDecimal result = num1.add(num2);
        result = result.stripTrailingZeros();
        return result.toPlainString();
    }

    private static String subtractTwoNumbers(BigDecimal num1, BigDecimal num2) {
        BigDecimal result = num1.subtract(num2);
        result = result.stripTrailingZeros();
        return result.toPlainString();
    }

    private static String multiplyTwoNumbers(BigDecimal num1, BigDecimal num2) {
        BigDecimal result = num1.multiply(num2);
        result = result.stripTrailingZeros();
        return result.toPlainString();
    }

    private static String divideTwoNumbers(BigDecimal num1, BigDecimal num2) {
        BigDecimal result = num1.divide(num2,10,BigDecimal.ROUND_HALF_UP);
        result = result.stripTrailingZeros();
        return result.toPlainString();
    }


    public static void main(String args[]) {

        System.out.println(welcomeMsg);
        
        // Get mode of operation and numbers from command line.
        int modeofoperation = initialiseModeOfOperation(args);
        initialiseNumbers(args);

        // Initiate result-variable as a string.
        String result = new String("");

        // Do the math!
        // checkArguments(args) returns an int with the mode of operation or a fatal error, if the arguments are invalid.

        switch ( modeofoperation )
        {
            case 1:
                result = addTwoNumbers(number1bd,number2bd);
                System.out.println(number1s + " added to " + number2s + " equals " + result);
                break;
            case 2:
                result = subtractTwoNumbers(number1bd,number2bd);
                System.out.println(number2s + " subtracted from " + number1s + " equals " + result);
                break;
            case 3:
                result = multiplyTwoNumbers(number1bd,number2bd);
                System.out.println(number1s + " multiplied by " + number2s + " equals " + result);
                break;
            case 4:
                result = divideTwoNumbers(number1bd,number2bd);
                System.out.println(number1s + " divided by " + number2s + " equals " + result);
                
                break;
        }

        // Print an extra line.
        System.out.println("");

    }

}
