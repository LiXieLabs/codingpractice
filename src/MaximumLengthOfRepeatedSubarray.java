/**
 * 718. Maximum Length of Repeated Subarray (https://leetcode.com/problems/maximum-length-of-repeated-subarray/description/)
 */
public class MaximumLengthOfRepeatedSubarray {

    /************** Solution 1: 2D DP *****************/
    /**
     * dp[i][j] 表示 nums[:i+1] 和 nums[:j+1] 包含 nums1[i] 和 nums2[j] 最长的 common subarray
     * dp[i][j] =
     * (1) if nums1[i] == nums2[j] => dp[i-1][j-1] + 1
     * (2) else => 0
     *
     * Time: O(M X N)   Space: O(N) as optimized to 1D rolling DP
     */
    public int findLength(int[] nums1, int[] nums2) {
        int r = nums1.length, c = nums2.length, res = 0;
        int[] dp = new int[c];
        for (int i = 0; i < r; i++) {
            int pre = 0;
            for (int j = 0; j < c; j++) {
                int tmp = dp[j];
                dp[j] = nums1[i] != nums2[j] ? 0 : pre + 1;
                res = Math.max(res, dp[j]);
                pre = tmp;
            }
        }
        return res;
    }

    // TODO: Multiple Solution => Sliding Window / KMP / Binary Search w/ Rolling Hashing

    public static void main(String[] args) {
        MaximumLengthOfRepeatedSubarray solution = new MaximumLengthOfRepeatedSubarray();
        System.out.println(solution.findLength(new int[]{1,2,3,2,1}, new int[]{3,2,1,4,7}));
        System.out.println(solution.findLength(new int[]{0,0,0,0,0}, new int[]{0,0,0,0,0}));
    }
}
