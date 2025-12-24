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
    public int mySqrt2(int x) {
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

    /************ Solution 3: 更小范围的可行解中 Binary Search ***************/
    /**
     * Time: O(logN)   Space: O(1)
     */
    public int mySqrt3(int x) {
        // find n, s.t. n * n <= x && (n + 1) * (n + 1) > x
        long lo = 0, hi = 1 << 16;
        while (lo < hi) {
            long mid = lo + (hi - lo) / 2;
            if (mid * mid > x) {
                hi = mid - 1;
            } else if ((mid + 1) * (mid + 1) <= x) {
                lo = mid + 1;
            } else {
                return (int) mid;
            }
        }
        return (int) lo;
    }

    /************ Solution 4: Recursion + bit manipulation ***************/
    /**
     * sqrt(n) = 2 x sqrt(n / 4)
     *         = 2^1 x sqrt(n / 2^2)
     *           m >> n = m / 2^n
     *           m << n = m x 2^n
     *         = sqrt(n >> 2) << 1
     *
     * Newton's Method 方法，此处省略，有时间再看！！！
     * https://en.wikipedia.org/wiki/Newton%27s_method
     *
     * Time: O(logN)   Space: O(logN) by recur stack
     */
    public int mySqrt(int x) {
        if (x < 2) return x; // base condition!!!
        int left = mySqrt(x >> 2) << 1;
        int right = left + 1;
        // 必须考虑 right，比如 sqrt(2)，left == 0, 结果是 1！！！
        return (long) right * right > x ? left : right;
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
