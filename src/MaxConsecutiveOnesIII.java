public class MaxConsecutiveOnesIII {

    public int longestOnes(int[] nums, int k) {
        int l = 0, r = 0, cnt = 0, res = 0;
        while (r < nums.length) {
            if (nums[r] == 0) cnt++;
            while (cnt > k) {
                if (nums[l] == 0) cnt--;
                l++;
            }
            res = Math.max(res, r++ - l + 1);
        }
        return res;
    }
}
