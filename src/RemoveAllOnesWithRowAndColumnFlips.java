public class RemoveAllOnesWithRowAndColumnFlips {

    public boolean removeOnes(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) return false;
        int r = grid.length, c = grid[0].length;
        for (int i = 1; i < r; i++) {
            int firstInRow = grid[i][0] ^ grid[0][0];
            for (int j = 1; j < c; j++) {
                // 注意！！！异或要加（），否则优先级问题报错
                if (firstInRow != (grid[i][j] ^ grid[0][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        RemoveAllOnesWithRowAndColumnFlips solution = new RemoveAllOnesWithRowAndColumnFlips();
        System.out.println(solution.removeOnes(new int[][]{{1,0,1},{0,1,0},{1,0,1}}));
        System.out.println(solution.removeOnes(new int[][]{{1,0,1},{0,1,0},{0,0,1}}));
    }


}
