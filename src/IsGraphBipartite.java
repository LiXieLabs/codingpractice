import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IsGraphBipartite {

    /************** Solution 1: 多源无向图 BFS 标记 id level by level ***************/
    /**
     * Time: O(N+E)   Space: O(N) by ids array
     */
    public boolean isBipartite1(int[][] graph) {
        int[] ids = new int[graph.length];
        Arrays.fill(ids, -1);
        int id = 0;
        for (int node = 0; node < graph.length; node++) {
            if (ids[node] != -1) continue;
            List<Integer> curLevel = new ArrayList<>();
            curLevel.add(node);
            while (!curLevel.isEmpty()) {
                List<Integer> nexLevel = new ArrayList<>();
                for (int cur : curLevel) {
                    ids[cur] = id;
                    for (int nex : graph[cur]) {
                        if (ids[nex] != -1) {
                            if (ids[nex] == id) return false;
                            continue;
                        }
                        nexLevel.add(nex);
                    }
                }
                curLevel = nexLevel;
                id ^= 1;
            }
        }
        return true;
    }

    /************** Solution 2: Union Find ***************/
    /**
     * 对每一个点，遍历其neighbors，将所有neighbors union起来
     * 如果一个点已经和其neighbor有同一个root，即union过，也即connected，则为false
     *
     * Time: O(E)   Space: O(N) by id/sz array
     */
    public boolean isBipartite(int[][] graph) {
        UnionFind785 uf = new UnionFind785(graph.length);
        for (int cur = 0; cur < graph.length; cur++) {
            for (int nex : graph[cur]) {
                if (uf.isConnected(cur, nex)) return false;
                uf.union(graph[cur][0], nex);
            }
        }
        return true;
    }

    public static void main(String[] args) {
        IsGraphBipartite solution = new IsGraphBipartite();
        // TC1
        System.out.println(solution.isBipartite(new int[][]{
                {1, 2, 3}, {0, 2}, {0, 1, 3}, {0, 2}
        })); // false
        // TC2
        System.out.println(solution.isBipartite(new int[][]{
                {1, 3},{0, 2},{1, 3},{0, 2}
        })); // true
    }
}

class UnionFind785 {

    int[] id;
    int[] sz;

    public UnionFind785(int n) {
        id = new int[n];
        sz = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }

    public int find(int p) {
        while (id[p] != p) {
            id[p] = id[id[p]];
            p = id[p];
        }
        return p;
    }

    public boolean isConnected(int p1, int p2) {
        return find(p1) == find(p2);
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