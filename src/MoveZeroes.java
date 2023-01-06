import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 283. Move Zeroes (https://leetcode.com/problems/move-zeroes/description/)
 */
public class MoveZeroes {

    /********* Solution 1: Dutch National Flag Algo to sort array *************/
    /**
     * Time: O(N)  Space: O(1)
     */
    public void moveZeroes(int[] nums) {
        for (int p1 = -1, p2 = 0; p2 < nums.length; p2++) {
            if (nums[p2] != 0) {
                p1++;
                int tmp = nums[p1];
                nums[p1] = nums[p2];
                nums[p2] = tmp;
            }
        }
    }

    private static void print(int[] input) {
        System.out.println(
                "[" + Arrays.stream(input).boxed().map(String::valueOf)
                        .collect(Collectors.joining(",")) + "]"
        );
    }

    public static void main(String[] args) {
        MoveZeroes solution = new MoveZeroes();

        int[] input1 = new int[]{0,1,0,3,12};
        solution.moveZeroes(input1);
        print(input1);

        int[] input2 = new int[]{0};
        solution.moveZeroes(input2);
        print(input2);

        int[] input3 = new int[]{1};
        solution.moveZeroes(input3);
        print(input3);
    }
}
