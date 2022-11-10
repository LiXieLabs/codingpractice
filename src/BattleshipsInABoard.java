public class BattleshipsInABoard {

    int r, c;
    char[][] board;

    /******************* Solution 1: recur DFS *********************/
    /**
     * Special case of https://leetcode.com/problems/number-of-islands/
     *
     * Time: O(M X N) Space: O(max(M, N)) by recur call stack
     */
    public int countBattleships1(char[][] board) {
        r = board.length;
        c = board[0].length;
        this.board = board;
        int cnt = 0;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (this.board[i][j] == 'X') {
                    cnt += 1;
                    sink(i, j);
                }
            }
        }
        return cnt;
    }

    private void sink(int i, int j) {
        this.board[i][j] = '.';
        if (i+1 < r && this.board[i+1][j] == 'X') {
            sink(i + 1, j);
        }
        if (j+1 < c && this.board[i][j+1] == 'X') {
            sink(i, j + 1);
        }
    }

    /*************** Solution 2: One-pass without extra-space ************/
    /**
     * Time: O(M X N) Space: O(1)
     */
    public int countBattleships(char[][] board) {
        this.board = board;
        r = board.length;
        c = board[0].length;
        int res = 0;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (isFirstBlock(i, j)) {
                    res++;
                }
            }
        }
        return res;
    }

    private boolean isFirstBlock(int i, int j) {
        return board[i][j] == 'X'
                && (i == 0 || board[i - 1][j] != 'X')
                && (j == 0 || board[i][j - 1] != 'X');
    }

    public static void main(String[] args) {
        BattleshipsInABoard solution = new BattleshipsInABoard();
        System.out.println(solution.countBattleships(
                new char[][]{{'X','.','.','X'},{'.','.','.','X'},{'.','.','.','X'}}));
    }


}
