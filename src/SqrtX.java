/**
 * 69. Sqrt(x) (https://leetcode.com/problems/sqrtx/description/)
 */
public class SqrtX {

    /************ Solution 1: 可行解中 Binary Search ***************/
    /**
     * Time: O(logN)   Space: O(1)
     */
    public int mySqrt1(int x) {
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

    /************ Solution 2: 可行解中 Binary Search ***************/
    /**
     * 更好理解
     *
     * Time: O(logN)   Space: O(1)
     */
    public int mySqrt(int x) {
        long lo = 1, hi = x / 2;
        while (lo <= hi) {
            long mid = lo + (hi - lo) / 2;
            long lower = mid * mid;
            long upper = (mid + 1) * (mid + 1);
            if (x < lower) {
                hi = mid - 1;
            } else if (x > upper) {
                lo = mid + 1;
            } else {
                return x == upper ? (int) mid + 1 : (int) mid;
            }
        }
        return x; // corner case TC1 & TC2
    }

    public static void main(String[] args) {
        SqrtX solution = new SqrtX();

        System.out.println(solution.mySqrt(0)); // 0
        System.out.println(solution.mySqrt(1)); // 1
        System.out.println(solution.mySqrt(4)); // 2
        System.out.println(solution.mySqrt(8)); // 2
        System.out.println(solution.mySqrt(2147395599)); // 46339
        System.out.println(solution.mySqrt(2147483647)); // 46340
    }
}
