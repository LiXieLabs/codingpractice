import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 200. Number of Islands (https://leetcode.com/problems/number-of-islands/description/)
 */
public class NumberOfIslands {

    /***************** Solution 1 ~ 3: Recur DFS + Iter DFS + BFS ****************/
    /**
     * Time: O(R * C) Space: O(R * C)
     */
    private static final int[][] DIREC = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public int numIslands1(char[][] grid) {
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
        for (int[] d : DIREC) {
            int ni = i + d[0], nj = j + d[1];
            if (ni >= 0 && ni < r && nj >= 0 && nj < c && grid[ni][nj] == '1') {
                recurSink(grid, ni, nj);
            }
        }
    }

    // Deque 也可以存 int[], 省去了转化
    public void dfsSink(char[][] grid, int i, int j) {
        int r = grid.length, c = grid[0].length;
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(i * c + j);
        while (!stack.isEmpty()) {
            int cur = stack.pop();
            int ci = cur / c, cj = cur % c;
            grid[ci][cj] = '0'; // 也可以像BFS那样，但是不是必须的
            for (int[] d : DIREC) {
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
            for (int[] d : DIREC) {
                int ni = ci + d[0], nj = cj + d[1];
                if (ni >= 0 && ni < r && nj >= 0 && nj < c && grid[ni][nj] == '1') {
                    queue.offer(ni * c + nj);
                    grid[ni][nj] = '0'; // 必须提前设置为0，不然会重复TLE
                }
            }
        }
    }

    /***************** Solution 4: Union Find ****************/
    /**
     * Time: O(R * C) Space: O(R * C)
     */
    private static final int[][] DIREC_2 = new int[][]{{1,0},{0,1}};

    public int numIslands(char[][] grid) {
        int r = grid.length, c = grid[0].length;
        UnionFind200 uf = new UnionFind200(grid);
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (grid[i][j] == '1') {
                    for (int[] d : DIREC_2) {
                        int ni = i + d[0], nj = j + d[1];
                        if (0 <= ni && ni < r && 0 <= nj && nj < c && grid[ni][nj] == '1') {
                            uf.union(i * c + j, ni * c + nj);
                        }
                    }
                }
            }
        }
        return uf.root;
    }

    public static void main(String[] args) {
        NumberOfIslands solution = new NumberOfIslands();
        System.out.println(solution.numIslands(new char[][]{
                {'1','1','0','0','0'},
                {'1','1','0','0','0'},
                {'0','0','1','0','0'},
                {'0','0','0','1','1'}
        })); // 3
    }
}

class UnionFind200 {

    int[] id;
    int[] sz;
    int root;

    public UnionFind200(char[][] grid) {
        int r = grid.length, c = grid[0].length;
        id = new int[r * c];
        sz = new int[r * c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (grid[i][j] == '1') {
                    int n = i * c + j;
                    id[n] = n;
                    sz[n] = 1;
                    root++;
                }
            }
        }
    }

    public int find(int p) {
        while (id[p] != p) {
            id[p] = id[id[p]]; // ⚠️注意⚠️ path compression
            p = id[p];
        }
        return p;
    }

    public void union(int p1, int p2) {
        int r1 = find(p1);
        int r2 = find(p2);
        if (r1 == r2) return;
        // ⚠️注意⚠️ weight balance - 小的并到大的上面！
        if (sz[r1] > sz[r2]) {
            id[r2] = r1;
            sz[r1] += sz[r2];
        } else {
            id[r1] = r2;
            sz[r2] += sz[r1];
        }
        root--;
    }
}
