import java.util.HashSet;
import java.util.Set;

public class WordSearch {

    private final static int[][] direc = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    private char[][] board;
    private int r, c;
    private String word;
    private Set<Integer> visited;

    /******************* Solution 1: Backtracking *********************/
    /**
     * N is the number of cells in the board and L is the length of the word to be matched.
     * Time: O(N*3^L) from each cell in board, explore 3 directions (as cannot go back, so 4 - 1 directions)
     * Space: O(L) from call stack
     */
    public boolean exist(char[][] board, String word) {
        this.word = word;
        this.board = board;
        r = board.length;
        c = board[0].length;
        visited = new HashSet<>();
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (this.word.charAt(0) == this.board[i][j]) {
                    if (recurFind(i, j, 1)) return true;
                }
            }
        }
        return false;
    }

    public boolean recurFind(int i, int j, int idx) {
        if (idx == word.length()) return true;
        visited.add(i * c + j);
        for (int[] d : direc) {
            int ni = i + d[0], nj = j + d[1];
            if (0 <= ni && ni < r && 0 <= nj && nj < c && board[ni][nj] == word.charAt(idx) && !visited.contains(ni * c + nj)) {
                if (recurFind(ni, nj, idx + 1)) return true;
            }
        }
        visited.remove(i * c + j);
        return false;
    }

    public static void main(String[] args) {
        WordSearch solution = new WordSearch();
//        {'A','B','C','E'},
//        {'S','F','C','S'},
//        {'A','D','E','E'}
        System.out.println(solution.exist(new char[][]{{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}}, "ABCCED"));
        System.out.println(solution.exist(new char[][]{{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}}, "SEE"));
        System.out.println(solution.exist(new char[][]{{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}}, "ABCB"));
    }

}
