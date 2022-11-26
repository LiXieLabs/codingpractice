import java.util.Arrays;

public class ThreeSumClosest {

    /***************** Solution 1: Two Pointers ***********************/
    /**
     * Same as:
     * 15. 3Sum (https://leetcode.com/problems/3sum/)
     * 只是因为不是查找 specific value，所以不能用 hashmap solution
     * 只能用 two pointers solution.
     *
     * Time: Sort + For-loop = O(NlogN) + O(N^2) = O(N^2)
     * Space: O(1) if by selection sort, O(logN) by built-in sort
     */
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int closestSum = nums[0] + nums[1] + nums[2];
        for (int i = 0; i < nums.length - 2; i++) {
            int j = i + 1, k = nums.length - 1;
            while (j < k) {
                int curSum = nums[i] + nums[j] + nums[k];
                if (Math.abs(curSum - target) < Math.abs(closestSum - target)) {
                    closestSum = curSum;
                }
                if (curSum < target) {
                    j++;
                } else if (curSum > target) {
                    k--;
                } else {
                    return target;
                }
            }
        }
        return closestSum;
    }

    public static void main(String[] args) {
        ThreeSumClosest solution = new ThreeSumClosest();
        System.out.println(solution.threeSumClosest(new int[]{-1,2,1,-4}, 1));
        System.out.println(solution.threeSumClosest(new int[]{0,0,0}, 1));
    }
}
