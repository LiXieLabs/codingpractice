/**
 * 240. Search a 2D Matrix II (https://leetcode.com/problems/search-a-2d-matrix-ii/description/)
 */
public class SearchA2DMatrixII {

    /********** Solution 1: Binary Search Each Column *****************/
    /**
     * Time: O(RlogC)     Space: O(1)
     */
    public boolean searchMatrix1(int[][] matrix, int target) {
        int row = matrix.length, col = matrix[0].length;
        if (row <= col) {
            for (int i = 0; i < row; i++) {
                int lo = 0, hi = col - 1;
                while (lo <= hi) {
                    int j = (lo + hi) >> 1;
                    if (matrix[i][j] == target) return true;
                    if (matrix[i][j] < target) {
                        lo = j + 1;
                    } else {
                        hi = j - 1;
                    }
                }
            }
            return false;
        } else {
            for (int j = 0; j < col; j++) {
                int lo = 0, hi = row - 1;
                while (lo <= hi) {
                    int i = (lo + hi) >> 1;
                    if (matrix[i][j] == target) return true;
                    if (matrix[i][j] < target) {
                        lo = i + 1;
                    } else {
                        hi = i - 1;
                    }
                }
            }
            return false;
        }
    }

    /********** Solution 2: Binary Search Along Diagonal *****************/
    /**
     * Time: O(log(min(R,C))!) = O(min(R,C)log(min(R,C))    Space: O(1)
     */
    int[][] matrix;
    int target, row, col;
    public boolean searchMatrix2(int[][] matrix, int target) {
        this.matrix = matrix;
        this.target = target;
        row = matrix.length;
        col = matrix[0].length;
        int shorterDim = Math.min(row, col);
        for (int i = 0; i < shorterDim; i++) {
            boolean verticalFound = binarySearch(i, true);
            boolean horizontalFound = binarySearch(i, false);
            if (verticalFound || horizontalFound) {
                return true;
            }
        }

        return false;
    }

    private boolean binarySearch(int start, boolean vertical) {
        int lo = start, hi = vertical ? col - 1 : row - 1;
        while (lo <= hi) {
            int mid = (lo + hi) >> 1;
            int cur = vertical ? matrix[start][mid] : matrix[mid][start];
            if (cur == target) return true;
            if (cur < target) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return false;
    }

    /************* Solution 3: Greedy Algo 最优解 *****************/
    /**
     * 左下角向右上角移动，大则减行，小则加列
     *
     * Time: O(R + C)     Space: O(1)
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        int row = matrix.length, col = matrix[0].length;
        int i = row - 1, j = 0;
        while (i >= 0 && j < col) {
            if (matrix[i][j] == target) return true;
            if (matrix[i][j] < target) j++;
            else i--;
        }
        return false;
    }

    public static void main(String[] args) {
        SearchA2DMatrixII solution = new SearchA2DMatrixII();
        System.out.println(solution.searchMatrix(new int[][]{
                { 1, 4, 7,11,15},
                { 2, 5, 8,12,19},
                { 3, 6, 9,16,22},
                {10,13,14,17,24},
                {18,21,23,26,30}
        }, 5)); // true
        System.out.println(solution.searchMatrix(new int[][]{
                { 1, 4, 7,11,15},
                { 2, 5, 8,12,19},
                { 3, 6, 9,16,22},
                {10,13,14,17,24},
                {18,21,23,26,30}
        }, 20)); // false
    }
}
