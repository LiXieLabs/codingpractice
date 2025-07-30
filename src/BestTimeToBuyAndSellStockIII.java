/**
 * 123. Best Time to Buy and Sell Stock III (https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/description/)
 */
public class BestTimeToBuyAndSellStockIII {

    /************* Solution 1: Bi-directional 1D DP ***********************/
    /**
     * 对于每一个分割线，将prices分成两部分，左右两部分分别求一个 transaction 的 max profit
     * 优化为左边向右计算 max profit，右边向左计算 max profit，再求 max(leftMax[i] + rightMax[i+1]), i 即为分割线位置
     *
     * Time: O(N) Space: O(N)
     */
    public int maxProfit1(int[] prices) {
        int l = prices.length;
        int[] leftMax = new int[l], rightMax = new int[l];
        int leftMinPrice = Integer.MAX_VALUE, rightMaxPrice = Integer.MIN_VALUE;
        for (int i = 0; i < l; i++) {
            leftMinPrice = Math.min(leftMinPrice, prices[i]);
            leftMax[i] = Math.max(i > 0 ? leftMax[i - 1] : 0, prices[i] - leftMinPrice);
            rightMaxPrice = Math.max(rightMaxPrice, prices[l - i - 1]);
            rightMax[l - i - 1] = Math.max(i > 0 ? rightMax[l - i] : 0, rightMaxPrice - prices[l - i - 1]);
        }
        int res = leftMax[l - 1]; // leftMax[l - 1] == rightMax[0]) 对应只有一个 transaction 的情况！！！
        for (int i = 0; i < l - 1; i++) {
            res = Math.max(res, leftMax[i] + rightMax[i + 1]);
        }
        return res;
    }

    /************* Solution 2: One-pass 1D DP ***********************/
    /**
     * cost1 和 profit1 对应 1st transaction 的最低成本和最高收益
     * cost2 是将 profit1 用于支付 2nd transaction 的成本之后的净成本 => ⚠️关键点！！！要想到 cost2 是什么！！！
     * profit2 则是两个 transaction 都结束时的最大收益
     *
     * Time: O(N) Space: O(1)
     */
    public int maxProfit(int[] prices) {
        int cost1 = Integer.MAX_VALUE, cost2 = Integer.MAX_VALUE;
        int profit1 = 0, profit2 = 0;
        for (int price : prices) {
            cost1 = Math.min(cost1, price);
            profit1 = Math.max(profit1, price - cost1);
            cost2 = Math.min(cost2, price - profit1);
            profit2 = Math.max(profit2, price - cost2);
        }
        return profit2;
    }

    public static void main(String[] args) {
        BestTimeToBuyAndSellStockIII solution = new BestTimeToBuyAndSellStockIII();
        System.out.println(solution.maxProfit(new int[]{3,3,5,0,0,3,1,4})); // 6
        System.out.println(solution.maxProfit(new int[]{1,2,3,4,5})); // 4
        System.out.println(solution.maxProfit(new int[]{7,6,4,3,1})); // 0
    }
}
