import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 84. Largest Rectangle in Histogram (https://leetcode.com/problems/largest-rectangle-in-histogram/description/)
 */
public class LargestRectangleInHistogram {

    // Same as 84. Largest Rectangle in Histogram => https://leetcode.com/problems/largest-rectangle-in-histogram/
    // Same as 907. Sum of Subarray Minimums => https://leetcode.com/problems/sum-of-subarray-minimums/
    // Same as 2104. Sum of Subarray Ranges => https://leetcode.com/problems/sum-of-subarray-ranges/

    /**************** Solution 1: Ascending Monotonic Stack ***************/
    /**
     * k 是 j 左边第一个比 j 小的，i 是 j 右边第一个比 j 小的，
     * 所以 k -> j 之间和 j -> i 之间都比 j 大，j 是 k -> i 范围内(exclusive)最小值，决定之间的面积
     * pattern 类似于
     *  1, 7, 8, 9, 3, 10, 11, 12, 2
     *  k --------- j ------------ i
     *  则 [7, 12] 由短板 3 决定，到 i 时，栈为 [1,3]，2 正在等待进栈
     *  这是由于单调递增栈维护当前值左边比其小的一系列值，而栈顶比 i 大的值都是局部较大值中的最小值。
     *
     * Time: O(N) each node enter and exit stack once
     * Space: O(N) by stack
     */
    public int largestRectangleArea(int[] heights) {
        Deque<Integer> monoIncStack = new ArrayDeque<>();
        monoIncStack.push(-1);
        int res = 0;
        for (int i = 0; i <= heights.length; i++) {
            while (monoIncStack.peek() != -1 && heights[monoIncStack.peek()] >= (i == heights.length ? 0 : heights[i])) {
                int j = monoIncStack.pop();
                int k = monoIncStack.peek();
                res = Math.max(res, (i - k - 1) * heights[j]);
            }
            monoIncStack.push(i);
        }
        return res;
    }

    public static void main(String[] args) {
        LargestRectangleInHistogram solution = new LargestRectangleInHistogram();
        System.out.println(solution.largestRectangleArea(new int[]{2,1,5,6,2,3})); // 10
        System.out.println(solution.largestRectangleArea(new int[]{2,4})); // 4
        System.out.println(solution.largestRectangleArea(new int[]{})); // 0
        System.out.println(solution.largestRectangleArea(new int[]{1,2,3,4,5})); // 9
    }

}