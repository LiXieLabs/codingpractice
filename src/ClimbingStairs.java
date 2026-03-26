/**
 * 70. Climbing Stairs (https://leetcode.com/problems/climbing-stairs/description/)
 */
public class ClimbingStairs {

    /********* Solution 1: 1D DP *************/
    /**
     * dp[i] 表示到达台阶 i 一共有几种方式
     * dp[i] = 走1步之前有几种方式 + 走2步之前有几种方式 = dp[i-1] + dp[i-2]
     *
     * 实际就是 Fibonacci Numbers: 1 2 3 5 8 13 ...
     *
     * Time: O(N)   Space: O(1)
     */
    public int climbStairs(int n) {
        int prepre = 0, pre = 1;
        for (int i = 1; i <= n; i++) {
            int cur = pre;
            pre = prepre + pre;
            prepre = cur;
        }
        return pre;
    }

    /********* Solution 2: Top-down recur with memoization *****************/
    /**
     * Time: O(N) [O(2^N) without memo]
     * Space: O(logN + N) = O(N)
     */
    int[] memo;

    public int climbStairs2(int n) {
        memo = new int[n + 1];
        memo[0] = 1;
        memo[1] = 1;
        return recur(n);
    }

    private int recur(int cur) {
        if (memo[cur] == 0) {
            memo[cur] = recur(cur - 1) + recur(cur - 2);
        }
        return memo[cur];
    }

    public static void main(String[] args) {
        ClimbingStairs solution = new ClimbingStairs();
        System.out.println(solution.climbStairs(1)); // 1
        System.out.println(solution.climbStairs(2)); // 2
        System.out.println(solution.climbStairs(3)); // 3
    }
}
