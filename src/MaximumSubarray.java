public class MaximumSubarray {

    /********** Solution 1: Kadane's Algo  ***************/
    /**
     * https://zh.wikipedia.org/wiki/%E6%9C%80%E5%A4%A7%E5%AD%90%E6%95%B0%E5%88%97%E9%97%AE%E9%A2%98
     * Kadane's algo 解决最大连续子数列和的问题，此处是求股价差值数列的最大连续子数列和
     */
    public int maxSubArray(int[] nums) {
        int maxSum = nums[0], curMax = nums[0];
        for (int i = 1; i < nums.length; i++) {
            curMax = Math.max(curMax + nums[i], nums[i]);
            maxSum = Math.max(maxSum, curMax);
        }
        return maxSum;
    }

    public static void main(String[] args) {
        MaximumSubarray solution = new MaximumSubarray();
        System.out.println(solution.maxSubArray(new int[]{-2,1,-3,4,-1,2,1,-5,4}));
        System.out.println(solution.maxSubArray(new int[]{1}));
        System.out.println(solution.maxSubArray(new int[]{5,4,-1,7,8}));
    }
}
