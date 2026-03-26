import java.util.ArrayDeque;
import java.util.Deque;

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
     * 当前层：
     * curr 为当前层括号中的当前总和
     * n 为当前层当前数字
     * sign 为当前层当前数字前符号
     * 上层环境：
     * stack [curr1, sign1, curr2, sign2...]
     *       (curr1    +   (curr2    -
     *
     * 三种时机结算：
     * 遇到符号 => 当前层更新
     * 遇到 ） => 当前层结算，合并上一层，变成当前层
     * 循环结束 => 最后一次 curr + sign * n
     *
     * ( 括号 = 进入新世界
     * ) 括号 = 结算当前世界，合并上层世界
     */
    public int calculate(String s) {
        Deque<Integer> stack = new ArrayDeque<>();
        int sign = 1, curr = 0, n = 0;
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                n = n * 10 + (c - '0');
            } else if (c == '+' || c == '-') {
                curr += sign * n;
                sign = c == '+' ? 1 : -1;
                n = 0;
            } else if (c == ')') {
                curr += sign * n;
                curr *= stack.pop(); // previous sign
                curr += stack.pop(); // previous curr
                sign = 1;
                n = 0;
            } else if (c == '(') {
                stack.push(curr);
                stack.push(sign);
                curr = 0;
                sign = 1;
                n = 0;
            }
        }
        return curr + sign * n;
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