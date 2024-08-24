/**
 * 74. Search a 2D Matrix (https://leetcode.com/problems/search-a-2d-matrix/description/)
 */
public class SearchA2DMatrix {

    /************** Solution 1: Binary Search to find 1st/last Occurrence **************/
    /**
     * Time: O(log(RC))    Space: O(1)
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        int row = matrix.length, col = matrix[0].length;
        int lo = 0, hi = row * col - 1;
        while (lo <= hi) {
            int mid = (lo + hi) >> 1;
            if (matrix[mid / col][mid % col] == target) {
                return true;
            } else if (matrix[mid / col][mid % col] < target) {
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
