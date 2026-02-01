public class ValidPalindromeIV {

    /************ Solution 1: Two Pointers *****************/
    /**
     * 注意提前结束！
     * 
     * Time: O(N)   Space: O(1)
     */
    public boolean makePalindrome(String s) {
        int l = 0, r = s.length() - 1, mismatch = 0;
        while (l < r) {
            if (s.charAt(l++) != s.charAt(r--) && ++mismatch > 2) return false;
        }
        return true;
    }
}
