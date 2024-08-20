/**
 * 312. Burst Balloons (https://leetcode.com/problems/burst-balloons/description/)
 */
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
     * （2）为了保证在求 dp[left][right] 时，所有 dp[left][i-1] 和 dp[i+1][right] 都已知，需从对角线向右上方推进
     *     即先从1开始遍历长度，left递增，类似制造一个逐渐增大的sliding window
     *
     * Time: O(N^3)   Space: O(N^2) by 2D DP array
     */
    public int maxCoins(int[] nums) {
        // add 2 fake balloons and guardians, but never burst them.
        int n = nums.length;
        int[] newNums = new int[n + 2];
        System.arraycopy(nums, 0, newNums, 1, n);
        newNums[0] = newNums[n + 1] = 1;

        int[][] dp = new int[n][n];
        for (int l = 1; l <= nums.length; l++) {
            for (int i = 0; i + l <= nums.length; i++) {
                int maxij = 0;
                int j = i + l - 1;
                for (int k = i; k <= j; k++) {
                    int left = i < k ? dp[i][k-1] : 0;
                    int right = k < j ? dp[k+1][j] : 0;
                    maxij = Math.max(maxij, left + right + newNums[i] * newNums[k+1] * newNums[j+2]);
                }
                dp[i][j] = maxij;
            }
        }
        return dp[0][n - 1];
    }

    public static void main(String[] args) {
        BurstBalloons solution = new BurstBalloons();
        System.out.println(solution.maxCoins(new int[]{3,1,5,8})); // 167
        System.out.println(solution.maxCoins(new int[]{1,5})); // 10
    }
}
