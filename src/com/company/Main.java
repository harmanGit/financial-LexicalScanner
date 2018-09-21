package com.company;

import java.util.Scanner;

/**
 * Harman Dhillon
 * OPL Homework 3
 * 9/19/18
 */
public class Main {
    public static void main(String[] args) {

        //User input
        Scanner sn = new Scanner(System.in);
        System.out.println("Check Checker!");
        System.out.println("Enter your check: {$xxx,xxx.xx}or \"q\" to quit or \"rsc\"to run system check");
        String userInput = sn.nextLine();

        //while loop to keep getting user input
        while (!userInput.equals("q")) {

            if (userInput.equals("rsc"))
                systemCheck();//method calls the runCurrencyChecker with some default test casees
            else if (userInput.contains("$"))
                runCurrencyChecker(userInput);//check to see if checks are in the proper format
            else
                System.out.println("Retry again!");
            System.out.println("Enter your check: {$xxx,xxx.xx}or \"q\" to quit or \"rsc\"to run system check");
            userInput = sn.nextLine();
        }
    }

    /**
     * Method takes in user input and checks to see if the format is correct. Pretends to be a scanner
     *
     * @param userInput String representing straight user input
     */
    private static void runCurrencyChecker(String userInput) {
        boolean results = false;// overall results of the currency checker
        boolean commaCheck = false;//represents if there are 3 digits before a comma

        int x = 0;//used to move through each case

        /**
         * case 0: checks for *
         * case 1: checks first 0 || end case
         * case 2: digit || end case
         * case 3: digit || end case
         * case 4: digit || end case
         * case 5: checks comma or (decimal || end case)
         * case 6: digit
         * case 7: digit
         * case 8: digit -->(can loop back to case 5) || end case
         */
        mainLoop:
        for (int i = 1; i < userInput.length() && userInput != null && userInput.charAt(0) == '$'; i++) {//going through the string, char by char
            char c = userInput.charAt(i);

            switch (x) {//scanner switch
                case 0://checks for * if there is a start it checks
                    if (c == '*')
                        x = 0;// if it a * then it will go back into the same switch case
                    else if (Character.isDigit(c))//else it will move along to the next one
                    {
                        x = 1;
                        --i;
                        break;
                    } else
                        break mainLoop;//break out of the for loop
                    break;
                case 1:// checks for digit, if its a zero
                    if (c == '0') {
                        if (userInput.charAt(i + 1) == '.')
                            results = properCents(userInput);//method checks to see if the cent value is proper
                        break mainLoop;
                    }
                    x++;//moving to the next spot in the switch
                case 2://checks for digits
                    if (Character.isDigit(c) && c != '0') //if its a valid digit, if its not set results to false and break
                        results = true;
                    else {
                        results = false;
                        break mainLoop;
                    }
                    x++;//moving along
                    break;
                case 3:
                    if (Character.isDigit(c))
                        results = true;
                    else if (c == '.') {//checking for .
                        results = properCents(userInput);//method checks to see if the cent value is proper
                        break mainLoop;
                    } else if (c == ',')//checking for comma, if it has one then a jump to 6th case is made
                    {
                        x = 6;
                        break;
                    } else {
                        results = false;
                        break mainLoop;
                    }
                    x++;
                    break;
                case 4: //digit check
                    if (Character.isDigit(c))
                        results = true;
                    else if (c == '.') {
                        results = properCents(userInput);
                        break mainLoop;
                    } else if (c == ',') {
                        x = 6;
                        break;
                    } else {
                        results = false;
                        break mainLoop;
                    }
                    x++;
                    break;
                case 5: //comma check
                    if (c == ',')
                        results = false; //if comma is there then set result = false because 3 more digits must follow ,
                    else if (c == '.') {
                        results = properCents(userInput);//method checks to see if the cent value is proper
                        break mainLoop;
                    } else if (commaCheck)//insures that a comma was placed after 3 digits
                        results = true;
                    else// else case if its not a , or a . or the end with commas place in the right location
                    {
                        results = false;
                        break mainLoop;
                    }
                    x++;
                    break;
                case 6:// digit check, same as others but there can be no decimal points, commas, and no true endcase
                    if (Character.isDigit(c))
                        results = false;
                    else {
                        results = false;
                        break mainLoop;
                    }
                    x++;
                    break;
                case 7:// digit check, same as others but there can be no decimal points, commas, and no true endcase
                    if (Character.isDigit(c))
                        results = false;
                    else {
                        results = false;
                        break mainLoop;
                    }
                    x++;
                    break;
                case 8:// digit check, same as others but there can be no decimal points, commas, and no true endcase.
                    // This case also jumps back to case 5 to check for commas, and decimals
                    if (Character.isDigit(c)) {
                        results = true;
                        commaCheck = true;
                        x = 5;
                    } else {
                        results = false;
                        break mainLoop;
                    }
                    break;
                default:
                    results = false;
                    break mainLoop;
            }
        }

        if (results)
            System.out.println(userInput + " is a valid check");
        else
            System.out.println(userInput + " is a invalid check");
    }

    /**
     * Method checks to see if the number of cents is proper of not.
     *
     * @param rawUserInput string representing raw user input
     * @return boolean true if its proper otherwise false
     */
    private static boolean properCents(String rawUserInput) {
        int rawUserInputLength = rawUserInput.length() - 1;// -1 because length() doesn't count from base 0
        double cents;
        if (rawUserInput.length() > 4)//checking to see if there enough values for ints
        {
            if (Character.isDigit(rawUserInput.charAt(rawUserInputLength - 3)) &&//checking to see if the char is a digit before the decimal
                    Character.isDigit(rawUserInput.charAt(rawUserInputLength - 1)) //checking tenths for valid #
                    && Character.isDigit(rawUserInput.charAt(rawUserInputLength))) //checking the hundredth for valid #
            {
                cents = Double.parseDouble(rawUserInput.substring(rawUserInputLength - 3));//converting into x.xx
                return cents >= 0.00;
            }
        }
        return false;
    }

