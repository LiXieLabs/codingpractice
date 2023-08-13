import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 909. Snakes and Ladders (https://leetcode.com/problems/snakes-and-ladders/)
 */
public class SnakesAndLadders {

    int l;

    /************* Solution 1: BFS ******************/
    /**
     * BFS =>  find the shortest path in unweighted graph
     * Dijkstra's algo => find the shortest path in weighted graph
     *
     * 难点在 number 向 matrix indices 转化
     * 也可以无脑 increment number，同时移动 indices，制作成 map
     * 但是牺牲空间复杂度了
     *         Pair<Integer, Integer>[] cells = new Pair[n * n + 1];
     *         int label = 1;
     *         Integer[] columns = new Integer[l];
     *         for (int i = 0; i < n; i++) {
     *             columns[i] = i;
     *         }
     *         for (int row = n - 1; row >= 0; row--) {
     *             for (int column : columns) {
     *                 cells[label++] = new Pair<>(row, column);
     *             }
     *             Collections.reverse(Arrays.asList(columns));
     *         }
     *
     * Time: O(L^2) as BFS = |V| + |E| = 6L^2 + L^2 = O(7L^2) = O(L^2)
     * Space: O(L^2) as max queue size is # of leaf node in 6-ary tree = O(L^2)
     * where L is matrix edge length L = Row = Col
     */
    public int snakesAndLadders(int[][] board) {
        l = board.length;
        int target = l * l;
        List<Integer> curLevel = new ArrayList<>();
        curLevel.add(1);
        Set<Integer> visited = new HashSet<>();
        visited.add(1);
        int step = 0;
        while (!curLevel.isEmpty()) {
            System.out.println(curLevel);
            List<Integer> nexLevel = new ArrayList<>();
            for (int cur : curLevel) {
                if (cur == target) return step;
                for (int n = cur + 1; n <= Math.min(cur + 6, target); n++) {
                    int[] indices = numberToIndex(n);
                    int nex = board[indices[0]][indices[1]] == -1 ? n : board[indices[0]][indices[1]];
                    if (!visited.contains(nex)) {
                        nexLevel.add(nex);
                        visited.add(n);
                        visited.add(nex);
                    }
                }
            }
            curLevel = nexLevel;
            step++;
        }
        return -1;

    }

    private int indexToNumber(int i, int j) {
        // (i * r + j) + 1
        // [(r - 1 - i) * r + ((r - 1 - i) % 2 == 0 ? j : (c - j))] + 1
        return ((l - 1 - i) * l + ((l - 1 - i) % 2 == 0 ? j : (l - j))) + 1;
    }

    private int[] numberToIndex(int n) {
        // i = (n - 1) / r; j = (n - 1) % r
        // i = (r - 1) - (n - 1) / r; j = (((n - 1) / r) % 2 == 0 ? ((n - 1) % r) : (c - 1 - (n - 1) % r)
        int i = l - 1 - (n - 1) / l;
        int j = ((n - 1) / l % 2 == 0 ? ((n - 1) % l) : (l - 1 - (n - 1) % l));
        return new int[]{i, j};
    }

    public static void main(String[] args) {
        SnakesAndLadders solution = new SnakesAndLadders();

        // TC#1
        System.out.println(solution.snakesAndLadders(new int[][]{
                {-1,-1,-1,-1,-1,-1},
                {-1,-1,-1,-1,-1,-1},
                {-1,-1,-1,-1,-1,-1},
                {-1,35,-1,-1,13,-1},
                {-1,-1,-1,-1,-1,-1},
                {-1,15,-1,-1,-1,-1}
        })); //4

        // TC#2
        System.out.println(solution.snakesAndLadders(new int[][]{
                {-1,-1},
                {-1, 3}
        })); //1
    }
}
