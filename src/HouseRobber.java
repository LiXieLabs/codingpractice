public class HouseRobber {

    /******* Solution 1: 1D DP *************/
    /**
     * dp[i] = max(dp[i-1], dp[i-2] + house[i]
     *
     * Time: O(N)   Space: O(1)
     */
    public int rob(int[] nums) {
        int prevprev = 0, prev = 0, res = 0;
        for (int i = 0; i < nums.length; i++) {
            res = Math.max(prev, prevprev + nums[i]);
            prevprev = prev;
            prev = res;
        }
        return res;
    }

    public static void main(String[] args) {
        HouseRobber solution = new HouseRobber();
        System.out.println(solution.rob(new int[]{1,2,3,1}));
        System.out.println(solution.rob(new int[]{2,7,9,3,1}));
    }
}
