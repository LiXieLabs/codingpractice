import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.Stream;

/**
 * 224. Basic Calculator (https://leetcode.com/problems/basic-calculator/)
 */
public class BasicCalculator {

    // Parsing Problem 通用解法
    // https://leetcode.com/problems/basic-calculator/discuss/2017431/Stop-hating-parsing-problems-and-start-having-fun

    /************ Solution 1: 把所有+/-想成数字的正负号，永远做加法 + stack保存当前括号外的临时解和符号 *************/
    /**
     * Time: O(N)   Space: O(N)
     */
    public int calculate1(String s) {
        Deque<Integer> stack = new ArrayDeque<>(); // 外面每一层的总和以及里面一层的符号
        int operand = 0; // 当前操作数
        int res = 0; // 当前括号层总和
        int sign = 1; // 当前符号
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                operand = operand * 10 + (c - '0');
            } else if (c == '+' || c == '-') {
                res += sign * operand;
                operand = 0;
                sign = c == '+' ? 1 : -1;
            } else if (c == '(') { // 开启新的一层，该层数字，和新一层符号放入stack
                stack.push(res);
                stack.push(sign);
                res = 0;
                sign = 1;
            } else if (c == ')') { // 结束当前层，从stack读取当前层符号，和上一层总和
                res += sign * operand;
                res *= stack.pop();
                res += stack.pop();
                operand = 0;
            }
        }
        return res + sign * operand;
    }

    /************** Solution 2: 类似 LC772 的解法 ********************/
    /**
     * cur 为当前层括号中的当前总和
     * n 为当前层当前数字
     * sign 为当前层当前数字前符号
     */
    public int calculate(String s) {
        Deque<int[]> stack = new ArrayDeque<>();
        int cur = 0, n = 0, sign = 1;
        for (int i = 0; i <= s.length(); i++) {
            int finalI = i;
            if (i == s.length() || Stream.of('+', '-', ')').anyMatch(c -> c == s.charAt(finalI))) {
                // 先将前一个数字 n 计入当前层结果 cur
                cur += sign * n;
                n = 0;
                // 再根据当前 char 决定运算层
                if (i == s.length()) { // 当前层结束，且为最外层，返回结果
                    return cur;
                } else if (s.charAt(i) == ')') { // 当前层结束，当前结果为上一层 n，pop 上一层 cur 和 sign
                    n = cur;
                    int[] upperLevel = stack.pop();
                    cur = upperLevel[0]; sign = upperLevel[1];
                } else { // 仍在当前层，根据正负号为 sign 赋值
                    sign = s.charAt(i) == '-' ? -1 : 1;
                }
            } else if (Character.isDigit(s.charAt(i))) { // 继续当前数字
                n = n * 10 + (s.charAt(i) - '0');
            } else if (s.charAt(i) == '(') { // 开始新的一层，当前中间值计入 stack
                stack.push(new int[]{cur, sign});
                cur = 0; n = 0; sign = 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        BasicCalculator solution = new BasicCalculator();
        System.out.println(solution.calculate("1 + 1")); // 2
        System.out.println(solution.calculate(" 2-1 + 2 ")); // 3
        System.out.println(solution.calculate( "(1+(4+5+2)-3)+(6+8)")); // 23
        System.out.println(solution.calculate( "-(2+3)")); // -5
        System.out.println(solution.calculate( "1+(-1)+2")); // 2
    }
}