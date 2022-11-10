public class JumpGame {

    /********** Solution 1: 1D Greedy Algo ***************/
    /**
     * Time: O(N)   Space: O(1)
     */
    public boolean canJump(int[] nums) {
        int curCanReach = 0, nexCanReach = 0;
        for (int i = 0; curCanReach < nums.length - 1; curCanReach = nexCanReach) {
            while (i <= curCanReach) {
                nexCanReach = Math.max(nexCanReach, i + nums[i++]);
            }
            if (nexCanReach == curCanReach) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        JumpGame solution = new JumpGame();
        System.out.println(solution.canJump(new int[]{2,3,1,1,4}));
        System.out.println(solution.canJump(new int[]{3,2,1,0,4}));
    }

}