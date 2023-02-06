import javafx.util.Pair;

import java.util.HashSet;
import java.util.Set;

/**
 * 416. Partition Equal Subset Sum (https://leetcode.com/problems/partition-equal-subset-sum/description/)
 */
public class PartitionEqualSubsetSum {

    /************* Solution 1: BFS 按照 nums 扩展 ***************/
    /**
     * BFS 按照 nums 扩展
     * Set<Pair<Integer, Integer>> 记录 nums[0:i] 分成俩个 subsets 的和的情况
     * 并分别记录将 nums[i] 更新给 left 和 right 的新的 subsets 的和的情况，更新给 next
     *
     * Time: O(2^N)   Space: O(2^N)
     */
    public boolean canPartition1(int[] nums) {
        Set<Pair<Integer, Integer>> curr = new HashSet<>();
        curr.add(new Pair<>(nums[0], 0));
        for (int i = 1; i < nums.length; i++) {
            Set<Pair<Integer, Integer>> next = new HashSet<>();
            for (Pair<Integer, Integer> partition : curr) {
                next.add(new Pair<>(partition.getKey() + nums[i], partition.getValue()));
                next.add(new Pair<>(partition.getKey(), partition.getValue() + nums[i]));
            }
            curr = next;
        }
        for (Pair<Integer, Integer> partition : curr) {
            if (partition.getKey().equals(partition.getValue())) return true;
        }
        return false;
    }

    /************** Solution 2: 2D DP - 不受邻居限制的 背包问题 *******************/
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
     * Time: O(S X N)   Space: O(S)
     * where S is the sum of nums, N is length os nums
     */
    public boolean canPartition(int[] nums) {
        // 统计 sum，奇数直接 false，偶数用于确定 dp 列数
        int total = 0;
        for (int n : nums) total += n;
        if (total % 2 == 1) return false;

        boolean[] dp = new boolean[total / 2 + 1];
        dp[0] = true;
        for (int n : nums) {
            // 自右向左，避免 j-n < 0 超出范围，以及 dp[j] 已经被修改
            for (int j = dp.length - 1; j >= n; j--) {
                dp[j] |= dp[j - n]; // 注意！！！|| 和 | 在 boolean 运算时的区别！！！
            }
        }
        return dp[total / 2];
    }

    public static void main(String[] args) {
        PartitionEqualSubsetSum solution = new PartitionEqualSubsetSum();
        System.out.println(solution.canPartition(new int[]{1,5,5,11})); // true;
        System.out.println(solution.canPartition(new int[]{1,2,3,5})); // false;
    }
}
