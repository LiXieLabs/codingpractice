import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * 220. Contains Duplicate III (https://leetcode.com/problems/contains-duplicate-iii/description/)
 */
public class ContainsDuplicateIII {

    /**
     * 这道题本质是要在一个 sliding window k == indexDiff 里维护一个数据结构，可以
     * （1）排序，随时知道最大最小值，或者比当前值大和小的值
     * （2）索引，随时找到 i - k 那个值的位置，并且安全删除
     */

    /***************** Solution 1: Sliding Window + TreeSet (BST) ********************/
    /**
     * Time: O(Nlogk)   Space: O(k)
     * where k == indexDiff
     */
    public boolean containsNearbyAlmostDuplicate1(int[] nums, int indexDiff, int valueDiff) {
        // k-size sliding window, has [nums[i-k], nums[i-1]] at i, where k == indexDiff.
        TreeSet<Integer> window = new TreeSet<>();
        for (int i = 0; i < nums.length; i++) {
            // 求解对当前 nums[i]，window 内 (满足 indexDiff)，
            // 有没有满足 [nums[i] - t, nums[i] + t] 的值，where t == valueDiff.
            Integer minVal = window.ceiling(nums[i] - valueDiff);
            Integer maxVal = window.floor(nums[i] + valueDiff);
            if (window.contains(nums[i])
                    || minVal != null && minVal <= nums[i]
                    || maxVal != null && nums[i] <= maxVal) {
                return true;
            }
            // 更新 nums[i] 入 window
            window.add(nums[i]);
            // 维护 window，使得其再次满足 window size <= k
            // ！！！！！！注意！！！！！！！
            // 不必担心 window 内有多个 nums[i - indexDiff]，
            // 那样的话，在遍历到同一值第二次出现时，diff == 0，早已返回 true！！！
            if (window.size() > indexDiff) {
                window.remove(nums[i - indexDiff]);
            }
        }
        return false;
    }

    /***************** Solution 2: Bucket Sort ********************/
    /**
     * Suppose we have consecutive buckets covering the range of nums with each bucket a width of (t+1).
     * If there are two item with difference <= t, one of the two will happen:
     * (1) the two in the same bucket
     * (2) the two in neighbor buckets
     *
     * Time: O(N)   Space: O(min(N, k)) by the HashMap.
     * where k == indexDiff
     */
    public boolean containsNearbyAlmostDuplicate(int[] nums, int indexDiff, int valueDiff) {
        Map<Long, Long> buckets = new HashMap<>();
        long t = valueDiff + 1; // +1 为了避免 0 的情况除不了
        for (int i = 0; i < nums.length; i++) {
            long b = getId(nums[i], t);
            if (buckets.containsKey(b)
                    || buckets.containsKey(b + 1) && Math.abs(buckets.get(b + 1) - nums[i]) <= valueDiff
                    || buckets.containsKey(b - 1) && Math.abs(buckets.get(b - 1) - nums[i]) <= valueDiff) {
                return true;
            }
            buckets.put(b, (long) nums[i]);
            // ⚠️注意！！！可以安心删除 nums[i - indexDiff] 的整个 bucket，
            // 因为这个 bucket 里面还有其他数字的话，刚才遇到这个数字已经返回 true 了！！！
            if (buckets.size() > indexDiff) {
                buckets.remove(getId(nums[i - indexDiff], t));
            }
        }
        return false;
    }

    // ⚠️注意！！！普通整除是向零取整数，TC3 会 fail！！！
    private long getId(long n, long w) {
        return Math.floorDiv(n, w);
    }

    public static void main(String[] args) {
        ContainsDuplicateIII solution = new ContainsDuplicateIII();
        System.out.println(solution.containsNearbyAlmostDuplicate(new int[]{1,2,3,1}, 3, 0)); // true
        System.out.println(solution.containsNearbyAlmostDuplicate(new int[]{1,5,9,1,5,9}, 2, 3)); // false
        System.out.println(solution.containsNearbyAlmostDuplicate(new int[]{-3, 3}, 2, 4)); // false
    }
}
