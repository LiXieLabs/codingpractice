import java.util.HashSet;
import java.util.Set;

/**
 * 41. First Missing Positive (https://leetcode.com/problems/first-missing-positive/description/)
 */
public class FirstMissingPositive {

    /************ Solution 1: O(N) Time + O(N) Space ***********/
    /**
     * Turn input into Positive Integer Set s
     * Then largest possible missing positive is s.size() + 1 (when s is [1,2,3,...,n] => 1st missing is n+1)
     */
    public int firstMissingPositive1(int[] nums) {
        Set<Integer> positives = new HashSet<>();
        for (int n : nums) {
            if (n > 0) positives.add(n);
        }
        for (int i = 1; i <= positives.size(); i++) {
            if (!positives.contains(i)) return i;
        }
        return positives.size() + 1;
    }

    /*********** Solution 2: O(N) Time + O(1) Space ***********/
    /**
     * 空间优化的关键：利用当前input空间标记正整数出现的情况
     * 因为candidate是[1, nums.size()+1], nums index是[0, nums.size()],
     * 用nums index标记对应数字出现过没有
     * Optimization 1: 找到一个candidate就和对应的index的number swap直到不是candidate了
     */
    public int firstMissingPositive2(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            /**
             * 本来应该是:
             * while (0 < nums[i] && nums[i] <= nums.length && nums[i] - 1 != i && nums[nums[i] - 1] != nums[i])
             * 1. nums[i] - 1 != i 表示当前位置正整数值是不是当前index值
             * 2. nums[nums[i] - 1] != nums[i] 是防止 [1,1] 陷入死循环
             * 实际上 nums[i] - 1 != i 可以被省略，因为 2. 满足，则 1. 一定满足
             */
            // if nums[i] is candidate & nums[nums[i]-1]还没被标记上
            while (0 < nums[i] && nums[i] <= nums.length && nums[nums[i]-1] != nums[i]) {
                // 交换值直到不是candidate或者对应位置已经被标记过了
                int temp = nums[nums[i]-1];
                nums[nums[i]-1] = nums[i];
                nums[i] = temp;
            }
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] - 1 != i) return i + 1;
        }
        return nums.length + 1;
    }

    /*********** Solution 3: O(N) Time + O(1) Space ***********/
    /**
     * 空间优化的关键：利用当前input空间标记正整数出现的情况
     * 因为candidate是[1, nums.size()+1], nums index是[0, nums.size()],
     * 用nums index标记对应数字出现过没有
     * Optimization 2: 找到一个candidate就把对应的index的number标记为负数
     * 需要预处理: 将<=0的数赋值为1+判断本身是否有1
     */
    public int firstMissingPositive(int[] nums) {
        // 预处理: 将 <=0 的数赋值为 1 + 判断本身是否有 1
        boolean containsOne = false;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) containsOne = true;
            if (nums[i] <= 0) nums[i] = 1;
        }
        if (!containsOne) return 1;

        for (int i = 0; i < nums.length; i++) {
            int val = Math.abs(nums[i]);
            if (0 < val && val <= nums.length && nums[val - 1] > 0) {
                nums[val-1] *= -1;
            }
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) return i + 1;
        }
        return nums.length + 1;
    }

    public static void main(String[] args) {
        FirstMissingPositive solution = new FirstMissingPositive();
        System.out.println(solution.firstMissingPositive(new int[]{1,2,0,1,-5,3,4})); // 5
        System.out.println(solution.firstMissingPositive(new int[]{1,2,0,1,-5,2,4})); // 3
        System.out.println(solution.firstMissingPositive(new int[]{-5,0})); // 1
        System.out.println(solution.firstMissingPositive(new int[]{3,4,-1,1})); // 2
        System.out.println(solution.firstMissingPositive(new int[]{1,1})); // 2
    }
}
