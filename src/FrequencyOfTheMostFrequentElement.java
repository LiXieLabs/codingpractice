import java.util.Arrays;

/**
 * 1838. Frequency of the Most Frequent Element (https://leetcode.com/problems/frequency-of-the-most-frequent-element/description/)
 */
public class FrequencyOfTheMostFrequentElement {

    // input size n <= 10^5, 则 TC = O(N) or O(NlogN) !!!

    /************* Solution 1: Sliding Window in Sorted Array ****************/
    /**
     * 在 sorted array 中，如果 nums[r] 是最终的 target，则它左边紧挨着的元素都是 candidates
     * 维护一个 nums[l:r] inclusive 窗口，使得:
     *     max(nums[i:j]) * windowSize - sum(nums[i:j]) <= k
     *  => nums[r] * (r - l + 1) - sum(nums[i:j]) <= k
     *
     * Time: O(NlogN + N) = O(NlogN)   Space: O(1)
     */
    public int maxFrequency(int[] nums, int k) {
        int l = 0, maxLen = 0;
        long curSum = 0;
        Arrays.sort(nums);
        for (int r = 0; r < nums.length; r++) {
            curSum += nums[r];
            // ⚠️注意⚠️只有 curSum 是 long 没用！nums[r] * (r - l + 1) 也可能很大！！！
            while ((long) nums[r] * (r - l + 1) - curSum > k) {
                curSum -= nums[l++];
            }
            maxLen = Math.max(maxLen, r - l + 1);
        }
        return maxLen;
    }
}
