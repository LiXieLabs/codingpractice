import java.util.Arrays;

/**
 * 174. Dungeon Game (https://leetcode.com/problems/dungeon-game/description/)
 */
public class DungeonGame {

    /**************** Solution 1: 2D DP *****************/
    /**
     * https://www.youtube.com/watch?v=pt-xIS6huIg
     * 
     * dp[i][j] 表示从 dungeon[i][j] 开始，
     * 最少需要多少 HP 可以到达右下角 dungeon[r-1][c-1]
     * 且离开时(即process完dungeon[i][j]) 还有 HP == 1
     *
     * dp[i][j] = max(1, min(dp[i+1][j],dp[i][j+1]) - dungeon[i][j])
     * 右下角往左上角推进，
     * 初始值为 dp[r][c-1] = dp[r-1][c] = 1，即到达右下角 dp[r-1][c-1] 的右边和下边时 HP 为 1
     *
     * min(dp[i+1][j],dp[i][j+1]) - dungeon[i][j]
     * 如果 dungeon[i][j] >= 0, 则需要更少的 HP 就可以到达 dungeon[i][j],
     *     min(dp[i+1][j],dp[i][j+1]) - dungeon[i][j] 有可能为负数，表示中间会加很多 HP，
     *     需要保持 >= 1，故与 1 取 max
     * 如果 dungeon[i][j] < 0, 则需要更多 HP, 则 min(dp[i+1][j],dp[i][j+1]) - dungeon[i][j] 为新的最小 HP 能到达 dungeon[i][j]
     *
     * Time: O(R X C)   Space: O(R X C)
     */
    public int calculateMinimumHP1(int[][] dungeon) {
        int row = dungeon.length, col = dungeon[0].length;
        int[][] dp = new int[row + 1][col + 1];

        // 初始化
        for (int[] r : dp) Arrays.fill(r, Integer.MAX_VALUE);
        dp[row][col - 1] = dp[row - 1][col] = 1;

        for (int i = row - 1; i >= 0; i--) {
            for (int j = col - 1; j >= 0; j--) {
                dp[i][j] = Math.max(1, Math.min(dp[i + 1][j], dp[i][j + 1]) - dungeon[i][j]);
            }
        }
        return dp[0][0];
    }

    /**************** Solution 2: Solution 1 优化至 1D *****************/
    /**
     * Time: O(R X C)   Space: O(C)
     */
    public int calculateMinimumHP(int[][] dungeon) {
        int row = dungeon.length, col = dungeon[0].length;
        int[] dp = new int[col + 1];

        // 初始化
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[col - 1] = 1;

        for (int i = row - 1; i >= 0; i--) {
            dp[col] = (i == row - 1 ? 1 : Integer.MAX_VALUE);
            for (int j = col - 1; j >= 0; j--) {
                dp[j] = Math.max(1, Math.min(dp[j], dp[j + 1]) - dungeon[i][j]);
            }
        }
        return dp[0];
    }

    public static void main(String[] args) {
        DungeonGame solution = new DungeonGame();
        System.out.println(solution.calculateMinimumHP(new int[][]{
                {-2,-3,3},
                {-5,-10,1},
                {10,30,-5}
        })); // 7
        System.out.println(solution.calculateMinimumHP(new int[][]{
                {0}
        })); // 1
    }
}
