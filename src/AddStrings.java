import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 415. Add Strings (https://leetcode.com/problems/add-strings/description/)
 */
public class AddStrings {

    /************ Solution 1: List<String> + Collections.reverse() ****************/
    /**
     * Time: O(N)   Space: O(N)
     */
    public String addStrings1(String num1, String num2) {
        int l1 = num1.length(), l2 = num2.length(), carry = 0;
        List<String> res = new ArrayList<>();
        for (int i = 1; l1 - i >= 0 || l2 - i >= 0 || carry > 0; i++) {
            if (l1 - i >= 0) carry += num1.charAt(l1 - i) - '0';
            if (l2 - i >= 0) carry += num2.charAt(l2 - i) - '0';
            res.add(String.valueOf(carry % 10)); // int -> String 不能自动转化！
            carry /= 10;
        }
        // Collections.reverse() 无返回值！
        Collections.reverse(res);
        return String.join("", res);
    }

    /************ Solution 2: StringBuilder   ****************/
    /**
     * Time: O(N)   Space: O(N)
     */
    public String addStrings2(String num1, String num2) {
        int p1 = num1.length() - 1, p2 = num2.length() - 1, carry = 0;
        StringBuilder sb = new StringBuilder();
        while (p1 >= 0 || p2 >= 0 || carry != 0) {
            int n1 = p1 >= 0 ? (num1.charAt(p1--) - '0') : 0;
            int n2 = p2 >= 0 ? (num2.charAt(p2--) - '0') : 0;
            int total = n1 + n2 + carry;
            carry = total / 10;
            sb.append(total % 10); // int -> char 自动转化！
        }
        // sb.reverse() 有返回值！
        return sb.reverse().toString();
    }

    /************ Solution 3: char[] + new String(arr, offset, count) ****************/
    /**
     * Time: O(N)   Space: O(N)
     */
    public String addStrings(String num1, String num2) {
        int n1 = num1.length(), n2 = num2.length();
        char[] res = new char[Math.max(n1, n2) + 1];
        int i = n1 - 1, j = n2 - 1, k = res.length - 1, carry = 0;

        while (i >= 0 || j >= 0 || carry != 0) {
            if (i >= 0) carry += num1.charAt(i--) - '0';
            if (j >= 0) carry += num2.charAt(j--) - '0';
            res[k--] = (char) ('0' + (carry % 10)); // int -> char 无自动转化！
            carry /= 10;
        }
        // char[] -> String: new String(arr, offset, count) = new String(arr, offset, len - offset);
        return new String(res, k + 1, res.length - (k + 1));
    }

    public static void main(String[] args) {
        AddStrings solution = new AddStrings();
        System.out.println(solution.addStrings("123", "11"));
        System.out.println(solution.addStrings("456", "77"));
        System.out.println(solution.addStrings("0", "0"));
    }
}
