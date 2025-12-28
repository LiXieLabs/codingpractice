import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 90. Subsets II (https://leetcode.com/problems/subsets-ii/description/)
 */
public class SubsetsII {

    /**************** Solution 1: Backtracking *******************/
    /**
     * 第 1 层 recur 遍历 nums[0:] 找到第 1 个加入 subset 的值 nums[i1]
     * 第 2 层 recur 遍历 nums[i1+1:] 找到第 2 个加入 subset 的值 nums[i2]
     * 第 3 层 recur 遍历 nums[i2+1:] 找到第 3 个加入 subset 的值 nums[i3]
     * :
     * 实际是一个 N-ary tree，每个 node 都要计入结果，因此每个 recur 一开始都 res.add
     *
     * Time: O(Sort + Copy x Subsets) = O(NlogN + N X 2^N) = O(N X 2^N)
     * Space: O(Sort + Recur Call Stack) = O(Java QuickSort logN + N) = O(N)
     */
    List<List<Integer>> res;
    int[] nums;
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        res = new ArrayList<>();
        Arrays.sort(nums); // Necessary for TC#3!!!!!
        this.nums = nums;
        recur(0, new ArrayList<>());
        return res;
    }

    private void recur(int start, List<Integer> subset) {
        res.add(new ArrayList<>(subset));
        for (int i = start; i < nums.length; i++) {
            if (i == start || nums[i] != nums[i - 1]) {
                subset.add(nums[i]);
                recur(i + 1, subset);
                subset.remove(subset.size() - 1);
            }
        }
    }

    public static void main(String[] args) {
        SubsetsII solution = new SubsetsII();
        // TC#1
        System.out.println(solution.subsetsWithDup(new int[]{1,2,2})); // [[], [1], [1, 2], [1, 2, 2], [2], [2, 2], [2]]
        // TC#2
        System.out.println(solution.subsetsWithDup(new int[]{0})); // [[], [0]]
        // TC#3
        System.out.println(solution.subsetsWithDup(new int[]{4,4,4,1,4})); // [[], [1], [1, 4], [1, 4, 4], [1, 4, 4, 4], [1, 4, 4, 4, 4], [4], [4, 4], [4, 4, 4], [4, 4, 4, 4]]
    }
}
