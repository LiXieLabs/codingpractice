import java.util.Arrays;
import java.util.stream.Collectors;

public class SingleNumberIII {

    public int[] singleNumber(int[] nums) {
        int x = 0;
        for (int n : nums) {
            x ^= n;
        }
        int y = 1;
        while ((y & x) == 0) {
            y <<= 1;
        }
        int a = 0, b = 0;
        for (int n : nums) {
            if ((n & y) == 0) {
                a ^= n;
            } else {
                b ^= n;
            }
        }
        return new int[]{a, b};
    }

    public String print(int[] input) {
        return Arrays.stream(input).mapToObj(String::valueOf).collect(Collectors.joining(","));
    }

    public static void main(String[] args) {
        SingleNumberIII solution = new SingleNumberIII();
        System.out.println(solution.print(solution.singleNumber(new int[]{1,2,1,3,2,5})));
        System.out.println(solution.print(solution.singleNumber(new int[]{-1,0})));
    }
}
