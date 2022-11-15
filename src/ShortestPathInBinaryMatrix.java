import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class ShortestPathInBinaryMatrix {

    static int[][] directions = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {-1, -1}, {1, 1}, {-1, 1}, {1, -1}};

    // ！！！小心！！！可以向八个方向走的不能用DP！！！

    /****************** Solution 1: BFS with Visited Set and Keep track of Level as Length *****************/
    /**
     * Time: O(N) Space: O(N)
     */
    public int shortestPathBinaryMatrix1(int[][] grid) {
        int r = grid.length, c = grid[0].length;
        if (grid[0][0] == 1 || grid[r-1][c-1] == 1) return -1;
        Deque<Integer> queue = new ArrayDeque<>();
        queue.offer(0);
        Set<Integer> visited = new HashSet<>();
        visited.add(0);
        int length = 1;
        while (queue.size() != 0) {
            Deque<Integer> nextQ = new ArrayDeque<>();
            for (int n : queue) {
                int i = n / c, j = n % c;
                // 不能只在加了di，dj里面判断返回值，因为那样{{0}}，起点即终点的情况会无解
                if (i == r - 1 && j == c - 1) return length;
                for (int[] d : directions) {
                    int ni = i + d[0], nj = j + d[1];
                    int nn = ni * c + nj;
                    if (0 <= ni && ni < r && 0 <= nj && nj < c && grid[ni][nj] == 0 && !visited.contains(nn)) {
                        nextQ.offer(nn);
                        visited.add(nn);
                    }
                }
            }
            queue = nextQ;
            length++;
        }
        return -1;
    }

    /****************** Solution 2: BFS without Visited Set and Level by Overwritting grid *****************/
    /**
     * Time: O(N) Space: O(N)
     */
    public int shortestPathBinaryMatrix(int[][] grid) {
        int r = grid.length, c = grid[0].length;
        if (grid[0][0] == 1 || grid[r-1][c-1] == 1) return -1;
        Deque<Integer> queue = new ArrayDeque<>();
        queue.offer(0);
        grid[0][0] = 1;
        while (queue.size() != 0) {
            Deque<Integer> nextQ = new ArrayDeque<>();
            for (int n : queue) {
                int i = n / c, j = n % c;
                // 不能只在加了di，dj里面判断返回值，因为那样{{0}}，起点即终点的情况会无解
                if (i == r - 1 && j == c - 1) return grid[i][j];
                for (int[] d : directions) {
                    int ni = i + d[0], nj = j + d[1];
                    int nn = ni * c + nj;
                    if (0 <= ni && ni < r && 0 <= nj && nj < c && grid[ni][nj] == 0) {
                        nextQ.offer(nn);
                        grid[ni][nj] = grid[i][j] + 1;
                    }
                }
            }
            queue = nextQ;
        }
        return -1;
    }

    /******************** Solution 3: A* Search ************************/
    // 还没看！！！https://leetcode.com/problems/shortest-path-in-binary-matrix/solution/

    public static void main(String[] args) {
        ShortestPathInBinaryMatrix solution = new ShortestPathInBinaryMatrix();
        System.out.println(solution.shortestPathBinaryMatrix(
                new int[][]{{0,1},
                            {1,0}}));
        System.out.println(solution.shortestPathBinaryMatrix(
                new int[][]{{0,0,0},
                            {1,1,0},
                            {1,1,0}}));
        System.out.println(solution.shortestPathBinaryMatrix(
                new int[][]{{1,0,0},
                            {1,1,0},
                            {1,1,0}}));
        System.out.println(solution.shortestPathBinaryMatrix(
                new int[][]{{0,0,0},
                            {0,1,0},
                            {0,0,0}}));
        System.out.println(solution.shortestPathBinaryMatrix(
                new int[][]{{0,1,1,0,0,0},
                            {0,1,0,1,1,0},
                            {0,1,1,0,1,0},
                            {0,0,0,1,1,0},
                            {1,1,1,1,1,0},
                            {1,1,1,1,1,0}}));
        System.out.println(solution.shortestPathBinaryMatrix(
                new int[][]{{0}}));
    }
}
