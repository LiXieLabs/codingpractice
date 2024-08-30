import java.util.Arrays;

/**
 * 188. Best Time to Buy and Sell Stock IV (https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/description/)
 */
public class BestTimeToBuyAndSellStockIV {

    /************* Solution 1: One-pass 1D DP ***********************/
    /**
     * 由 BestTimeToBuyAndSellStockIII Solution 2 扩展而来
     *
     * cost1 和 profit1 对应 1st transaction 的最低成本和最高收益
     * cost2 是将 profit1 用于支付 2nd transaction 的成本之后的净成本
     * profit2 则是两个 transaction 都结束时的最大收益
     * :
     * cost(k) 是将 profit(k-1) 用于支付 kth transaction 的成本之后的净成本
     * profit(k) 则是k个 transaction 都结束时的最大收益
     *
     * Time: O(kN)  Space: O(2k) = O(k)
     */
    public int maxProfit(int k, int[] prices) {
        int l = prices.length;
        int[] costs = new int[k], profits = new int[k];
        Arrays.fill(costs, Integer.MAX_VALUE);
        for (int price : prices) {
            for (int t = 0; t < k; t++) {
                costs[t] = Math.min(costs[t], t > 0 ? price - profits[t - 1] : price);
                profits[t] = Math.max(profits[t], price - costs[t]);
            }
        }
        return profits[k - 1];
    }

    public static void main(String[] args) {
        BestTimeToBuyAndSellStockIV solution = new BestTimeToBuyAndSellStockIV();
        System.out.println(solution.maxProfit(2, new int[]{2,4,1})); // 2
        System.out.println(solution.maxProfit(2, new int[]{3,2,6,5,0,3})); // 7
        System.out.println(solution.maxProfit(2, new int[]{7,6,4,3,1})); // 0
    }
}
