/**
 * 91. Decode Ways (https://leetcode.com/problems/decode-ways/description/)
 */
public class DecodeWays {

    /********************* Solution 1: Top Down Recursive + Memoization *********************/
    /**
     * Time: Memoized to O(N) as each index calls recur once
     * Space: O(N) by memo array & recur stack
     */
    Integer[] memo;
    String s;

    public int numDecodings1(String s) {
        memo = new Integer[s.length() + 1];
        memo[s.length()] = 1;
        this.s = s;
        return recur(0);
    }

    private int recur(int i) {
        if (memo[i] == null) {
            memo[i] = 0;
            int n1 = s.charAt(i) - '0';
            if (n1 > 0) memo[i] = recur(i + 1);
            if (i + 1 < s.length()) {
                int n2 = s.charAt(i + 1) - '0';
                if (n1 == 1 || n1 == 2 && n2 <= 6) {
                    memo[i] += recur(i + 2);
                }
            }
        }
        return memo[i];
    }

    /********************* Solution 2: Bottom Up 1D DP *********************/
    /**
     * dp[i] 表示以 i 为 end index (exclusive) 的 s[0:i] 有 dp[i] 种 decode 结果
     *
     * dp[i] = dp[i-1] if (s[i-1:i] is valid)
     *       + dp[i-2] if (s[i-2:i] is valid)
     *
     * valid means
     *  (1) Integer.parseInt in [1,26]
     *  (2) !startsWith("0")
     *
     * Time: O(N)   Space: O(N)
     */
    public int numDecodings3(String s) {
        int[] dp = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            int n1 = s.charAt(i) - '0';
            if (n1 != 0) dp[i] = i - 1 >= 0 ? dp[i - 1] : 1;
            if (i - 1 >= 0) {
                int n2 = s.charAt(i - 1) - '0';
                if (n2 == 1 || n2 == 2 && n1 <= 6) {
                    dp[i] += i - 2 >= 0 ? dp[i - 2] : 1;
                }
            }
        }
        return dp[s.length() - 1];
    }

    /********************* Solution 3: Solution 5 O(1) Space *********************/
    /**
     * dp[i] 表示到 s[i] 为止有多少种 decode ways
     * dp[i] = s[i] 单独 decode
     *       + s[i-1,i+1] 一起 decode
     *       = dp[i-1] (s[i] != 0)
     *       + dp[i-2] (1 <= s[i-1,i+1] <= 26)
     * Time: O(N)   Space: O(1)
     */
    public int numDecodings(String s) {
        int prepre = 1, pre = 1;
        for (int i = 0; i < s.length(); i++) {
            int cur = 0;
            int n1 = s.charAt(i) - '0';
            if (n1 != 0) cur = i - 1 >= 0 ? pre : 1;
            if (i - 1 >= 0) {
                int n2 = s.charAt(i - 1) - '0';
                if (n2 == 1 || n2 == 2 && n1 <= 6) {
                    cur += i - 2 >= 0 ? prepre : 1;
                }
            }
            prepre = pre;
            pre = cur;
        }
        return pre;
    }

    public static void main(String[] args) {
        DecodeWays solution = new DecodeWays();
        System.out.println(solution.numDecodings("2611055971756562")); // 4
        System.out.println(solution.numDecodings("11106")); // 2
        System.out.println(solution.numDecodings("12")); // 2
        System.out.println(solution.numDecodings("226")); // 3
        System.out.println(solution.numDecodings("06")); // 0
    }
}
