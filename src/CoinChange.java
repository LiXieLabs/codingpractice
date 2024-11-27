/**
 * 322. Coin Change (https://leetcode.com/problems/coin-change/description/)
 */
public class CoinChange {

    /*************** Solution 1: DP bottom up *******************/
    /**
     * Time: O(S X N) where S denotes amount, N denotes # of denominations
     * Space: O(S) for dp array
     */
    public int coinChange1(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        for (int i = 1; i <= amount; i++) {
            int minCnt = Integer.MAX_VALUE;
            for (int coin : coins) {
                int remain = i - coin;
                if (remain >= 0 && dp[remain] != -1) {
                    minCnt = Math.min(minCnt, dp[remain]);
                }
            }
            dp[i] = minCnt == Integer.MAX_VALUE ? -1 : minCnt + 1;
        }
        return dp[amount];
    }

    private int[] memo;
    private int[] coins;

    /***************** Solution 2: Recur + Memo Top Down ***************/
    /**
     * 也可以：
     * Map<Integer, Integer> memo = new HashMap<>();
     * memo.put(0, 0);
     *
     * Time complexity : O(S X N).
     * In the worst case the recursive tree of the algorithm has height of S
     * and the algorithm solves only S subproblems because it caches precalculated solutions in a table.
     * Each subproblem is computed with N iterations, one by coin denomination.
     *
     * Space complexity : O(S)
     * We use extra space for the memoization table.
     */
    public int coinChange(int[] coins, int amount) {
        this.coins = coins;
        this.memo = new int[amount + 1];
        return recur(amount);
    }

    public int recur(int amount) {
        if (amount == 0) return 0;
        if (amount < 0) return -1;
        if (memo[amount] == 0) {
            int minCnt = Integer.MAX_VALUE;
            for (int coin : this.coins) {
                int curCnt = recur(amount - coin);
                if (curCnt >= 0) {
                    minCnt = Math.min(minCnt, curCnt);
                }
            }
            memo[amount] = minCnt == Integer.MAX_VALUE ? -1 : minCnt + 1;
        }
        return memo[amount];
    }

    public static void main(String[] args) {
        CoinChange solution = new CoinChange();
        System.out.println(solution.coinChange(new int[]{1,2,5}, 11)); // 3
        System.out.println(solution.coinChange(new int[]{2}, 3)); // -1
        System.out.println(solution.coinChange(new int[]{1}, 0)); // 0
        System.out.println(solution.coinChange(new int[]{2147483647}, 2)); // -1
    }
}