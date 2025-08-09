import java.util.ArrayList;
import java.util.List;

/**
 * 9. Palindrome Number (https://leetcode.com/problems/palindrome-number/)
 */
public class PalindromeNumber {

    /************* Solution 1: Turn int to String + Two Pointers to check Palindrome String ****************/
    /**
     * Time: O(N)   Space: O(1)
     */
    public boolean isPalindrome1(int x) {
        return isPalindromeStr(String.valueOf(x));
    }

    private boolean isPalindromeStr(String s) {
        int l = 0, r = s.length() - 1;
        while (l < r) {
            if (s.charAt(l++) != s.charAt(r--)) return false;
        }
        return true;
    }

    /********* Solution 2: Turn int into digit list + Two Pointers to check Palindrome Integer List *********/
    /**
     * Time: O(N)   Space: O(N)
     */
    public boolean isPalindrome2(int x) {
        if (x < 10) return x >= 0;
        List<Integer> num = new ArrayList<>();
        while (x > 0) {
            num.add(x % 10);
            x /= 10;
        }
        int l = 0, r = num.size() - 1;
        while (l < r) {
            if (!num.get(l++).equals(num.get(r--))) return false;
        }
        return true;
    }

    /********* Solution 3: Revert the second half of int + Compare with first half *********/
    /**
     * 1221 => 12 == 12
     * 12321 => 12 == 123 / 10
     *
     * Time: O(N) or O(lg(x))   Space: O(1)
     */
    public boolean isPalindrome(int x) {
        if (x < 10) return x >= 0;
        if (x % 10 == 0) return false; // 必须有这个，不然最后一位是 0 的话（比如：10 或者 110）会错！！！
        int reverted = 0;
        while (reverted < x) {
            reverted = reverted * 10 + x % 10;
            x /= 10;
        }
        // 偶数位 || 奇数位
        return x == reverted || x == reverted / 10;
    }

    public static void main(String[] args) {
        PalindromeNumber solution = new PalindromeNumber();
        // false
        System.out.println(solution.isPalindrome(1000021));
        System.out.println(solution.isPalindrome(-121));
        System.out.println(solution.isPalindrome(10));
        // true
        System.out.println(solution.isPalindrome(12321));
        System.out.println(solution.isPalindrome(1221));
        System.out.println(solution.isPalindrome(1));
        System.out.println(solution.isPalindrome(0));
    }
}
