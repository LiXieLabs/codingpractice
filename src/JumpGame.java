/**
 * 55. Jump Game (https://leetcode.com/problems/jump-game/description/)
 */
public class JumpGame {

    /********** Solution 1: 1D Greedy Algo ***************/
    /**
     * Time: O(N)   Space: O(1)
     */
    public boolean canJump(int[] nums) {
        int maxReach = 0;
        for (int i = 0; i < nums.length; i++) {
            if (maxReach < i) {
                return false;
            }
            int curReach = i + nums[i];
            maxReach = Math.max(maxReach, curReach);
        }
        return true;
    }

    public static void main(String[] args) {
        JumpGame solution = new JumpGame();
        System.out.println(solution.canJump(new int[]{2,3,1,1,4})); // true
        System.out.println(solution.canJump(new int[]{3,2,1,0,4})); // false
    }

}