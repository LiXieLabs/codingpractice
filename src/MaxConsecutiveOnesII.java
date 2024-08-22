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

    /************** Solution 2: Sliding Window *******************/
    /**
     * cnt标记flip了几个0，r尽可能向右移动，遇到0则flip
     * l向右移动维护，当前flip的0不超过1个
     * 更新最大值，并继续右移r
     */
    public int findMaxConsecutiveOnes2(int[] nums) {
        int l = 0, r = 0, cnt = 0, res = 0;
        while (r < nums.length) {
            if (nums[r] == 0) cnt++;
            while (cnt > 1) {
                if (nums[l] == 0) cnt--;
                l++;
            }
            res = Math.max(res, r++ - l + 1);
        }
        return res;
    }

    /************** Solution 3: 另一种 Sliding Window *******************/
    /**
     * flipped 0 不超过 1 的前提下，尽可能右移 r
     * 更新最大值
     * 右移 l 直到 flipped 0 少于一个了
     */
    public int findMaxConsecutiveOnes(int[] nums) {
        int maxSum = 0;
        int l = 0, r = 0;
        int flipped = 0;
        while (r < nums.length) {
            while (r < nums.length && (nums[r] != 0 || flipped < 1)) {
                if (nums[r] == 0) {
                    flipped++;
                }
                r++;
            }
            maxSum = Math.max(maxSum, r - l);
            while (l < r && flipped == 1) {
                if (nums[l] == 0) {
                    flipped--;
                }
                l++;
            }
        }
        return maxSum;
    }

    public static void main(String[] args) {
        MaxConsecutiveOnesII solution = new MaxConsecutiveOnesII();
        System.out.println(solution.findMaxConsecutiveOnes(new int[]{1,0,1,1,0})); // 4
        System.out.println(solution.findMaxConsecutiveOnes(new int[]{1,0,1,1,0,1})); // 4
    }
}
