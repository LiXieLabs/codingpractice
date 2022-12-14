import java.util.HashMap;
import java.util.Map;

/**
 * 523. Continuous Subarray Sum (https://leetcode.com/problems/continuous-subarray-sum/description/)
 */
public class ContinuousSubarraySum {

    /************* Solution 1: Prefix Sum + 2 For Loops (TLE) ****************/
    /**
     * Time: O(N^2)   Space: O(N)
     */
    public boolean checkSubarraySum1(int[] nums, int k) {
        // calculate prefix sums
        int[] prefixSum = new int[nums.length + 1];
        for (int i = 1; i < prefixSum.length; i++) {
            prefixSum[i] = prefixSum[i - 1] + nums[i - 1];
        }
        // sum(nums[i:j]) = prefixSum[j] - prefixSum[i] (j - i >= 2)
        for (int i = 0; i < prefixSum.length - 2; i++) {
            for (int j = i + 2; j < prefixSum.length; j++) {
                if ((prefixSum[j] - prefixSum[i]) % k == 0) return true;
            }
        }
        return false;
    }

    /************* Solution 2: Prefix Sum + 类似 2 Sum ****************/
    /**
     * (prefixSum[j] - prefixSum[i]) % k == 0
     * ====> prefixSum[j] % k == prefixSum[i] % k
     * 每一步的 prefixSum[i] % k，和对应的 i 记录在 hashmap 中，
     * 到达 j 时，查找 prefixSum[J] % k，
     * 如果 prefixSum[i] % k == prefixSum[j] % k 且 j - i >= 2，return true
     *
     * 类似 2 Sum，区别是
     * （1）不是针对 num 本身，而是针对 prefixSum % k
     * （2）不是在 map 中找 target 互补数，而是找相等的数
     *
     * Time: O(N)   Space: O(N)
     */
    public boolean checkSubarraySum(int[] nums, int k) {
        Map<Integer, Integer> remainderToIndex = new HashMap<>();
        remainderToIndex.put(0, -1);
        int prefixSum = 0;
        for (int i = 0; i < nums.length; i++) {
            prefixSum += nums[i];
            if (i - remainderToIndex.getOrDefault(prefixSum % k, i) >= 2) return true;
            remainderToIndex.putIfAbsent(prefixSum % k, i);
        }
        return false;
    }

    public static void main(String[] args) {
        ContinuousSubarraySum solution = new ContinuousSubarraySum();
        System.out.println(solution.checkSubarraySum(new int[]{23,2,4,6,7}, 6));
        System.out.println(solution.checkSubarraySum(new int[]{23,2,6,4,7}, 6));
        System.out.println(solution.checkSubarraySum(new int[]{23,2,6,4,7}, 13));
    }
}
