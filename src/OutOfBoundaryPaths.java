import java.util.Arrays;

public class OutOfBoundaryPaths {

    private final static int[][] direc = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    // 小心！！！Math.pow 是 double
    private final static int mod = (int) (Math.pow(10, 9) + 7);
    private int m, n;
    private int[][][] memo;

    /******************* Solution 1: Recur + Memo (3D array) *********************/
    /**
     * Time: Amortized to O(m*n*maxMove)
     * Space: O(m*n*maxMove) from 3D memo array
     */
    public int findPaths1(int m, int n, int maxMove, int startRow, int startColumn) {
        this.m = m;
        this.n = n;
        memo = new int[m][n][maxMove+1];
        // array设置初始值！！！
        for (int[][] plane : memo) for (int[] line : plane) Arrays.fill(line, -1);
        return recur(maxMove, startRow, startColumn);
    }

    public int recur(int remainMove, int i, int j) {
        if (i < 0 || i >= m || j < 0 || j >= n) return 1;
        if (remainMove == 0) return 0;
        if (memo[i][j][remainMove] == -1) {
            int count = 0;
            for (int[] d : direc) {
                int ni = i + d[0], nj = j + d[1];
                count = (count + recur(remainMove - 1, ni, nj)) % mod;
            }
            memo[i][j][remainMove] = count;
        }
        return memo[i][j][remainMove];
    }

    /******************* Solution 2: 3D DP *********************/
    /**
     * dp[x][y][z] 标记 z moves 时，到达 x, y 坐标有几种路径
     * 初始状态 dp[startRow][startCol][0] = 1
     * 随着x,y平面沿着z轴推进, 相当于一个 2D 平面的 BFS
     * 每走一步 (z+=1)，遍历 x, y 平面，对于每个位置，只要 > 0 (说明上一层已经可以到达该位置), 则向四周推进一步
     * 如果出界了，累计入结果；如果没出界，更新入下一层路径数。
     *
     * Time: O(m*n*maxMove)
     * Space: O(m*n*maxMove)
     */
    private static final int[][] DIREC = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    private static final int MOD = 1_000_000_007;

    public int findPaths(int m, int n, int maxMove, int startRow, int startColumn) {
        int[][] curr = new int[m][n];
        curr[startRow][startColumn] = 1;
        int res = 0;
        for (int move = 0; move < maxMove; move++) {
            int[][] next = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (curr[i][j] > 0) {
                        for (int[] d : DIREC) {
                            int ni = i + d[0], nj = j + d[1];
                            if (0 <= ni && ni < m && 0 <= nj && nj < n) {
                                next[ni][nj] = (next[ni][nj] + curr[i][j]) % MOD;
                            } else {
                                res = (res + curr[i][j]) % MOD;
                            }
                        }
                    }
                }
            }
            curr = next;
        }
        return res;
    }


    public static void main(String[] args) {
        OutOfBoundaryPaths solution = new OutOfBoundaryPaths();
        System.out.println(solution.findPaths(10, 10, 0, 5, 5));
        System.out.println(solution.findPaths(2, 2, 2, 0, 0));
        System.out.println(solution.findPaths(1, 3, 3, 0, 1));
        System.out.println(solution.findPaths(3, 3, 3, 1, 2));
        System.out.println(solution.findPaths(8, 50, 23, 5, 26));
    }
}
