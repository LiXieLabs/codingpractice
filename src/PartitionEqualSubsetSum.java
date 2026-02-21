/**
 * 416. Partition Equal Subset Sum (https://leetcode.com/problems/partition-equal-subset-sum/description/)
 */
public class PartitionEqualSubsetSum {

    /**
     * 题目可理解为：Can we choose some numbers that add up to Sum/2?
     * 本质是：O/1 Backpack Problem - Decision Version
     *
     * Partition Problem	   Knapsack Interpretation
     * nums[i]	               item weight
     * target = S/2	           bag capacity
     * pick or not pick	0/1    choice
     * exact sum required	   subset sum
     */

    /**
     *                        LC416	           LC39
     * 背包类型	              0/1 背包	      完全背包
     * 目标	                  是否存在	      列出所有
     * 递归含义	            处理第 i 个元素	选择下一个元素
     *                     （index-driven） （combination-driven）
     * 递归结构	             二叉决策	     for 循环枚举
     * 是否必须遍历所有候选	❌ 不需要	       ✅ 必须
     */

    /************* Solution 1: Top-Down Recur + Memoization ***************/
    /**
     * Time: O(N * SUM/2) = O(N * SUM)
     * Space: O(N * SUM/2) by memo + O(N) by recur stack = O(N * SUM)
     */
    int[] nums;
    Boolean[][] memo; // null = unknown, true/false computed

    public boolean canPartition1(int[] nums) {
        int sum = 0;
        for (int x : nums) sum += x;
        if ((sum & 1) == 1) return false;

        this.nums = nums;
        sum /= 2;
        memo = new Boolean[nums.length][sum + 1];
        return recur(0, sum);
    }

    private boolean recur(int i, int target) {
        // base case
        if (target == 0) return true;
        if (i == nums.length || target < 0) return false;

        // memo found
        if (memo[i][target] != null) return memo[i][target];

        // ans = exclude i || include i
        boolean ans = recur(i + 1, target) || recur(i + 1, target - nums[i]);
        return memo[i][target] = ans;
    }

    /************** Solution 2: 2D DP => 1D DP - 不受邻居限制的 背包问题 *******************/
    /**
     * 不受邻居限制的 背包问题
     * 也有点类似 coin change，但是不能重复利用某一项
     *
     * dp[i][j] 表示从 nums[0:i] 包含 i 中取一些数，能否和为 j
     * base case: dp[0][0] == true，取 0 个数，和为 0
     * result: dp[nums.length-1][sum/2]
     *
     * 对于 dp[i][j]
     * (1) 如果求和包含 nums[i]，dp[i][j] = dp[i-1][j-nums[i]]
     * (2) 如果求和不包含 nums[i]，dp[i][j] = dp[i-1][j]
     *
     * 则 dp[i][j] = dp[i-1][j-nums[i]] || dp[i-1][j]
     *
     * Time: O(N * SUM/2) = O(N * SUM)
     * Space: O(SUM/2) by dp = O(SUM)
     */
    public boolean canPartition(int[] nums) {
        // 统计 sum，奇数直接 false，偶数用于确定 dp 列数
        int sum = 0;
        for (int x : nums) sum += x;
        if ((sum & 1) == 1) return false;

        sum /= 2;
        boolean[] dp = new boolean[sum + 1];
        dp[0] = true;
        for (int n : nums) {
            // 自右向左，避免 j-n < 0 超出范围，以及 dp[j] 已经被修改
            for (int j = sum; j >= n; j--) {
                dp[j] |= dp[j - n];
            }
        }
        return dp[sum];
    }

    public static void main(String[] args) {
        PartitionEqualSubsetSum solution = new PartitionEqualSubsetSum();
        System.out.println(solution.canPartition(new int[]{1,5,5,11})); // true;
        System.out.println(solution.canPartition(new int[]{1,2,3,5})); // false;
    }
}
