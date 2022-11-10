import java.util.Random;

public class RandomPickWithWeight {

    /********* Solution 1: Random + Prefix Sum + Binary Search (Bisect Left) ******/
    /**
     * bisect left: https://github.com/python/cpython/blob/2.7/Lib/bisect.py#L67
     * bisect right: https://github.com/python/cpython/blob/2.7/Lib/bisect.py#L24
     */

    Random rand = new Random();
    int[] w;

    public RandomPickWithWeight(int[] w) {
        int i = 0;
        // add weights as prefix sum for binary search
        while (++i < w.length) {
            w[i] += w[i-1];
        }
        this.w = w;
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
