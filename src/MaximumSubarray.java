/**
 * 53. Maximum Subarray (https://leetcode.com/problems/maximum-subarray/description/)
 */
public class MaximumSubarray {

    /**
     * 53. Maximum Subarray (https://leetcode.com/problems/maximum-subarray/description/)
     * 121. Best Time to Buy and Sell Stock (https://leetcode.com/problems/best-time-to-buy-and-sell-stock/)
     */

    /********** Solution 1: Kadane's Algo - DP  ***************/
    /**
     * https://zh.wikipedia.org/wiki/%E6%9C%80%E5%A4%A7%E5%AD%90%E6%95%B0%E5%88%97%E9%97%AE%E9%A2%98
     * Kadane's algo 解决最大连续子数列和的问题
     * 类似 BestTimeToBuyAndSellStock.java
     *
     * Time: O(N) Space: O(1)
     */
    public int maxSubArray1(int[] nums) {
        int maxSum = nums[0], curMax = nums[0];
        for (int i = 1; i < nums.length; i++) {
            // 先更新当前最大和的子数列，前面+当前 or 只取当前，取更大的。
            curMax = Math.max(curMax + nums[i], nums[i]);
            // 更新全局最优解
            maxSum = Math.max(maxSum, curMax);
        }
        return maxSum;
    }

    /********** Solution 2: Follow up - Divider and Conquer  ***************/
    /**
     * Time: T(N) = 2T(N/2) + O(N) = O(NlogN)   Space: O(logN)
     */
    int[] nums;
    public int maxSubArray(int[] nums) {
        this.nums = nums;
        return divideAndConquer(0, nums.length - 1);
    }

    private int divideAndConquer(int l, int r) {
        if (l > r) return Integer.MIN_VALUE;
        if (l == r) return nums[l];
        int mid = (l + r) >> 1;

        // Divide
        int maxLeft = divideAndConquer(l, mid);
        int maxRight = divideAndConquer(mid + 1, r);

        // Conquer - part 1
        // calculate sum and maxSum from mid to begin (including mid)
        int maxMidToBegin = Integer.MIN_VALUE;
        int curSum = 0;
        for (int i = mid; i >= l; i--) {
            curSum += nums[i];
            maxMidToBegin = Math.max(maxMidToBegin, curSum);
        }
        // calculate sum and maxSum from mid to end (including mid + 1)
        int maxMidToEnd = Integer.MIN_VALUE;
        curSum = 0;
        for (int i = mid + 1; i <= r; i++) {
            curSum += nums[i];
            maxMidToEnd = Math.max(maxMidToEnd, curSum);
        }
        // get max sum including mid
        int maxIncludeMid = maxMidToBegin + maxMidToEnd;

        // Conquer - part 2
        return Math.max(maxIncludeMid, Math.max(maxLeft, maxRight));
    }

    public static void main(String[] args) {
        MaximumSubarray solution = new MaximumSubarray();
        System.out.println(solution.maxSubArray(new int[]{-2,1,-3,4,-1,2,1,-5,4})); // 6
        System.out.println(solution.maxSubArray(new int[]{1})); // 1
        System.out.println(solution.maxSubArray(new int[]{5,4,-1,7,8})); // 23
    }
}
