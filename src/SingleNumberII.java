/**
 * 137. Single Number II (https://leetcode.com/problems/single-number-ii/description/)
 */
public class SingleNumberII {

    /*********** Solution 1: bit manipulation *****************/
    /**
     * int32 对于每一位（32位），count 所有 nums 该位有几个 1
     * 对于 target 的该位，count = 3x + 1 (x >= 0)
     * 对于 non-target 的该位，count = 3x (x >= 0)
     *
     * Time: O(32N) = O(N)   Space: O(1)
     */
    public int singleNumber1(int[] nums) {
        int res = 0;
        for (int shift = 0; shift < 32; shift++) {
            int count = 0;
            for (int n : nums) {
                count += (n >> shift) & 1;
            }
            if (count % 3 == 1) res |= (1 << shift);
        }
        return res;
    }

    /*********** Solution 2: bit manipulation - advanced *****************/
    /**
     * seenOnce = (seenOnce XOR n) AND (NOT seenTwice)
     * seenTwice = (seenTwice XOR n) AND (NOT seenOnce)
     *
     * If a bit appears the 1st time, add it to seenOnce. It will not be added to seenTwice because of it's presence in seenOnce.
     * If a bit appears the 2nd time, remove it from seenOnce and add it to seenTwice.
     * If a bit appears the 3rd time, it won't be added to seenOnce because it is already present in seenTwice. After that it will be removed from seenTwice.
     *
     * At last, seenOnce = target, seenTwice = 0.
     *
     * Time: O(32N) = O(N)   Space: O(1)
     */
    public int singleNumber(int[] nums) {
        int seenOnce = 0, seenTwice = 0;
        for (int n : nums) {
            seenOnce = (seenOnce ^ n) & (~seenTwice);
            seenTwice = (seenTwice ^ n) & (~seenOnce);
        }
        return seenOnce;
    }

    public static void main(String[] args) {
        SingleNumberII solution = new SingleNumberII();
        System.out.println(solution.singleNumber(new int[]{2,2,3,2})); // 3
        System.out.println(solution.singleNumber(new int[]{0,1,0,1,0,1,99})); // 99
        System.out.println(solution.singleNumber(new int[]{0,1,0,1,0,1,Integer.MIN_VALUE})); // -2147483648
        System.out.println(solution.singleNumber(new int[]{0,1,0,1,0,1,Integer.MAX_VALUE})); // 2147483647
    }
}
