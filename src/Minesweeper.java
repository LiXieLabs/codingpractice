import java.util.Arrays;
import java.util.stream.Collectors;

public class Minesweeper {

    /**
     * String - Character/char - Integer/int 转化
     * Integer/int => String:
     *      String s = String.valueOf((char) intValue);
     * Character/char => String:
     *      String s = String.valueOf(charValue);
     * Integer/int (ascii) => Character/char:
     *      char c = (char) intValue;
     * Integer/int (real value) => Character/char:
     *      char c = (char) ('0' + intValue);
     *      char c = Character.fromDigit(intValue, 10);
     *
     */

    private static final int[][] DIREC = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}, {1, 1}, {-1, -1}, {1, -1}, {-1, 1}};

    private char[][] board;
    private int row;
    private int col;

    /**************** Solution 1: recur DFS *********************/
    /**
     * Time: O(M X N)   Space: O(M X N)
     */
    public char[][] updateBoard(char[][] board, int[] click) {

        if (board[click[0]][click[1]] == 'M') {
            board[click[0]][click[1]] = 'X';
            return board;
        }

        this.board = board;
        row = board.length;
        col = board[0].length;

        recur(click[0], click[1]);
        return this.board;
    }

    private void recur(int i, int j) {
        if (board[i][j] == 'E') {
            int count = 0;
            for (int[] d : DIREC) {
                int ni = i + d[0], nj = j + d[1];
                if (0 <= ni && ni < row && 0 <= nj && nj < col && board[ni][nj] == 'M') {
                    count++;
                }
            }
            if (count > 0) {
                // 小心！！！Character.forDigit(n, 10)也可以！！！
                board[i][j] = (char) ('0' + count);
            } else {
                board[i][j] = 'B';
                for (int[] d : DIREC) {
                    int ni = i + d[0], nj = j + d[1];
                    if (0 <= ni && ni < row && 0 <= nj && nj < col) {
                        recur(ni, nj);
                    }
                }
            }

        }
    }

    private static void print(char[][] input) {
        System.out.println(Arrays.stream(input)
                .map(row ->
                        "[" + new String(row).chars().mapToObj(
                                intValue -> String.valueOf((char) intValue))
                                .collect(Collectors.joining(",")) + "]")
                .collect(Collectors.joining("\n")));
    }

    public static void main(String[] args) {
        Minesweeper solution = new Minesweeper();

        char[][] board1 = new char[][]{
                {'E','E','E','E','E'},
                {'E','E','M','E','E'},
                {'E','E','E','E','E'},
                {'E','E','E','E','E'}

        };
        print(solution.updateBoard(board1, new int[]{3, 0}));

        char[][] board2 = new char[][]{
                {'B','1','E','1','B'},
                {'B','1','M','1','B'},
                {'B','1','1','1','B'},
                {'B','B','B','B','B'}

        };
        print(solution.updateBoard(board2, new int[]{1, 2}));
    }

}