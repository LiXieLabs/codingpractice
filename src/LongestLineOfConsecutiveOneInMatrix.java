import java.util.Arrays;

public class LongestLineOfConsecutiveOneInMatrix {

    // Solution 1
    public int longestLine1(int[][] mat) {
        if (mat == null || mat.length == 0 || mat[0] == null || mat[0].length == 0) return 0;
        int r = mat.length, c = mat[0].length, maxLen = 0;
        // horizontal, vertical, diagonal
        int[][] pre = new int[c + 1][3];
        for (int i = 0; i < r; i++) {
            int[][] cur = new int[c + 1][3];
            for (int j = 1; j < c + 1; j++) {
                if (mat[i][j - 1] == 1) {
                    cur[j][0] = cur[j - 1][0] + 1;
                    cur[j][1] = pre[j][1] + 1;
                    cur[j][2] = pre[j - 1][2] + 1;
                }
                // 记住Arrays.stream求最大最小值方式
                maxLen = Math.max(maxLen, Arrays.stream(cur[j]).reduce(Integer::max).getAsInt());
            }
            pre = cur;
        }
        // anti-diagonal
        for (int total = 0; total < r + c - 2; total++) {
            // total+1, r, c中最小的数是这条反对角线上的元素数，如果不可能超越当先最大值，则直接跳过
            if (Math.min(total + 1, Math.min(r, c)) <= maxLen) continue;
            int curMaxLen = 0;
            for (int i = 0; i < r; i++) {
                int j = total - i;
                if (j < 0 || j >= c) continue; // 小心r!=c会Index溢出
                curMaxLen = mat[i][j] == 1 ? curMaxLen + 1 : 0;
                maxLen = Math.max(maxLen, curMaxLen);
            }
        }
        return maxLen;
    }

    // Solution 2 合并反对角线
    public int longestLine2(int[][] mat) {
        if (mat == null || mat.length == 0 || mat[0] == null || mat[0].length == 0) return 0;
        int r = mat.length, c = mat[0].length, maxLen = 0;
        // horizontal, vertical, diagonal, anti-diagonal
        int[][] pre = new int[c + 2][4]; // 注意左右各加了一个空位as boundary guard
        for (int i = 0; i < r; i++) {
            int[][] cur = new int[c + 2][4];
            for (int j = 1; j < c + 1; j++) {
                if (mat[i][j - 1] == 1) {
                    cur[j][0] = cur[j - 1][0] + 1;
                    cur[j][1] = pre[j][1] + 1;
                    cur[j][2] = pre[j - 1][2] + 1;
                    cur[j][3] = pre[j + 1][3] + 1;
                }
                // 记住Arrays.stream求最大最小值方式
                maxLen = Math.max(maxLen, Arrays.stream(cur[j]).reduce(Integer::max).orElse(0));
            }
            pre = cur;
        }
        return maxLen;
    }

    public static void main(String[] args) {
        LongestLineOfConsecutiveOneInMatrix solution = new LongestLineOfConsecutiveOneInMatrix();
        System.out.println(solution.longestLine2(new int[][]{{0,1,1,0},{0,1,1,0},{0,0,0,1}}));
        System.out.println(solution.longestLine2(new int[][]{{1,1,1,1},{0,1,1,0},{0,0,0,1}}));
    }
}
