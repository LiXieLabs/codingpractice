/**
 * 485. Max Consecutive Ones (https://leetcode.com/problems/max-consecutive-ones/description/)
 */
public class MaxConsecutiveOnes {

    /********** Solution 1: Count on the go and updating the global max ************/
    /**
     * Time: O(N)   Space: O(1)
     */
    public int findMaxConsecutiveOnes(int[] nums) {
        int res = 0, cur = 0;
        for (int n : nums) {
            if (n == 1) {
                cur++;
            } else {
                res = Math.max(res, cur);
                cur = 0;
            }
        }
        return Math.max(res, cur);
    }

    public static void main(String[] args) {
        MaxConsecutiveOnes solution = new MaxConsecutiveOnes();
        System.out.println(solution.findMaxConsecutiveOnes(new int[]{1,1,0,1,1,1})); // 3
        System.out.println(solution.findMaxConsecutiveOnes(new int[]{1,0,1,1,0,1})); // 2
    }
}
