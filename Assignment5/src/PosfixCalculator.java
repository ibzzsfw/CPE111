
/**
 * Assignment 5.2's class
 * Change the expression and calculate.
 * 
 * @author Suppakorn Rakna (63070501061)
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PosfixCalculator {

    /**
     * Important data set used in many methods.
     */
    static class Data {

        static Stack<String> operatorStack = new Stack<String>();
        static Stack<Double> numberStack = new Stack<Double>();
        static ArrayList<String> posfixQueue = new ArrayList<String>();
    }

    /**
     * Convert infix form to posfix form.
     * 
     * @param state The combination of state of each token such as state (1+1) will
     *              be 71218.
     * @param token List of expressions, especially infix.
     */
    public static void infixToPosfix(String state, List<String> token) {

        int i = 0;
        int group = 0;
        int prior = 0;
        String buff = "\0";

        state = "7" + state;
        token.add(0, "(");
        token.add(")");
        Data.operatorStack.clear();
        Data.posfixQueue.clear();
        for (i = 0; i < token.size(); i++) {

            group = Integer.parseInt(String.valueOf(state.charAt(i)));

            if (group == 1) {
                Data.posfixQueue.add(token.get(i));
            } else if (group >= 2 && group <= 6) {
                do {
                    String priority = "1+1-2*2/3^";
                    buff = Data.operatorStack.peek();
                    if (buff.equals("-")) {
                        prior = 2;
                    } else {
                        prior = TokenAnalysis.State.stateOf(buff);
                    }
                    if (prior >= group && prior <= 6) {
                        if (group == 2 && prior == 2) {
                            if (priority.charAt(priority.indexOf(buff) - 1) >= priority
                                    .charAt(priority.indexOf(token.get(i)) - 1)) {
                                buff = Data.operatorStack.pop();
                                Data.posfixQueue.add(buff);
                            } else {
                                break;
                            }
                        } else {
                            buff = Data.operatorStack.pop();
                            Data.posfixQueue.add(buff);
                        }
                    }
                } while (prior >= group && prior <= 6);
                Data.operatorStack.push(token.get(i));
            } else if (group == 7) {
                Data.operatorStack.push(token.get(i));
            } else if (group == 8) {
                do {
                    buff = Data.operatorStack.pop();
                    if (!buff.equals("(")) {
                        Data.posfixQueue.add(buff);
                    }
                } while (!buff.equals("("));
            }
        }
    }

    /**
     * Calculate expression.
     * 
     * @param ans             answer.
     * @param posfixQueue     The queue of posfix expression.
     * @param calculatorError Validate during calculations, especially for dealing
     *                        with calculations with mathematic limitations
     *                        (IndeterminateFormException and OutOfDomainException).
     * @return new answer. (same variable)
     */
    public static double calculatePosfix(double ans, boolean calculatorError) {

        int i = 0;
        int group = 0;
        double prevAns = 0.0;
        double num = 0.0, num1 = 0.0, num2 = 0.0;
        String token = new String("");

        prevAns = ans;

        for (i = 0; i < Data.posfixQueue.size(); i++) {

            token = Data.posfixQueue.get(i);

            if (token.equals("-")) {
                group = 2;
            } else {
                group = TokenAnalysis.State.stateOf(token);
            }

            if (group == 1) {
                num1 = parseDouble(token, ans);
                Data.numberStack.push(num1);
            } else if (group == 2) {
                num1 = Data.numberStack.pop();
                num2 = Data.numberStack.pop();
                if (token.equals("+")) {
                    Data.numberStack.push(num2 + num1);
                } else if (token.equals("-")) {
                    Data.numberStack.push(num2 - num1);
                } else if (token.equals("*")) {
                    Data.numberStack.push(num2 * num1);
                } else if (token.equals("/")) {
                    if (num1 == 0) {
                        System.out.println("answer> error : IndeterminateFormException " + num2 + "/" + num1);
                        calculatorError = true;
                    } else {
                        Data.numberStack.push(num2 / num1);
                    }
                } else if (token.equals("^")) {
                    if (num1 == 0 && num2 == 0) {
                        System.out.println("answer> error : IndeterminateFormException " + num2 + "^" + num1);
                        calculatorError = true;
                    } else {
                        Data.numberStack.push(Math.pow(num2, num1));
                    }
                }
            } else if (group == 5) {
                num1 = Data.numberStack.pop();
                Data.numberStack.push(-num1);
            } else if (group == 6) {
                if (token.equalsIgnoreCase("sin")) {
                    num = Math.sin(Data.numberStack.pop() * Math.PI / 180);
                    Data.numberStack.push(num);
                } else if (token.equalsIgnoreCase("cos")) {
                    num = Math.cos(Data.numberStack.pop() * Math.PI / 180);
                    Data.numberStack.push(num);
                } else if (token.equalsIgnoreCase("tan")) {
                    if (Math.cos(Data.numberStack.peek() * Math.PI / 180) == 0) {
                        System.out.println("answer> error : OutOfDomainException of tan");
                        calculatorError = true;
                    } else {
                        num = Math.tan(Data.numberStack.pop() * Math.PI / 180);
                        Data.numberStack.push(num);
                    }
                } else if (token.equalsIgnoreCase("asin")) {
                    if (Data.numberStack.peek() < -1 || Data.numberStack.peek() > 1) {
                        System.out.println("answer> error : OutOfDomainException of asin");
                        calculatorError = true;
                    } else {
                        num = 180 * Math.asin(Data.numberStack.pop()) / Math.PI;
                        Data.numberStack.push(num);
                    }
                } else if (token.equalsIgnoreCase("acos")) {
                    if (Data.numberStack.peek() < -1 || Data.numberStack.peek() > 1) {
                        System.out.println("answer> error : OutOfDomainException of acos");
                        calculatorError = true;
                    } else {
                        num = 180 * Math.acos(Data.numberStack.pop()) / Math.PI;
                        Data.numberStack.push(num);
                    }
                } else if (token.equalsIgnoreCase("atan")) {
                    num = 180 * Math.atan(Data.numberStack.pop()) / Math.PI;
                    Data.numberStack.push(num);
                } else if (token.equalsIgnoreCase("sqrt")) {
                    if (Data.numberStack.peek() < 0) {
                        System.out.println("answer> error : OutOfDomainException " + Data.numberStack.peek() + " < 0");
                        calculatorError = true;
                    } else {
                        num = Math.sqrt(Data.numberStack.pop());
                        Data.numberStack.push(num);
                    }
                } else if (token.equalsIgnoreCase("log")) {
                    if (Data.numberStack.peek() <= 0) {
                        System.out.println("answer> error : OutOfDomainException " + Data.numberStack.peek() + " <= 0");
                        calculatorError = true;
                    } else {
                        num = Math.log(Data.numberStack.pop());
                        Data.numberStack.push(num);
                    }
                } else if (token.equalsIgnoreCase("exp")) {
                    num = Math.exp(Data.numberStack.pop());
                    Data.numberStack.push(num);
                } else if (token.equalsIgnoreCase("abs")) {
                    num = Math.abs(Data.numberStack.pop());
                    Data.numberStack.push(num);
                }
            }
        }
        if (calculatorError) {
            return prevAns;
        } else {
            return Data.numberStack.pop();
        }
    }

    /**
     * Change String(number or constant) to double.
     * 
     * @param token String(number or constant) that we want to change the type.
     * @param ans   ans from calculation.
     * @return double value of the String.
     */
    private static double parseDouble(String token, double ans) {

        int i = 0;
        boolean stopIterate = false;
        String[] tokenList = { "pi", "E", "ans" };
        double[] parseList = { Math.PI, Math.E, ans };

        while (i < tokenList.length && !stopIterate) {
            stopIterate = token.equalsIgnoreCase(tokenList[i]);
            i++;
        }
        if (stopIterate == true) {
            return parseList[i - 1];
        } else {
            return Double.parseDouble(token);
        }
    }

    /**
     * It is an additional class used to display important variables. It is useful
     * to monitor small processes. In the event of a program error, it can be used
     * to find the fault. The method name is obvious to call when using.
     */
    static class Render {

        public static void stackOperator() {

            int i = 0;
            System.out.print("stack ");
            while (i < Data.operatorStack.size()) {
                System.out.print(Data.operatorStack.elementAt(i) + " ");
                i++;
            }
            System.out.println();
        }

        public static void stackNumber() {

            int i = 0;
            System.out.print("stack ");
            while (i < Data.numberStack.size()) {
                System.out.print(Data.numberStack.elementAt(i) + "  ");
                i++;
            }
            System.out.println();
        }

        public static void queue() {

            for (int i = 0; i < Data.posfixQueue.size(); i++) {
                System.out.print(Data.posfixQueue.get(i) + " ");
            }
            System.out.println();
        }

        public static void expression(List<String> token) {

            for (int i = 0; i < token.size(); i++) {
                System.out.print(token.get(i));
            }
            System.out.println();
        }
    }
}
