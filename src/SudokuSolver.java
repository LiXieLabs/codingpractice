import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 37. Sudoku Solver (https://leetcode.com/problems/sudoku-solver/)
 */
public class SudokuSolver {

    /********** Solution 1: Backtracking **************/
    /**
     * Time: O(9^K) where K is the initial number of empty cells
     * Space: O(rows + cols + cubes + recurStack) = O(4 * 81) = O(1)
     */
//    char[][] board;
//    boolean[][] rows, cols, cubes;

//    public void solveSudoku(char[][] board) {
//        this.board = board;
//        rows = new boolean[9][9];
//        cols = new boolean[9][9];
//        cubes = new boolean[9][9];
//
//        for (int i = 0; i < 9; i++) {
//            for (int j = 0; j < 9; j++) {
//                if (board[i][j] == '.') continue;
//                int n = board[i][j] - '1';
//                rows[i][n] = true;
//                cols[j][n] = true;
//                cubes[getCubeIndex(i, j)][n] = true;
//            }
//        }
//
//        recurSolve(0, 0);
//    }
//
//    private boolean recurSolve(int i, int j) {
//        if (i == 9 && j == 0) return true;
//        int[] nextIndex = getNextIndex(i, j);
//        if (board[i][j] != '.') {
//            return recurSolve(nextIndex[0], nextIndex[1]);
//        }
//        for (int candidate : getCandidates(i, j)) {
//            updateCandidate(i, j, candidate);
//            if (recurSolve(nextIndex[0], nextIndex[1])) {
//                return true;
//            }
//            removeCandidate(i, j, candidate);
//        }
//        return false;
//    }
//
//    private Set<Integer> getCandidates(int i, int j) {
//        Set<Integer> candidates = new HashSet<>();
//        for (int n = 1; n <= 9; n++) {
//            if (!rows[i][n - 1] && !cols[j][n - 1] && !cubes[getCubeIndex(i, j)][n - 1]) {
//                candidates.add(n);
//            }
//        }
//        return candidates;
//
//    }
//
//    private void updateCandidate(int i, int j, int n) {
//        rows[i][n - 1] = true;
//        cols[j][n - 1] = true;
//        cubes[getCubeIndex(i, j)][n - 1] = true;
//        board[i][j] = (char) ('0' + n);
//    }
//
//    private void removeCandidate(int i, int j, int n) {
//        rows[i][n - 1] = false;
//        cols[j][n - 1] = false;
//        cubes[getCubeIndex(i, j)][n - 1] = false;
//        board[i][j] = '.';
//    }
//
//    private int[] getNextIndex(int i, int j) {
//        int n = (i * 9 + j) + 1;
//        return new int[]{n / 9, n % 9};
//    }
//
//    private int getCubeIndex(int i, int j) {
//        return (i / 3) * 3 + j / 3;
//    }

    /********** Solution 2: Backtracking with Bit Masking **************/
    /**
     * Time: O(9^K) where K is the initial number of empty cells
     * Space: O(rows + cols + cubes + recurStack) = O(3 * 9 + 81) = O(1)
     */
    private static final int N = 9;

    char[][] board;
    int[] rows;
    int[] cols;
    int[] cubes;

    public void solveSudoku(char[][] board) {
        this.board = board;
        rows = new int[N];
        cols = new int[N];
        cubes = new int[N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] != '.') {
                    int mask = (1 << (board[i][j] - '0'));
                    int cubeIdx = (i / 3) * 3 + (j / 3);
                    rows[i] |= mask;
                    cols[j] |= mask;
                    cubes[cubeIdx] |= mask;
                }
            }
        }

        recurResolve(0);
    }

    private boolean recurResolve(int n) {
        if (n == N * N) return true;
        int i = n / N, j = n % N;
        if (board[i][j] != '.') return recurResolve(n + 1);
        for (int fill = 1; fill <= 9; fill++) {
            int mask = (1 << fill);
            int cubeIdx = (i / 3) * 3 + (j / 3);
            if ((rows[i] & mask) == 0 && (cols[j] & mask) == 0 && (cubes[cubeIdx] & mask) == 0) {
                fillNumber(i, j, cubeIdx, mask, fill);
                if (recurResolve(n + 1)) return true;
                removeNumber(i, j, cubeIdx, mask);
            }
        }
        return false;
    }

    private void fillNumber(int i, int j, int cubeIdx, int mask, int fill) {
        rows[i] |= mask;
        cols[j] |= mask;
        cubes[cubeIdx] |= mask;
        board[i][j] = (char) ('0' + fill);
    }

    private void removeNumber(int i, int j, int cubeIdx, int mask) {
        rows[i] ^= mask;
        cols[j] ^= mask;
        cubes[cubeIdx] ^= mask;
        board[i][j] = '.';
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
