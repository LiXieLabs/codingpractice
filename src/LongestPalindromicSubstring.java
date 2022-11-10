public class LongestPalindromicSubstring {

    /***** Solution 1: intuitive - check每个中心点，向两边扩散找最长，更新全局 *****/
    /**
     * Time: O(N^2)
     */
    public String longestPalindrome1(String s) {
        String res = "";
        for (int i = 0; i < s.length(); i++) {
            // 优化提前结束，没有也行
            if ((i+1) * 2 < res.length() || (s.length() - i) * 2 < res.length()) {
                return res;
            }
            String s1 = find(s, i, i);
            if (s1.length() > res.length()) res = s1;
            String s2 = find(s, i, i+1);
            if (s2.length() > res.length()) res = s2;
        }
        return res;
    }

    public String find(String s, int l, int r) {
        if (l < 0 || r >= s.length() || s.charAt(l) != s.charAt(r)) return "";
        while (l - 1 >= 0 && r + 1 < s.length() && s.charAt(l - 1) == s.charAt(r + 1)) {
            l--;
            r++;
        }
        return s.substring(l, r+1);
    }

    /*************** Solution 2: 2D DP *******************/
    /**
     * i = [0, l-1] is position of left pointer
     * j = [0, l-1] is position of right pointer
     * dp[i][j] denotes if s[i:j+1] is Palindrome
     * dp[i][j] = true if i == j
     *          = s[i]==s[j] if j - i == 1
     *          = dp[i-1][j-1] && s[i]==s[j] if j - i > 1
     * dp二维矩阵，由对角线向右上方推进
     * Time: O(N^2) Space: O(N)
     */
    public String longestPalindrome(String s) {
        boolean[] dp = new boolean[s.length()];
        String res = "";
        for (int i = s.length() - 1; i >= 0; i--) {
            boolean pre = dp[i];
            for (int j = i; j < s.length(); j++) {
                boolean tmp = dp[j];
                if (i == j) {
                    dp[j] = true;
                } else if (j - i == 1) {
                    dp[j] = (s.charAt(i) == s.charAt(j));
                } else {
                    dp[j] = (pre & s.charAt(i) == s.charAt(j));
                }
                if (dp[j] & j - i + 1 > res.length()) {
                    res = s.substring(i, j + 1);
                }
                pre = tmp;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        LongestPalindromicSubstring solution = new LongestPalindromicSubstring();
        System.out.println(solution.longestPalindrome("babad"));
        System.out.println(solution.longestPalindrome("cbbd"));
    }
}
