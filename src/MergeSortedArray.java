import java.util.Arrays;

/**
 * 88. Merge Sorted Array (https://leetcode.com/problems/merge-sorted-array/description/)
 */
public class MergeSortedArray {

    /*************** Solution 1: One time + Two Pointers **************/
    /**
     * 从后往前！！！
     * Time: O(M + N)   Space: O(1)
     */
    public void merge1(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1, j = n - 1, k = m + n - 1;
        while (k >= 0) {
            nums1[k--] = (j < 0 || i >= 0 && nums1[i] >= nums2[j]) ? nums1[i--] : nums2[j--];
        }
    }

    /*************** Solution 2: 更好理解的 Solution 1 **************/
    /**
     * Time: O(M + N)   Space: O(1)
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1, j = n - 1, k = m + n - 1;
        while (k >= 0) {
            int n1 = i >= 0 ? nums1[i] : Integer.MIN_VALUE;
            int n2 = j >= 0 ? nums2[j] : Integer.MIN_VALUE;
            if (n1 > n2) {
                nums1[k--] = nums1[i--];
            } else {
                nums1[k--] = nums2[j--];
            }
        }
    }

    public static void main(String[] args) {
        MergeSortedArray solution = new MergeSortedArray();
        int[] nums1, nums2;

        // TC1 -> [1, 2, 2, 3, 5, 6]
        nums1 = new int[]{1,2,3,0,0,0};
        nums2 = new int[]{2,5,6};
        solution.merge(nums1, 3, nums2, 3);
        System.out.println(Arrays.toString(nums1));

        // TC2 -> [1]
        nums1 = new int[]{1};
        nums2 = new int[]{};
        solution.merge(nums1, 1, nums2, 0);
        System.out.println(Arrays.toString(nums1));

        // TC3 -> [1]
        nums1 = new int[]{0};
        nums2 = new int[]{1};
        solution.merge(nums1, 0, nums2, 1);
        System.out.println(Arrays.toString(nums1));
    }
}
