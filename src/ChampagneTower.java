public class ChampagneTower {

    /*************** Solution 1: Simulation by 2D DP *******************/
    /**
     *
     * Pascal Triangle
     * A[i][j] =          左上方溢出的一半            +        右上方溢出的一半
     *          Math.max(A[i-1][j-1] - 1, 0) / 2.0 + Math.max(A[i-1][j] - 1, 0) / 2.0
     *
     * 优化：
     *  假设 query_row == r, query_glass == c
     * （1）实际上不用遍历整个(0,0)到(r,c)的三角形，只需要遍历由(0,0),(r-c,0),(r,c),(c,c)构成的平行四边形即可
     *     只有这个平行四边形内的杯子对(r,c)位置的杯子有影响
     *     (r-c,0)在左侧，过了这个点，startCol每次可以缩进一个位置开始
     *     (c,c)在右侧，过了这个点，endCol到c即可停止，不用到达该行最末端
     * （2）如果某一行都是0了，则可以提前停止，防止poured很小，行数很大浪费时间
     * （3）dp用了guardians防止溢出，其值为0，表示没有酒
     * （4）1D DP + prev 表示 2D DP 按行滚动
     *
     * Time: O(N^2)  Space: O(N^2)
     */
    public double champagneTower(int poured, int query_row, int query_glass) {
        double[] dp = new double[query_row + 3];
        int startCol = 0;
        dp[1] = poured;
        for (int r = 1; r <= query_row; r++) {
            if (r > query_row - query_glass) startCol++;
            int endCol = Math.min(r, query_glass);
            double prev = dp[startCol];
            boolean allZero = true;
            for (int c = startCol; c <= endCol; c++) {
                double temp = dp[c + 1];
                double left = Math.max(prev - 1, 0) / 2.0;
                double right = Math.max(dp[c + 1] - 1, 0) / 2.0;
                dp[c + 1] = left + right;
                if (dp[c + 1] > 0) allZero = false;
                prev = temp;
            }
            if (allZero) break;
        }
        return Math.min(dp[query_glass + 1], 1);
    }

    public static void main(String[] args) {
        ChampagneTower solution = new ChampagneTower();
        System.out.println(solution.champagneTower(1,1,1));
        System.out.println(solution.champagneTower(2,1,1));
        System.out.println(solution.champagneTower(100000009,33,17));
        System.out.println(solution.champagneTower(2,2,0));
        System.out.println(solution.champagneTower(200,15,11));

    }

}