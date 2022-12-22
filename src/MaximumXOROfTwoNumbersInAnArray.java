import java.util.HashSet;
import java.util.Set;

/**
 * 421. Maximum XOR of Two Numbers in an Array (https://leetcode.com/problems/maximum-xor-of-two-numbers-in-an-array/)
 */
public class MaximumXOROfTwoNumbersInAnArray {

    /************ Solution 1: Brute Force - 2 For Loops (TLE) ****************/
    /**
     * Time: O(N^2)   Space: (1)
     */
    public int findMaximumXOR1(int[] nums) {
        int maxXor = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                maxXor = Math.max(maxXor, nums[i] ^ nums[j]);
            }
        }
        return maxXor;
    }

    /********** Solution 2: Bit Manipulation + Two Sum *******************/
    /**
     * 位异或实际是不进位的加法
     * 位异或定理：
     * （1）交换律：a ^ b = b ^ a
     * （2）因果互换律：a ^ b = c 则 a ^ c = b && b ^ c = a
     *
     * 从左向右判断该位能不能通过 n1 ^ n2 变为 1
     * mask => (1000, 1100, 1110, 1111), mask前一位，前两位，前三位，前四位。。。
     * 类似于 Two Sum, 寻找 n1 ^ n2 = curXor，遍历 n1 寻找 n2 = n1 ^ curXor
     * 如果找到，更新 maxXor
     * 当遍历到下一位，curXor 是将 maxXor 的该位翻成 1
     *
     * Time: O(maxLen X N) = O(NlogN)
     *       as N <= 2^31 - 1, logN <= 32, 可视为常数
     *       O(N)
     *
     * Space: O(N) by HashSet
     */
    public int findMaximumXOR(int[] nums) {
        int maxNum = nums[0];
        for (int n : nums) maxNum = Math.max(maxNum, n);
        int maxLen = Integer.toBinaryString(maxNum).length();

        int maxXor = 0, mask = 0;
        Set<Integer> set = new HashSet<>();
        for (int i = maxLen - 1; i >= 0; i--) {
            mask |= (1 << i);
            set.clear();
            int curXor = maxXor | (1 << i);
            for (int n : nums) {
                if (set.contains(curXor ^ (n & mask))) {
                    maxXor = curXor;
                    break;
                }
                set.add(n & mask);
            }
        }
        return maxXor;
    }

    // TODO: Trie Algo

    public static void main(String[] args) {
        MaximumXOROfTwoNumbersInAnArray solution = new MaximumXOROfTwoNumbersInAnArray();
        System.out.println(solution.findMaximumXOR(new int[]{8,10,2})); // 10
        System.out.println(solution.findMaximumXOR(new int[]{3,10,5,25,2,8})); // 28
        System.out.println(solution.findMaximumXOR(new int[]{14,70,53,83,49,91,36,80,92,51,66,70})); // 127
    }
}
