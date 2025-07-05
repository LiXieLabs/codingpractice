import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 167. Two Sum II - Input Array Is Sorted (https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/description/)
 */
public class TwoSumII {

    /*************** Solution 1: Two Pointers ******************/
    /**
     * Time: O(N) Space: O(1)
     */
    public int[] twoSum(int[] numbers, int target) {
        int i = 0, j = numbers.length - 1;
        while (i < j) {
            if (numbers[i] + numbers[j] == target) {
                return new int[]{i + 1, j + 1};
            } else if (numbers[i] + numbers[j] < target) {
                i++;
            } else {
                j--;
            }
        }
        return new int[]{-1, -1};
    }

    public static void main(String[] args) {
        TwoSumII solution = new TwoSumII();
        System.out.println(Arrays.stream(solution.twoSum(new int[]{2,7,11,15}, 9)).boxed().collect(Collectors.toList())); // [1,2]
        System.out.println(Arrays.stream(solution.twoSum(new int[]{2,3,4}, 6)).boxed().collect(Collectors.toList())); // [1,3]
        System.out.println(Arrays.stream(solution.twoSum(new int[]{-1,0}, -1)).boxed().collect(Collectors.toList())); // [1,2]
    }
}
