public class MaxConsecutiveOnes {

    public int findMaxConsecutiveOnes(int[] nums) {
        int res = 0, cur = 0;
        for (int n : nums) {
            if (n == 1) {
                cur++;
            } else {
                res = Math.max(res, cur);
                cur = 0;
            }
        }
        return Math.max(res, cur);
    }
}
