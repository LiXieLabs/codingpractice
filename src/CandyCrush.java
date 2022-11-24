import java.util.Arrays;
import java.util.stream.Collectors;

public class CandyCrush {

    private int[][] curBoard;
    private int r, c;

    /*************** Solution 1: Simulation ******************/
    /**
     * 行列分开处理标记 by count() + mark()
     * 不能变成0，需要先变成负值，行遍历完还要遍历列，不能改变值！！！
     * drop() 是 two pointers
     *
     * Time: O((R X C)^2)
     * (1) each call takes 3 X R X C
     * (2) assume for each row, in each call, only 3 candies are connected, and crushed,
     *     and only in the next call, there will by another 3 candies connected, total call # will be (R X C) / 3
     * Total is (3 X R X C) X (R X C) / 3 = (R X C)^2
     *
     * Space: O(1) as modifying locally.
     */
    public int[][] candyCrush(int[][] board) {
        curBoard = board;
        r = board.length;
        c = board[0].length;
        boolean changed = false;

        // 对每一行，统计连续相同个数，如果 >= 3，标记为负值
        for (int i = 0; i < r; i++) {
            int j = 0;
            while (j < c) {
                int absVal = Math.abs(curBoard[i][j]);
                int cnt = count(i, j, 0, 1, absVal);
                if (absVal != 0 && cnt >= 3) {
                    mark(i, j, 0, 1, absVal);
                    changed = true;
                }
                j += cnt;
            }
        }

        // 对每一列，统计连续相同个数，如果 >= 3，标记为负值
        for (int j = 0; j < c; j++) {
            int i = 0;
            while (i < r) {
                int absVal = Math.abs(curBoard[i][j]);
                int cnt = count(i, j, 1, 0, absVal);
                if (absVal != 0 && cnt >= 3) {
                    mark(i, j, 1, 0, absVal);
                    changed = true;
                }
                i += cnt;
            }
        }

        // 对于每一列，用 Two Pointers 自下向上
        // p1标记待更新行，p2标记当前遍历行，如果 p2 位置 >0 赋值给 p1，并移动 p1
        drop();

        return changed ? candyCrush(curBoard) : curBoard;
    }

    private int count(int i, int j, int di, int dj, int n) {
        int cnt = 0;
        while (0 <= i && i < r && 0 <= j && j < c && Math.abs(curBoard[i][j]) == n) {
            cnt++;
            i += di;
            j += dj;
        }
        return cnt;
    }

    private void mark(int i, int j, int di, int dj, int n) {
        while (0 <= i && i < r && 0 <= j && j < c && Math.abs(curBoard[i][j]) == n) {
            curBoard[i][j] = -n;
            i += di;
            j += dj;
        }
    }

    private void drop() {
        for (int j = 0; j < c; j++) {
            int i1 = r - 1;
            for (int i2 = r - 1; i2 >= 0; i2--) {
                if (curBoard[i2][j] > 0) {
                    curBoard[i1--][j] = curBoard[i2][j];
                }
            }
            while (i1 >= 0) {
                curBoard[i1--][j] = 0;
            }
        }
    }

    private static void print(int[][] input) {
        for (int[] row : input) {
            System.out.println("["
                    + Arrays.stream(row).mapToObj(String::valueOf).collect(Collectors.joining(","))
                    + "]");
        }
        System.out.println("\n");
    }

    public static void main(String[] args) {
        CandyCrush solution = new CandyCrush();

        // TC1
        int[][] res1 = solution.candyCrush(new int[][]{
                {110, 5, 112, 113, 114},
                {210, 211, 5, 213, 214},
                {310, 311, 3, 313, 314},
                {410, 411, 412, 5, 414},
                {5, 1, 512, 3, 3},
                {610, 4, 1, 613, 614},
                {710, 1, 2, 713, 714},
                {810, 1, 2, 1, 1},
                {1, 1, 2, 2, 2},
                {4, 1, 4, 4, 1014}
        });
        print(res1);

        // TC2
        int[][] res2 = solution.candyCrush(new int[][]{
                {1, 3, 5, 5, 2},
                {3, 4, 3, 3, 1},
                {3, 2, 4, 5, 2},
                {2, 4, 4, 5, 5},
                {1, 4, 4, 1, 1}
        });
        print(res2);
    }

}
