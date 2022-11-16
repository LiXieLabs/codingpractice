import java.util.HashSet;
import java.util.Set;

public class MakingALargeIsland {

    private static final int[][] DIREC = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    /************** Solution 1: Union Find with Size **********************/
    /**
     * 也可以DFS，对每个岛标记一个id，第二部分一样
     *
     * Time: O(N X N)
     * Space: O(N X N) by int[] in UnionFind
     */
    public int largestIsland(int[][] grid) {
        // init islands for each 1
        int n = grid.length;
        UnionFind827 uf = new UnionFind827(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) continue;
                for (int[] d : DIREC) {
                    int ni = i + d[0], nj = j + d[1];
                    if (0 <= ni && ni < n && 0 <= nj && nj < n && grid[ni][nj] == 1) {
                        uf.union(i * n + j, ni * n + nj);
                    }
                }
            }
        }
        // for each 0, 找到四周的 1 的 unique roots, 累加对应的 island area
        // for each 1, 找到对应的 island area
        // 每次结束，更新全局最大值 maxArea
        int maxArea = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    maxArea = Math.max(maxArea, uf.sz[i * n + j]);
                } else {
                    int curArea = 1;
                    Set<Integer> uniqueIslands = new HashSet<>();
                    for (int[] d : DIREC) {
                        int ni = i + d[0], nj = j + d[1];
                        if (0 <= ni && ni < n && 0 <= nj && nj < n && grid[ni][nj] == 1)
                            uniqueIslands.add(uf.find(ni * n + nj));
                    }
                    for (int island : uniqueIslands) {
                        curArea += uf.sz[island];
                    }
                    maxArea = Math.max(maxArea, curArea);
                }
            }
        }
        return maxArea;
    }

    public static void main(String[] args) {
        MakingALargeIsland solution = new MakingALargeIsland();

        // TC0
        System.out.println(solution.largestIsland(new int[][]{
                {0, 0},
                {1, 0}
        }));

        // TC1
        System.out.println(solution.largestIsland(new int[][]{
                {1, 0},
                {0, 1}
        }));

        // TC2
        System.out.println(solution.largestIsland(new int[][]{
                {1, 1},
                {0, 1}
        }));

        // TC3
        System.out.println(solution.largestIsland(new int[][]{
                {1, 1},
                {1, 1}
        }));
    }

}

class UnionFind827 {

    int[] id;
    int[] sz;

    public UnionFind827(int n) {
        id = new int[n * n];
        sz = new int[n * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                id[i * n + j] = i * n + j;
                sz[i * n + j] = 1;
            }
        }
    }

    public int find(int p) {
        while (id[p] != p) {
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
