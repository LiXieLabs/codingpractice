import java.util.ArrayDeque;
import java.util.Deque;

public class BasicCalculatorII {

    /************** Solution 1: stack 记录 +- 运算，res operator operand 记录 *\/ 运算 **************/
    /**
     * Time: O(N)  Space: O(1)
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
    public int calculate(String s) {
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

    public static void main(String[] args) {
        BasicCalculatorII solution = new BasicCalculatorII();
        System.out.println(solution.calculate("3+2*2"));
        System.out.println(solution.calculate(" 3/2 "));
        System.out.println(solution.calculate(" 3+5 / 2 "));
        System.out.println(solution.calculate("1-1+1"));
        System.out.println(solution.calculate("4/3+2"));
    }
}