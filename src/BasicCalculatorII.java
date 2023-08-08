import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.Stream;

/**
 * 227. Basic Calculator II (https://leetcode.com/problems/basic-calculator-ii/)
 */
public class BasicCalculatorII {

    /************** Solution 1: stack 记录 +- 运算，res operator operand 记录 *\/ 运算 **************/
    /**
     * Time: O(N)  Space: O(N)
     */
    public int calculate1(String s) {
        Deque<Integer> stack = new ArrayDeque<>();
        int res = 1;
        int operand = 0;
        char operator = '*';
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                operand = operand * 10 + (c - '0');
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                if (operator == '*') {
                    res *= operand;
                } else {
                    res /= operand;
                }
                operand = 0;
                operator = '*';
                if (c == '+' || c == '-') {
                    stack.push(res);
                    stack.push(c == '+' ? 1 : -1);
                    res = 1;
                } else {
                    operator = c;
                }
            }
        }

        if (operator == '*') {
            res *= operand;
        } else {
            res /= operand;
        }
        stack.push(res);
        res = 0;
        while (!stack.isEmpty()) {
            int num = stack.pop();
            int sign = stack.isEmpty() ? 1 : stack.pop();
            res += sign * num;
        }
        return res;

    }

    /************** Solution 2: 优化 **************/
    /**
     * sum + sign 记录 +- 运算的临时结果
     * res operator operand 记录 *\/ 运算的临时结果
     * 只有两层
     *
     * 任意时刻，只有 sum + sign * (res operator operand)
     * default value: 0 + 1 * (1 * 0)
     *
     * Time: O(N)  Space: O(1)
     */
    public int calculate2(String s) {
        // sum + sign 记录 +- 运算的临时结果
        int sum = 0;
        int sign = 1;
        // res operator operand 记录 */ 运算的临时结果
        int res = 1;
        int operand = 0;
        char operator = '*';

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                operand = operand * 10 + (c - '0');
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                // 遇到新的符号，先把 */ 层累进到 res
                if (operator == '*') {
                    res *= operand;
                } else {
                    res /= operand;
                }
                // 并且将 */ 层 res 之外的归零
                operand = 0;
                operator = '*';
                if (c == '+' || c == '-') {
                    // 如果新符号是 +-，累进 +- 层
                    sum += sign * res;
                    // 并将 +- 层变量赋值 & 归default
                    sign = c == '+' ? 1 : -1;
                    res = 1;
                } else {
                    // 如果新符号是 */，将 */ 层变量赋值
                    operator = c;
                }
            }
        }

        // */层累进
        if (operator == '*') {
            res *= operand;
        } else {
            res /= operand;
        }
        // +-层累进
        sum += sign * res;

        return sum;
    }

    /************** Solution 2: 类似 LC772 的解法 ********************/
    /**
     * total 为当前 + - 层中的当前总和
     * cur 为当前 * / 层中的当前总积
     * n 为当前数字
     *
     * Time: O(N)  Space: O(1)
     */
    public int calculate(String s) {
        int total = 0, cur = 0, n = 0;
        char lastOperator = '+';
        for (int i = 0; i <= s.length(); i++) {
            int finalI = i;
            if (i == s.length()
                    || Stream.of('+', '-', '*', '/').anyMatch(c -> c == s.charAt(finalI))) {
                // 先根据 last operator 操作 cur 和 n
                if (lastOperator == '*') {
                    cur *= n;
                } else if (lastOperator == '/') {
                    cur /= n;
                } else {
                    total += cur;
                    int sign = lastOperator == '-' ? -1 : 1;
                    cur = sign * n;
                }
                n = 0;
                // 再根据当前 char 决定运算层
                if (i == s.length()) { // 当前层结束，且为最外层，返回结果
                    return total + cur;
                } else { // 继续当前层
                    lastOperator = s.charAt(i);
                }
            } else if (Character.isDigit(s.charAt(i))) {
                n = n * 10 + (s.charAt(i) - '0');
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        BasicCalculatorII solution = new BasicCalculatorII();
        System.out.println(solution.calculate("3+2*2")); // 7
        System.out.println(solution.calculate(" 3/2 ")); // 1
        System.out.println(solution.calculate(" 3+5 / 2 ")); // 5
        System.out.println(solution.calculate("1-1+1")); // 1
        System.out.println(solution.calculate("4/3+2")); // 3
    }
}