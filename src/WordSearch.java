import java.util.HashSet;
import java.util.Set;

/**
 * 79. Word Search (https://leetcode.com/problems/word-search/)
 */
public class WordSearch {

    private static final int[][] DIREC = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    char[][] board;
    int r, c;
    String word;

    /******************* Solution 1: Backtracking *********************/
    /**
     * N is the number of cells in the board and L is the length of the word to be matched.
     * Time: O(N*3^L) from each cell in board, explore 3 directions (as cannot go back, so 4 - 1 directions), like 3-ary tree
     * Space: O(L) from call stack
     */
    public boolean exist(char[][] board, String word) {
        this.board = board;
        r = board.length;
        c = board[0].length;
        this.word = word;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (board[i][j] == word.charAt(0) && recur(i, j, 1)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean recur(int i, int j, int k) {
        if (k == word.length()) return true;
        boolean res = false;
        char original = board[i][j];
        board[i][j] = '*';
        for (int[] d : DIREC) {
            int ni = i + d[0], nj = j + d[1];
            if (0 <= ni && ni < r && 0 <= nj && nj < c && board[ni][nj] == word.charAt(k)) {
                res = recur(ni, nj, k + 1);
                if (res) break;
            }
        }
        board[i][j] = original;
        return res;
    }

    public static void main(String[] args) {
        WordSearch solution = new WordSearch();
//        {'A','B','C','E'},
//        {'S','F','C','S'},
//        {'A','D','E','E'}
        System.out.println(solution.exist(new char[][]{{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}}, "ABCCED")); // true
        System.out.println(solution.exist(new char[][]{{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}}, "SEE")); // true
        System.out.println(solution.exist(new char[][]{{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}}, "ABCB")); // false
        System.out.println(solution.exist(new char[][]{{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}}, "BA")); // true
    }
}
