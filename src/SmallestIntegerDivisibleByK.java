import java.util.HashSet;
import java.util.Set;

/**
 * 1015. Smallest Integer Divisible by K (https://leetcode.com/problems/smallest-integer-divisible-by-k/description/)
 */
public class SmallestIntegerDivisibleByK {

    /************ Solution 1: Check remainder loop *************/
    /**
     *        1 % 7 = 1
     *       11 % 7 = 4
     *      111 % 7 = 6
     *     1111 % 7 = 5
     *    11111 % 7 = 2
     *   111111 % 7 = 0
     *  1111111 % 7 = 1
     *  最多k次，陷入循环
     *
     * Time: O(K)   Space: O(K)
     */
    public int smallestRepunitDivByK1(int k) {
        if (k % 2 == 0 || k % 5 == 0) return -1;
        Set<Integer> seen = new HashSet<>();
        int remainder = 0, length = 1;
        while (true) {
            remainder = (remainder * 10 + 1) % k;
            if (remainder == 0) return length;
            if (!seen.add(remainder)) return -1;
            length++;
        }
    }

    /************ Solution 2: Check remainder loop 空间优化 O(1) Space *************/
    /**
     * K 的 remainder 的范围是 [0,K-1]，根据 pigeonhole principle，遍历 K 次，一定至少有两个余数是重复的，
     * 在此之前没找到 0，则一定陷入循环了
     *
     * Time: O(K)   Space: O(1)
     */
    public int smallestRepunitDivByK(int k) {
        if (k % 2 == 0 || k % 5 == 0) return -1;
        int remainder = 0;
        for (int length = 1; length <= k; length++) {
            remainder = (remainder * 10 + 1) % k;
            if (remainder == 0) return length;
        }
        return -1;
    }

    public static void main(String[] args) {
        SmallestIntegerDivisibleByK solution = new SmallestIntegerDivisibleByK();
        System.out.println(solution.smallestRepunitDivByK(1));
        System.out.println(solution.smallestRepunitDivByK(2));
        System.out.println(solution.smallestRepunitDivByK(3));
        System.out.println(solution.smallestRepunitDivByK(7));
    }
}
