/**
 * 64. Minimum Path Sum (https://leetcode.com/problems/minimum-path-sum/description/)
 */
public class MinimumPathSum {

    /********************** Solution 1: 1D DP ***********************/
    /**
     * Transition function:
     * dp[i][j] denotes the min steps when reach at grid[i][j].
     * dp[i][j] = grid[i][j] + min(dp[i-1][j], dp[i][j-1])
     *
     * Time: O(R x C)   Space: O(C)
     */
    public int minPathSum1(int[][] grid) {
        int r = grid.length, c = grid[0].length;
        int[] dp = new int[c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                int prevMin = Math.min(
                        i == 0 ? Integer.MAX_VALUE : dp[j],
                        j == 0 ? Integer.MAX_VALUE : dp[j - 1]);
                dp[j] = grid[i][j] + (prevMin == Integer.MAX_VALUE ? 0 : prevMin);

            }
        }
        return dp[c - 1];
    }

    /********************** Solution 2: Space Complexity Optimized Solution 1 ***********************/
    /**
     * In-place keep record of dp matrix.
     *
     * Time: O(R x C)   Space: O(1)
     */
    public int minPathSum(int[][] grid) {
        int r = grid.length, c = grid[0].length;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                int prevMin = Math.min(
                        i == 0 ? Integer.MAX_VALUE : grid[i-1][j],
                        j == 0 ? Integer.MAX_VALUE : grid[i][j - 1]);
                grid[i][j] += (prevMin == Integer.MAX_VALUE ? 0 : prevMin);

            }
        }
        return grid[r - 1][c - 1];
    }

    public static void main(String[] args) {
        MinimumPathSum solution = new MinimumPathSum();
        System.out.println(solution.minPathSum(new int[][]{
                {1,3,1},
                {1,5,1},
                {4,2,1}
        })); // 7
        System.out.println(solution.minPathSum(new int[][]{
                {1,2,3},
                {4,5,6}
        })); // 12
    }

}
