/**
 * 348. Design Tic-Tac-Toe (https://leetcode.com/problems/design-tic-tac-toe/description/)
 */
public class DesignTicTacToe {

    /************* Solution 1: count by rows, cols & diagonals ***********/
    /**
     * rows[i][0] 表示 player1 在 row i 一共占有几个位置
     * rows[i][1] 表示 player2 在 row i 一共占有几个位置
     *
     * cols[j][0] 表示 player1 在 col j 一共占有几个位置
     * cols[j][1] 表示 player2 在 col j 一共占有几个位置
     *
     * diagonals[0][0] 表示 player1 在 正对角线 一共占有几个位置
     * diagonals[0][1] 表示 player2 在 正对角线 一共占有几个位置
     *
     * diagonals[1][0] 表示 player1 在 反对角线 一共占有几个位置
     * diagonals[1][1] 表示 player2 在 反对角线 一共占有几个位置
     *
     * Time: O(1)   Space: O(N)
     */
    int[][] rows, cols, diagonals;

    public DesignTicTacToe(int n) {
        rows = new int[n][2];
        cols = new int[n][2];
        diagonals = new int[2][2];
    }

    public int move(int row, int col, int player) {
        int size = rows.length;
        if (++rows[row][player - 1] == size) return player;
        if (++cols[col][player - 1] == size) return player;
        if (row == col) {
            if (++diagonals[0][player - 1] == size) return player;
        }
        if (row + col == size - 1) {
            if (++diagonals[1][player - 1] == size) return player;
        }
        return 0;
    }

    /************* Solution 2: solution 1 优化至 1D ***********/
    /**
     * rows[i][0] 和 rows[i][1] 合并，player1在该行+1，player2在该行-1，如果绝对值为n，则win
     * cols和diagonals同理
     *
     * Time: O(1)   Space: O(N)
     */
    int[] rows2, cols2, diagonals2;

    public DesignTicTacToe(int n, boolean solution2) {
        rows2 = new int[n];
        cols2 = new int[n];
        diagonals2 = new int[2];
    }

    public int move2(int row, int col, int player) {
        int size = rows2.length;
        int score = player == 1 ? 1 : -1;
        rows2[row] += score;
        cols2[col] += score;
        if (row == col) diagonals2[0] += score;
        if (row + col == size - 1) diagonals2[1] += score;
        // 不用 Math.abs，直接 x score 也行！！！
        if (Math.abs(rows2[row]) == size
                || Math.abs(cols2[col]) == size
                || Math.abs(diagonals2[0]) == size
                || Math.abs(diagonals2[1]) == size) return player;
        return 0;
    }

    public static void main(String[] args) {
        DesignTicTacToe solution1 = new DesignTicTacToe(3);
        System.out.println(solution1.move(0, 0, 1)); // 0
        System.out.println(solution1.move(0, 2, 2)); // 0
        System.out.println(solution1.move(2, 2, 1)); // 0
        System.out.println(solution1.move(1, 1, 2)); // 0
        System.out.println(solution1.move(2, 0, 1)); // 0
        System.out.println(solution1.move(1, 0, 2)); // 0
        System.out.println(solution1.move(2, 1, 1)); // 1

        DesignTicTacToe solution2 = new DesignTicTacToe(3, true);
        System.out.println(solution2.move2(0, 0, 1)); // 0
        System.out.println(solution2.move2(0, 2, 2)); // 0
        System.out.println(solution2.move2(2, 2, 1)); // 0
        System.out.println(solution2.move2(1, 1, 2)); // 0
        System.out.println(solution2.move2(2, 0, 1)); // 0
        System.out.println(solution2.move2(1, 0, 2)); // 0
        System.out.println(solution2.move2(2, 1, 1)); // 1
    }
}
