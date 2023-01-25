/**
 * 415. Add Strings (https://leetcode.com/problems/add-strings/description/)
 */
public class AddStrings {

    /************ Solution 1: Simulation & String/Char Operation  ****************/
    /**
     * Time: O(N)   Space: O(N)
     */
    public String addStrings(String num1, String num2) {
        int p1 = num1.length() - 1, p2 = num2.length() - 1, carry = 0;
        StringBuilder sb = new StringBuilder();
        while (p1 >= 0 || p2 >= 0 || carry != 0) {
            int n1 = p1 >= 0 ? (num1.charAt(p1--) - '0') : 0;
            int n2 = p2 >= 0 ? (num2.charAt(p2--) - '0') : 0;
            int total = n1 + n2 + carry;
            carry = total / 10;
            sb.append(total % 10);
        }
        return sb.reverse().toString();
    }

    public static void main(String[] args) {
        AddStrings solution = new AddStrings();
        System.out.println(solution.addStrings("123", "11"));
        System.out.println(solution.addStrings("456", "77"));
        System.out.println(solution.addStrings("0", "0"));
    }
}
