/**
 * 136. Single Number (https://leetcode.com/problems/single-number/description/)
 */
public class SingleNumber {

    /******** Solution 1: bit manipulation - XOR as filter *************/
    /**
     * Time: O(N) Space: O(1)
     */
    public int singleNumber(int[] nums) {
        int filter = 0;
        for (int n : nums) filter ^= n;
        return filter;
    }

    public static void main(String[] args) {
        SingleNumber solution = new SingleNumber();
        System.out.println(solution.singleNumber(new int[]{4,2,1,2,1})); // 4
        System.out.println(solution.singleNumber(new int[]{-4,2,-11,2,-11})); // -4
    }
}
