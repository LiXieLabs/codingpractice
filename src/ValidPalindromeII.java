public class ValidPalindromeII {

    // Time: O(N) Space: O(1) + O(logN) recur call stack
    // Solution: https://leetcode.com/problems/valid-palindrome-ii/solution/
    public boolean validPalindrome(String s) {
        return isPalindrome(s, 0, s.length() - 1, 1);
    }

    public boolean isPalindrome(String s, int l, int r, int remove) {
        if (l >= r) return true;
        if (s.charAt(l) == s.charAt(r)) return isPalindrome(s, l+1, r-1, remove);
        if (remove > 0) return isPalindrome(s, l+1, r, remove-1) || isPalindrome(s, l, r-1, remove-1);
        return false;
    }

    public static void main(String[] args) {
        ValidPalindromeII solution = new ValidPalindromeII();
        System.out.println(solution.validPalindrome("aba"));
        System.out.println(solution.validPalindrome("abca"));
        System.out.println(solution.validPalindrome("abc"));
        System.out.println(solution.validPalindrome("cbbcc"));
    }
}
