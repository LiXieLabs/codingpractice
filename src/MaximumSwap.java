/**
 * 670. Maximum Swap (https://leetcode.com/problems/maximum-swap/description/)
 */
public class MaximumSwap {

    /************* Solution 1: Monotonic Stack 简化为 Greedy Algo ****************/
    /**
     * 基本原理是不断刷新最大值 j，最大值左边比其小的 i，越左边约好，交换 i 和 j 即可
     *
     * 自右向左维护单调递减栈，只要还在递减就不用更新
     * 遇到较栈顶更大的值，将当前栈首尾交换更新res
     *
     * 发现栈中间值其实无所谓，故简化为只保存栈首尾
     * leftMostLessThanMax为栈顶值
     * maxNum，maxIdx是栈底值和位置
     *
     * Time: O(N)   Space: O(1)
     */
    public int maximumSwap1(int num) {
        int i = 0, cur = num, res = num;
        int maxNum = -1, maxIdx = -1, leftMostLessThanMax = -1;
        while (cur > 0) {
            int digit = cur % 10;
            cur /= 10;
            if (digit > maxNum) { // 相同最大值取最右边的
                if (leftMostLessThanMax > maxIdx) {
                    res = Math.max(res, swap(num, leftMostLessThanMax, maxIdx));
                }
                maxNum = digit;
                maxIdx = i;
            } else if (digit < maxNum) {
                leftMostLessThanMax = i;
            }
            i++;
        }
        if (leftMostLessThanMax > maxIdx) {
            res = Math.max(res, swap(num, leftMostLessThanMax, maxIdx));
        }
        return res;
    }

    private int swap(int num, int i, int j) {
        int timesA = (int) Math.pow(10, i);
        int timesB = (int) Math.pow(10, j);
        int a = num / timesA % 10; // i 位置的 digit
        int b = num / timesB % 10; // j 位置的 digit
        // n - a x 10^i - b x 10^j + a x 10^j + b x 10^i
        return num + (a - b) * (timesB - timesA);
    }

    /************* Solution 2: Turn to String + Bucket Sort ****************/
    /**
     * 1st loop: index 统计每个 num char 最后出现的位置
     * 2nd loop: 对于从左开始的数字，一旦找到一个比其大的数字的位置比其靠右，swap并返回
     *
     * Time: O(N)   Space: O(N)
     */
    public int maximumSwap(int num) {
        char[] nums = String.valueOf(num).toCharArray();
        int[] index = new int[10];
        for (int i = 0; i < nums.length; i++) {
            index[nums[i] - '0'] = i;
        }
        for (int i = 0; i < nums.length; i++) {
            for (int j = 9; j > nums[i] - '0'; j--) {
                if (index[j] > i) {
                    nums[index[j]] = nums[i];
                    nums[i] = (char) (j + '0');
                    return Integer.parseInt(new String(nums));
                }
            }
        }
        return num;
    }

    public static void main(String[] args) {
        MaximumSwap solution = new MaximumSwap();
        System.out.println(solution.maximumSwap(98368)); // 98863
        System.out.println(solution.maximumSwap(9976)); // 9976
        System.out.println(solution.maximumSwap(2736)); // 7236
        System.out.println(solution.maximumSwap(767892)); // 967872
    }
}
