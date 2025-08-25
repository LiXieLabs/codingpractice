/**
 * 4. Median of Two Sorted Arrays (https://leetcode.com/problems/median-of-two-sorted-arrays/description/)
 */
public class MedianOfTwoSortedArrays {

    /******************* Solution 1: Divide & Conquer *******************/
    /**
     *
     * Space Complexity: O(1)  Time Complexity: O(log(M + N))
     */
    public double findMedianSortedArrays1(int[] nums1, int[] nums2) {
        int l1 = nums1.length, l2 = nums2.length;
        int k1 = (l1 + l2 + 1) / 2, k2 = (l1 + l2 + 2) / 2;
        return (find(nums1, nums2, 0, nums1.length - 1, 0, nums2.length - 1, k1)
                + find(nums1, nums2, 0, nums1.length - 1, 0, nums2.length - 1, k2)) / 2.0;
    }

    public double find(int[] nums1, int[] nums2, int l1, int r1, int l2, int r2, int k) {
        // Base Cases
        if (l1 > r1) return nums2[l2 + k - 1];
        if (l2 > r2) return nums1[l1 + k - 1];
        if (k == 1) return Math.min(nums1[l1], nums2[l2]);

        int v1 = l1 + k / 2 - 1 <= r1 ? nums1[l1 + k / 2 - 1] : Integer.MAX_VALUE;
        int v2 = l2 + k / 2 - 1 <= r2 ? nums2[l2 + k / 2 - 1] : Integer.MAX_VALUE;

        if (v1 < v2) {
            return find(nums1, nums2, l1 + k / 2, r1, l2, Math.min(l2 + k / 2, r2), k - k / 2);
        } else {
            return find(nums1, nums2, l1, Math.min(l1 + k / 2, r1), l2 + k / 2, r2, k - k / 2);
        }
    }

    /******************* Solution 2: Ultimate Binary Search by Cut *******************/
    /**
     * 可扩展为 find any kth element in 2 sorted array!
     * (1) ensure nums1 is the SMALLER array (swap if necessary).
     * (2) binary search on the nums1's slots
     * (3) calculate corresponding partition in the larger array nums2 by k
     * (4) check if partition is valid (all left elements <= right elements)
     * (5) adjust search range based on comparison
     * 
     * Space Complexity: O(1)  Time Complexity: O(log(min(M, N))
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int l1 = nums1.length, l2 = nums2.length;
        if (l1 > l2) return findMedianSortedArrays(nums2, nums1); // 一个优化！！！不然 TC4 也会 ArrayIndexOutOfBoundsException！！！
        int k = (l1 + l2 + 1) / 2; // ⚠️注意⚠️ 必须 +1，不然有 ArrayIndexOutOfBoundsException 风险！！！
        int lo = 0, hi = l1; // 注意！！！标记的是slot，故不是l1-1
        while (lo <= hi) {
            int c1 = (lo + hi) / 2;
            int c2 = k - c1;
            int L1 = c1 - 1 >= 0 ? nums1[c1 - 1] : Integer.MIN_VALUE;
            int R1 = c1 < l1 ? nums1[c1] : Integer.MAX_VALUE;
            int L2 = c2 - 1 >= 0 ? nums2[c2 - 1] : Integer.MIN_VALUE;
            int R2 = c2 < l2 ? nums2[c2] : Integer.MAX_VALUE;
            if (L1 > R2) {
                hi = c1 - 1;
            } else if (L2 > R1) {
                lo = c1 + 1;
            } else {
                return (l1 + l2) % 2 == 0 ? (Math.max(L1, L2) + Math.min(R1, R2)) / 2.0 : Math.max(L1, L2);
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        MedianOfTwoSortedArrays solution = new MedianOfTwoSortedArrays();
        System.out.println(solution.findMedianSortedArrays(new int[]{1,3,5,7}, new int[]{2,4,6})); // 4.0
        System.out.println(solution.findMedianSortedArrays(new int[]{1,2}, new int[]{3,4})); // 2.5
        System.out.println(solution.findMedianSortedArrays(new int[]{1,3}, new int[]{2})); // 2.0
        System.out.println(solution.findMedianSortedArrays(new int[]{2}, new int[]{})); // 2.0
    }
}
