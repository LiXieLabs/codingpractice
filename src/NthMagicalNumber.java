/**
 * 878. Nth Magical Number (https://leetcode.com/problems/nth-magical-number/)
 */
public class NthMagicalNumber {

//    private static final int MOD = (int) (1e9 + 7);
    private static final int MOD = 1_000_000_007;

    /*********** Solution 1: Two Pointers Sort 2 Sorted Arrays (TLE) *************/
    /**
     * nth smallest element in 2 sorted array
     * Time: O(N)   Space: O(1)
     */
    public int nthMagicalNumber1(int n, int a, int b) {
        int ma = 0, pa = 0, mb = 0, pb = 0, count = 0, res = 0;
        while (count < n) {
            if (ma < mb || pa + a < pb + b) {
                pa += a;
                ma += pa / MOD;
                pa %= MOD;
                res = pa;
            } else if (ma > mb || pa + a > pb + b) {
                pb += b;
                mb += pb / MOD;
                pb %= MOD;
                res = pb;
            } else {
                pa += a;
                ma += pa / MOD;
                pa %= MOD;
                pb += b;
                mb += pb / MOD;
                pb %= MOD;
                res = pa;
            }
            count++;
        }
        return res;
    }

    /************* Solution 2: Binary Search in Possible Results *****************/
    /**
     * 对于一个数字 x，不超过 x 的、能被 a 或 b 整除的数共有 N 个:
     * N = x / a + x / b - x / lcm
     * 其中 lcm = leastCommonMultiple(a, b) = a * b / greatestCommonDivisor(a, b)
     *         = 最小公倍数 = a x b / 最大公约数
     *
     * x 的范围是 [min(a,b), n * min(a,b)]
     *
     * 可行解中二分查找还有：
     * 类似:
     * 410. Split Array Largest Sum (https://leetcode.com/problems/split-array-largest-sum/)
     * 378. Kth Smallest Element in a Sorted Matrix (https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/)
     * 875. Koko Eating Bananas (https://leetcode.com/problems/koko-eating-bananas/description/)
     * 878. Nth Magical Number (https://leetcode.com/problems/nth-magical-number/)
     * 1011. Capacity To Ship Packages Within D Days (https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/description/)
     * 1231. Divide Chocolate (https://leetcode.com/problems/divide-chocolate/)
     *
     * Time: O(log(n*min(a,b))   Space: O(1)
     */
    public int nthMagicalNumber(int n, int a, int b) {
        long lcm = (long) a * b / gcd(a, b);
        long lo = Math.min(a, b), hi = n * lo;
        while (lo < hi) {
            long mid = lo + (hi - lo) / 2;
            long count = mid / a + mid / b - mid / lcm; // count 表示 mid 在 a 和 b 的所有倍数中排第几位
            if (count < n) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return (int) (lo % MOD);
    }

    // a  b  a%b  b%(a%b) ...
    // gcd(a,b) = gcd(b,a%b) 不断把 a/b 部分去掉！
    private int gcd(int a, int b) {
        if (a < b) {
            int tmp = a;
            a = b;
            b = tmp;
        }
        while (b != 0) {
            int tmp = a % b;
            a = b;
            b = tmp;
        }
        return a;
    }

    public static void main(String[] args) {
        NthMagicalNumber solution = new NthMagicalNumber();
        System.out.println(solution.nthMagicalNumber(1, 2, 3));
        System.out.println(solution.nthMagicalNumber(4, 2, 3));
        System.out.println(solution.nthMagicalNumber(5, 2, 4));
        System.out.println(solution.nthMagicalNumber(1000000000, 40000, 40000));
    }
}
