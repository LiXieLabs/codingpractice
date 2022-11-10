import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

    /******************* Solution 2: DP *********************/
    /**
     * dp[x][y][z] 标记从(x,y)出发，恰好用z次move，可以出界的路线个数
     * z == 1 时，所有corner是2，所有edge是1
     * z > 1 时，d[x][y][z] = d[x+1][y][z-1] if(x+1,y)未出界
     *                      +d[x-1][y][z-1] if(x-1,y)未出界
     *                      +d[x][y+1][z-1] if(x,y+1)未出界
     *                      +d[x][y-1][z-1] if(x,y-1)未出界
     * 随着x,y平面沿着z轴推进，不断累加d[startRow][startColumn]，
     * 即为用<=maxMove步，走出边界的路线个数总和
     *
     * Time: O(m*n*maxMove)
     * Space: O(m*n*maxMove)
     */
    public int findPaths(int m, int n, int maxMove, int startRow, int startColumn) {
        int total = 0;
        int[][] prev = new int[m][n];
        for (int move = 1; move <= maxMove; move++) {
            int[][] curr = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    // init dp
                    if (move == 1) {
                        if (i == 0) curr[i][j]++;
                        if (i == m - 1) curr[i][j]++;
                        if (j == 0) curr[i][j]++;
                        if (j == n - 1) curr[i][j]++;
                    }
                    for (int[] d : direc) {
                        int ni = i + d[0], nj = j + d[1];
                        if (0 <= ni && ni < m && 0 <= nj && nj < n) {
                            curr[i][j] = (curr[i][j] + prev[ni][nj]) % mod;
                        }
                    }
                }
            }
            total = (total + curr[startRow][startColumn]) % mod;
            prev = curr;
        }
        return total;
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
