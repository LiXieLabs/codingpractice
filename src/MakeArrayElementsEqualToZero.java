public class MakeArrayElementsEqualToZero {

    /********** Solution 1: Left prefix sum & Right prefix sum ******************/
    /**
     * 从左到右计算 left prefix sum -> l
     * 从右到左计算 right prefix sum -> r
     * 找到 nums[i] == 0 位置，使得：
     * （1）l[i] == r[i]，先向哪一边都可以，res += 2;
     * （2）l[i] r[i] 相差 1，先向多的一边才行，res += 1;
     *
     * Time: O(N)   Space: O(N)
     */
    public int countValidSelections1(int[] nums) {
        int n = nums.length;
        int[] l = new int[n], r = new int[n];
        for (int i = 0; i < n; i++) {
            l[i] = nums[i] + (i - 1 >= 0 ? l[i - 1] : 0);
            r[n - i - 1] = nums[n - i - 1] + (n - i < n ? r[n - i] : 0);
        }
        int res = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) {
                if (l[i] == r[i]) res += 2;
                else if (Math.abs(l[i] - r[i]) == 1) res += 1;
            }
        }
        return res;
    }

    /********** Solution 2: Solution 1 的空间优化 ******************/
    /**
     * left denotes the left prefix sum
     * total denotes the sum of the nums
     * right prefix sum = total - left
     *
     * Time: O(N)   Space: O(1)
     */
    public int countValidSelections(int[] nums) {
        int total = 0;
        for (int x : nums) total += x;

        int left = 0, res = 0;
        for (int x : nums) {
            if (x == 0) {
                if (total - left == left) res += 2;
                if (Math.abs(total - left - left) == 1) res += 1;
            }
            left += x;
        }
        return res;
    }
}
