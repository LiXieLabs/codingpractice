import javafx.util.Pair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NumberOfDistinctIslands {

    /************* Solution 1: Union Find **********************/
    /**
     * Time: O(R X C)   Space: O(R X C)
     */

    // 注意！！！必须往左上union，如果四个方向都加上会错！！！
    private static final int[][] DIREC1 = new int[][]{{-1, 0}, {0, -1}};
    public int numDistinctIslands1(int[][] grid) {
        // init union find and union to build island
        int r = grid.length, c = grid[0].length;
        UnionFind694 uf = new UnionFind694(r, c);
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (grid[i][j] == 0) continue;
                int cur = i * c + j;
                for (int[] d : DIREC1) {
                    int ni = i + d[0], nj = j + d[1];
                    if (0 <= ni && ni < r && 0 <= nj && nj < c && grid[ni][nj] == 1) {
                        uf.union(cur, ni * c + nj);
                    }
                }
            }
        }

        // shape = Set<Pair<i - root_i, j - root_j>>
        Map<Integer, Set<Pair<Integer, Integer>>> rootToShapes = new HashMap<>();
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (grid[i][j] == 0) continue;
                int n = i * c + j;
                int root = uf.find(n); // 注意！！！必须再路径压缩一次！！！
                rootToShapes.putIfAbsent(root, new HashSet<>());
                rootToShapes.get(root).add(new Pair<>(n / c - root / c, n % c - root % c));
            }
        }
        return new HashSet<>(rootToShapes.values()).size();
    }

    /************* Solution 2: Recursive DFS **********************/
    /**
     * 因为遍历顺序是一定的，所以相同shape的island的root
     * 和对应的其他点的相遇顺序也是也一定的，可以用来identity一个island
     *
     * Time: O(R X C)   Space: O(R X C)
     */
    private static final int[][] DIREC2 = new int[][]{{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
    Set<Pair<Integer, Integer>> island;
    public int numDistinctIslands(int[][] grid) {
        int r = grid.length, c = grid[0].length;
        Set<Set<Pair<Integer, Integer>>> islands = new HashSet<>();
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (grid[i][j] == 0) continue;
                island = new HashSet<>();
                recurDfs(grid, r, c, i, j, i, j);
                islands.add(island);
            }
        }
        return islands.size();
    }

    private void recurDfs(int[][] grid, int r, int c, int i, int j, int rooti, int rootj) {
        island.add(new Pair<>(i - rooti, j - rootj));
        grid[i][j] = 0;
        for (int[] d : DIREC2) {
            int ni = i + d[0], nj = j + d[1];
            if (0 <= ni && ni < r && 0 <= nj && nj < c && grid[ni][nj] == 1) {
                recurDfs(grid, r, c, ni, nj, rooti, rootj);
            }
        }
    }

    public static void main(String[] args) {
        NumberOfDistinctIslands solution = new NumberOfDistinctIslands();

        // TC1
        System.out.println(solution.numDistinctIslands(new int[][]{
                {1,1,0,0,0},
                {1,1,0,0,0},
                {0,0,0,1,1},
                {0,0,0,1,1}
        })); // 1

        // TC2
        System.out.println(solution.numDistinctIslands(new int[][]{
                {1,1,0,1,1},
                {1,0,0,0,0},
                {0,0,0,0,1},
                {1,1,0,1,1}
        })); // 3

        // TC3
        System.out.println(solution.numDistinctIslands(new int[][]{
                {0,0,1},
                {1,0,1},
                {1,1,1}
        })); // 1

        // TC4
        System.out.println(solution.numDistinctIslands(new int[][]{
                {1,1,1,1},
                {1,0,1,0},
                {0,0,0,0},
                {0,1,1,1},
                {1,1,0,1}
        })); // 2

        // TC5
        System.out.println(solution.numDistinctIslands(new int[][]{
                {0,0,0,0,1,1,1},
                {1,1,1,1,1,1,1},
                {0,0,0,0,0,0,0},
                {0,0,0,0,1,1,1},
                {1,1,1,1,1,1,0},
                {0,0,0,1,0,0,0}
        })); // 2

    }
}

class UnionFind694 {

    int[] id;
    int[] sz;

    public UnionFind694(int r, int c) {
        id = new int[r * c];
        sz = new int[r * c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                id[i * c + j] = i * c + j;
                sz[i * c + j] = 1;
            }
        }
    }

    public int find(int p) {
        if (id[p] != p) {
            id[p] = id[id[p]];
            p = id[p];
        }
        return p;
    }

    public void union(int p1, int p2) {
        int r1 = find(p1), r2 = find(p2);
        if (r1 == r2) return;
        if (sz[r1] > sz[r2]) {
            id[r2] = r1;
            sz[r1] += sz[r2];
        } else {
            id[r1] = r2;
            sz[r2] += sz[r1];
        }
    }
}
