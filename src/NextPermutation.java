import java.util.Arrays;
import java.util.stream.Collectors;

public class NextPermutation {

    /************* Solution 1: Two Pointers ****************/
    /**
     * Time: O(N)  Space: O(1)
     */
    public void nextPermutation(int[] nums) {
        // special case
        if (nums.length <= 1) return;

        // 从后往前找到第一个递增结束的位置，该位置之后已经是最大的了，没有变大的空间了
        // 注意，等号情况也要往前走，因为后面几个数字全都相等的话也就没有增大的空间了
        int cur = nums.length - 1;
        while (cur - 1 >= 0 && nums[cur - 1] >= nums[cur]) {
            cur--;
        }
        // 翻转尾部递增部分
        swap(nums, cur, nums.length - 1);
        // 5,4,3,2,1 => 1,2,3,4,5 直接结束
        if (cur == 0) return;
        // 从后到前找到最后一个大于pre的位置，和pre调换
        int pre = cur - 1;
        cur = nums.length - 1;
        while (cur > pre + 1 && nums[cur - 1] > nums[pre]) {
            cur--;
        }
        int temp = nums[pre];
        nums[pre] = nums[cur];
        nums[cur] = temp;
    }

    private void swap(int[] nums, int l, int r) {
        while (l < r) {
            int temp = nums[l];
            nums[l++] = nums[r];
            nums[r--] = temp;
        }
    }

    public static void main(String[] args) {
        NextPermutation solution = new NextPermutation();

        int[] input = new int[]{1,2,3,4,5};
        for (int i = 0; i < 11; i++) {
            solution.nextPermutation(input);
            System.out.println(Arrays.stream(input).boxed().collect(Collectors.toList()));
        }

    }
}
