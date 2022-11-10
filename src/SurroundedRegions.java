import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class SurroundedRegions {

    private Set<Integer> visited;
    private char[][] board;
    private final int[][] direc = new int[][]{{0, 1},{1, 0},{0, -1},{-1, 0}};
    private int row;
    private int col;

    /******************* Solution 1: recursive DFS from boundary ********************/
    /**
     * 1个优化：节省visited空间，in-place标记visited cell为'E'
     *         最后遍历，'X' => 'X', 'O' => 'X', 'E' => 'O'
     *
     * Time: O(N) Space: O(N)
     */
    public void solve(char[][] board) {

        // iterate over boundaries & search all connected O & put in hashset
        row = board.length;
        col = board[0].length;
        this.board = board;
        visited = new HashSet<>();
        for (int c = 0; c < col; c++) {
            if (board[0][c] == 'O' && !visited.contains(c)) {
                recur(0, c);
            }
            if (board[row - 1][c] == 'O' && !visited.contains((row - 1) * col + c)) {
                recur(row - 1, c);
            }
        }
        for (int r = 0; r < row; r++) {
            if (board[r][0] == 'O' && !visited.contains(r * col)) {
                recur(r, 0);
            }
            if (board[r][col - 1] == 'O' && !visited.contains(r * (col - 1) + col - 1)) {
                recur(r, col - 1);
            }
        }

        // iterate over board & flip all O not in hashset to X
        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < col - 1; j++) {
                if (board[i][j] == 'O' && !visited.contains(i * col + j)) {
                    board[i][j] = 'X';
                }
            }
        }

    }

    public void recur(int i, int j) {
        visited.add(i * col + j);
        for (int[] d : direc) {
            int di = d[0], dj = d[1];
            int ni = i + di, nj = j + dj;
            if (0 <= ni && ni < row && 0 <= nj && nj < col && board[ni][nj] == 'O' && !visited.contains(ni * col + nj)) {
                recur(ni, nj);
            }
        }
    }

    private static void print(char[][] input) {
        System.out.println(Arrays.stream(input).map(row -> Arrays.toString(row)).collect(Collectors.joining("\n")));
    }

    public static void main(String[] args) {
        SurroundedRegions solution = new SurroundedRegions();

        char[][] board1 = new char[][]{{'X','X','X','X'},
                                       {'X','O','O','X'},
                                       {'X','X','O','X'},
                                       {'X','O','X','X'}};
        solution.solve(board1);
        print(board1);

        char[][] board2 = new char[][]{{'O','O','O'},
                                       {'O','O','O'},
                                       {'O','O','O'}};
        solution.solve(board2);
        print(board2);
    }

}