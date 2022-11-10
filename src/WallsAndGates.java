import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class WallsAndGates {

    private static final int[][] DIREC = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    private static final int R = Integer.MAX_VALUE;

    /******************* Solution 1: BFS initial queue is all the GATEs **************/
    /**
     * Time: O(R * C) Space: O(R * C)
     *
     * 优化了：
     * 1. 省略了visited，不为R的已经visited过了
     * 2. GATE下一步是GATE，可以忽略，因为一个点通过GateB到GateA，则到GateB距离一定更小
     */
    public void wallsAndGates(int[][] rooms) {
        int row = rooms.length, col = rooms[0].length;
        List<Integer> queue = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (rooms[i][j] == 0) {
                    // BFS from current GATE
                    queue.add(i * col + j);
                }
            }
        }

        int distance = 1;
        while (!queue.isEmpty()) {
            List<Integer> nextQueue = new ArrayList<>();
            for (int curr : queue) {
                int r = curr / col, c = curr % col;
                rooms[r][c] = Math.min(rooms[r][c], distance);
                for (int[] d : DIREC) {
                    int nr = r + d[0], nc = c + d[1];
                    // 不用visited标记，不为R的已经加入过queue了！！！
                    if (0 <= nr && nr < row && 0 <= nc && nc < col && rooms[nr][nc] == R) {
                        rooms[nr][nc] = distance;
                        nextQueue.add(nr * col + nc);
                    }
                }
            }
            distance++;
            queue = nextQueue;
        }
    }

    public static void print(int[][] input) {
        System.out.println(
                Arrays.stream(input)
                                .map(row -> "[" + Arrays.stream(row)
                                        .mapToObj(String::valueOf)
                                        .collect(Collectors.joining(",")) + "]").collect(Collectors.joining("\n"))
        );
    }

    public static void main(String[] args) {
        WallsAndGates solution = new WallsAndGates();

        int[][] board1 = new int[][]{
                {R, -1, 0, R},
                {R, R, R, -1},
                {R, -1, R, -1},
                {0, -1, R, R}
        };
        solution.wallsAndGates(board1);
        print(board1);

        int[][] board2 = new int[][]{
                {-1}
        };
        solution.wallsAndGates(board2);
        print(board2);
    }
}