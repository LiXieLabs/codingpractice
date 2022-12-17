/**
 * 152. Maximum Product Subarray (https://leetcode.com/problems/maximum-product-subarray/description/)
 */
public class MaximumProductSubarray {

    /************* Solution 1: 1D DP ****************/
    /**
     * 类似 Kadane's Algo 计算最大子数列和
     * curMax是截止到包含当前位置的局部最大值
     * curMin是截止到包含当前未知的局部最小值
     * curMax * n <= 0，则归 1，表示甩掉之前的子数列，重新统计
     *
     * Time: O(N)   Space: O(1)
     */
    public int maxProduct(int[] nums) {
        int res = nums[0], curMax = 1, curMin = 1;
        for (int n : nums) {
            int nexMax = Math.max(curMax * n, curMin * n);
            curMin = Math.min(curMax * n, curMin * n);
            res = Math.max(res, nexMax);
            curMax = Math.max(nexMax, 1);
        }
        return res;
    }

    public static void main(String[] args) {
        MaximumProductSubarray solution = new MaximumProductSubarray();
        System.out.println(solution.maxProduct(new int[]{2,3,-2,-2,4}));
        System.out.println(solution.maxProduct(new int[]{-2,0,-1}));
        System.out.println(solution.maxProduct(new int[]{-2,-3,-4}));
        System.out.println(solution.maxProduct(new int[]{-2}));
        System.out.println(solution.maxProduct(new int[]{-2,-3,-4}));
    }
}
