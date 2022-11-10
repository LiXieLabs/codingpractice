import java.util.Stack;

public class EvaluateReversePolishNotation {

    /************* Solution 1: by Stack ****************/
    /**
     * Time: O(N)   Space: O(N)
     */
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        for (String token : tokens) {
            if ("+".equals(token) || "-".equals(token) || "*".equals(token) || "/".equals(token)) {
                int n2 = stack.pop(), n1 = stack.pop();
                if ("+".equals(token)) stack.push(n1 + n2);
                if ("-".equals(token)) stack.push(n1 - n2);
                if ("*".equals(token)) stack.push(n1 * n2);
                if ("/".equals(token)) stack.push(n1 / n2);
            } else {
                stack.push(Integer.valueOf(token));
            }
        }
        return stack.pop();
    }

    public static void main(String[] args) {
        EvaluateReversePolishNotation solution = new EvaluateReversePolishNotation();
        System.out.println(solution.evalRPN(new String[]{"2","1","+","3","*"}));
        System.out.println(solution.evalRPN(new String[]{"4","13","5","/","+"}));
        System.out.println(solution.evalRPN(new String[]{"10","6","9","3","+","-11","*","/","*","17","+","5","+"}));
    }
}
