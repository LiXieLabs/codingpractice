/**
 * 829. Consecutive Numbers Sum (https://leetcode.com/problems/consecutive-numbers-sum/description/)
 */
public class ConsecutiveNumbersSum {

    /*********** Solution 1: Math + Enumeration ************/
    /**
     * s denotes start number
     * c denotes count of consecutive numbers starting from s, and sum to n
     * 这个关系可以表示为：
     *      s * c + (0 + c)(c + 1) / 2 = n
     *  =>  2sc + c^2 + c = 2n
     *  =>  c * (c + 2s + 1) = 2n
     *  遍历 c 的可行解 [1, sqrt(2n)], 尝试拆分为两个正整数的乘积
     */
    public int consecutiveNumbersSum(int n) {
        int result = 0;
        for (int c = 1; c <= (int) Math.sqrt(2 * n); c++) {
            if (2 * n % c == 0 && (2 * n / c - c - 1) % 2 == 0) {
                result++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        ConsecutiveNumbersSum solution = new ConsecutiveNumbersSum();
        System.out.println(solution.consecutiveNumbersSum(5));
        System.out.println(solution.consecutiveNumbersSum(9));
        System.out.println(solution.consecutiveNumbersSum(15));
    }
}
