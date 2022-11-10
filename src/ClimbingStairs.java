public class ClimbingStairs {

    /********* Solution 1: 1D DP *************/
    /**
     * dp[i] 表示到达台阶 i 一共有几种方式
     * dp[i] = 走1步之前有几种方式 + 走2步之前有几种方式 = dp[i-1] + dp[i-2]
     *
     * Time: O(N)   Space: O(1)
     */
    public int climbStairs(int n) {
        int prevprev = 0, prev = 1;
        for (int i = 1; i <= n; i++) {
            int temp = prev;
            prev = prevprev + prev;
            prevprev = temp;
        }
        return prev;
    }

    public static void main(String[] args) {
        ClimbingStairs solution = new ClimbingStairs();
        System.out.println(solution.climbStairs(1));
        System.out.println(solution.climbStairs(2));
        System.out.println(solution.climbStairs(3));
    }
}
