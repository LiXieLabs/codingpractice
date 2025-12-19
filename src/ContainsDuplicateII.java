import java.util.HashMap;
import java.util.Map;

/**
 * 219. Contains Duplicate II (https://leetcode.com/problems/contains-duplicate-ii/description/)
 */
public class ContainsDuplicateII {

    /************ Solution 1: Sliding Window size = k + Map as counter **************/
    /**
     * Time: O(N)  Space: O(N)
     */
    public boolean containsNearbyDuplicate1(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            // 更新右边界
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
            // 更新左边界
            if (i - k - 1 >= 0) map.put(nums[i - k - 1], map.get(nums[i - k - 1]) - 1);
            // 判断
            if (map.get(nums[i]) > 1) return true;
        }
        return false;
    }

    /************ Solution 2: Map 只记录最后位置即可！更优解！！！ **************/
    /**
     * Time: O(N)  Space: O(N)
     */
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> lastSeen = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int j = lastSeen.getOrDefault(nums[i], -1);
            if (j >= 0 && i - j <= k) return true;
            lastSeen.put(nums[i], i);
        }
        return false;
    }

    public static void main(String[] args) {
        ContainsDuplicateII solution = new ContainsDuplicateII();
        System.out.println(solution.containsNearbyDuplicate(new int[]{1,2,3,1}, 3)); // true
        System.out.println(solution.containsNearbyDuplicate(new int[]{1,0,1,1}, 1)); // true
        System.out.println(solution.containsNearbyDuplicate(new int[]{1,2,3,1,2,3}, 2)); // false
    }
}
