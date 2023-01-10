import java.util.HashMap;
import java.util.Map;

/**
 * 992. Subarrays with K Different Integers (https://leetcode.com/problems/subarrays-with-k-different-integers/description/)
 */
public class SubarraysWithKDifferentIntegers {

    /*********** Solution 1: Sliding Window to find exact k window [l,r] & increment l until < k **********/
    /**
     * Intuitive:
     * 遍历 r，每次先把 r 加入 counter，右移 l 直到 <= k
     * 如果 == k, 则更新 res，直到 l 右移再次至 < k
     *
     * Time: Worst O(N^2)   Space: O(K)
     */
    public int subarraysWithKDistinct1(int[] nums, int k) {
        Map<Integer, Integer> counter = new HashMap<>();
        int l = 0, res = 0;
        for (int r = 0; r < nums.length; r++) {
            counter.put(nums[r], counter.getOrDefault(nums[r], 0) + 1);
            while (counter.size() > k) {
                counter.put(nums[l], counter.get(nums[l]) - 1);
                if (counter.get(nums[l]) == 0) counter.remove(nums[l]);
                l++;
            }
            if (counter.size() == k) {
                Map<Integer, Integer> tmpCounter = new HashMap<>();
                for (int tmpLeft = l; tmpLeft <= r - k + 1; tmpLeft++) {
                    res++;
                    tmpCounter.put(nums[tmpLeft], tmpCounter.getOrDefault(nums[tmpLeft], 0) + 1);
                    if (tmpCounter.get(nums[tmpLeft]).equals(counter.get(nums[tmpLeft]))) break;
                }
            }
        }
        return res;
    }

    /*********** Solution 2: Sliding Window to find (<= k distinct) - (<= k - 1 distinct) **********/
    /**
     * Intuitive:
     * 遍历 r，每次先把 r 加入 counter，右移 l 直到 <= k
     * 如果 == k, 则更新 res，直到 l 右移再次至 < k
     *
     * Time: O(N)   Space: O(K)
     */
    public int subarraysWithKDistinct(int[] nums, int k) {
        return atMostKDistinct(nums, k) - atMostKDistinct(nums, k - 1);
    }

    private int atMostKDistinct(int[] nums, int k) {
        Map<Integer, Integer> counter = new HashMap<>();
        int l = 0, res = 0;
        for (int r = 0; r < nums.length; r++) {
            counter.put(nums[r], counter.getOrDefault(nums[r], 0) + 1);
            while (counter.size() > k) {
                counter.put(nums[l], counter.get(nums[l]) - 1);
                if (counter.get(nums[l]) == 0) counter.remove(nums[l]);
                l++;
            }
            // 以 l 开头有 r - l + 1 个 subarrays，累计入 res
            res += r - l + 1;
        }
        return res;
    }

    public static void main(String[] args) {
        SubarraysWithKDifferentIntegers solution = new SubarraysWithKDifferentIntegers();
        System.out.println(solution.subarraysWithKDistinct(new int[]{1,2,1,2,3}, 2)); // 7
        System.out.println(solution.subarraysWithKDistinct(new int[]{1,2,1,3,4}, 3)); // 3
        System.out.println(solution.subarraysWithKDistinct(new int[]{1,2,1,2,3}, 1)); // 5
    }
}
