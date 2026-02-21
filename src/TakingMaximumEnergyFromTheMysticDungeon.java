public class TakingMaximumEnergyFromTheMysticDungeon {

    /******** Solution 1: 1D DP backward **************/
    /**
     * dp[i] denotes energy we gain starting from index i
     * dp[i] = dp[i + k] + energy[i]
     *
     * Time: O(N)   Space: O(N)
     */
    public int maximumEnergy1(int[] energy, int k) {
        int[] dp = new int[energy.length];
        int res = Integer.MIN_VALUE;
        for (int i = dp.length - 1; i >= 0; i--) {
            dp[i] = energy[i];
            if (i + k < dp.length) dp[i] += dp[i + k];
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    /******** Solution 2: 空间优化 by rolling index **************/
    /**
     * Time: O(N)   Space: O(k)
     */
    public int maximumEnergy2(int[] energy, int k) {
        int[] dp = new int[k];
        int res = Integer.MIN_VALUE;
        for (int i = energy.length - 1; i >= 0; i--) {
            int r = i % k;
            dp[r] += energy[i];
            res = Math.max(res, dp[r]);
        }
        return res;
    }

    /******** Solution 3: 空间优化 by in-place update **************/
    /**
     * Time: O(N)   Space: O(1)
     */
    public int maximumEnergy(int[] energy, int k) {
        int res = Integer.MIN_VALUE;
        for (int i = energy.length - 1; i >= 0; i--) {
            if (i + k < energy.length) energy[i] += energy[i + k];
            res = Math.max(res, energy[i]);
        }
        return res;
    }
}
