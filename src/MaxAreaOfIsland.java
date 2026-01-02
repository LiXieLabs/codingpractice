import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 695. Max Area of Island (https://leetcode.com/problems/max-area-of-island/description/)
 */
public class MaxAreaOfIsland {

    /**
     * UNION FIND
     * | Technique                         | What it prevents                  |
     * | --------------------------------- | --------------------------------- |
     * | Path compression                  | Expensive repeated `find()`       |
     * | Union by size/rank (small->large) | Bad tree shape in the first place |
     *
     * | Find strategy    | Worst-case (single) | Amortized |
     * | ---------------- | ------------------- | --------- |
     * | No compression   | `O(n)`              | `O(n)`    |
     * | Path halving     | `O(log n)`          | `O(α(n))` |
     * | Full compression | `O(n)`              | `O(α(n))` |
     *
     * α(n) = inverse Ackermann function = O(1)
     *
     * | Strategy                | Amortized find |
     * | ----------------------- | -------------- |
     * | Compression + balancing | `O(α(n))`      |
     * | Compression only        | Can degrade    |
     * | Balancing only          | `O(log n)`     |
     */

    /***************** Solution 1: Iterative DFS by Stack **************************/
    /**
     * 直接变成 BFS (queue) 也可以
     *
     * Time: O(R X C)
     * Space: O(R X C) by stack
     */
    private static final int[][] DIREC1 = new int[][]{{-1, 0}, {0, -1}, {0, 1}, {1, 0}};
    public int maxAreaOfIsland1(int[][] grid) {
        int r = grid.length, c = grid[0].length;
        int maxArea = 0;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (grid[i][j] == 0) continue;
                Deque<int[]> stack = new ArrayDeque<>();
                stack.push(new int[]{i, j});
                grid[i][j] = 0; // 必须先变成0，不能等 pop 出来再变，会 fail
                int curArea = 0;
                while (!stack.isEmpty()) {
                    int[] cur = stack.pop();
                    curArea++;
                    for (int[] d : DIREC1) {
                        int[] nex = new int[]{cur[0] + d[0], cur[1] + d[1]};
                        if (0 <= nex[0] && nex[0] < r && 0 <= nex[1] && nex[1] < c && grid[nex[0]][nex[1]] == 1) {
                            stack.push(nex);
                            grid[nex[0]][nex[1]] = 0;
                        }
                    }
                }
                maxArea = Math.max(maxArea, curArea);
            }
        }
        return maxArea;
    }

    /***************** Solution 2: Recursive DFS **************************/
    /**
     * Time: O(R X C)
     * Space: O(R X C) by stack
     */
    public int maxAreaOfIsland2(int[][] grid) {
        int r = grid.length, c = grid[0].length;
        int maxArea = 0;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (grid[i][j] == 0) continue;
                grid[i][j] = 0;
                maxArea = Math.max(maxArea, recurDfs(grid, r, c, i, j));
            }
        }
        return maxArea;
    }

    private int recurDfs(int[][] grid, int r, int c, int i, int j) {
        int curArea = 1;
        for (int[] d : DIREC1) {
            int ni = i + d[0], nj = j + d[1];
            if (0 <= ni && ni < r && 0 <= nj && nj < c && grid[ni][nj] == 1) {
                grid[ni][nj] = 0;
                curArea += recurDfs(grid, r, c, ni, nj);
            }
        }
        return curArea;
    }

    /***************** Solution 3: Union Find with Path Compression + Weight Balanced **************************/
    /**
     * Time: O(R X C)
     * Space: O(R X C)
     */
    private static final int[][] DIREC2 = new int[][]{{-1, 0}, {0, -1}};

    public int maxAreaOfIsland(int[][] grid) {
        int r = grid.length, c = grid[0].length;
        UnionFind695 uf = new UnionFind695(grid);
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (grid[i][j] == 1) {
                    int cur = i * c + j;
                    // ⚠️注意⚠️ 两个都要！不然上面和左边如果只靠 cur 连接，则 union 不起来了！
                    for (int[] d : DIREC2) {
                        int ni = i + d[0], nj = j + d[1];
                        if (0 <= ni && ni < r && 0 <= nj && nj < c && grid[ni][nj] == 1) {
                            int nex = ni * c + nj;
                            uf.union(cur, nex);
                        }
                    }
                }
            }
        }

        // 优化为在 uf 中维护一个 maxSize
//        int maxArea = 0;
//        for (int i = 0; i < r * c; i++) {
//            maxArea = Math.max(maxArea, uf.sz[i]);
//        }
//        return maxArea;

        return uf.maxSize;
    }

    public static void main(String[] args) {
        MaxAreaOfIsland solution = new MaxAreaOfIsland();

        // TC1
        System.out.println(solution.maxAreaOfIsland(new int[][]{
                {1}
        }));

        // TC2
        System.out.println(solution.maxAreaOfIsland(new int[][]{
                {0,0,1,0,0,0,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,0,1,1,1,0,0,0},
                {0,1,1,0,1,0,0,0,0,0,0,0,0},
                {0,1,0,0,1,1,0,0,1,0,1,0,0},
                {0,1,0,0,1,1,0,0,1,1,1,0,0},
                {0,0,0,0,0,0,0,0,0,0,1,0,0},
                {0,0,0,0,0,0,0,1,1,1,0,0,0},
                {0,0,0,0,0,0,0,1,1,0,0,0,0}
        })); // 6

        // TC3
        System.out.println(solution.maxAreaOfIsland(new int[][]{
                {0,0,0,0,0,0,0,0}
        })); // 0
    }
}

class UnionFind695 {

    int[] id;
    int[] sz;
    int maxSize;

    public UnionFind695(int[][] grid) {
        int r = grid.length, c = grid[0].length;
        id = new int[r * c];
        sz = new int[r * c];
        maxSize = 0;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                int n = i * c + j;
                id[n] = n;
                sz[n] = grid[i][j];
                maxSize = Math.max(maxSize, grid[i][j]); // 必须如此！否则 TC1 会 fail
            }
        }
    }

    public int find(int p) {
        // Path halving
//        while (id[p] != p) {
//            id[p] = id[id[p]];
//            p = id[p];
//        }
//        return p;

        // Full compression
        if (id[p] != p) id[p] = find(id[p]);
        return id[p];
    }

    public void union(int p1, int p2) {
        int r1 = find(p1);
        int r2 = find(p2);
        // ⚠️注意⚠️ 容易忽略导致错误！
        if (r1 == r2) return;
        if (sz[r1] > sz[r2]) {
            id[r2] = r1;
            sz[r1] += sz[r2];
            maxSize = Math.max(maxSize, sz[r1]);
        } else {
            id[r1] = r2;
            sz[r2] += sz[r1];
            maxSize = Math.max(maxSize, sz[r2]);
        }
    }
}