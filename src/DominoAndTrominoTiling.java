/**
 * 790. Domino and Tromino Tiling (https://leetcode.com/problems/domino-and-tromino-tiling/description/)
 */
public class DominoAndTrominoTiling {

    private static final int MOD = (int) 1e9 + 7;

    /********* Solution 1: 1D DP ***************/
    /**
     * f[i] 表示到第 i 列全部填满的方法总数
     * p[i] 表示到第 i 列右上角没填满的方法总数，与右下角没填满数量相等
     *
     * f[i] = f[i-1] + f[i-2] + 2p[i-1]
     * p[i] = f[i-2] + p[i-1]
     *
     * f[i] = f[i-1] + f[i-2] + 2p[i-1]
     * f[i-1] 对应将新增的第i列铺一个"日"字型tile
     * f[i-2] 对应将新增的第i列和前面的第i-1列铺两个横着的"日"字型tile
     * p[i-1] 对应将新增的第i列和前面的第i-1列的左下角角铺一个"』"型tile
     * 乘以2，是因为有一个轴对称情况
     *
     * Time: O(N)   Space: O(N)
     */
    public int numTilings1(int n) {
        long[] f = new long[n + 1];
        f[0] = f[1] = 1;
        long[] p = new long[n + 1];
        for (int i = 2; i <= n; i++) {
            f[i] = (f[i - 1] + f[i - 2] + 2 * p[i - 1]) % MOD;
            p[i] = (f[i - 2] + p[i - 1]) % MOD;
        }
        return (int) f[n];
    }

    /********* Solution 2: 1D DP with rolling index array ***************/
    /**
     * 与 Solution 1 一样，Space Optimization by Rolling Index
     *
     * Time: O(N)   Space: O(1)
     */
    public int numTilings2(int n) {
        long[][] dp = new long[3][2];
        dp[0][0] = dp[1][0] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i % 3][0] = (dp[(i + 2) % 3][0] + dp[(i + 1) % 3][0] + 2 * dp[(i + 2) % 3][1]) % MOD;
            dp[i % 3][1] = (dp[(i + 1) % 3][0] + dp[(i + 2) % 3][1]) % MOD;
        }
        return (int) dp[n % 3][0];
    }

    /********* Solution 3: 1D DP with no partial array + rolling index array ***************/
    /**
     * f[i] = f[i-1] + f[i-2] + 2p[i-1] ----- (1)
     * p[i] = f[i-2] + p[i-1] ------ (2)
     *
     * f[i-1] = f[i-2] + f[i-3] + 2p[i-2] ------ 由(1)推导
     * f[i] - f[i-1] = f[i-1] + f[i-2] + 2p[i-1] - f[i-2] - f[i-3] - 2p[i-2]
     *               = f[i-1] - f[i-3] + 2(p[i-1] - p[i-2])
     *
     * p[i-1] - p[i-2] = f[i-3] ------ 由(2)推导
     * f[i] = 2f[i-1] + f[i-3] ====> 最终状态转移方程，摆脱了p[]依赖，类似Fibonacci数列
     *
     * Time: O(N)   Space: O(1)
     */
    public int numTilings(int n) {
        long[] f = new long[4];
        f[0] = f[1] = 1;
        for (int i = 2; i <= n; i++) {
            f[i % 4] = (2 * f[(i + 3) % 4] + f[(i + 1) % 4]) % MOD;
        }
        return (int) f[n % 4];
    }

    public static void main(String[] args) {
        DominoAndTrominoTiling solution = new DominoAndTrominoTiling();
        System.out.println(solution.numTilings(6)); //53
        System.out.println(solution.numTilings(30)); //312342182
        System.out.println(solution.numTilings(60)); //882347204
    }
}
