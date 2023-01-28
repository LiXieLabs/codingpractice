import java.util.Arrays;
import java.util.Comparator;

/**
 * 1691. Maximum Height by Stacking Cuboids (https://leetcode.com/problems/maximum-height-by-stacking-cuboids/description/)
 */
public class MaximumHeightByStackingCuboids {

    /***************************** Solution 1: 1D DP ******************************/
    /**
     * Step 1: sort each cuboid in increasing order & sort cuboids in decreasing order
     * Step 2: dp[i] denotes max height including cuboid i
     *         dp[i] = max(cuboids[i][2], cuboids[i][2] + d[j]) for (j <= i && cuboids[j][0&1&2] <= cuboids[i][0&1&2])
     *         即对于 cuboid i，找到前面可以放置于其上的 cuboid j，cuboids[i][2] + d[j] 即为新的 height 的 candidate，找到最大值为 dp[i]
     *         如果都没有，则单独放置, 则 cuboids[i][2] 为 dp[i]
     *
     * Time: O(N^2)   Space: O(N)
     */
    public int maxHeight(int[][] cuboids) {

        // sort each cuboid in increasing order & sort cuboids in decreasing order
        for (int i = 0; i < cuboids.length; i++) {
            Arrays.sort(cuboids[i]);
        }
        Arrays.sort(cuboids, Comparator.comparingInt(c -> -(c[0] + c[1] + c[2])));

        // dp[i] denotes max height including cuboid i
        // dp[i] = max(cuboids[i][2], cuboids[i][2] + d[j]) for (j <= i && cuboids[j][0&1&2] <= cuboids[i][0&1&2])
        int maxHeight = cuboids[0][2];
        int[] dp = new int[cuboids.length];
        dp[0] = maxHeight;
        for (int i = 1; i < cuboids.length; i++) {
            int curHeight = cuboids[i][2];
            for (int j = i - 1; j >= 0; j--) {
                if (cuboids[i][0] <= cuboids[j][0] && cuboids[i][1] <= cuboids[j][1] && cuboids[i][2] <= cuboids[j][2]) {
                    curHeight = Math.max(curHeight, cuboids[i][2] + dp[j]);
                }
            }
            dp[i] = curHeight;
            maxHeight = Math.max(maxHeight, curHeight);
        }
        return maxHeight;
    }

    public static void main(String[] args) {
        MaximumHeightByStackingCuboids solution = new MaximumHeightByStackingCuboids();
        System.out.println(solution.maxHeight(new int[][]{{5,6,4},{7,9,8},{3,2,1}}));
        System.out.println(solution.maxHeight(new int[][]{{50,45,20},{95,37,53},{45,23,12}}));
        System.out.println(solution.maxHeight(new int[][]{{38,25,45},{76,35,3}}));
        System.out.println(solution.maxHeight(new int[][]{{7,11,17},{7,17,11},{11,7,17},{11,17,7},{17,7,11},{17,11,7}}));

    }
}
