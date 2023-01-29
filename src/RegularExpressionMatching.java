import java.util.HashSet;
import java.util.Set;

/**
 * 10. Regular Expression Matching (https://leetcode.com/problems/regular-expression-matching/description/)
 */
public class RegularExpressionMatching {

    /******************** Solution 1: Top Down Recursive + Memo (TLE) *******************/
    /**
     * Time:
     * before memo O(2^(S+P))
     * after memo O(S X P)
     *
     * Space: O(S X P)
     */
    Set<String> memo;
    public boolean isMatch1(String s, String p) {
        memo = new HashSet<>();
        return recur(s, 0, p, 0);
    }

    private boolean recur(String s, int i, String p, int j) {
        String key = i + "," + j;
        if (memo.contains(key)) return false;
        boolean match = false;
        if (j >= p.length())  {
            match = (i >= s.length());
        } else {
            boolean firstMatch = i < s.length() && (s.charAt(i) == p.charAt(j) || '.' == p.charAt(j));
            if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
                match = (firstMatch && recur(s, i + 1, p, j)) || recur(s, i, p, j + 2);
            } else {
                match = firstMatch && recur(s, i + 1, p, j + 1);
            }
        }
        if (!match) memo.add(key);
        return match;
    }

    /******************** Solution 2: Bottom Up DP *******************/
    /**
     * Time: O(S X P)   Space: O(S X P)
     */
    public boolean isMatch(String s, String p) {
        s = "#" + s; p = "#" + p;
        boolean[][] dp = new boolean[s.length()][p.length()];

        // 设置初始值, 第一列除第一个一定全为false
        dp[0][0] = true; // 相当于空的 s & p，match = true
        for (int j = 2; j < p.length(); j++) {
            // 相当于空s和a*b*c*，match = true
            if ('*' == p.charAt(j)) dp[0][j] = dp[0][j - 2];
        }

        for (int i = 1; i < s.length(); i++) {
            for (int j = 1; j < p.length(); j++) {
                if (s.charAt(i) == p.charAt(j) || '.' == p.charAt(j)) {
                    dp[i][j] = dp[i - 1][j - 1]; // 当前位置match
                } else if ('*' == p.charAt(j)) {
                    dp[i][j] = dp[i][j - 2] // 当前*组忽略
                            || ((s.charAt(i) == p.charAt(j - 1) || '.' == p.charAt(j - 1)) && dp[i - 1][j]); // *前一个位置match
                }
            }
        }
        return dp[s.length() - 1][p.length() - 1];
    }

    public static void main(String[] args) {
        RegularExpressionMatching solution = new RegularExpressionMatching();
        System.out.println(solution.isMatch("mississippi", "mis*is*p*.")); // false
        System.out.println(solution.isMatch("aaaaaaaaaaaaaaaaaaab", "a*a*a*a*a*a*a*a*a*a*a*a*a*a*a*")); // false
        System.out.println(solution.isMatch("abcaaaaaaabaabcabac", ".*ab.a.*a*a*.*b*b*")); // true
        System.out.println(solution.isMatch("aabcbcbcaccbcaabc", ".*a*aa*.*b*.c*.*a*")); // true
        System.out.println(solution.isMatch("b", "a*b")); // true
        System.out.println(solution.isMatch("aa", "a")); // false
        System.out.println(solution.isMatch("aa", "a*")); // true
        System.out.println(solution.isMatch("ab", ".*")); // true
    }
}
