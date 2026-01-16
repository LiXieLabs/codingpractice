import java.util.ArrayList;
import java.util.List;

public class RottingOranges {

    private static final int[][] DIREC = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    /*************** Solution 1: BFS ********************/
    /**
     * Time: O(R X C)   Space: O(R X C)
     */
    public int orangesRotting(int[][] grid) {
        int totalFresh = 0, r = grid.length, c = grid[0].length;

        // 先找到 minute == 0 的所有 rotten orange
        List<int[]> currLevel = new ArrayList<>();
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (grid[i][j] == 2) {
                    currLevel.add(new int[]{i, j});
                } else if (grid[i][j] == 1) {
                    totalFresh++;
                }
            }
        }

        // 开始 BFS 感染，minute 实际就是 BFS 的 level
        int minute = 0;
        // ⚠️注意⚠️ 必须 totalFresh > 0，不然会多进去一次循环！
        while (totalFresh > 0 && !currLevel.isEmpty()) {
            minute++;
            List<int[]> nextLevel = new ArrayList<>();
            for (int[] p : currLevel) {
                for (int[] n : DIREC) {
                    int ni = p[0] + n[0], nj = p[1] + n[1];
                    if (0 <= ni && ni < r && 0 <= nj && nj < c && grid[ni][nj] == 1) {
                        totalFresh--;
                        grid[ni][nj] = 2;
                        nextLevel.add(new int[]{ni, nj});
                    }
                }
            }
            currLevel = nextLevel;
        }
        return totalFresh == 0 ? minute : -1;
    }
}