    /**
     * Method that runs the runCurrencyChecker with some predefined test cases.
     */
    private static void systemCheck() {
        String[] testCases = {"0", "0.00", "$*$0.00", "$**000.10", "$0.100", "***0.00", "$.00", "$0.0", "$10.0", "$1000",
                "$100,00", "1,000", "$*********10,000,*00.01", "$*0.00", "$0.01", "$0.10", "$10.00", "$10.10", "$100",
                "$1,000", "$10,000", "$100,000", "$*********10,000,000.01"};
        for (int t = 0; t < testCases.length; t++)
            runCurrencyChecker(testCases[t]);
    }
}
////Better way of doing this I think
//
//    package com.company;
//
//import java.util.Scanner;
//
//    public class Main {
//
//        public static void main(String[] args) {
//
//            Scanner sn = new Scanner(System.in);
//            System.out.println("Check Checker!");
//            System.out.println("Enter anykey to continue");
//            String userInput = sn.nextLine();
//
//            while (!userInput.equals("q")) {
//                System.out.println("Enter your check: {$xxx,xxx.xx}or \"q\" to quit or \"rsc\"to run system check");
//                userInput = sn.nextLine();
//
//                if (userInput.equals("rsc"))
//                    systemCheck();
//                else if(userInput.contains("$"))
//                    runCurrencyChecker(userInput);
//                else
//                    System.out.println("Retry again!");
//
//            }
//        }
//
//        private static void runCurrencyChecker(String userInput) {
//            boolean isStar = true; //true if the characters are '*'
//            boolean nonZero = true; //true if the first digit is non zero
//            boolean goodValue = false; //true if the numbers are digits or ',' or '.'
//            boolean results = false;// overall results of the currency checker
//
//            if (userInput.charAt(0) == '$')//checking to see if the first char is a '$'
//            {
//                for (int i = 1; i < userInput.length() && userInput != null; i++) {//going through the string, char by char
//                    char c = userInput.charAt(i);
//                    if (userInput.charAt(i) == '$')//checking
//                        break;
//                    else if (nonZero && c == '0') {
//                        results = properCents(userInput);
//                        break;
//                    } else if (Character.isDigit(c) || c == ',' || c == '.') {
//                        goodValue = true;
//                        isStar = false;
//                        nonZero = false;
//                    } else if (isStar && c == '*')
//                        isStar = true;
//                    else {
//                        goodValue = false;
//                        break;
//                    }
//                }
//                if (!nonZero && goodValue)
//                    results = commaCheck(userInput);
//            }
//
//            if (results)
//                System.out.println(userInput + " is a valid input");
//            else
//                System.out.println(userInput + " is a invalid input");
//        }
//
//        /**
//         * Method checks to see if the cents value is proper
//         *
//         * @param rawUserInput string representing raw user input
//         * @return boolean, true if its proper else its false
//         */
//        private static boolean properCents(String rawUserInput) {
//            int rawUserInputLength;
//            double cents;
//            if (rawUserInput.length() - 1 > 3)//checking to see if there enough values for ints
//            {
//                rawUserInputLength = rawUserInput.length() - 1;// -1 because length() doesn't count from base 0
//                if (Character.isDigit(rawUserInput.charAt(rawUserInputLength - 3)) && //checking to see if the char is a digit before the decimal
//                        Character.isDigit(rawUserInput.charAt(rawUserInputLength - 1)) //checking tenths for valid #
//                        && Character.isDigit(rawUserInput.charAt(rawUserInputLength))) //checking the hundredth for valid #
//                {
//                    cents = Double.parseDouble(rawUserInput.substring(rawUserInputLength - 3));//converting into x.xx
//                    return cents >= 0.00;
//                }
//            }
//            return false;
//        }
//
//        /**
//         * Method checks to see if the commas are in the right place, and or if its just under $999
//         *
//         * @param rawUserInput String representing raw user input
//         * @return boolean true if commas are okay and false if they are not
//         */
//        private static boolean commaCheck(String rawUserInput) {
//            int i = rawUserInput.length() - 1;// -1 because length() doesn't count from base 0
//            int placeCommaCounter = 0;
//            if (rawUserInput.contains(".")) {//checking to see if the value contains a decimal
//                if (!properCents(rawUserInput))//returns false overall if the decimals are incorrect
//                    return false;
//                i = rawUserInput.length() - 4;
//            }
//            //reading the string backwards, length - 1 because length() doesnt count from = 0
//            for (; i > 0; i--) {
//                char c = rawUserInput.charAt(i);
//                if (c == '*' || c == '$')
//                    break;
//                if (Character.isDigit(c) && placeCommaCounter != 3)
//                    placeCommaCounter++;
//                else if (c == ',' && placeCommaCounter == 3)
//                    placeCommaCounter = 0;
//                else
//                    return false;
//            }
//            return true;
//        }
//
//        /**
//         * Runs generic pre made tests
//         */
//        private static void systemCheck() {
//            String[] testCases = {"0.00", "$*$0.00", "$0.100", "***0.00", "$.00", "$0.0", "$10.0", "$1000", "$100,00",
//                    "1,000", "$*********10,000,*00.01", "$0.00", "$0.01", "$0.10", "$10.00", "$10.10", "$100", "$1,000", "$10,000", "$100,000",
//                    "$*********10,000,000.01"};
//            for (int t = 0; t < testCases.length; t++)
//                runCurrencyChecker(testCases[t]);
//        }
//    }
//
//}
