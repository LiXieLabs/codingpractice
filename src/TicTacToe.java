public class TicTacToe {

    public String tictactoe(int[][] moves) {
        int[][] board = new int[3][3];
        int currentPlayer = 1; // A is 1; B is -1;
        for (int i = 0; i < moves.length && i < 9; i++) {

            int x = moves[i][0];
            int y = moves[i][1];
            board[x][y] = currentPlayer;

            // check row
            if (board[x][0] == currentPlayer
                    && board[x][1] == currentPlayer
                    && board[x][2] == currentPlayer)
                return currentPlayer == 1 ? "A" : "B";
            // check col
            if (board[0][y] == currentPlayer
                    && board[1][y] == currentPlayer
                    && board[2][y] == currentPlayer)
                return currentPlayer == 1 ? "A" : "B";
            // check diagonal
            if (x == y
                    && board[0][0] == currentPlayer
                    && board[1][1] == currentPlayer
                    && board[2][2] == currentPlayer)
                return currentPlayer == 1 ? "A" : "B";
            // check back-diagonal
            if (x + y == 2
                    && board[2][0] == currentPlayer
                    && board[1][1] == currentPlayer
                    && board[0][2] == currentPlayer)
                return currentPlayer == 1 ? "A" : "B";

            currentPlayer *= -1;

        }
        return moves.length < 9 ? "Pending" : "Draw";
    }

    // 但是如果每一步不一定是valid的，还是需要像上面那样keep一个board
    public String tictactoe1(int[][] moves) {
        int[][] rows = new int[2][3], cols = new int[2][3];
        int[] d1 = new int[2], d2 = new int[2];
        for (int i = 0; i < moves.length; i++) {
            int r = moves[i][0], c = moves[i][1], player = i % 2;
            if (++rows[player][r] == 3
                    || ++cols[player][c] == 3
                    || r == c && ++d1[player] == 3
                    || r + c == 2 && ++d2[player] == 3)
                return player == 0 ? "A" : "B";
        }
        return moves.length < 9 ? "Pending" : "Draw";
    }

    public static void main(String[] args) {
        TicTacToe solution = new TicTacToe();
        System.out.println(solution.tictactoe1(new int[][]{{0,0}, {2,0}, {1,1}, {2,1}, {2,2}}));
        System.out.println(solution.tictactoe1(new int[][]{{0,0}, {1,1}, {0,1}, {0,2}, {1,0}, {2,0}}));
        System.out.println(solution.tictactoe1(new int[][]{{0,0}, {1,1}, {2,0}, {1,0}, {1,2}, {2,1}, {0,1}, {0,2}}));
        System.out.println(solution.tictactoe1(new int[][]{{0,0}, {1,1}, {2,0}, {1,0}, {1,2}, {2,1}, {0,1}, {0,2}, {2,2}}));
    }
}
