/**
 * 875. Koko Eating Bananas (https://leetcode.com/problems/koko-eating-bananas/description/)
 */
public class KokoEatingBananas {

    /*********** Solution 1: Binary Search in 可行解 feasible solutions **********/
    /**
     * 可行解 => [1, max(piles)]
     * 在可行解范围内 binary search
     *
     * Time: O(NlogM)
     * N is the piles.length, M is max(piles)
     *
     * 类似:
     * 410. Split Array Largest Sum (https://leetcode.com/problems/split-array-largest-sum/)
     * 378. Kth Smallest Element in a Sorted Matrix (https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/)
     * 875. Koko Eating Bananas (https://leetcode.com/problems/koko-eating-bananas/description/)
     * 878. Nth Magical Number (https://leetcode.com/problems/nth-magical-number/)
     * 1011. Capacity To Ship Packages Within D Days (https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/description/)
     * 1231. Divide Chocolate (https://leetcode.com/problems/divide-chocolate/)
     *
     * Space: O(1)
     */
    public int minEatingSpeed(int[] piles, int h) {
        int maxPile = piles[0];
        for (int i = 1; i < piles.length; i++) maxPile = Math.max(maxPile, piles[i]);
        if (h == piles.length) return maxPile;
        int lo = 1, hi = maxPile;
        while (lo < hi) {
            int mid = (lo + hi) >> 1;
            int hrs = tryEat(piles, mid);
            if (hrs <= h) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }

    /**
     * ceil(b / k) = (b + k - 1) / k   // integer division
     * “想要整数除法向上取整，就加 divisor - 1”
     * ⚠️注意⚠️ 只在 b >= 0, k > 0 时有效！
     *
     * 假设 a = -10, b = 3
     * | 表达式                   | 结果 |
     * | --------------------- | -: |
     * | `a / b`               | -3 |
     * | `Math.ceilDiv(a, b)`  | -3 |
     * | `Math.floorDiv(a, b)` | -4 |
     *
     * 假设 a = 10, b = 3
     * | 表达式                   | 结果 |
     * | --------------------- | -: |
     * | `a / b`               |  3 |
     * | `Math.floorDiv(a, b)` |  3 |
     * | `(a + b - 1) / b`     |  4 |
     * | `Math.ceilDiv(a, b)`  |  4 |
     */
    private int tryEat(int[] piles, int k) {
        int hrs = 0;
        for (int b : piles) {
            hrs += (b + k - 1) / k;
//            hrs += Math.ceil((double) b / k);
//            hrs += (b / k) + (b % k == 0 ? 0 : 1);
        }
        return hrs;
    }

    public static void main(String[] args) {
        KokoEatingBananas solution = new KokoEatingBananas();
        // TC1
        System.out.println(solution.minEatingSpeed(new int[]{3,6,7,11}, 8)); // 4
        // TC2
        System.out.println(solution.minEatingSpeed(new int[]{30,11,23,4,20}, 5)); // 30
        // TC3
        System.out.println(solution.minEatingSpeed(new int[]{30,11,23,4,20}, 6)); // 23
        // TC4
        System.out.println(solution.minEatingSpeed(new int[]{3,6,7,11}, 4)); // 11
    }
}
