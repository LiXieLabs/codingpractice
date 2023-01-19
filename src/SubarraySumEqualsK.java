import java.util.HashMap;
import java.util.Map;

/**
 * 560. Subarray Sum Equals K (https://leetcode.com/problems/subarray-sum-equals-k/description/)
 */
public class SubarraySumEqualsK {

    /******************* Solution 1: Prefix Sum *********************/
    /**
     * Time: O(N^2)  Space: O(N)
     */
    public int subarraySum1(int[] nums, int k) {
        int[] sum = new int[nums.length + 1];
        for (int i = 1; i < sum.length; i++) {
            sum[i] = sum[i - 1] + nums[i - 1];
        }
        int count = 0;
        for (int i = 0; i < sum.length; i++) {
            for (int j = i + 1; j < sum.length; j++) {
                if (sum[j] - sum[i] == k) count++;
            }
        }
        return count;
    }

    /******************* Solution 2: Calculate Sum on-the-go *********************/
    /**
     * Time: O(N^2)  Space: O(1)
     */
    public int subarraySum2(int[] nums, int k) {
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                if (sum == k) count++;
            }
        }
        return count;
    }

    /******************* Solution 3: HashMap 记录 Prefix Sum *********************/
    /**
     * 类似 TwoSum
     * 此处是寻找 curSum - preSum == k
     * 再将 curSum 更新入保存 preSum : count 的 hashMap
     *
     * Time: O(N)  Space: O(N)
     */
    public int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>(); // key: sum, value: count of end index j, s.t. SUM[0,j] = sum
        int sum = 0, count = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (sum == k) count++; // 也可以初始map.put(0, 1) 然后删掉这行
            if (map.containsKey(sum - k)) count += map.get(sum - k);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return count;
    }

    public static void main(String[] args) {
        SubarraySumEqualsK solution = new SubarraySumEqualsK();
        System.out.println(solution.subarraySum(new int[]{1, 1, 1}, 2));
        System.out.println(solution.subarraySum(new int[]{1, 2, 3}, 3));
    }
}
