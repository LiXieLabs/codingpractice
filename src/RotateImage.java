import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 48. Rotate Image (https://leetcode.com/problems/rotate-image/description/)
 */
public class RotateImage {

    /****** Solution 1: 对角线交换 + 每行左右two pointers交换 **************/
    /**
     * 先以 i=j 对角线交换对称元素 => 转置(transpose)
     * 再每行左右用 two pointers 交换 => 行翻转(reverse)
     *
     * Time: O(N)   Space: O(1)
     */
    public void rotate1(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = tmp;
            }
        }
        for (int i = 0; i < n; i++) {
            int left = 0, right = n - 1;
            while (left < right) {
                int tmp = matrix[i][left];
                matrix[i][left++] = matrix[i][right];
                matrix[i][right--] = tmp;
            }
        }
    }

    /****** Solution 2: 分为四个象限 + 遍历左上角第一象限 + 一次交换中心对称的四个元素 **************/
    /**
     *  1  2 |  3  4
     *  5  6 |  7  8      第一象限
     * --------------  =>  1  2
     *  9 10 | 11 12       5  6
     * 13 14 | 15 16
     *
     *  1  |  2    3
     *     -----------     第一象限
     *  4 |  5  |  6   =>  1
     *  --------           4
     *  7    8 |  9
     *
     *
     * Time: O(N)   Space: O(1)
     */
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < (n + 1) / 2; i++) {
            for (int j = 0; j < n / 2; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[n - 1 - j][i];
                matrix[n - 1 - j][i] = matrix[n - 1 - i][n - 1 - j];
                matrix[n - 1 - i][n - 1 - j] = matrix[j][n - 1 - i];
                matrix[j][n - 1 - i] = tmp;
            }
        }
    }

    private static void print(int[][] input) {
        for (int[] row : input) {
            System.out.println("["
                    + Arrays.stream(row).mapToObj(String::valueOf).collect(Collectors.joining(","))
                    + "]");
        }
        System.out.println("\n");
    }

    public static void main(String[] args) {
        RotateImage solution = new RotateImage();

        int[][] m1 = new int[][]{
                {1,2,3},
                {4,5,6},
                {7,8,9}
        };
        solution.rotate(m1);
        print(m1);

        int[][] m2 = new int[][]{
                { 1, 2, 3, 4},
                { 5, 6, 7, 8},
                { 9,10,11,12},
                {13,14,15,16},
        };
        solution.rotate(m2);
        print(m2);
    }
}
