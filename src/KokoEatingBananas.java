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

    private int tryEat(int[] piles, int k) {
        int hrs = 0;
        for (int pile : piles) {
            hrs += Math.ceil((double) pile / k);
//            hrs += (pile / k) + (pile % k == 0 ? 0 : 1);
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
