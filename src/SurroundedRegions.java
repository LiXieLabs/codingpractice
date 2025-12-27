import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 130. Surrounded Regions (https://leetcode.com/problems/surrounded-regions/description/)
 */
public class SurroundedRegions {

    /******************* Solution 1: recursive DFS from boundary ********************/
    /**
     * 1个优化：节省 connectedToEdge 空间，in-place标记visited cell为'E'
     *         最后遍历，'X' => 'X', 'O' => 'X', 'E' => 'O'
     *
     * Time: O(N) Space: O(N)
     */
    int[][] DIREC = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    char[][] board;
    int r, c;
    Set<Integer> connectedToEdge;

    public void solve(char[][] board) {
        this.board = board;
        r = board.length;
        c = board[0].length;

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j += i == 0 || i == r - 1 ? 1 : c - 1) {
                if (board[i][j] == 'O') {
                    recur(i, j);
                }
            }
        }

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                } else if (board[i][j] == 'E') {
                    board[i][j] = 'O';
                }
            }
        }
    }

    private void recur(int i, int j) {
        if (0 <= i && i < r && 0 <= j && j < c && board[i][j] == 'O') {
            board[i][j] = 'E';
            for (int[] d : DIREC) {
                recur(i + d[0], j + d[1]);
            }
        }
    }

    private static void print(char[][] input) {
        System.out.println(Arrays.stream(input).map(row -> Arrays.toString(row)).collect(Collectors.joining("\n")));
    }

    public static void main(String[] args) {
        SurroundedRegions solution = new SurroundedRegions();

        char[][] board1 = new char[][]{
                {'X','X','X','X'},
                {'X','O','O','X'},
                {'X','X','O','X'},
                {'X','O','X','X'}};
        solution.solve(board1);
        print(board1);
//        [X, X, X, X]
//        [X, X, X, X]
//        [X, X, X, X]
//        [X, O, X, X]

        char[][] board2 = new char[][]{
                {'O','O','O'},
                {'O','O','O'},
                {'O','O','O'}};
        solution.solve(board2);
        print(board2);
//        [O, O, O]
//        [O, O, O]
//        [O, O, O]
    }

}