/**
 * 7. Reverse Integer (https://leetcode.com/problems/reverse-integer/description/)
 */
public class ReverseInteger {

    /******************* Solution 1: Overflow Check ***************************/
    /**
     * Time: O(logN), there is roughly O(logxN) digits in N (logxN 中 x 为底下)
     * Space: O(1)
     */
    public int reverse(int x) {
        int reversed = 0;
        while (x != 0) {
            int pop = x % 10;
            x /= 10;
            // ⚠️注意⚠️ 关键是这个 check ！！！
            if (reversed > Integer.MAX_VALUE / 10 || reversed == Integer.MAX_VALUE / 10 && pop > 7) return 0;
            if (reversed < Integer.MIN_VALUE / 10 || reversed == Integer.MIN_VALUE / 10 && pop < -8) return 0;
            reversed = reversed * 10 + pop;
        }
        return reversed;
    }

    public static void main(String[] args) {
        ReverseInteger solution = new ReverseInteger();
        System.out.println(solution.reverse(123)); // 321
        System.out.println(solution.reverse(-123)); // -321
        System.out.println(solution.reverse(120)); // 21
        System.out.println(solution.reverse(Integer.MIN_VALUE)); // 0
        System.out.println(solution.reverse(Integer.MAX_VALUE)); // 0
        System.out.println(solution.reverse(1234567899)); // 0
        System.out.println(solution.reverse(-1234567899)); // 0
        System.out.println(solution.reverse(1534236469)); // 0
    }
}
