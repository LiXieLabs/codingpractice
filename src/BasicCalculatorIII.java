import javafx.util.Pair;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.Stream;

/**
 * 772. Basic Calculator III (https://leetcode.com/problems/basic-calculator-iii/)
 */
public class BasicCalculatorIII {

    int i;

    /******** Solution 1: Recursive Backtracking ***************/
    /**
     * recurCalc 只负责当前这个括号内的计算，内部的括号交给下一层计算
     * i 只走一遍
     *
     * Time: O(N)   Space: O(N)
     */
    public int calculate1(String s) {
        i = 0;
        return recurCalc(s);
    }

    private int recurCalc(String s) {
        Deque<Integer> nums = new ArrayDeque<>();
        int n = 0;
        char lastOperator = '+';
        while (i <= s.length()) {
            if (i == s.length()
                    || Stream.of('+', '-', '*', '/', ')').anyMatch(c -> c == s.charAt(i))) {
                if ('+' == lastOperator) {
                    nums.push(n);
                } else if ('-' == lastOperator) {
                    nums.push(-1 * n);
                } else if ('*' == lastOperator) {
                    nums.push(nums.pop() * n);
                } else { // '/' == lastOperator
                    nums.push(nums.pop() / n);
                }
                n = 0;
                if (i == s.length() || s.charAt(i) == ')') {
                    while (!nums.isEmpty()) {
                        n += nums.poll();
                    }
                    i++;
                    return n;
                } else {
                    lastOperator = s.charAt(i++);
                }
            } else if (Character.isDigit(s.charAt(i))) {
                n = n * 10 + (s.charAt(i++) - '0');
            } else { // s.charAt(i) == '('
                i++;
                n = recurCalc(s);
            }
        }
        return -1;
    }

    /******** Solution 2: Iterative Backtracking (最优解) ***************/
    /**
     * total 是该层 + - 结果，当前层的 stack，直接累计结果，不用维护 stack
     * cur 是该层最新的 * / 结果，直接累计运算
     * n 是当前数字
     * lastOperator 是 n 前面一个运算符
     *
     * Time: O(N)   Space: O(N)
     */
    public int calculate(String s) {
        Deque<Pair<int[], Character>> stack = new ArrayDeque<>();
        int total = 0, cur = 0, n = 0;
        Character lastOperator = '+';
        for (int i = 0; i <= s.length(); i++) {
            int finalI = i;
            if (i == s.length()
                    || Stream.of('+', '-', '*', '/', ')').anyMatch(c -> s.charAt(finalI) == c)) {
                // 先根据 last operator 操作 cur 和 n
                if (lastOperator == '*') {
                    cur *= n;
                } else if (lastOperator == '/'){
                    cur /= n;
                } else { // lastOperator == '+' '-' => 前一个 '*' '/' 层结束，开始新的 cur 前，把前一个 cur 计入 total
                    total += cur;
                    int sign = lastOperator == '-' ? -1 : 1;
                    cur = sign * n;
                }
                n = 0;
                // 再根绝当前 char 决定运算层
                if (i == s.length()) { // 当前层即为最外层，返回结果
                    return total + cur;
                } else if (s.charAt(i) == ')') { // 当前层结束，结果为上一层的 n，pop出剩下的中间结果
                    n = total + cur;
                    Pair<int[], Character> pair = stack.pop();
                    total = pair.getKey()[0]; cur = pair.getKey()[1];
                    lastOperator = pair.getValue();
                } else { // s.charAt(i) == '+' '-' '*' '/' => 继续当前层计算
                    lastOperator = s.charAt(i);
                }
            } else if (Character.isDigit(s.charAt(i))) {
                n = n * 10 + (s.charAt(i) - '0');
            } else if (s.charAt(i) == '(') { // 开始新的一层，把当前层中间结果暂时计入 stack
                stack.push(new Pair<>(new int[]{total, cur}, lastOperator));
                total = 0; cur = 0;
                lastOperator = '+';
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        BasicCalculatorIII solution = new BasicCalculatorIII();
        System.out.println(solution.calculate("1+1")); // 2
        System.out.println(solution.calculate("6-4/2")); // 4
        System.out.println(solution.calculate("2*(5+5*2)/3+(6/2+8)")); // 21
    }

}
