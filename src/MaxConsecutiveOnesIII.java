/**
 * 1004. Max Consecutive Ones III (https://leetcode.com/problems/max-consecutive-ones-iii/description/)
 */
public class MaxConsecutiveOnesIII {

    /************** Solution 1: Sliding Window *******************/
    /**
     * flipped标记flip了几个0，r尽可能向右移动，遇到0则flip
     * l向右移动维护，当前flip的0不超过k个
     * 更新最大值，并继续右移r
     *
     * 最优解 same as
     * 487. Max Consecutive Ones II (https://leetcode.com/problems/max-consecutive-ones-ii/description/)
     */
    public int longestOnes(int[] nums, int k) {
        int flipped = 0, l = 0, maxLen = 0;
        for (int r = 0; r < nums.length; r++) {
            if (nums[r] == 0) {
                flipped++;
                while (flipped > k) {
                    if (nums[l++] == 0) flipped--;
                }
            }
            maxLen = Math.max(maxLen, r - l + 1);
        }
        return maxLen;
    }

    public static void main(String[] args) {
        MaxConsecutiveOnesIII solution = new MaxConsecutiveOnesIII();
        System.out.println(solution.longestOnes(new int[]{1,1,1,0,0,0,1,1,1,1,0}, 2)); // 6
        System.out.println(solution.longestOnes(new int[]{0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1}, 3)); // 10
    }
}
