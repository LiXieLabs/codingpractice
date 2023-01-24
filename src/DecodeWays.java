import java.util.HashMap;
import java.util.Map;

/**
 * 91. Decode Ways (https://leetcode.com/problems/decode-ways/description/)
 */
public class DecodeWays {

    /********************* Solution 1: Top Down Recursive + Memoization *********************/
    /**
     * Time: Memoized to O(N) as each index calls recur once
     * Space: O(N) by memo HashMap & recur stack
     */
    Map<Integer, Integer> memo;
    public int numDecodings1(String s) {
        memo = new HashMap<>();
        memo.put(s.length(), 1);
        return recur(s, 0);
    }

    private int recur(String s, int startIdx) {
        if (!memo.containsKey(startIdx)) {
            int res = 0;
            for (int endIdx = startIdx + 1; endIdx - startIdx <= 2 && endIdx <= s.length(); endIdx++) {
                String str = s.substring(startIdx, endIdx);
                int num = Integer.parseInt(str);
                if (1 <= num && num <= 26 && !str.startsWith("0")) {
                    res += recur(s, endIdx);
                }
            }
            memo.put(startIdx, res);
        }
        return memo.get(startIdx);
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
    public int numDecodings2(String s) {
        int[] dp = new int[s.length() + 1];
        dp[0] = 1;
        for (int endIdx = 1; endIdx <= s.length(); endIdx++) {
            for (int startIdx = endIdx - 1; endIdx - startIdx <= 2 && startIdx >= 0; startIdx--) {
                String str = s.substring(startIdx, endIdx);
                int num = Integer.parseInt(str);
                if (1 <= num && num <= 26 && !str.startsWith("0")) {
                    dp[endIdx] += dp[startIdx];
                }
            }
        }
        return dp[s.length()];
    }

    /********************* Solution 3: Solution 2 优化为 O(1) space *********************/
    /**
     * Time: O(N)   Space: O(1)
     */
    public int numDecodings(String s) {
        int[] dp = new int[3];
        dp[0] = 1;
        for (int endIdx = 1; endIdx <= s.length(); endIdx++) {
            dp[endIdx % 3] = 0; // 注意！！！不清空会越来越大！！！
            for (int startIdx = endIdx - 1; endIdx - startIdx <= 2 && startIdx >= 0; startIdx--) {
                String str = s.substring(startIdx, endIdx);
                int num = Integer.parseInt(str);
                if (1 <= num && num <= 26 && !str.startsWith("0")) {
                    dp[endIdx % 3] += dp[startIdx % 3];
                }
            }
        }
        return dp[s.length() % 3];
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
