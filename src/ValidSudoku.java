import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 36. Valid Sudoku (https://leetcode.com/problems/valid-sudoku/description/)
 */
public class ValidSudoku {

    /************ Solution 1: HashSet or Array ******************/
    /**
     * Store 9 rows, 9 cols, 9 cubes status separately
     * Map<i, Set>
     * or Set[]
     * or int[][]
     * All works
     *
     * Time: O(N^2)   Space: O(N^2) worst case
     */
    public boolean isValidSudoku1(char[][] board) {
        Map<Integer, Set<Integer>> rows = new HashMap<>();
//        Set<Character>[] rows = new HashSet[9];
//        int[][] rows = new int[9][9];
        Map<Integer, Set<Integer>> cols = new HashMap<>();
        Map<Integer, Set<Integer>> cubes = new HashMap<>();
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c] == '.') continue;

                rows.putIfAbsent(r, new HashSet<>());
                if (!rows.get(r).add(board[r][c] - '0')) return false;

                cols.putIfAbsent(c, new HashSet<>());
                if (!cols.get(c).add(board[r][c] - '0')) return false;

                int cubeIdx = (r / 3) * 3 + c / 3;
                cubes.putIfAbsent(cubeIdx, new HashSet<>());
                if (!cubes.get(cubeIdx).add(board[r][c] - '0')) return false;
            }
        }
        return true;
    }

    /************ Solution 2: Bit Masking ******************/
    /**
     * Store 9 rows, 9 cols, 9 cubes status separately
     * each row/col/cube is a number,
     * and 1st 9 bits of the number stands for a number in the row/col/cube
     * 1 means it presents, or else 0 means it doesn't present.
     *
     * Time: O(N^2)   Space: O(1)
     */
    public boolean isValidSudoku(char[][] board) {
        int N = 9;
        int[] rows = new int[N];
        int[] cols = new int[N];
        int[] cubes = new int[N];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                if (board[r][c] == '.') continue;

                int n = board[r][c] - '1';
                int mask = (1 << n);

                if ((rows[r] & mask) != 0) return false;
                rows[r] |= mask;

                if ((cols[c] & mask) != 0) return false;
                cols[c] |= mask;

                int cubeIdx = (r / 3) * 3 + (c / 3);
                if ((cubes[cubeIdx] & mask) != 0) return false;
                cubes[cubeIdx] |= mask;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        ValidSudoku solution = new ValidSudoku();

        System.out.println(solution.isValidSudoku(
                new char[][]{
                        {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                        {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                        {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                        {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                        {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                        {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                        {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                        {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                        {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
                }
        )); // true

        System.out.println(solution.isValidSudoku(
                new char[][]{
                        {'8', '3', '.', '.', '7', '.', '.', '.', '.'},
                        {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                        {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                        {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                        {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                        {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                        {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                        {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                        {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
                }
        )); // false
    }
}
