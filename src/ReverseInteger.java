/**
 * 7. Reverse Integer (https://leetcode.com/problems/reverse-integer/description/)
 */
public class ReverseInteger {

    /******************* Solution 1: Overflow Check ***************************/
    /**
     * 不必变成正数处理！
     * -123 / 10 = -12；-123 % 10 = -3
     *
     * Time: O(logN), there is roughly O(log10N) digits in N (log10N 中 10 为底)
     * Space: O(1)
     */
    public int reverse(int x) {
        int reversed = 0;
        while (x != 0) {
            int pop = x % 10;
            x /= 10;
            // ⚠️注意⚠️ 关键是这个 check ！！！
            if (reversed > Integer.MAX_VALUE / 10 || reversed == Integer.MAX_VALUE / 10 && pop > Integer.MAX_VALUE % 10) return 0;
            if (reversed < Integer.MIN_VALUE / 10 || reversed == Integer.MIN_VALUE / 10 && pop < Integer.MIN_VALUE % 10) return 0;
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
