/**
 * 714. Best Time to Buy and Sell Stock with Transaction Fee (https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/description/)
 */
public class BestTimeToBuyAndSellStockWithTransactionFee {

    /************* Solution 1: One-pass 1D DP ***********************/
    /**
     * 2种状态 的 状态机
     * 节点
     * sold[i] - 第 i 天未持有股票
     * hold[i] - 第 i 天持有股票
     * 边
     * sold[i] <- hold[i-1] + price[i] - fee 第i天卖出
     *         <- sold[i-1] 不操作
     * hold[i] <- sold[i-1] - price[i] 第i天买入
     *         <- hold[i-1] 不操作
     *
     * DP 状态转移方程
     * sold[i] = max(sold[i-1], hold[i-1] + price[i] - fee) 卖出扣费
     * hold[i] = max(hold[i-1], sold[i-1] - price[i])
     * 或者
     * sold[i] = max(sold[i-1], hold[i-1] + price[i])
     * hold[i] = max(hold[i-1], sold[i-1] - price[i] - fee) 买入扣费
     *
     * Time: O(N)  Space: O(N) -> O(1)
     */
    public int maxProfit1(int[] prices, int fee) {
        // 小心！！！ Integer.MIN_VALUE，preHold + price - fee 会整型溢出！！！
        int hold = -prices[0], sold = 0;
        for (int price : prices) {
            int preHold = hold;
            hold = Math.max(hold, sold - price);
            sold = Math.max(sold, preHold + price - fee);
        }
        return sold;
    }

    public int maxProfit(int[] prices, int fee) {
        int hold = Integer.MIN_VALUE, sold = 0;
        for (int price : prices) {
            int preHold = hold;
            hold = Math.max(hold, sold - price - fee);
            sold = Math.max(sold, preHold + price);
        }
        return sold;
    }

    public static void main(String[] args) {
        BestTimeToBuyAndSellStockWithTransactionFee solution = new BestTimeToBuyAndSellStockWithTransactionFee();
        System.out.println(solution.maxProfit(new int[]{1,3,2,8,4,9}, 2)); // 8
        System.out.println(solution.maxProfit(new int[]{1,3,7,5,10,3}, 3)); // 6
    }
}
