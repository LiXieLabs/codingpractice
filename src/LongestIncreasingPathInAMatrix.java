import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class LongestIncreasingPathInAMatrix {

    private static final int[][] DIREC = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    private int[][] matrix;
    private int row;
    private int col;
    private int[][] memo;

    /**************** Solution 1: Recur + Memo ****************/
    /**
     * Time complexity : O(M X N)
     * Each vertex/cell will be calculated once and only once,
     * and each edge will be visited once and only once.
     * The total time complexity is then O(V+E).
     * V is the total number of vertices and EE is the total number of edges.
     * In our problem, O(V)=O(M X N), O(E)=O(4V)=O(M XN ).
     *
     * Space complexity : O(M X N). The cache dominates the space complexity.
     */
    public int longestIncreasingPath1(int[][] matrix) {
        this.matrix = matrix;
        row = matrix.length;
        col = matrix[0].length;

        // Optional
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(t -> t[0]));
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                minHeap.offer(new int[]{matrix[i][j], i, j});
            }
        }
        memo = new int[row][col];
        int res = 0;
        while (!minHeap.isEmpty()) {
            int[] cur = minHeap.poll();
            int r = cur[1], c = cur[2];
        }
        return res;
    }

    private int recur(int i, int j) {
        if (memo[i][j] == 0) {
            for (int[] d : DIREC) {
                int di = d[0], dj = d[1];
                int ni = i + di, nj = j + dj;
                if (0 <= ni && ni < row && 0 <= nj && nj < col && matrix[ni][nj] > matrix[i][j]) {
                    memo[i][j] = Math.max(memo[i][j], recur(ni, nj));
                }
            }
            memo[i][j]++;
        }
        return memo[i][j];
    }

    /********************* Solution 2: Topological Sort / Kahn's Algo (BFS) *****************/
    /**
     * https://en.wikipedia.org/wiki/Topological_sorting#Kahn's_algorithm
     * 有向无环图/无环检测
     *
     * Time complexity : O(M X N).
     * The the topological sort is O(V+E) = O(M X N)
     * Here, V is the total number of vertices and E is the total number of edges.
     * In our problem, O(V) = O(M X N), O(E)=O(4V)=O(M X N).
     *
     * Space complexity : O(M X N).
     * We need to store the out degrees and each level of leaves.
     */
    public int longestIncreasingPath(int[][] matrix) {
        row = matrix.length;
        col = matrix[0].length;

        int[][] indegree = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                for (int[] d : DIREC) {
                    int ni = i + d[0], nj = j + d[1];
                    if (0 <= ni && ni < row && 0 <= nj && nj < col && matrix[ni][nj] > matrix[i][j]) {
                        indegree[ni][nj]++;
                    }
                }
            }
        }

        List<int[]> zeroIndegree = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (indegree[i][j] == 0) {
                    zeroIndegree.add(new int[]{i, j});
                }
            }
        }

        int level = 0;
        while (!zeroIndegree.isEmpty()) {
            level++;
            List<int[]> nextLevel = new ArrayList<>();
            for (int[] cur : zeroIndegree) {
                int i = cur[0], j = cur[1];
                for (int[] d : DIREC) {
                    int ni = i + d[0], nj = j + d[1];
                    if (0 <= ni && ni < row && 0 <= nj && nj < col && matrix[ni][nj] > matrix[i][j]) {
                        indegree[ni][nj]--;
                        if (indegree[ni][nj] == 0) {
                            nextLevel.add(new int[]{ni, nj});
                        }
                    }
                }
            }
            zeroIndegree = nextLevel;
        }
        return level;
    }

    public static void main(String[] args) {
        LongestIncreasingPathInAMatrix solution = new LongestIncreasingPathInAMatrix();
        System.out.println(solution.longestIncreasingPath(new int[][]{
                {9, 9, 4},
                {6, 6, 8},
                {2, 1, 1}
        }));
        System.out.println(solution.longestIncreasingPath(new int[][]{
                {3, 4, 5},
                {3, 2, 6},
                {2, 2, 1}
        }));
    }
}