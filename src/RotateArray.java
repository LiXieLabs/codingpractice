import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 189. Rotate Array (https://leetcode.com/problems/rotate-array/description/)
 */
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
     * input: [1,2,3,4,5,6,7,8,9], k = 3
     * swap [0, l - k - 1] => [6,5,4,3,2,1,7,8,9]
     * swap [l - k, l - 1] => [6,5,4,3,2,1,9,8,7]
     * k = min(k, l - k) ### 这步是必须的！针对 k >= l / 2 的情况，类似 TC3!!! 右移动 k 相当于 左移 l - k!!!
     * swap num[d] & nums[l - d - 1] => [7,8,9,3,2,1,4,5,6]
     * swap [k, l - k - 1] => [7,8,9,1,2,3,4,5,6]
     *
     * Time: O(N)  Space: (1)
     */
    public void rotate2(int[] nums, int k) {
        int l = nums.length;
        k %= l;
        if (k == 0) return;
        int i = 0, j = l - k  - 1;
        while (i < j) {
            swap(nums, i++, j--);
        }
        i = l - k;
        j = l - 1;
        while (i < j) {
            swap(nums, i++, j--);
        }
        k = Math.min(k, l - k);
        for (int d = 0; d < k; d++) {
            swap(nums, d, l - d - 1);
        }
        i = k;
        j = l - k - 1;
        while (i < j) {
            swap(nums, i++, j--);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /************* Solution 3: In-place Reverse **************/
    /**
     * input: [1,2,3,4,5,6,7], k = 3
     * reverse(nums, 0, l - 1) => [7,6,5,4,3,2,1]
     * reverse(nums, 0, k - 1) => [5,6,7]
     * reverse(nums, k, l - 1) => [1,2,3,4]
     * output: [5,6,7,1,2,3,4]
     *
     * Time: O(N)  Space: (1)
     */
    public void rotate3(int[] nums, int k) {
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

    /************* Solution 4: In-place Cyclic Replacement **************/
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

        // TC3
        int[] arr3 = new int[]{-1,-100,3,99};
        solution.rotate(arr3, 3);
        print(arr2); // [-100,3,99,-1]
    }
}
