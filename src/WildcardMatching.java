/**
 * 44. Wildcard Matching (https://leetcode.com/problems/wildcard-matching/description/)
 */
public class WildcardMatching {

    /************ Solution 1: 2D DP ********************/
    /**
     * Transition function:
     * dp[i][j]
     * = dp[i-1][j-1] if s[i] == p[j] or p[j] == '?'
     * = dp[i][j-1] || dp[i-1][j] if p[j] == '*'
     *
     *  Time: O(S x P)   Space: O(S x P)
     */
    public boolean isMatch1(String s, String p) {
        s = "#" + s;
        p = "#" + p;
        boolean[][] dp = new boolean[s.length()][p.length()];
        dp[0][0] = true;
        for (int j = 1; j < p.length() && p.charAt(j) == '*'; j++) {
            dp[0][j] = true;
        }
        for (int i = 1; i < s.length(); i++) {
            for (int j = 1; j < p.length(); j++) {
                if (s.charAt(i) == p.charAt(j) || p.charAt(j) == '?') {
                    dp[i][j] = dp[i-1][j-1];
                } else if (p.charAt(j) == '*') {
                    dp[i][j] = dp[i][j-1] || dp[i-1][j];
                }
            }
        }
        return dp[s.length()-1][p.length()-1];
    }

    /************ Solution 2: Optimized Solution1 -> 1D DP  ********************/
    /**
     * Transition function:
     * dp[i][j]
     * = dp[i-1][j-1] if s[i] == p[j] or p[j] == '?'
     * = dp[i][j-1] || dp[i-1][j] if p[j] == '*'
     *
     *  Time: O(S x P)   Space: O(S x P)
     */
    public boolean isMatch(String s, String p) {
        s = "#" + s;
        p = "#" + p;
        boolean[] dp = new boolean[p.length()];
        dp[0] = true;
        for (int j = 1; j < p.length() && p.charAt(j) == '*'; j++) {
            dp[j] = true;
        }
        for (int i = 1; i < s.length(); i++) {
            boolean prev = dp[0];
            // ⚠️注意⚠️第二行开始是 false！！！
            if (i == 1) dp[0] = false;
            for (int j = 1; j < p.length(); j++) {
                boolean temp = dp[j];
                if (s.charAt(i) == p.charAt(j) || p.charAt(j) == '?') {
                    dp[j] = prev;
                } else if (p.charAt(j) == '*') {
                    dp[j] = dp[j-1] || dp[j];
                } else {
                    // ⚠️注意⚠️ else 情况要写出来覆盖掉上一行的值！！！
                    dp[j] = false;
                }
                prev = temp;
            }
        }
        return dp[p.length()-1];
    }

    public static void main(String[] args) {
        WildcardMatching solution = new WildcardMatching();
        System.out.println(solution.isMatch("aa", "a")); // false
        System.out.println(solution.isMatch("aa", "*")); // true
        System.out.println(solution.isMatch("cb", "?a")); // false
        System.out.println(solution.isMatch("adceb", "*a*b")); // true
        System.out.println(solution.isMatch("acdcb", "a*c?b")); // false
    }
}
