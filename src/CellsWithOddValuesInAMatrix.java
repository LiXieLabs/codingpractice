public class CellsWithOddValuesInAMatrix {

    public int oddCells(int m, int n, int[][] indices) {
        int[] rows = new int[m], cols = new int[n];
        for (int[] index : indices) {
            rows[index[0]]++;
            cols[index[1]]++;
        }
        int count = 0;
        for (int r : rows) {
            for (int c : cols) {
                count += (r + c) % 2;
            }
        }
        return count;
    }

    public int oddCells1(int m, int n, int[][] indices) {
        boolean[] oddRows = new boolean[m], oddCols = new boolean[n];
        for (int[] index : indices) {
            oddRows[index[0]] ^= true; // if row index[0] appears odd times, it will correspond to true.
            oddCols[index[1]] ^= true; // if col index[1] appears odd times, it will correspond to true.
        }
        int count = 0;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                count += oddRows[i] ^ oddCols[j] ? 1 : 0; // only cell (i, j) with odd times count of row + column would get odd values.
            }
        }
        return count;
    }

    public static void main(String[] args) {
        CellsWithOddValuesInAMatrix solution = new CellsWithOddValuesInAMatrix();
        System.out.println(solution.oddCells1(2, 3, new int[][]{{0,1},{1,1}}));
        System.out.println(solution.oddCells1(2, 2, new int[][]{{1,1},{0,0}}));
    }
}
