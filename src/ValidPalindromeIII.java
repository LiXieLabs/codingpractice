public class ValidPalindromeIII {

    // i = [0, l-1] is position of left pointer
    // j = [0, l-1] is position of right pointer

    // dp[i][j] denotes # of removes that is needed to turn substring s[i:j+1] to Palindrome
    // dp[i][j] = min(dp[i+1][j]+1, dp[i][j-1]+1, dp[i+1][j-1] if s[i]==s[j])

    // dp二维矩阵，对角线初始值都是0(因为i==j,每个字母本身是palindrome的),由对角线向右上方推进，
    // 右上角dp[0][l-1]表示s变成palindrome最少需要多少remove，小于等于k即可

    // Time: O(N^2) Space: O(N)
    public boolean isPalindrome(String s, int k) {
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
        System.out.println(solution.isPalindrome("abcdeca", 2));
        System.out.println(solution.isPalindrome("abbababa", 1));
        System.out.println(solution.isPalindrome("fcgihcgeadfehgiabegbiahbeadbiafgcfchbcacedbificicihibaeehbffeidiaiighceegbfdggggcfaiibefbgeegbcgeadcfdfegfghebcfceiabiagehhibiheddbcgdebdcfegaiahibcfhheggbheebfdahgcfcahafecfehgcgdabbghddeadecidicchfgicbdbecibddfcgbiadiffcifiggigdeedbiiihfgehhdegcaffaggiidiifgfigfiaiicadceefbhicfhbcachacaeiefdcchegfbifhaeafdehicfgbecahidgdagigbhiffhcccdhfdbd",216));
    }
}
