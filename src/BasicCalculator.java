import java.util.ArrayDeque;
import java.util.Deque;

public class BasicCalculator {

    // Parsing Problem 通用解法
    // https://leetcode.com/problems/basic-calculator/discuss/2017431/Stop-hating-parsing-problems-and-start-having-fun

    /************ Solution 1: 把所有+/-想成数字的正负号，永远做加法 + stack保存当前括号外的临时解和符号 *************/
    /**
     * Time: O(N)   Space: O(N)
     */
    public int calculate(String s) {
        Deque<Integer> stack = new ArrayDeque<>();
        int operand = 0;
        int res = 0;
        int sign = 1;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                operand = operand * 10 + (c - '0');
            } else if (c == '+' || c == '-') {
                res += sign * operand;
                operand = 0;
                sign = c == '+' ? 1 : -1;
            } else if (c == '(') {
                stack.push(res);
                stack.push(sign);
                res = 0;
                sign = 1;
            } else if (c == ')') {
                res += sign * operand;
                res *= stack.pop();
                res += stack.pop();
                operand = 0;
            }
        }
        return res + sign * operand;
    }

    public static void main(String[] args) {
        BasicCalculator solution = new BasicCalculator();
        System.out.println(solution.calculate("1 + 1"));
        System.out.println(solution.calculate(" 2-1 + 2 "));
        System.out.println(solution.calculate( "(1+(4+5+2)-3)+(6+8)"));
        System.out.println(solution.calculate( "-(2+3)"));
        System.out.println(solution.calculate( "1+(-1)+2"));

    }
}