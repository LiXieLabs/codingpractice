import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * 150. Evaluate Reverse Polish Notation (https://leetcode.com/problems/evaluate-reverse-polish-notation/description/)
 */
public class EvaluateReversePolishNotation {

    /************* Solution 1: by Stack ****************/
    /**
     * Deque 也行，操作一样
     *
     * Time: O(N)   Space: O(N)
     */
    private static final Set<String> OPERATORS = new HashSet<>(Arrays.asList("+", "-", "*", "/"));

    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        for (String token : tokens) {
//            if ("+".equals(token) || "-".equals(token) || "*".equals(token) || "/".equals(token)) {
            if (OPERATORS.contains(token)) {
                int n2 = stack.pop(), n1 = stack.pop();
                // try with new switch syntax (JDK14+) instead of using if conditions
                switch (token) {
                    case "+" -> stack.push(n1 + n2);
                    case "-" -> stack.push(n1 - n2);
                    case "*" -> stack.push(n1 * n2);
                    case "/" -> stack.push(n1 / n2);
                }
            } else {
                stack.push(Integer.valueOf(token));
            }
        }
        return stack.pop();
    }

    public static void main(String[] args) {
        EvaluateReversePolishNotation solution = new EvaluateReversePolishNotation();
        System.out.println(solution.evalRPN(new String[]{"2","1","+","3","*"})); // 9
        System.out.println(solution.evalRPN(new String[]{"4","13","5","/","+"})); // 6
        System.out.println(solution.evalRPN(new String[]{"10","6","9","3","+","-11","*","/","*","17","+","5","+"})); // 22
    }
}
