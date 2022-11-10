import java.util.ArrayDeque;
import java.util.Deque;

public class LargestRectangleInHistogram {

    // Same as 84. Largest Rectangle in Histogram => https://leetcode.com/problems/largest-rectangle-in-histogram/
    // Same as 907. Sum of Subarray Minimums => https://leetcode.com/problems/sum-of-subarray-minimums/
    // Same as 2104. Sum of Subarray Ranges => https://leetcode.com/problems/sum-of-subarray-ranges/

    /**************** Solution 1: Ascending Monotonic Stack ***************/
    /**
     * k 是 j 左边第一个比 j 小的，i 是 j 右边第一个比 j 小的，
     * 所以 k -> j 之间和 j -> i 之间都比 j 大，j 是 k -> i 范围内(exclusive)最小值，决定之间的面积
     *
     * Time: O(N) each node enter and exit stack once
     * Space: O(N) by stack
     */
    public int largestRectangleArea(int[] heights) {
        int res = 0, l = heights.length;
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i <= l; i++) {
            while (!stack.isEmpty() && heights[stack.peek()] > (i == l ? 0 : heights[i])) {
                int j = stack.pop();
                int k = stack.isEmpty() ? -1 : stack.peek();
                res = Math.max(res, (i - k - 1) * heights[j]);
            }
            stack.push(i);
        }
        return res;
    }

    public static void main(String[] args) {
        LargestRectangleInHistogram solution = new LargestRectangleInHistogram();
        System.out.println(solution.largestRectangleArea(new int[]{2,1,5,6,2,3}));
        System.out.println(solution.largestRectangleArea(new int[]{2,4}));
        System.out.println(solution.largestRectangleArea(new int[]{}));
        System.out.println(solution.largestRectangleArea(new int[]{1,2,3,4,5}));
    }

}