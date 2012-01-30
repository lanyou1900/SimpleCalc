/**
 * Created by IntelliJ IDEA.
 * User: skovmand
 * Date: 28/01/12
 * Time: 12.10
 */

/**
 * Created by IntelliJ IDEA.
 * User: skovmand
 * Date: 28/01/12
 * Time: 12.10
 */

import java.math.BigDecimal;

public class SimpleCalc {
    
    // Initialise public version number and usage info.
    public static String welcomeMsg = "SimpleCalc - The simple calculator - v1.03";
    public static String usage = "Usage: SimpleCalc number1 [+-*/] number2. To multiply, use \\* instead of *.";
    
    // Initialise variables to be used in calculations.
    public static BigDecimal number1bd = BigDecimal.ZERO;
    public static BigDecimal number2bd = BigDecimal.ZERO;
    
    // Initialise plain string-values for output (to make the code simpler later...)
    public static String number1s = "";
    public static String number2s = "";
    
    
    public static void fatalError(String exitMsg, int errorCode) {
        System.out.println("Fatal Error " + errorCode + ": " + exitMsg);
        System.out.println(usage);
        System.exit(errorCode);  
    }
    
    private static int initialiseModeOfOperation(String[] args) {
        // Check for arguments.
        if (args.length < 3)
            fatalError("Not enough arguments given.", 1);

        // The first and third argument should be a number, the second a single char.
        // What is the mode of operation?
        int modeOfOperation = 0;
        
        if (args[1].equalsIgnoreCase("+"))
            modeOfOperation=1;
        else if (args[1].equalsIgnoreCase("-"))
            modeOfOperation=2;
        else if (args[1].equalsIgnoreCase("*"))
            modeOfOperation=3;
        else if (args[1].equalsIgnoreCase("/"))
            modeOfOperation=4;
        else
            fatalError("Invalid operator '" + args[1] + "'. Use +, -, * or /", 1);
        
        return modeOfOperation;
    }
    
    private static void initialiseNumbers(String[] args) {
        // The first and third argument should be a number, the second a single char.
        
        // Are the two numbers actually numbers?
        // Initialise the two numbers into number1bd and number2bd:
        
        // Initialise number1bd and number2bd from args[0] and args[2].
        number1bd = toBigDecimal(args[0]).stripTrailingZeros();
        number2bd = toBigDecimal(args[2]).stripTrailingZeros();
        
        // Create plain text versions of BigDecimals
        number1s = number1bd.toPlainString();
        number2s = number2bd.toPlainString();
        
    }
    
    private static BigDecimal toBigDecimal(String num) throws NumberFormatException {
        // Returns a BigDecimal from a String or handles a NumberFormatException.
        
        try {
            return new BigDecimal(num);
        } catch (NumberFormatException e) {
            fatalError("Invalid number '" + num + "'. Must be a numeric value. Use '.' for decimals.", 2);
        }
        
        return BigDecimal.ZERO;
    }
    
    
    // Functions for doing basic calculations:
    private static String addTwoNumbers(BigDecimal num1, BigDecimal num2) {
        return num1.add(num2)
                   .stripTrailingZeros()
                   .toPlainString();
    }
    
    private static String subtractTwoNumbers(BigDecimal num1, BigDecimal num2) {
        return num1.subtract(num2)
                   .stripTrailingZeros()
                   .toPlainString();
    }
    
    private static String multiplyTwoNumbers(BigDecimal num1, BigDecimal num2) {
        return num1.multiply(num2)
                   .stripTrailingZeros()
                   .toPlainString();
    }
    
    private static String divideTwoNumbers(BigDecimal num1, BigDecimal num2) {
        return num1.divide(num2, 10, BigDecimal.ROUND_HALF_UP)
                   .stripTrailingZeros()
                   .toPlainString();
    }
    
    
    public static void main(String args[]) {
        
        System.out.println(welcomeMsg);
        
        // Get mode of operation and numbers from command line.
        int modeOfOperation = initialiseModeOfOperation(args);
        initialiseNumbers(args);
        
        // Initiate result-variable as a string.
        String result;
        
        // Do the math!
        // checkArguments(args) returns an int with the mode of operation or a fatal error, if the arguments are invalid.
        
        switch ( modeOfOperation )
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
        System.out.println();
        
    }

    
}