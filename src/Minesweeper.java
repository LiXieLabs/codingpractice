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

    char[][] board;
    int r, c;

    /**************** Solution 1: recur DFS *********************/
    /**
     * Time: O(M X N)   Space: O(M X N)
     */
    public char[][] updateBoard(char[][] board, int[] click) {
        int i = click[0], j = click[1];
        if (board[i][j] == 'M') {
            board[i][j] = 'X';
        } else {
            this.board = board;
            r = board.length;
            c = board[0].length;
            reveal(i, j);
        }
        return board;
    }

    private void reveal(int i, int j) {
        int mineCnt = 0;
        for (int[] d : DIREC) {
            int ni = i + d[0], nj = j + d[1];
            if (0 <= ni && ni < r && 0 <= nj && nj < c && board[ni][nj] == 'M') {
                mineCnt++;
            }
        }
        if (mineCnt == 0) {
            board[i][j] = 'B';
            for (int[] d : DIREC) {
                int ni = i + d[0], nj = j + d[1];
                if (0 <= ni && ni < r && 0 <= nj && nj < c && board[ni][nj] == 'E') {
                    reveal(ni, nj);
                }
            }
        } else {
            board[i][j] = (char) ('0' + mineCnt);
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