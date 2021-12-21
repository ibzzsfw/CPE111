import java.util.List;
import java.util.Scanner;

public class Assignment5 {

    public static void main(String[] args) {

        double ans = 0.0;
        boolean calculatorError = false;
        String state = new String("");
        List<String> token;
        Scanner scanner = new Scanner(System.in);

        do {
            calculatorError = false;
            token = TokenAnalysis.stringToArray(scanner);
            state = TokenAnalysis.State.validate(token);
            if (TokenAnalysis.State.stateOf(token.get(0)) != 9) {
                if (token.get(0).equalsIgnoreCase("help") || token.get(0).equalsIgnoreCase("token")) {
                    help();
                } else if (!state.equalsIgnoreCase("error")) {
                    PosfixCalculator.infixToPosfix(state, token);
                    ans = PosfixCalculator.calculatePosfix(ans, calculatorError);
                    if (ans * Math.pow(10, 10) % Math.pow(10, 10) == 0 && !calculatorError) {
                        System.out.println("answer> " + (int) ans);
                    } else if (!calculatorError) {
                        System.out.println("answer> " + ans);
                    }
                } else {
                    System.out.println("answer> error");
                }
            }
        } while (TokenAnalysis.State.stateOf(token.get(0)) != 9);
        System.out.print("End program\n\n");
        scanner.close();
    }

    private static void help() {

        System.out.println(
                "answer> token = sin, cos, tan, asin, acos, atan, sqrt, log, exp, abs +, -, *, /, ^, (, ), pi, ans");
    }
}