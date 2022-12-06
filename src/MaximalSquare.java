public class MaximalSquare {

    /************ Solution 1: 2D DP 记录{边长，横向1，纵向1} *****************/
    /**
     * dp[i][j] = {l, m, n}
     * l 表示以 matrix[i][j] 为右下角的最大square的边长
     * m 表示到 matrix[i][j] 为止左边连续 1 个数
     * n 表示到 matrix[i][j] 为止上边连续 1 个数
     *
     * if matrix[i][j] == '1':
     * dp[i][j][0] = min(dp[i-1][j-1][0],dp[i][j-1][1],dp[i-1][j][2]) + 1;
     * dp[i][j][1] = dp[i][j-1][1] + 1;
     * dp[i][j][2] = dp[i-1][j][2] + 1;
     * else:
     * dp[i][j] = {0,0,0}
     *
     * Time: O(R X C)   Space: (3C)
     */
    public int maximalSquare1(char[][] matrix) {
        int r = matrix.length, c = matrix[0].length, res = 0;
        int[][] dp = new int[c + 1][3];
        for (int i = 0; i < r; i++) {
            int pre0 = 0;
            for (int j = 1; j <= c; j++) {
                int cur0 = dp[j][0];
                if (matrix[i][j - 1] == '1') {
                    dp[j][0] = Math.min(pre0, Math.min(dp[j - 1][1], dp[j][2])) + 1;
                    res = Math.max(res, dp[j][0]);
                    dp[j][1] = dp[j - 1][1] + 1;
                    dp[j][2] += 1;
                } else {
                    dp[j] = new int[]{0, 0, 0};
                }
                pre0 = cur0;
            }
        }
        return res * res;
    }

    /************ Solution 2: 2D DP 优化，只记录边长 *****************/
    /**
     * dp[i][j] = l
     * l 表示以 matrix[i][j] 为右下角的最大square的边长
     *
     * if matrix[i][j] == '1':
     * dp[i][j] = min(dp[i-1][j-1],dp[i][j-1],dp[i-1][j]) + 1;
     * else:
     * dp[i][j] = 0
     *
     * Time: O(R X C)   Space: (C)
     */
    public int maximalSquare(char[][] matrix) {
        int r = matrix.length, c = matrix[0].length, res = 0;
        int[] dp = new int[c + 1];
        for (int i = 0; i < r; i++) {
            int pre = 0;
            for (int j = 1; j <= c; j++) {
                int cur = dp[j];
                if (matrix[i][j - 1] == '1') {
                    dp[j] = Math.min(pre, Math.min(dp[j - 1], dp[j])) + 1;
                    res = Math.max(res, dp[j]);
                } else {
                    dp[j] = 0;
                }
                pre = cur;
            }
        }
        return res * res;
    }

    public static void main(String[] args) {
        MaximalSquare solution = new MaximalSquare();

        System.out.println(solution.maximalSquare(new char[][]{
                {'1','1','1','1','1'},
                {'1','1','1','1','1'},
                {'0','0','0','0','0'},
                {'1','1','1','1','1'},
                {'1','1','1','1','1'}
        }));

        System.out.println(solution.maximalSquare(new char[][]{
                {'1','0','1','0','0'},
                {'1','0','1','1','1'},
                {'1','1','1','1','1'},
                {'1','0','0','1','0'}
        }));

        System.out.println(solution.maximalSquare(new char[][]{
                {'1'}
        }));
    }
}
