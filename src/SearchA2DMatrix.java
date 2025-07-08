/**
 * 74. Search a 2D Matrix (https://leetcode.com/problems/search-a-2d-matrix/description/)
 */
public class SearchA2DMatrix {

    /************** Solution 1: Binary Search to find 1st/last Occurrence **************/
    /**
     * Time: O(log(RC))    Space: O(1)
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        int r = matrix.length, c = matrix[0].length;
        int lo = 0, hi = r * c - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int i = mid / c, j = mid % c;
            if (matrix[i][j] == target) return true;
            if (matrix[i][j] < target) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        SearchA2DMatrix solution = new SearchA2DMatrix();
        System.out.println(solution.searchMatrix(new int[][]{
                { 1, 3, 5, 7},
                {10,11,16,20},
                {23,30,34,60}
        }, 3)); // true
        System.out.println(solution.searchMatrix(new int[][]{
                { 1, 3, 5, 7},
                {10,11,16,20},
                {23,30,34,60}
        }, 13)); // false
    }
}
