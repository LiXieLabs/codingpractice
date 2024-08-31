import java.util.Arrays;

/**
 * 309. Best Time to Buy and Sell Stock with Cooldown (https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/description/)
 */
public class BestTimeToBuyAndSellStockWithCoolDown {

    /************* Solution 1: One-pass 1D DP ***********************/
    /**
     * 3种状态 的 状态机
     * 节点
     * rest[i] - 第 i 天没有持有股票
     * sold[i] - 第 i 天卖出股票
     * hold[i] - 第 i 天持有股票
     * 边
     * rest[i] <- rest[i-1]
     *            sold[i-1]
     * sold[i] <- hold[i-1] + price[i]
     * hold[i] <- hold[i-1]
     *         <- rest[i-1] - price[i]
     *
     * DP 状态转移方程
     * rest[i] = max(rest[i-1], sold[i-1])
     * sold[i] = hold[i-1] + price[i]
     * hold[i] = max(hold[i-1], rest[i-1] - price[i])
     *
     * Time: O(N)  Space: O(N) -> O(1)
     */
    public int maxProfit(int[] prices) {
        int rest = 0, sold = 0, hold = Integer.MIN_VALUE;
        for (int price : prices) {
            int preRest = rest;
            rest = Math.max(rest, sold);
            sold = hold + price;
            hold = Math.max(hold, preRest - price);
        }
        return Math.max(rest, sold);
    }

    public static void main(String[] args) {
        BestTimeToBuyAndSellStockWithCoolDown solution = new BestTimeToBuyAndSellStockWithCoolDown();
        System.out.println(solution.maxProfit(new int[]{1,2,3,0,2})); // 3
        System.out.println(solution.maxProfit(new int[]{1})); // 0
    }
}
