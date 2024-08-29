/**
 * 121. Best Time to Buy and Sell Stock (https://leetcode.com/problems/best-time-to-buy-and-sell-stock/)
 */
public class BestTimeToBuyAndSellStock {

    /********** Solution 1: Intuitive ***************/
    public int maxProfit1(int[] prices) {
        int maxProfit = 0, minPrice = Integer.MAX_VALUE;
        for (int price : prices) {
            maxProfit = Math.max(maxProfit, price - minPrice);
            minPrice = Math.min(minPrice, price);
        }
        return maxProfit;
    }

    /********** Solution 2: Kadane's Algo - 一种DP ***************/
    /**
     * https://zh.wikipedia.org/wiki/%E6%9C%80%E5%A4%A7%E5%AD%90%E6%95%B0%E5%88%97%E9%97%AE%E9%A2%98
     * Kadane's algo 解决最大连续子数列和的问题，此处是求股价差值数列的最大连续子数列和
     *
     * if the interviewer twists the question slightly by giving the difference array of prices,
     * Ex: for {1, 7, 4, 11}, if he gives {0, 6, -3, 7}, we can calculate by following:
     * Calculate the difference maxCur += prices[i] - prices[i-1] of the original array,
     * and find a continuous subarray giving maximum profit. If the difference falls below 0, reset it to zero.
     */
    public int maxProfit(int[] prices){
        int maxProfit = 0, curMax = 0;
        for (int i = 1; i < prices.length; i++) {
            // 有负数，且允许子数列长度为0，则可以直接和0比较
//            curMax = Math.max(curMax + prices[i] - prices[i-1], 0);
            curMax = Math.max(curMax + prices[i] - prices[i-1], prices[i] - prices[i-1]);
            maxProfit = Math.max(maxProfit, curMax);
        }
        return maxProfit;
    }

    public static void main(String[] args) {
        BestTimeToBuyAndSellStock solution = new BestTimeToBuyAndSellStock();
        System.out.println(solution.maxProfit(new int[]{7,1,5,3,6,4})); // 5
        System.out.println(solution.maxProfit(new int[]{7,6,4,3,1})); // 0
    }
}
