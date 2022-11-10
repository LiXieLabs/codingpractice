public class CountSquareSubmatricesWithAllOnes {
    //   input:
    //   [0,1,1,1],
    //   [1,1,1,1],
    //   [0,1,1,1]

    //   DP:
    //   [0,1,1,1],
    //   [1,1,2,2],
    //   [0,1,2,3]
    public int countSquares(int[][] matrix) {
        int[] dp = new int[matrix[0].length + 1];
        int res = 0;
        for (int i = 0; i < matrix.length; i++) {
            int pre = dp[0];
            for (int j = 1; j < matrix[0].length + 1; j++) {
                int tmp = dp[j];
                if (matrix[i][j-1] == 1) {
                    dp[j] = Math.min(dp[j-1], Math.min(pre, dp[j])) + 1;
                } else {
                    dp[j] = 0;
                }
                pre = tmp;
                res += dp[j];
            }
        }
        return res;
    }

    public static void main(String[] args) {
        CountSquareSubmatricesWithAllOnes solution = new CountSquareSubmatricesWithAllOnes();
        System.out.println(solution.countSquares(new int[][]{{0,1,1,1},{1,1,1,1},{0,1,1,1}}));
        System.out.println(solution.countSquares(new int[][]{{1,1},{0,0},{1,1}}));
    }
}
