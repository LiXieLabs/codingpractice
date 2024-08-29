/**
 * 122. Best Time to Buy and Sell Stock II (https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/description/)
 */
public class BestTimeToBuyAndSellStockII {

    /************** Solution 1: 1D DP *****************/
    /**
     * 121. Best Time to Buy and Sell Stock (https://leetcode.com/problems/best-time-to-buy-and-sell-stock/)
     * Solution 2 Kadane's Algo 的衍生算法
     *
     * 121 中 curMax + prices[i] - prices[i - 1] >= 0，就可以 hold 住股票，因为只有买卖一次的机会，但不一定是max，需要一直跟max比较
     * 122 中 prices[i] - prices[i - 1] >= 0 hold 住股票才有意义，一旦下跌，没必要hold住股票，因为可以先卖了再买
     *
     * Time: O(N)   Space: O(1)
     */
    public int maxProfit1(int[] prices) {
        int maxProfit = 0;
        for (int i = 1; i < prices.length; i++) {
            int curProfit = Math.max(prices[i] - prices[i - 1], 0);
            maxProfit = Math.max(maxProfit, maxProfit + curProfit);
        }
        return maxProfit;
    }

    /************** Solution 2: Solution 1 优化 *****************/
    /**
     * 只买那些 i - 1，使得 prices[i] - prices[i - 1] >= 0 的日期
     * 一旦有 i - 1，使得 prices[i] - prices[i - 1] < 0，则在 i - 1 抛售
     *
     * Time: O(N)   Space: O(1)
     */
    public int maxProfit(int[] prices) {
        int curMax = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] - prices[i - 1] >= 0) {
                curMax += prices[i] - prices[i - 1];
            }
        }
        return curMax;
    }

    public static void main(String[] args) {
        BestTimeToBuyAndSellStockII solution = new BestTimeToBuyAndSellStockII();
        System.out.println(solution.maxProfit(new int[]{7,1,5,3,6,4})); // 7
        System.out.println(solution.maxProfit(new int[]{1,2,3,4,5})); // 4
        System.out.println(solution.maxProfit(new int[]{7,6,4,3,1})); // 0
    }
}
