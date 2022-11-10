import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class SumOfSubarrayMinimums {

    // Same as 84. Largest Rectangle in Histogram => https://leetcode.com/problems/largest-rectangle-in-histogram/
    // Same as 907. Sum of Subarray Minimums => https://leetcode.com/problems/sum-of-subarray-minimums/
    // Same as 2104. Sum of Subarray Ranges => https://leetcode.com/problems/sum-of-subarray-ranges/

    /********** Solution 1: Ascending Monotonic Stack + DP ****************/
    /**
     * Ascending Monotonic Stack 实际上记录的是每个值的左边最近一个比它小的值 => Previous Less Element
     * Descending Monotonic Stack 实际上记录的是每个值的左边最近一个比它大的值 => Previous Greater Element
     *
     * 每个arr[j]左边第一个比他小的值(prevLess)对应的index为k
     * 每个arr[j]右边第一个比他小的值(nextLess)对应的index为i
     * 则以arr[j]为最小值的subarrays = 左边开始end with arr[j] * 右边开始start with arr[j]
     *                            = (j - k) * (i - j)
     * 和为 arr[j] * (j - k) * (i - j)
     *
     * Time: O(N) Space: O(N)
     */
    public int sumSubarrayMins(int[] arr) {
        Deque<Integer> ascendStack = new ArrayDeque<>();
        int res = 0, mod = (int) Math.pow(10, 9) + 7;
        for (int i = 0; i <= arr.length; i++) {
            while (!ascendStack.isEmpty() && arr[ascendStack.peek()] > (i == arr.length ? Integer.MIN_VALUE : arr[i])) {
                int middle = ascendStack.pop();
                int prevLess = ascendStack.isEmpty() ? -1 : ascendStack.peek();
                // i is nextLess of middle
                res += ((long) arr[middle] * (middle - prevLess) * (i - middle)) % mod;
                res %= mod;
            }
            ascendStack.push(i);
        }
        return res;
    }

    public static void main(String[] args) {
        SumOfSubarrayMinimums solution = new SumOfSubarrayMinimums();
        System.out.println(solution.sumSubarrayMins(new int[]{3,1,2,4}));
        System.out.println(solution.sumSubarrayMins(new int[]{11,81,94,43,3}));
    }
}
