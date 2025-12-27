import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 48. Rotate Image (https://leetcode.com/problems/rotate-image/description/)
 */
public class RotateImage {

    int[][] matrix;

    /****** Solution 1: 对角线交换 + 每行左右two pointers交换 **************/
    /**
     * 先以 i=j 对角线交换对称元素 => 转置(transpose)
     * 再每行左右用 two pointers 交换 => 行翻转(reverse)
     *
     * Time: O(N)   Space: O(1)
     */
    public void rotate1(int[][] matrix) {
        this.matrix = matrix;
        int n = matrix.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                swap(i, j, j, i);
            }
        }
        for (int i = 0; i < n; i++) {
            int left = 0, right = n - 1;
            while (left < right) {
                swap(i, left++, i, right--);
            }
        }
    }

    /****** Solution 2: 上下翻转 + 对角线交换 **************/
    /**
     * 先上下翻转
     * 再以 i=j 对角线交换对称元素 => 转置(transpose)
     *
     * Time: O(N)   Space: O(1)
     */
    public void rotate2(int[][] matrix) {
        this. matrix = matrix;
        int n = matrix.length;
        if (n == 1) return;
        for (int i = 0; i < n / 2; i++) {
            for (int j = 0; j < n; j++) {
                swap(i, j, n - i - 1, j);
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                swap(i, j, j, i);
            }
        }
    }

    private void swap(int i1, int j1, int i2, int j2) {
        int tmp = matrix[i1][j1];
        matrix[i1][j1] = matrix[i2][j2];
        matrix[i2][j2] = tmp;
    }

    /****** Solution 3: 分为四个象限 + 遍历左上角第一象限 + 一次交换中心对称的四个元素 **************/
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
     * 重点！！！
     * 0,1 -> 1,3 -> 3,2 -> 2,0
     * i,j -> j,n-i-1 -> n-i-1,n-j-1 -> n-j-1,i
     * 每个到下一个，都按照 x,y -> y,n-x-1 推导
     *
     * Time: O(N)   Space: O(1)
     */
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        // 注意象限划分方法！！！
        // 反过来 i < n / 2 && j < (n + 1) / 2 也行！
        for (int i = 0; i < (n + 1) / 2; i++) {
            for (int j = 0; j < n / 2; j++) {
                int tmp = matrix[i][j];
                // 逆时针赋值！
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
//        [7,4,1]
//        [8,5,2]
//        [9,6,3]


        int[][] m2 = new int[][]{
                { 1, 2, 3, 4},
                { 5, 6, 7, 8},
                { 9,10,11,12},
                {13,14,15,16},
        };
        solution.rotate(m2);
        print(m2);
//        [13,9,5,1]
//        [14,10,6,2]
//        [15,11,7,3]
//        [16,12,8,4]
    }
}
