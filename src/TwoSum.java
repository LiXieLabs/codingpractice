import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 1. Two Sum (https://leetcode.com/problems/two-sum/description/)
 */
public class TwoSum {

    /************** Solution 1: HashMap *******************/
    /**
     * Time: O(N) Space: O(N)
     */
    public int[] twoSum(int[] nums, int target) {
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                if (map.containsKey(target - nums[i])) {
                    return new int[]{map.get(target - nums[i]), i};
                }
                map.put(nums[i], i);
            }
            return new int[]{-1, -1};
    }

    public static void main(String[] args) {
        TwoSum solution = new TwoSum();
        System.out.println(Arrays.toString(solution.twoSum(new int[]{3,2,4}, 6))); // {1,2}
        System.out.println(Arrays.toString(solution.twoSum(new int[]{3,3}, 6))); // {0,1}
    }

}
