import java.util.Random;

public class RandomPickWithWeight {

    /********* Solution 1: Random + Prefix Sum + Binary Search (Bisect Left) ******/
    /**
     * bisect left: https://github.com/python/cpython/blob/2.7/Lib/bisect.py#L67
     * bisect right: https://github.com/python/cpython/blob/2.7/Lib/bisect.py#L24
     *
     * original w:                2,         3
     * prefix sum:                2,         5
     * indices with weight: 0,  0,  1,  1,  1
     * possible sums:       1,  2,  3,  4,  5
     *                     1&2 <= 2   3&4&5<=5
     */

    Random rand;
    int[] w;

    public RandomPickWithWeight(int[] w) {
        this.w = w;
        // calculate w as prefix sum array
        for (int i = 1; i < w.length; i++) {
            w[i] += w[i-1];
        }
        rand = new Random();
    }

    public int pickIndex() {
        // pick random from sum of weight
        int pick = rand.nextInt(w[w.length - 1]) + 1;
        // bisect left find the slot s,
        // s.t. all i < s, prefixSum[i] < pick,
        // and prefixSum[s] is the first prefixSum >= pick.
        int lo = 0, hi = w.length;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (w[mid] < pick) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo;
    }

    public static void main(String[] args) {
        RandomPickWithWeight solution = new RandomPickWithWeight(new int[]{1, 3, 2});
        System.out.println(solution.pickIndex());
        System.out.println(solution.pickIndex());
        System.out.println(solution.pickIndex());
        System.out.println(solution.pickIndex());
        System.out.println(solution.pickIndex());

        System.out.println(solution.pickIndex());
        System.out.println(solution.pickIndex());
        System.out.println(solution.pickIndex());
        System.out.println(solution.pickIndex());
        System.out.println(solution.pickIndex());

        System.out.println(solution.pickIndex());
        System.out.println(solution.pickIndex());
        System.out.println(solution.pickIndex());
        System.out.println(solution.pickIndex());
        System.out.println(solution.pickIndex());

        System.out.println(solution.pickIndex());
        System.out.println(solution.pickIndex());
        System.out.println(solution.pickIndex());
        System.out.println(solution.pickIndex());
        System.out.println(solution.pickIndex());

        System.out.println(solution.pickIndex());
        System.out.println(solution.pickIndex());
        System.out.println(solution.pickIndex());
        System.out.println(solution.pickIndex());
        System.out.println(solution.pickIndex());

        System.out.println(solution.pickIndex());
        System.out.println(solution.pickIndex());
        System.out.println(solution.pickIndex());
        System.out.println(solution.pickIndex());
        System.out.println(solution.pickIndex());
    }

}
