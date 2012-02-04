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
import java.math.BigInteger;

public class SimpleCalc {
    
    // Initialise public version number and usage info.
    public static String welcomeMsg = "SimpleCalc - The simple calculator - v1.1";
    public static String usage = "Usage: SimpleCalc [math expression] (precision). Use only +, -, * and /.";

    // Default Precision Variable
    public static Integer outputPrecision = 10;
    
    
    public static void fatalError(String exitMsg, int errorCode) {
        if (exitMsg.length() > 0)   // if the exitMsg is not ""
            System.out.println("Fatal Error " + errorCode + ": " + exitMsg);

        System.out.println(usage);
        System.out.println("");

        System.exit(errorCode);  
    }

    private static void initialiseArgumentsAndSetPrecision(String[] args) {
        // Check for arguments.
        if (args.length < 1 || args[0].equals("?") || args[0].equals("--help"))
            fatalError("", 1);

        if(args.length > 1 && isInteger(args[1]))
            outputPrecision = new Integer(args[1]);
        else if(args.length > 1 && !isInteger(args[1]))
            fatalError("Invalid precision. Must be an integer.",1);
    }
    
    private static BigDecimal toBigDecimal(String num) throws NumberFormatException {
        // Returns a BigDecimal from a String or handles a NumberFormatException.
        
        try {
            return new BigDecimal(num);
        } catch (NumberFormatException e) {
            fatalError("Invalid number '" + num + "'. Must be a numeric value. Use '.' for decimals.", 2);
        }
        
        return BigDecimal.ZERO; // is never called.
    }

    private static boolean isBigDecimal(String num) {
        // Test if a number is a BigDecimal.

        BigDecimal testVariable = BigDecimal.ZERO;

        try {
            testVariable = new BigDecimal(num);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    private static boolean isInteger(String num) {
        // Test if a number is an integer..

        Integer testVariable = 0;

        try {
            testVariable = new Integer(num);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }


    private static String [] splitStringAtPlusSigns(String input) {
        // Replace '-' with '+-' to be able to split the string at '+' for both subtraction and addition.
        // Afterwards, split the array at '+'

        String replacedPlusMinusString = "";

        if ( input.substring(0,1).equals("-") ) {
            // If first char is "-", it shouldn't be replaced (or else the string with start like "+-2+2" and the program will crash).
            replacedPlusMinusString = "-" + input.substring(1).replaceAll("-","+-");
        }
        else {
            replacedPlusMinusString = input.replaceAll("-","+-");
        }

        // Split string at '+'
        return replacedPlusMinusString.split("\\+");
    }
    
    private static String [] splitStringAtMultiplicationDivisionSigns(String input) {

        // Replace the "/" with "*1/" - to turn the division into multiplication.
        String replacedMultiplicationDivisionString = input.replaceAll("/","*1/");

        // Split the string at "*"
        return replacedMultiplicationDivisionString.split("\\*");
    }

    private static String calculateNumericValue (String input) {
        // Try to calculate the numeric value of the input string.
        // For now, assume that it contains only multiplication and division.

        // Replace the "/" with "*1/" - to turn the division into multiplication.
        // Split the string at "*"
        String [] splitArrayAtStar = splitStringAtMultiplicationDivisionSigns(input);

        // Initiate tempMultiplicationValue as splitArrayAtStarOrDash[0] (to get an initial value for multiplication).
        BigDecimal tempMultiplicationValue = new BigDecimal(splitArrayAtStar[0]);

        // Cycle through the array split at '*' and calculate the product.
        // Notice the loop starts at j=1 - that is because tempMultiplicationValue is set as splitArrayAtStar[0].
        for (int j=1 ; j<splitArrayAtStar.length ; j++)
        {

            // Check if the value is a BigDecimal. If not, assume that it is because it contains "/" (dashes) and calculate the division.
            if (!isBigDecimal(splitArrayAtStar[j]))
            {
                // Split the number at "/"
                String [] splitArrayAtDash = splitArrayAtStar[j].split("/");

                // Initiate temporary BigDecimal value as first array value.
                BigDecimal tempDivisionValue = new BigDecimal(splitArrayAtDash[0]);

                // Initiate the second value as a BigDecimal.
                BigDecimal tempStringAsBigDecimal = new BigDecimal(splitArrayAtDash[1]);

                // Do the division. As all the divisions are done as "1/x", there are always only two values here.
                tempDivisionValue = tempDivisionValue.divide(tempStringAsBigDecimal, outputPrecision*100, BigDecimal.ROUND_HALF_UP);

                // Put the value back in the splitArrayAtStar[j].
                splitArrayAtStar[j] = tempDivisionValue.toPlainString();
            }

            // Turn the value at splitArrayAtStar[j] into a BigDecimal.
            BigDecimal tempStringAsBigDecimal = new BigDecimal(splitArrayAtStar[j]);

            // As the loops iterates, tempMultiplicationValue will be the product of the array values.
            tempMultiplicationValue = tempMultiplicationValue.multiply(tempStringAsBigDecimal);
        }

        // Now the product of the splitArrayAtPlus-array value is calculated. Put it back into the array.
        return tempMultiplicationValue.toPlainString();
    }

    public static void main(String args[]) {

        System.out.println(welcomeMsg);
        initialiseArgumentsAndSetPrecision(args);

        System.out.println("Precision is " + outputPrecision + " decimals.");


        // Parse the input string.

        // 1:
        // Replace '-' with '+-' to be able to split the string at '+' for both subtraction and addition.
        // Afterwards, split the array at '+'
        // The result is an array with values to be added together.
        String [] splitArrayAtPlus = splitStringAtPlusSigns(args[0]);

        
        // 2:
        // Calculate the numeric value of each entry in the splitArrayAtPlus-array.
        // When all entries are numeric, the sum can be calculated.

        for (int i=0 ; i<splitArrayAtPlus.length ; i++)
        {
            // Two scenarios:
            // 1: Either the string is a BigDecimal (if there are no "/" and "*"-signs in the string)
            // 2: The string contains contains non-numeric chars.

            // Use boolean isBigDecimal to check if the number is a BigDecimal.
            // If it is not a BigDecimal (ie. a numeric value), try to calculate the numeric value.

            if ( !isBigDecimal(splitArrayAtPlus[i]) )
                splitArrayAtPlus[i] = calculateNumericValue( splitArrayAtPlus[i] );
        }


        // Calculate the sum of all values in splitArrayAtPlus:

        BigDecimal finalSum = BigDecimal.ZERO;

        for (int i = 0 ; i<splitArrayAtPlus.length ; i++)
            finalSum = finalSum.add( toBigDecimal(splitArrayAtPlus[i]) );

        // Divide with one and round to outputPrecision to get the final result
        finalSum = finalSum.divide(BigDecimal.ONE, outputPrecision, BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
        
        // Print the result.
        System.out.println(args[0] + " = " + finalSum.toPlainString());

        // Print an extra line...
        System.out.println();
        
    }

    
}