import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 217. Contains Duplicate (https://leetcode.com/problems/contains-duplicate/description/)
 */
public class ContainsDuplicate {

    /*********** Solution 1: HashSet ****************/
    /**
     * HashSet 空间换时间
     * Time: O(N)       Space: O(N)
     */
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int n : nums) {
            if (!set.add(n)) return true;
        }
        return false;
    }

    /*********** Solution 2: Arrays.sort in place ****************/
    /**
     * Sort 时间换空间
     * Time: O(NlogN)   Space: O(1)
     */
    public boolean containsDuplicate1(int[] nums) {
        Arrays.sort(nums);
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) return true;
        }
        return false;
    }
}

