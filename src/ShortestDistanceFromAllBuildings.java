import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ShortestDistanceFromAllBuildings {

    private static final int[][] DIREC = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    /******************* Solution 1 : BFS **********************/
    /**
     * Time: O(M^2 x N^2)   Space: O(M x N)
     *
     * 本方法，用额外空间
     * （1）count 记录对于每个 land 有几个 building 到达过
     * （2）visited 记录每个
     */
    public int shortestDistance(int[][] grid) {
        int row = grid.length, col = grid[0].length;
        int totalBldg = 0;
        int[][] distances = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                // 对于每个building，BFS找land
                if (grid[i][j] == 1) {
                    List<Integer> queue = new ArrayList<>();
                    queue.add(i * col + j);
                    int step = 1;
                    while (!queue.isEmpty()) {
                        List<Integer> nextQueue = new ArrayList<>();
                        for (int n : queue) {
                            int r = n / col, c = n % col;
                            for (int[] d : DIREC) {
                                int nr = r + d[0], nc = c + d[1];
                                // -totalBldg标记上一轮到达过的land，省略了visited
                                if (0 <= nr && nr < row && 0 <= nc && nc < col && grid[nr][nc] == -totalBldg) {
                                    distances[nr][nc] += step;
                                    grid[nr][nc]--;
                                    nextQueue.add(nr * col + nc);
                                }
                            }
                        }
                        queue = nextQueue;
                        step++;
                    }
                    totalBldg++;
                }
            }
        }
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                // 全部building到达过的land才是candidate
                if (grid[i][j] == -totalBldg) {
                    res = Math.min(res, distances[i][j]);
                }
            }
        }
        return res == Integer.MAX_VALUE ? -1 : res;
    }

    public static void main(String[] args) {
        ShortestDistanceFromAllBuildings solution = new ShortestDistanceFromAllBuildings();
        System.out.println(solution.shortestDistance(
                new int[][]{{1,0,2,0,1},
                            {0,0,0,0,0},
                            {0,0,1,0,0}}));
        System.out.println(solution.shortestDistance(
                new int[][]{{1,0}}));
        System.out.println(solution.shortestDistance(
                new int[][]{{1}}));
    }
}
