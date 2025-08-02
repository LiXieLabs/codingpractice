import java.util.Arrays;
import java.util.HashSet;
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
        connectedToEdge = new HashSet<>();
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (i == 0 || i == r - 1 || j == 0 || j == c - 1) {
                    int cur = i * c + j;
                    if (board[i][j] == 'O' && connectedToEdge.add(cur)) {
                        recur(i, j);
                    }
                }
            }
        }
        for (int i = 1; i < r - 1; i++) {
            for (int j = 1; j < c - 1; j++) {
                if (board[i][j] == 'O' && !connectedToEdge.contains(i * c + j)) {
                    board[i][j] = 'X';
                }
            }
        }
    }

    private void recur(int i, int j) {
        for (int[] d : DIREC) {
            int ni = i + d[0], nj = j + d[1];
            int nex = ni * c + nj;
            if (0 <= ni && ni < r && 0 <= nj && nj < c
                    && board[ni][nj] == 'O' && connectedToEdge.add(nex)) {
                recur(ni, nj);
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