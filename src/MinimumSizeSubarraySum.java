/**
 * 209. Minimum Size Subarray Sum (https://leetcode.com/problems/minimum-size-subarray-sum/description/)
 */
public class MinimumSizeSubarraySum {

    /************ Solution 1: Sliding Window *****************/
    /**
     * 维护 window 的条件是：
     * 向右移动 l 直到 l == r 或者 下一次移动会导致 curSum - nums[l] < target
     * 取反则为 while 条件：l < r && curSum - nums[l] >= target
     *
     * Time: O(N)   Space: O(1)
     */
    public int minSubArrayLen(int target, int[] nums) {
        int l = 0, curSum = 0, minLen = nums.length + 1;
        for (int r = 0; r < nums.length; r++) {
            curSum += nums[r];
            while (l < r && curSum - nums[l] >= target) {
                curSum -= nums[l];
                l++;
            }
            if (curSum >= target) {
                minLen = Math.min(minLen, r - l + 1);
            }
        }
        return minLen == nums.length + 1 ? 0 : minLen;
    }

    public static void main(String[] args) {
        MinimumSizeSubarraySum solution = new MinimumSizeSubarraySum();

        System.out.println(solution.minSubArrayLen(15, new int[]{1,2,3,4,5})); // 5

        System.out.println(solution.minSubArrayLen(7, new int[]{2,3,1,2,4,3})); // 2

        System.out.println(solution.minSubArrayLen(4, new int[]{1,4,4})); // 1

        System.out.println(solution.minSubArrayLen(11, new int[]{1,1,1,1,1,1,1,1})); // 0

    }
}
