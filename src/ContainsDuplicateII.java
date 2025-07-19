import java.util.HashMap;
import java.util.Map;

/**
 * 219. Contains Duplicate II (https://leetcode.com/problems/contains-duplicate-ii/description/)
 */
public class ContainsDuplicateII {

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
