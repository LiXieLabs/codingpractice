import java.util.ArrayDeque;
import java.util.Deque;

public class NumberOfIslands {

    static int[][] directions = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    /***************** Solution 1 ~ 3: Recur DFS + Iter DFS + BFS ****************/
    /**
     * Time: O(R * C) Space: O(R * C)
     */
    public int numIslands(char[][] grid) {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    count++;
//                    recurSink(grid, i, j);
//                    dfsSink(grid, i, j);
                    bfsSink(grid, i, j);
                }
            }
        }
        return count;
    }

    public void recurSink(char[][] grid, int i, int j) {
        int r = grid.length, c = grid[0].length;
        grid[i][j] = '0';
        for (int[] d : directions) {
            int ni = i + d[0], nj = j + d[1];
            if (ni >= 0 && ni < r && nj >= 0 && nj < c && grid[ni][nj] == '1') {
                recurSink(grid, ni, nj);
            }
        }
    }

    public void dfsSink(char[][] grid, int i, int j) {
        int r = grid.length, c = grid[0].length;
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(i * c + j);
        while (!stack.isEmpty()) {
            int cur = stack.pop();
            int ci = cur / c, cj = cur % c;
            grid[ci][cj] = '0'; // 也可以像BFS那样，但是不是必须的
            for (int[] d : directions) {
                int ni = ci + d[0], nj = cj + d[1];
                if (ni >= 0 && ni < r && nj >= 0 && nj < c && grid[ni][nj] == '1') {
                    stack.push(ni * c + nj);
                }
            }
        }
    }

    public void bfsSink(char[][] grid, int i, int j) {
        int r = grid.length, c = grid[0].length;
        Deque<Integer> queue = new ArrayDeque<>();
        queue.offer(i * c + j);
        grid[i][j] = '0';
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            int ci = cur / c, cj = cur % c;
            for (int[] d : directions) {
                int ni = ci + d[0], nj = cj + d[1];
                if (ni >= 0 && ni < r && nj >= 0 && nj < c && grid[ni][nj] == '1') {
                    queue.offer(ni * c + nj);
                    grid[ni][nj] = '0'; // 必须提前设置为0，不然会重复TLE
                }
            }
        }
    }

    public static void main(String[] args) {
        NumberOfIslands solution = new NumberOfIslands();
        System.out.println(solution.numIslands(new char[][]{
                {'1','1','0','0','0'},
                {'1','1','0','0','0'},
                {'0','0','1','0','0'},
                {'0','0','0','1','1'}
        }));
    }
}
