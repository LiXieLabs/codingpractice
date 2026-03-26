import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 368. Largest Divisible Subset (https://leetcode.com/problems/largest-divisible-subset/description/)
 */
public class LargestDivisibleSubset {

    /**
     * 300. Longest Increasing Subsequence (https://leetcode.com/problems/longest-increasing-subsequence/description/)
     * 368. Largest Divisible Subset (https://leetcode.com/problems/largest-divisible-subset/description/)
     */

    /************** Solution 1: 1D DP ****************/
    /**
     * Sort + LIS DP + Reconstruct
     * LIS DP:
     * 300. Longest Increasing Subsequence (https://leetcode.com/problems/longest-increasing-subsequence/description/)
     *
     * Time: Sort O(N) + LIS DP O(N^2) + Reconstruct O(N) = O(N^2)
     * Space: O(N)
     */
    public List<Integer> largestDivisibleSubset(int[] nums) {
        // sort
        Arrays.sort(nums);

        // dp
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);
        int[] pre = new int[nums.length];
        Arrays.fill(pre, -1);
        int k = 0;
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] % nums[j] == 0 && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    pre[i] = j;
                }
            }
            if (dp[i] > dp[k]) k = i;
        }

        // reconstruct
        List<Integer> res = new ArrayList<>();
        while (k >= 0) {
            res.add(nums[k]);
            k = pre[k];
        }
        Collections.reverse(res);
        return res;
    }
}
