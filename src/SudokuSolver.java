import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 37. Sudoku Solver (https://leetcode.com/problems/sudoku-solver/)
 */
public class SudokuSolver {

    char[][] board;
    boolean[][] rows, cols, cubes;

    /********** Solution 1: Backtracking **************/
    /**
     * Time: O(9^K) where K is the initial number of empty cells
     * Space: O(rows + cols + cubes + recurStack) = O(4 * 81) = O(1)
     */
    public void solveSudoku(char[][] board) {
        this.board = board;
        rows = new boolean[9][9];
        cols = new boolean[9][9];
        cubes = new boolean[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') continue;
                int n = board[i][j] - '1';
                rows[i][n] = true;
                cols[j][n] = true;
                cubes[getCubeIndex(i, j)][n] = true;
            }
        }

        recurSolve(0, 0);
    }

    private boolean recurSolve(int i, int j) {
        if (i == 9 && j == 0) return true;
        int[] nextIndex = getNextIndex(i, j);
        if (board[i][j] != '.') {
            return recurSolve(nextIndex[0], nextIndex[1]);
        }
        for (int candidate : getCandidates(i, j)) {
            updateCandidate(i, j, candidate);
            if (recurSolve(nextIndex[0], nextIndex[1])) {
                return true;
            }
            removeCandidate(i, j, candidate);
        }
        return false;
    }

    private Set<Integer> getCandidates(int i, int j) {
        Set<Integer> candidates = new HashSet<>();
        for (int n = 1; n <= 9; n++) {
            if (!rows[i][n - 1] && !cols[j][n - 1] && !cubes[getCubeIndex(i, j)][n - 1]) {
                candidates.add(n);
            }
        }
        return candidates;

    }

    private void updateCandidate(int i, int j, int n) {
        rows[i][n - 1] = true;
        cols[j][n - 1] = true;
        cubes[getCubeIndex(i, j)][n - 1] = true;
        board[i][j] = (char) ('0' + n);
    }

    private void removeCandidate(int i, int j, int n) {
        rows[i][n - 1] = false;
        cols[j][n - 1] = false;
        cubes[getCubeIndex(i, j)][n - 1] = false;
        board[i][j] = '.';
    }

    private int[] getNextIndex(int i, int j) {
        int n = (i * 9 + j) + 1;
        return new int[]{n / 9, n % 9};
    }

    private int getCubeIndex(int i, int j) {
        return (i / 3) * 3 + j / 3;
    }

    private static void print(char[][] input) {
        Arrays.stream(input).forEach(System.out::println);
    }

    public static void main(String[] args) {
        SudokuSolver solution = new SudokuSolver();

        char[][] board1 = new char[][]{
                {'5','3','.','.','7','.','.','.','.'},
                {'6','.','.','1','9','5','.','.','.'},
                {'.','9','8','.','.','.','.','6','.'},
                {'8','.','.','.','6','.','.','.','3'},
                {'4','.','.','8','.','3','.','.','1'},
                {'7','.','.','.','2','.','.','.','6'},
                {'.','6','.','.','.','.','2','8','.'},
                {'.','.','.','4','1','9','.','.','5'},
                {'.','.','.','.','8','.','.','7','9'}
        };
        solution.solveSudoku(board1);
        print(board1);
//        result:
//        534678912
//        672195348
//        198342567
//        859761423
//        426853791
//        713924856
//        961537284
//        287419635
//        345286179

    }
}
