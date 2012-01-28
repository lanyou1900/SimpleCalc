/**
 * Created by IntelliJ IDEA.
 * User: skovmand
 * Date: 28/01/12
 * Time: 12.10
 * My simple calculator!
 */

import java.math.BigDecimal;

public class SimpleCalc {
    
    public static void fatalError(String exitMsg, int errorCode) {
        System.out.println("Fatal Error " + errorCode + ": " + exitMsg);
        System.exit(errorCode);  
    }
    
    public static int checkArguments(String[] args) {
        if(args.length < 3)
            fatalError("Usage: SimpleCalc [add/subtract/multiply/divide] number1 number2", 1);


        // What is the mode of operation?

        int modeofoperation = 0;

        if (args[0].equalsIgnoreCase("add"))
            modeofoperation=1;
        else if (args[0].equalsIgnoreCase("subtract"))
            modeofoperation=2;
        else if (args[0].equalsIgnoreCase("multiply"))
            modeofoperation=3;
        else if (args[0].equalsIgnoreCase("divide"))
            modeofoperation=4;
        else
            fatalError("First argument not valid. Usage: SimpleCalc [add/multiply/divide/square] number1 number2", 2);

        return modeofoperation;
    }

    private static BigDecimal toBigDecimal(String num) {

        BigDecimal numbd = new BigDecimal(0);

        try {
            numbd = new BigDecimal(num);
            numbd = numbd.stripTrailingZeros();
        }
        catch (NumberFormatException e) {
            fatalError("Invalid input " + num + ". Must be a numeric value. Use '.' for decimals.", 3);
        }

        // Return the stripped value:
        return numbd;

    }

    public static void main( String args[] ) {

        System.out.println("SimpleCalc - The simple calculator.");

        // Check arguments
        int modeofoperation = checkArguments(args);

        // Initiate BigDecimal values from args[1] and args[2].
        BigDecimal number1bd = toBigDecimal(args[1]);
        BigDecimal number2bd = toBigDecimal(args[2]);

        // Create plain string-values for output (to make the code simpler later...)
        String number1s = new String( number1bd.toPlainString() );
        String number2s = new String( number2bd.toPlainString() );

        // Initiate result-variable
        BigDecimal result = new BigDecimal(0);
        String isRounded = new String("");

        // Do the math!
        // checkArguments(args) returns an int with the mode of operation or a fatal error, if the arguments are invalid.

        switch ( modeofoperation )
        {
            case 1:
                result = number1bd.add(number2bd);
                result = result.stripTrailingZeros();
                System.out.println(number1s + " added to " + number2s + " equals " + result.toPlainString());
                break;
            case 2:
                result = number1bd.subtract(number2bd);
                result = result.stripTrailingZeros();
                System.out.println(number2s + " subtracted from " + number1s + " equals " + result.toPlainString());
                break;
            case 3:
                result = number1bd.multiply(number2bd);
                result = result.stripTrailingZeros();
                System.out.println(number1s + " multiplied by " + number2s + " equals " + result.toPlainString());
                break;
            case 4:
                result = number1bd.divide(number2bd,10,BigDecimal.ROUND_HALF_UP);
                result = result.stripTrailingZeros();
                System.out.println(number1s + " divided by " + number2s + " equals " + result.toPlainString() + isRounded);
                
                break;
        }

    }

}
