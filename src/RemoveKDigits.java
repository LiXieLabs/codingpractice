import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 402. Remove K Digits (https://leetcode.com/problems/remove-k-digits/description/)
 */
public class RemoveKDigits {

    /************* Solution 1: Greedy + Monotonic Stack ***************/
    /**
     * 维护单调递增stack，相等可以不移除，如：【1，2，2，3】
     * 难点在于greedy即最优解，不用每遍移除一个数字，而是只一遍，一旦有递减，就可以把前面大的移除！
     *
     * Time: O(N) each char at most enter & exit stack once
     * Space: O(N) by stack
     */
    public String removeKdigits(String num, int k) {
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < num.length(); i++) {
            char c = num.charAt(i);
            while (!stack.isEmpty() && stack.peek() > c && k > 0) {
                stack.pop();
                k--;
            }
            // if用于去除leading zeros, 也可以StringBuilder的时候去除
            if (!stack.isEmpty() || c != '0') stack.push(c);
        }
        // 如果是单调递增的字符串，最后可能有剩余的移除名额
        while (k-- > 0 && !stack.isEmpty()) stack.pop();
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) sb.append(stack.pollLast());
        return sb.length() == 0 ? "0" : sb.toString();
    }

    public static void main(String[] args) {
        RemoveKDigits solution = new RemoveKDigits();
        System.out.println(solution.removeKdigits("1234567890", 9)); // 0
        System.out.println(solution.removeKdigits("1432219", 3)); // 1219
        System.out.println(solution.removeKdigits("10200", 1)); // 200
        System.out.println(solution.removeKdigits("10", 2)); // 0
    }
}