import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 32. Longest Valid Parentheses (https://leetcode.com/problems/longest-valid-parentheses/description/)
 */
public class LongestValidParentheses {

    /************** Solution 1: 2D DP ******************/
    /**
     * Time: O(N^2)  Space: O(N^2)
     *
     * dp[i][j][0] denotes S[i:j](inclusive) 有几个 ( 还没匹配上
     * dp[i][j][1] denotes S[i:j](inclusive) 有几个 ) 还没匹配上
     * 每行从左向右推进，遇到 ) 且有没有匹配完的 ( [dp[i][j-1][0] > 0]，则消除，
     * 否则累加对应括号位
     *
     * 实际还是遍历了每种substring，只不过每种substring的valid不用O(N)时间check，而是O(1)时间
     */
    public int longestValidParentheses1(String s) {
        int res = 0, l = s.length();
        int[][][] dp = new int[l][l][2];
        for (int i = 0; i < l; i++) {
            if (s.charAt(i) == '(') {
                dp[i][i][0] = 1;
            } else {
                continue; //第一个为 ) 可以直接跳过整行
            }
            for (int j = i + 1; j < l; j++) {
                if (s.charAt(j) == '(') {
                    dp[i][j] = new int[]{dp[i][j - 1][0] + 1, dp[i][j - 1][1]};
                } else if (dp[i][j - 1][0] > 0) {
                    dp[i][j] = new int[]{dp[i][j - 1][0] - 1, dp[i][j - 1][1]};
                } else {
                    dp[i][j] = new int[]{dp[i][j - 1][0], dp[i][j - 1][1] + 1};
                }
                if (dp[i][j][0] == 0 && dp[i][j][1] == 0) {
                    res = Math.max(res, j - i + 1);
                }
            }
        }
        return res;
    }

    /*************** Solution 2: 1D DP ****************************/
    /**
     * Time: O(N)  Space: O(N) by dp array
     *
     * dp[i] denotes the length of the longest valid substring ending at ith index
     * thus,
     * (1) if chatAt(i) == '(', it is always 0
     * (2) if chatAt(i) == ')'
     *     (2.1) if charAt(i-1) == '(', 凑成一对，dp[i] = dp[i-2]+2
     *     (2.2) if chatAt(i-2) == ')' 且 chatAt(i-dp[i-1]-1) == '(', 凑成一对，dp[i] = dp[i-1] + 2 + dp[i-dp[i-1]-2]
     */
    public int longestValidParentheses2(String s) {
        int res = 0, l = s.length();
        int[] dp = new int[l];
        for (int i = 1; i < l; i++) {
            if (s.charAt(i) == ')') {
                if (s.charAt(i - 1) == '(') {
                    dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
                } else if (i - dp[i - 1] - 1 >= 0 && s.charAt(i - dp[i - 1] - 1) == '(') {
                    dp[i] = dp[i - 1] + 2 + (i - dp[i - 1] - 2 >= 0 ? dp[i - dp[i - 1] - 2] : 0) + 2;
                }
                res = Math.max(res, dp[i]);
            }
        }
        return res;
    }

    /**************** Solution 3: One-pass by Stack ***************/
    /**
     * Time: O(N)  Space: O(N) by stack
     *
     * Stack记录还没有被匹配上的括号的index
     * 该方法最好理解 + 容易想到和实现！！！
     */
    public int longestValidParentheses3(String s) {
        int maxLen = 0;
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(-1);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ')') {
                if (stack.peek() != -1 && s.charAt(stack.peek()) == '(') {
                    stack.pop();
                    maxLen = Math.max(maxLen, i - stack.peek());
                } else {
                    stack.push(i);
                }
            } else {
                stack.push(i);
            }
        }
        return maxLen;
    }

    /**************** Solution 4: Greedy Two-pass without extra space ***************/
    /**
     * Time: O(N)  Space: O(1)
     */
    public int longestValidParentheses(String s) {
        int res = 0, l = s.length();
        // 从左往右，左右括号数量相等则更新res，
        // 可以用数量判断的前提是：一旦右括号多，则前面的肯定无效，左右数量都归零！！！
        int left = 0, right = 0;
        for (int i = 0; i < l; i++) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }
            if (left == right) {
                res = Math.max(res, left + right);
            } else if (right >= left) {
                left = right = 0;
            }
        }
        // 从右往左，左右括号数量相等则更新res，
        // 可以用数量判断的前提是：一旦左括号多，则后面的肯定无效，左右数量都归零！！！
        left = right = 0;
        for (int i = l - 1; i >= 0; i--) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }
            if (left == right) {
                res = Math.max(res, left + right);
            } else if (left >= right) {
                left = right = 0;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        LongestValidParentheses solution = new LongestValidParentheses();
        System.out.println(solution.longestValidParentheses("(()"));  // 2
        System.out.println(solution.longestValidParentheses(")()())")); // 4
        System.out.println(solution.longestValidParentheses("")); // 0
    }

}