import java.util.ArrayList;
import java.util.List;

/**
 * 300. Longest Increasing Subsequence (https://leetcode.com/problems/longest-increasing-subsequence/description/)
 */
public class LongestIncreasingSubsequence {

    /************ Solution 1: 1D DP **********************/
    /**
     * dp[i] = max(1, dp[j] + 1) for j = [0,i)
     *
     * Time: O(N^2) Space: O(N)
     */
    public int lengthOfLIS1(int[] nums) {
        int[] dp = new int[nums.length];
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    /************ Solution 2: Greedy Algo + Binary Search **********************/
    /**
     * Time: O(NlogN) Space: O(N)
     */
    public int lengthOfLIS(int[] nums) {
        ArrayList<Integer> sub = new ArrayList<>();
        sub.add(nums[0]);

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > sub.get(sub.size() - 1)) {
                sub.add(nums[i]);
            } else {
                // Find the first element in sub that is greater than or equal to num
                int j = binarySearch(sub, nums[i]);
                sub.set(j, nums[i]);
            }
        }

        return sub.size();
    }

    private int binarySearch(List<Integer> sub, int target) {
        int lo = 0, hi = sub.size() - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (sub.get(mid) < target) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo;
    }

    public static void main(String[] args) {
        LongestIncreasingSubsequence solution = new LongestIncreasingSubsequence();
        System.out.println(solution.lengthOfLIS(new int[]{2,5,7,1,3,4,6})); // 4
        System.out.println(solution.lengthOfLIS(new int[]{1,3,6,7,9,4,10,5,6})); // 6
        System.out.println(solution.lengthOfLIS(new int[]{10,9,2,5,3,7,101,18})); // 4
        System.out.println(solution.lengthOfLIS(new int[]{0,1,0,3,2,3})); // 4
        System.out.println(solution.lengthOfLIS(new int[]{7,7,7,7,7,7,7})); // 1
    }
}
