import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 260. Single Number III (https://leetcode.com/problems/single-number-iii/description/)
 */
public class SingleNumberIII {

    /************ Solution 1: Bit Manipulation ****************/
    /**
     * (1) XOR 保留所有 odd time elements
     * (2) a AND (-a) 提取 a 的第一个为 1 的 bit
     * (3) x ^ y 是 x 和 y 所有不一样的 bit 的位置为 1
     *
     * Time: O(2N) = O(N)  Space: O(N)
     */
    public int[] singleNumber(int[] nums) {
        // bitMask = x ^ y
        int bitMask = 0;
        for (int n : nums) bitMask ^= n;

        // 此时 bitMask = x ^ y
        // firstDiffBit 为 x 和 y 第一个不一样的 bit
        int firstDiffBit = bitMask & (-bitMask);

        // 把 nums 中，跟 x，y 其中一个一样的都挑出来再做一遍 xor
        // 这样其中一个就单独留下来了
        int x = 0;
        for (int n : nums) {
            // (n & firstDiffBit) != 0 也可以，相当于找另一个，
            // 但是不能是 > 0，因为可能 < 0!!!
            if ((n & firstDiffBit) == 0) {
                x ^= n;
            }
        }

        // bitMask ^ x = x ^ y ^ x = y
        return new int[]{x, bitMask ^ x};
    }

    public String print(int[] input) {
        return Arrays.stream(input).mapToObj(String::valueOf).collect(Collectors.joining(","));
    }

    public static void main(String[] args) {
        SingleNumberIII solution = new SingleNumberIII();
        System.out.println(solution.print(solution.singleNumber(new int[]{1,2,1,3,2,5}))); // [5,3]
        System.out.println(solution.print(solution.singleNumber(new int[]{-1,0}))); // [0,-1]
    }
}
