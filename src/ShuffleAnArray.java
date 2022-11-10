import java.util.Arrays;
import java.util.Random;

public class ShuffleAnArray {

    private int[] nums;
    private Random rand;

    public ShuffleAnArray(int[] nums){
        this.nums = nums;
        this.rand = new Random();
    }

    public int[] reset() {
        return this.nums;
    }

    public int[] shuffle() {
        int[] res = this.nums.clone(); // deep copy an array!!!
        for (int i = 0; i < this.nums.length; i++) {
            int j = rand.nextInt(this.nums.length - i) + i;
            int tmp = res[i];
            res[i] = res[j];
            res[j] = tmp;
        }
        return res;
    }

    public static void main(String[] args) {
        ShuffleAnArray solution = new ShuffleAnArray(new int[]{1,2,3,4,5,6,7,8,9});
        System.out.println(Arrays.toString(solution.shuffle()));
        System.out.println(Arrays.toString(solution.shuffle()));
        System.out.println(Arrays.toString(solution.shuffle()));
        System.out.println(Arrays.toString(solution.reset()));
        System.out.println(Arrays.toString(solution.shuffle()));
        System.out.println(Arrays.toString(solution.shuffle()));
        System.out.println(Arrays.toString(solution.shuffle()));

        ShuffleAnArray solution2 = new ShuffleAnArray(new int[]{1,2,3,4,5,6,7,8,9});
        System.out.println(Arrays.toString(solution2.shuffle()));
        System.out.println(Arrays.toString(solution2.shuffle()));
        System.out.println(Arrays.toString(solution2.shuffle()));
        System.out.println(Arrays.toString(solution2.reset()));
        System.out.println(Arrays.toString(solution2.shuffle()));
        System.out.println(Arrays.toString(solution2.shuffle()));
        System.out.println(Arrays.toString(solution2.shuffle()));
    }
}
