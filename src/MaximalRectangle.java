/**
 * 85. Maximal Rectangle (https://leetcode.com/problems/maximal-rectangle/description/)
 */
public class MaximalRectangle {

    /************** Solution 1: DP + LC84 (Monostack) *****************/
    /**
     * 每行看作一排 histogram，转化为 leetcode 84
     *
     * Time: O(M x N)  Space: O(N)
     */

    LargestRectangleInHistogram Leetcode84 = new LargestRectangleInHistogram();

    public int maximalRectangle(char[][] matrix) {
        int r = matrix.length, c = matrix[0].length, res = 0;
        int[] dp = new int[c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                dp[j] = (matrix[i][j] == '1' ? dp[j] + 1 : 0);
            }
            res = Math.max(res, Leetcode84.largestRectangleArea(dp));
        }
        return res;
    }

    public static void main(String[] args) {
        MaximalRectangle solution = new MaximalRectangle();
        System.out.println(solution.maximalRectangle(new char[][]{
                {'1','0','1','0','0'},
                {'1','0','1','1','1'},
                {'1','1','1','1','1'},
                {'1','0','0','1','0'}
        })); // 6
        System.out.println(solution.maximalRectangle(new char[][]{
                {'0'}
        })); // 0
        System.out.println(solution.maximalRectangle(new char[][]{
                {'1'}
        })); // 1
    }
}
