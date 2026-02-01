import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ValidPalindromeIII {

    /**************** Solution 1: Top Down Recursive + Memoization *****************/
    /**
     * 解法由 680. Valid Palindrome II (https://leetcode.com/problems/valid-palindrome-ii/description/) 衍生来
     * memo in 3D: l, r, remainRemove
     *
     * Time: O(N^2 X K)   Space: O(N^2 X K)
     */
    String s;
    Map<String, Boolean> memo;
    // 也可以 int[][][] memo = new int[s.length()][s.length()][k + 1]
    // -1 => unknown; 0 => invalid; 1 => valid，但是会得到 memory limit exceeded!

    public boolean isValidPalindrome1(String s, int k) {
        this.s = s;
        memo = new HashMap<>();
        return recur(0, s.length() - 1, k);
    }

    private boolean recur(int l, int r, int remove) {
        if (l >= r) return true;
        String key = l + "," + r + "," + remove;
        if (!memo.containsKey(key)) {
            boolean valid;
            if (s.charAt(l) == s.charAt(r)) {
                valid = recur(l + 1, r - 1, remove);
            } else if (remove == 0) {
                valid = false;
            } else {
                valid = recur(l + 1, r, remove - 1) || recur(l, r - 1, remove -1);
            }
            memo.put(key, valid);
        }
        return memo.get(key);
    }

    /**************** Solution 2: 另一种更优化的 Top Down Recursive + Memoization *****************/
    /**
     * memo in 2D
     * memo[l][r] 使得 s[l:r] isPalindrome 需要的最少的 remove
     *
     * Time: O(N^2)   Space: O(N^2)
     */
    int[][] memo2;

    public boolean isValidPalindrome2(String s, int k) {
        this.s = s;
        memo2 = new int[s.length()][s.length()];
        for (int[] line : memo2) Arrays.fill(line, -1);
        return minRemove(0, s.length() - 1) <= k;
    }

    private int minRemove(int l, int r) {
        if (l >= r) return 0;
        if (memo2[l][r] == -1) {
            int res;
            if (s.charAt(l) == s.charAt(r)) {
                res = minRemove(l + 1, r - 1);
            } else {
                res = 1 + Math.min(minRemove(l + 1, r), minRemove(l, r - 1));
            }
            memo2[l][r] = res;
        }
        return memo2[l][r];
    }

    /**************** Solution 3: 由 Solution 2 演化而来的 2D DP -> 1D DP *****************/
    /**
     * i = [0, l-1] is position of left pointer
     * j = [0, l-1] is position of right pointer
     *
     * dp[i][j] denotes # of removes that is needed to turn substring s[i:j+1] to Palindrome
     * dp[i][j] = min(dp[i+1][j]+1, dp[i][j-1]+1, dp[i+1][j-1] if s[i]==s[j])
     *
     * dp二维矩阵，对角线初始值都是0(因为i==j,每个字母本身是palindrome的),由对角线向右上方推进，
     * 右上角dp[0][l-1]表示s变成palindrome最少需要多少remove，小于等于k即可
     *
     * Time: O(N^2) Space: O(N)
     */
    public boolean isValidPalindrome(String s, int k) {
        int l = s.length();
        int[] dp = new int[l+1];
        for (int i = l - 1; i >= 0; i--) {
            int pre = dp[i];
            for (int j = i + 1; j < l; j++) {
                int tmp = dp[j+1];
                dp[j+1] = Math.min(dp[j+1]+1, dp[j]+1);
                if (s.charAt(i) == s.charAt(j)) {
                    dp[j+1] = Math.min(dp[j+1], pre);
                }
                pre = tmp;
            }
        }
        return dp[l] <= k;
    }

    public static void main(String[] args) {
        ValidPalindromeIII solution = new ValidPalindromeIII();
        System.out.println(solution.isValidPalindrome("abcdeca", 2));
        System.out.println(solution.isValidPalindrome("abbababa", 1));
        System.out.println(solution.isValidPalindrome("fcgihcgeadfehgiabegbiahbeadbiafgcfchbcacedbificicihibaeehbffeidiaiighceegbfdggggcfaiibefbgeegbcgeadcfdfegfghebcfceiabiagehhibiheddbcgdebdcfegaiahibcfhheggbheebfdahgcfcahafecfehgcgdabbghddeadecidicchfgicbdbecibddfcgbiadiffcifiggigdeedbiiihfgehhdegcaffaggiidiifgfigfiaiicadceefbhicfhbcachacaeiefdcchegfbifhaeafdehicfgbecahidgdagigbhiffhcccdhfdbd",216));
    }
}
