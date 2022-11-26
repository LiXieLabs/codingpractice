import java.util.Arrays;
import java.util.stream.Collectors;

public class RotateArray {

    /************* Solution 1: Recursive Brute-Force (TLE) **************/
    /**
     * Time: O(N X K)  Space: O(1)
     */
    public void rotate1(int[] nums, int k) {
        int l = nums.length;
        k = k % l;
        if (k == 0) return;
        int pre = nums[l - 1];
        for (int i = 0; i < l; i++) {
            int tmp = nums[i];
            nums[i] = pre;
            pre = tmp;
        }
        rotate(nums, k - 1);
    }

    /************* Solution 2: In-place Reverse **************/
    /**
     * input: [1,2,3,4,5,6,7], k = 3
     * reverse(nums, 0, l - 1) => [7,6,5,4,3,2,1]
     * reverse(nums, 0, k - 1) => [5,6,7]
     * reverse(nums, k, l - 1) => [1,2,3,4]
     * output: [5,6,7,1,2,3,4]
     *
     * Time: O(N)  Space: (1)
     */
    public void rotate2(int[] nums, int k) {
        int l = nums.length;
        k = k % l;
        if (k == 0) return;
        reverse(nums, 0, l - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, l - 1);
    }

    private void reverse(int[] nums, int l, int r) {
        while (l < r) {
            int tmp = nums[l];
            nums[l++] = nums[r];
            nums[r--] = tmp;
        }
    }

    /************* Solution 3: In-place Cyclic Replacement **************/
    /**
     * 从start开始，nums[(i + k) % L] = nums[i]
     * (i + xk) % L = i
     * => x is L / gcd(k, L)
     * => 如果 gck(k, L) > 1, 则 start == 0，一次无法遍历完所有的点
     * => Iterate from 0 to gcd(k, n) - 1, which will make sure every element was moved.
     *
     * 注意！！！相邻的位置肯定不会再不同loop中被重复遍历到，除非 gcd(k, L) == 1
     * (i + xk) % L = i + 1
     *
     * Time: O(N)  Space: (1)
     */
    public void rotate(int[] nums, int k) {
        int l = nums.length, updated = 0;
        for (int start = 0; updated < nums.length; start++) {
            int i = (start + k) % l, pre = nums[start];
            boolean end = false;
            while (!end) {
                end = i == start;
                int tmp = nums[i];
                nums[i] = pre;
                pre = tmp;
                updated++;
                i = (i + k) % l;
            }
        }
    }

    private static void print(int[] input) {
        System.out.println("[" + Arrays.stream(input).mapToObj(String::valueOf).collect(Collectors.joining(",")) + "]");
    }

    public static void main(String[] args) {
        RotateArray solution = new RotateArray();

        // TC1
        int[] arr1 = new int[]{1,2,3,4,5,6,7};
        solution.rotate(arr1, 10);
        print(arr1); // [5,6,7,1,2,3,4]

        // TC2
        int[] arr2 = new int[]{-1,-100,3,99};
        solution.rotate(arr2, 2);
        print(arr2); // [3,99,-1,-100]
    }
}
