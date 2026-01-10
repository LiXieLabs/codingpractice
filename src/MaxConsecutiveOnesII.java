import java.util.ArrayList;
import java.util.List;

/**
 * 487. Max Consecutive Ones II (https://leetcode.com/problems/max-consecutive-ones-ii/description/)
 */
public class MaxConsecutiveOnesII {

    /*************** Solution 1: 类似DP *****************/
    public int findMaxConsecutiveOnes1(int[] nums) {
        // sum up consecutive ones
        // [1,1,1,0,0,1,0,1] => [3,0,0,1,0,1]
        List<Integer> lst = new ArrayList<>();
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) {
                sum++;
            } else {
                if (sum > 0) {
                    lst.add(sum);
                    sum = 0;
                }
                lst.add(0);
            }
        }
        // ⚠️注意⚠️必须有这一步，否则最后一串 1 会丢失！！！
        if (sum > 0) lst.add(sum);

        // 遍历处理过的array，0的话把左右相加加自己更新最大值
        int res = 0;
        for (int i = 0; i < lst.size(); i++) {
            if (lst.get(i) == 0) {
                int cur = 1;
                if (i - 1 >= 0) cur += lst.get(i-1);
                if (i + 1 < lst.size()) cur += lst.get(i+1);
                res = Math.max(res, cur);
            } else {
                res = Math.max(res, lst.get(i));
            }
        }
        return res;
    }

//    Follow up: What if the input numbers come in one by one as an infinite stream?
//    In other words, you can't store all numbers coming from the stream as it's too large to hold in memory.
//    Could you solve it efficiently？👇

    /************** Solution 2: One Pass ****************/
    /**
     * preOnes, zero, curOnes
     */
    public int findMaxConsecutiveOnes2(int[] nums) {
        int preOnes = 0, curOnes = 0, maxLen = 0;
        for (int n : nums) {
            if (n == 1) {
                curOnes++;
            } else {
                maxLen = Math.max(maxLen, curOnes + preOnes + 1);
                preOnes = curOnes;
                curOnes = 0;
            }
        }
        if (curOnes > 0) {
            // 为了 [0,1] 情况！
            if (curOnes < nums.length) preOnes++;
            // 为了 [1,0,1] 情况！
            if (preOnes > 0) curOnes += preOnes;
            maxLen = Math.max(maxLen, curOnes);
        }
        return maxLen;
    }

    /************** Solution 3: Sliding Window *******************/
    /**
     * cnt标记flip了几个0，r尽可能向右移动，遇到0则flip
     * l向右移动维护，当前flip的0不超过1个
     * 更新最大值，并继续右移r
     *
     * 最优解！！！可以用于翻转任意 k 个 0 的情况！！！
     * 1004. Max Consecutive Ones III (https://leetcode.com/problems/max-consecutive-ones-iii/description/)
     */
    public int findMaxConsecutiveOnes(int[] nums) {
        int flipped = 0, l = 0, maxLen = 0;
        for (int r = 0; r < nums.length; r++) {
            if (nums[r] == 0) {
                flipped++;
                while (flipped > 1) {
                    if (nums[l++] == 0) flipped--;
                }
            }
            maxLen = Math.max(maxLen, r - l + 1);
        }
        return maxLen;
    }

    public static void main(String[] args) {
        MaxConsecutiveOnesII solution = new MaxConsecutiveOnesII();
        System.out.println(solution.findMaxConsecutiveOnes(new int[]{1,0,1,1,0})); // 4
        System.out.println(solution.findMaxConsecutiveOnes(new int[]{1,0,1,1,0,1})); // 4
    }
}
