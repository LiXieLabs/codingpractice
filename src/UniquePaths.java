/**
 * 62. Unique Paths (https://leetcode.com/problems/unique-paths/description/)
 */
public class UniquePaths {

    /*********** Solution 1: 2D DP + 1D 空间优化 ****************/
    /**
     * dp[i][j] = dp[i-1][j] + dp[i][j-1]
     *
     * Time: O(R X C)   Space: O(C)
     */
    public int uniquePaths(int m, int n) {
        int[] dp = new int[n+1];
        dp[1] = 1;
        for (int i = 0; i < m; i++) {
            for (int j = 1; j < dp.length; j++) {
                dp[j] += dp[j-1];
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        UniquePaths solution = new UniquePaths();
        System.out.println(solution.uniquePaths(3, 7));
        System.out.println(solution.uniquePaths(3, 2));
    }
}
