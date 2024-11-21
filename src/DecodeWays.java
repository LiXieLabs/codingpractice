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
        return recur1(s, 0);
    }

    private int recur1(String s, int startIdx) {
        if (!memo.containsKey(startIdx)) {
            int res = 0;
            for (int endIdx = startIdx + 1; endIdx - startIdx <= 2 && endIdx <= s.length(); endIdx++) {
                String str = s.substring(startIdx, endIdx);
                int num = Integer.parseInt(str);
                if (1 <= num && num <= 26 && !str.startsWith("0")) {
                    res += recur1(s, endIdx);
                }
            }
            memo.put(startIdx, res);
        }
        return memo.get(startIdx);
    }

    /********************* Solution 2: Solution 1 更好理解的版本 *********************/
    /**
     * Time: Memoized to O(N) as each index calls recur once
     * Space: O(N) by memo HashMap & recur stack
     */
    public int numDecodings2(String s) {
        memo = new HashMap<>();
        memo.put(s.length(), 1);
        return recur2(s, 0);
    }

    private int recur2(String s, int i) {
        if (!memo.containsKey(i)) {
            int res = 0;
            // s[i] 单独 decode
            if (s.charAt(i) != '0') res = recur2(s, i + 1);
            // s[i,i+2] 一起 decode
            if (i + 1 < s.length() && s.charAt(i) != '0') {
                int doubleDigitNum = Integer.parseInt(s.substring(i, i + 2));
                if (1 <= doubleDigitNum && doubleDigitNum <= 26) {
                    res += recur2(s, i + 2);
                }
            }
            memo.put(i, res);
        }
        return memo.get(i);
    }

    /********************* Solution 3: Bottom Up 1D DP *********************/
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

    /********************* Solution 4: Solution 3 优化为 O(1) space *********************/
    /**
     * Time: O(N)   Space: O(1)
     */
    public int numDecodings4(String s) {
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

    /********************* Solution 5: Solution 4 更容易理解的版本 *********************/
    /**
     * dp[i] 表示到 s[i] 为止有多少种 decode ways
     * dp[i] = s[i] 单独 decode
     *       + s[i-1,i+1] 一起 decode
     *       = dp[i-1] (s[i] != 0)
     *       + dp[i-2] (1 <= s[i-1,i+1] <= 26)
     * Time: O(N)   Space: O(1)
     */
    public int numDecodings(String s) {
        int prepre = 0, pre = 1;
        for (int i = 0; i < s.length(); i++) {
            int cur = 0;
            // s[i] 单独 decode
            if (s.charAt(i) != '0') cur = pre;
            // s[i-1,i+1] 一起 decode
            if (i - 1 >= 0 && s.charAt(i - 1) != '0') {
                int doubleDigitNum = Integer.parseInt(s.substring(i - 1, i + 1));
                if (1 <= doubleDigitNum && doubleDigitNum <= 26) {
                    cur += prepre;
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
