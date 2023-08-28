/**
 * 69. Sqrt(x) (https://leetcode.com/problems/sqrtx/description/)
 */
public class SqrtX {

    /************ Solution 1: 可行解中 Binary Search ***************/
    /**
     * Time: O(logN)   Space: O(1)
     */
    public int mySqrt(int x) {
        // ⚠️注意！！！必须 long，不然 TC5 溢出不然进入死循环！！！
        long lo = 0, hi = x;
        while (lo < hi) {
            // ⚠️注意！！！必须 + 1，不然 TC4 死循环！！！
            long mid = (lo + hi + 1) >> 1;
            long y = mid * mid;
            if (y == x) return (int) mid;
            if (y > x) {
                hi = mid - 1;
            } else {
                lo = mid;
            }
        }
        return (int) lo;
    }

    public static void main(String[] args) {
        SqrtX solution = new SqrtX();

        System.out.println(solution.mySqrt(0)); // 0
        System.out.println(solution.mySqrt(1)); // 1
        System.out.println(solution.mySqrt(4)); // 2
        System.out.println(solution.mySqrt(8)); // 2
        System.out.println(solution.mySqrt(2147395599)); // 46339
    }
}
