public class BurstBalloons {

    /************** Solution 1: 2D DP *******************/
    /**
     * Top-down by recur state + memo => Top-down DP => Bottom-up DP
     * 演化思路：https://leetcode.com/problems/burst-balloons/solution/
     *
     * dp[left][right] 表示在 [left,right] 范围内只剩一个 balloon 要 burst 的话，最大能获得的 coin
     * 假设这个 balloon 是 i，有 left <= i <= right
     * 则 burst 该 balloon 的 gain = balloon[left-1] * balloon[i] * balloon[right+1]
     * 在 busrt 之前，burst [left,i-1] 和 [i+1,right] 的 remain = dp[left][i-1] + dp[i+1][right]
     * 遍历所有 i (left <= i <= right), 最大的 gain + remain => dp[left][right]
     *
     * （1）dp 只有右上半边(包含对角线)有意义
     * （2）dp[0][r] 和 dp[l][n-1] 没有意义，因为不能 burst fake ballons
     * （3）为了保证在求 dp[left][right] 时，所有 dp[left][i-1] 和 dp[i+1][right] 都已知，需从右下方开始横向z字型向上求解
     *     dp[left][i-1] 在 dp[left][right] 的同行左侧
     *     dp[i+1][right] 在 dp[left][right] 的同列下方
     *     
     * Time: O(N^3)   Space: O(N^2) by 2D DP array
     */
    public int maxCoins(int[] nums) {
        // add 2 fake balloons and guardians, but never burst them.
        int n = nums.length + 2;
        int[] newNums = new int[n];
        System.arraycopy(nums, 0, newNums, 1, nums.length);
        newNums[0] = newNums[n - 1] = 1;

        int[][] dp = new int[n][n];
        for (int l = n - 2; l >= 1; l--) {
            for (int r = l; r <= n - 2; r++) {
                for (int i = l; i <= r; i++) {
                    int gain = newNums[l - 1] * newNums[i] * newNums[r + 1];
                    int remain = dp[l][i - 1] + dp[i + 1][r];
                    dp[l][r] = Math.max(dp[l][r], gain + remain);
                }
            }
        }
        return dp[1][n - 2];
    }

    public static void main(String[] args) {
        BurstBalloons solution = new BurstBalloons();
        System.out.println(solution.maxCoins(new int[]{3,1,5,8}));
        System.out.println(solution.maxCoins(new int[]{1,5}));
    }
}
