import java.util.HashSet;
import java.util.Set;

/**
 * 10. Regular Expression Matching (https://leetcode.com/problems/regular-expression-matching/description/)
 */
public class RegularExpressionMatching {

    // https://app.gitbook.com/o/-LEzdBqqw8FawHA3mleT/s/-LEzdBqr7mNEEfIbMstL/dp/10.-regular-expression-matching

    /******************** Solution 1: Top Down Recursive + Memo *******************/
    /**
     * 几种情况需要考虑：
     * p 到头了 => s 必须也到头了，s 不能还有没 match 的
     * s 到头了 => p 不一定到头了，后面还可以有 *
     * s[i] 和 p[j] 可以 match => firstMatch = i < s.length() && (s.charAt(i) == p.charAt(j) || '.' == p.charAt(j))
     * （1）后面没有 *，必须 firstMatch && recur(i+1,j+1)
     * （2）后面有 *，则
     *     a. 当前 s[i] match 掉 => firstMatch && recur(i+1,j)
     *     b. 当前 s[i] 不 match => recur(i,j+2)
     *
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
        boolean match;
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
     * dp[i][j] = dp[i-1][j-1] && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.') ->【左上角向右下角转移】
     *          || p.charAt[j] == '*' && (p[i][j-2] || (s.charAt(i) == p.charAt(j - 1) || p.charAt(j - 1) == '.') && dp[i-1][j])
     *                                        |                                        |
     *                          【左两列向右转移，即舍弃当前*项】               【上一行向下转移，即试图扩展当前*项】
     *
     * Time: O(S X P)   Space: O(S X P)
     */
    public boolean isMatch2(String s, String p) {
        s = "#" + s; p = "#" + p;
        int r = s.length(), c = p.length();
        boolean[][] dp = new boolean[r][c];

        // 设置初始值, 第一列除第一个一定全为false
        dp[0][0] = true; // 相当于空的 s & p，match = true
        for (int j = 2; j < c; j++) {
            // 相当于空s和a*b*c*，match = true
            if ('*' == p.charAt(j)) dp[0][j] = dp[0][j - 2];
        }

        for (int i = 1; i < r; i++) {
            for (int j = 1; j < c; j++) {
                dp[i][j] = dp[i - 1][j - 1] && match(s, i, p, j)
                        || p.charAt(j) == '*' && (dp[i][j - 2] || dp[i - 1][j] && match(s, i, p, j - 1));
            }
        }
        return dp[s.length() - 1][p.length() - 1];
    }

    /******************** Solution 3: 更好理解更简洁的 Solution 2 *******************/
    /**
     * Time: O(S X P)   Space: O(S X P)
     */
    String s, p;
    public boolean isMatch(String s, String p) {
        s = "#" + s;
        p = "#" + p;
        this.s = s;
        this.p = p;
        boolean[][] dp = new boolean[s.length()][p.length()];
        dp[0][0] = true;
        for (int i = 0; i < s.length(); i++) {
            for (int j = 1; j < p.length(); j++) {
                if (match(i, j)) {
                    dp[i][j] = i > 0 && dp[i-1][j-1];
                } else if (j > 1 && p.charAt(j) == '*') {
                    dp[i][j] = dp[i][j-2] || match(i,j-1) && dp[i-1][j];
                }
            }
        }
        return dp[s.length()-1][p.length()-1];
    }

    private boolean match(int i, int j) {
        return s.charAt(i) == p.charAt(j) || p.charAt(j) == '.';
    }

    private boolean match(String s, int i, String p, int j) {
        return s.charAt(i) == p.charAt(j) || p.charAt(j) == '.';
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
