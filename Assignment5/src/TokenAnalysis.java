
/**
 * The TokenAnalysis class use to analyze the expression received from the user
 * and give the state using the validation.
 * 
 * @author Suppakorn Rakna (63070501061)
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TokenAnalysis {

    /**
     * Get a infix expression form user then convert to an ArrayList of String named
     * "token".
     * 
     * @param scanner Scanner obj. from Driver code (to avoid NoLineFound).
     * @return An ArrayList named "token" to handle in calculator and state.
     */
    public static ArrayList<String> stringToArray(Scanner scanner) {

        System.out.print("expression> ");
        String infixExpression = scanner.nextLine();
        infixExpression = transform(infixExpression);
        return new ArrayList<String>(Arrays.asList(infixExpression.trim().split("\\s+")));
    }

    /**
     * Add white space before and after an operator or a negative sign.
     * 
     * @param string An operator or a negative sign that we want to transform.
     * @return New String of input with left and right white space.
     */
    private static String transform(String string) {

        string = string.replace("+", " + ");
        string = string.replace("-", " - ");
        string = string.replace("*", " * ");
        string = string.replace("/", " / ");
        string = string.replace("^", " ^ ");
        string = string.replace("(", " ( ");
        string = string.replace(")", " ) ");
        string = string.replace("~", " ~ ");
        string = string.replace("!", " ! ");
        return string;
    }

    /**
     * Set the state of The token received from the user. By getting the state of
     * each of them arranged in a string. It is then used to verify the accuracy
     * according to the work of the finite state automata.
     */
    static class State {

        /**
         * Determine state(group) of a token. For example stateOf("+") is 2.
         * 
         * @param string a token.
         * @return A number of group.
         */
        public static int stateOf(String string) {

            if (Validate.number(string) || Validate.constant(string)) {
                return 1;
            } else if (Validate.negativeSign(string)) {
                return 5;
            } else if (Validate.operator(string)) {
                return 2;
            } else if (Validate.function(string)) {
                return 6;
            } else if (string.matches("[(]")) {
                return 7;
            } else if (string.matches("[)]")) {
                return 8;
            } else if (Validate.terminate(string)) {
                return 9;
            } else {
                return -1;
            }
        }

        /**
         * Gets the state of each token and changes it to a string for validation.
         * 
         * In tihs method if found a state that is a negative sign, change the token to
         * "-" to "~". And Check the correctness of the parentheses.
         * 
         * Use this method in validate method.
         * 
         * @param token List of expressions, especially infix.
         * @return String of a number of group.
         */
        private static String getState(List<String> token) {

            int i = 0;
            int cur = 0, prev = 0;
            int leftParentheses = 0, rightParentheses = 0;
            String state = new String("");

            while (i < token.size()) {
                if ((prev == 1 || prev == 8) && token.get(i).matches("-")) {
                    cur = 2;
                } else {
                    cur = stateOf(token.get(i));
                }
                if (cur == 5) {
                    token.remove(i);
                    token.add(i, "~");
                }
                if (cur == 7) {
                    leftParentheses++;
                }
                if (cur == 8) {
                    rightParentheses++;
                }
                if (rightParentheses > leftParentheses) {
                    return "false";
                }

                state += String.valueOf(cur);
                prev = cur;
                i++;
            }
            if (leftParentheses == rightParentheses && !state.contains("-1")) {
                return state;
            } else {
                return "false";
            }
        }

        /**
         * Finite state automata validation.
         * 
         * @param token the token that we want to check.
         * @return A string of a number of groups if it is correct. or "error" if it is
         *         wrong.
         */
        public static String validate(List<String> token) {

            int i = 0;
            boolean check = true;
            String state = getState(token);
            String[] nextState = { "1567", "289", "1567", "", "", "167", "7", "1567", "289", "" };
            state += "8";
            if (!state.equalsIgnoreCase("false")) {
                while (check && i < state.length() - 1) {
                    check = nextState[Integer.parseInt(String.valueOf(state.charAt(i)))]
                            .contains(String.valueOf(state.charAt(i + 1)));
                    i++;
                }
                if (i == state.length() - 1) {
                    return state;
                }
            }
            return "error";
        }
    }

    /**
     * Gets the string (token called through token.get (<index>)) to analyze the
     * type.
     */
    static class Validate {

        /**
         * Used to analyze whether a token is a real number or not.
         * 
         * @param token the token that we want to check.
         * @return A results
         */
        private static boolean number(String token) {

            boolean check = true;

            try {
                Double.parseDouble(token);
            } catch (Exception e) {
                check = false;
            }
            return check;
        }

        /**
         * Used to analyze whether a token is a function or not.
         * 
         * @param token the token that we want to check.
         * @return A results
         */
        private static boolean function(String token) {

            int i = 0;
            String[] function = { "sin", "cos", "tan", "asin", "acos", "atan", "sqrt", "log", "exp", "abs" };
            boolean check = false;

            while (i < function.length && !check) {
                check = token.equalsIgnoreCase(function[i++]);
            }

            return check;
        }

        /**
         * Used to analyze whether a token is an operator or not.
         * 
         * @param token the token that we want to check.
         * @return A results
         */
        private static boolean operator(String token) {

            return (token.matches("[-+*/^]"));
        }

        /**
         * Used to analyze whether a token is a negative sign or not.
         * 
         * @param token the token that we want to check.
         * @return A results
         */
        private static boolean negativeSign(String token) {

            return (token.matches("[-~!]"));
        }

        /**
         * Used to analyze whether a token is a constant or not.
         * 
         * @param token the token that we want to check.
         * @return A results
         */
        private static boolean constant(String token) {

            int i = 0;
            String[] constant = { "ans", "pi", "e" };
            boolean check = false;

            while (i < constant.length && !check) {
                check = token.equalsIgnoreCase(constant[i++]);
            }

            return check;
        }

        /**
         * Used to analyze whether a token is a terminate phrase or not.
         * 
         * @param token the token that we want to check.
         * @return A results
         */
        private static boolean terminate(String token) {

            int i = 0;
            String[] terminate = { "end", "exit", "quit" };
            boolean check = false;

            while (i < terminate.length && !check) {
                check = token.equalsIgnoreCase(terminate[i++]);
            }

            return check;
        }
    }
}
