import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ThreeSum {

    /************* Solution 1: Two Pointers similar as 2SumII *****************/
    /**
     * 可modify input array情况下最优解
     * 等同于
     * 167. Two Sum II - Input Array Is Sorted (https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/description/)
     *
     * Time: O(NlogN + N^2) = O(N^2)
     * Space: O(1)
     */
    public List<List<Integer>> threeSum1(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        int target = 0;
        for (int i = 0; i < nums.length - 2 && nums[i] <= target; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            int j = i + 1, k = nums.length - 1;
            while (j < k) {
                if (nums[i] + nums[j] + nums[k] <= target) {
                    if (nums[i] + nums[j] + nums[k] == target) {
                        res.add(Arrays.asList(nums[i], nums[j], nums[k]));
                    }
                    do {
                        j++;
                    } while (j < nums.length - 1 && nums[j] == nums[j - 1]);
                } else {
                    do {
                        k--;
                    } while (k > j && nums[k] == nums[k + 1]);
                }
            }
        }
        return res;
    }

    /************* Solution 2: HashSet similar as 2Sum *****************/
    /**
     * follow up: 不允许modify input array和sort
     * 等同于
     * 1. Two Sum (https://leetcode.com/problems/two-sum/description/)
     *
     * Time: O(NlogN + N^2) = O(N^2)
     * Space: O(1)
     */
    public List<List<Integer>> threeSum(int[] nums) {
        Set<List<Integer>> res = new HashSet<>();
        Set<Integer> visited1stNum = new HashSet<>();
        int target = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            // 跳过重复的 nums[i]
            if (!visited1stNum.add(nums[i])) continue;
            // 对于 nums[i+1:] 做 newTarget = target - nums[i] 的 2Sum
            Set<Integer> seenRemains = new HashSet<>();
            for (int j = i + 1; j < nums.length; j++) {
                int remain = target - nums[i] - nums[j];
                // 如果当前nums[jm]是之前看到过的nums[i]和nums[jn]的remain，则计入结果
                if (seenRemains.contains(nums[j])) {
                    List<Integer> cur = Arrays.asList(nums[i], nums[j], remain);
                    Collections.sort(cur);
                    res.add(cur);
                }
                // 将nums[i]和nums[jm]的remain写入待配对的set
                seenRemains.add(remain);
            }
        }
        return new ArrayList<>(res);
    }

    public static void main(String[] args) {
        ThreeSum solution = new ThreeSum();
        System.out.println(solution.threeSum(new int[]{-1,0,1,2,-1,-4}));
        System.out.println(solution.threeSum(new int[]{0,1,1}));
        System.out.println(solution.threeSum(new int[]{0,0,0}));
    }

}
