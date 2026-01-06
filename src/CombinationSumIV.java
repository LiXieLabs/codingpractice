import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 377. Combination Sum IV (https://leetcode.com/problems/combination-sum-iv/)
 */
public class CombinationSumIV {

    /************** Solution 1: 1D DP - Bottom Up *******************/
    /**
     * 比起 Combination Sum 系列，更接近 Coin Change!!!
     *
     * dp[s] denotes for sum = s, how many sequences are there.
     * dp[s] = dp[s - nums[i]] for each target - nums[i] >= 0
     *
     * 注意要疑问，顺序如何解决的？=>
     * nums=[1,2], target=3
     * dp[3] = dp[3-2] + dp[3-1]
     *       = (1,2) + (1,1,1)&(2,1)
     *
     * Time: O(Target x N)   Space: O(Target)
     */
    public int combinationSum4(int[] nums, int target) {
        Arrays.sort(nums);
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int s = 1; s <= target; s++) {
            for (int i = 0; i < nums.length && nums[i] <= s; i++) {
                dp[s] += dp[s - nums[i]];
            }
        }
        return dp[target];
    }

    /************* Solution 2: Recursive + Memo - Top Down ***************/
    /**
     * Time: O(Target x N)   Space: O(Target)
     */
    Map<Integer, Integer> memo;
    int[] nums;

    public int combinationSum42(int[] nums, int target) {
        Arrays.sort(nums);
        this.nums = nums;
        memo = new HashMap<>();
        memo.put(0, 1);
        return recur(target);
    }

    private int recur(int target) {
        if (!memo.containsKey(target)) {
            int count = 0;
            for (int i = 0; i < nums.length && nums[i] <= target; i++) {
                count += recur(target - nums[i]);
            }
            memo.put(target, count);
        }
        return memo.get(target);
    }

}
