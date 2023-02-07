import java.util.ArrayList;
import java.util.List;

/**
 * 78. Subsets (https://leetcode.com/problems/subsets/description/)
 */
public class Subsets {

    /************* Solution 1: Backtracking by 2 Cases (Include/Exclude each num) ****************/
    /**
     * 每个值有两种情况 (1)包含 or (2)不包含 在subset里面
     * 故一共有 2^N 种 subsets
     *
     * Time: O(N X 2^N)
     * 一共有 2^N 个解，每个解需要一条 N 的 recur stack 才能得到，故 O(N X 2^N)
     *
     * Space: O(N) by call stack
     */
    List<List<Integer>> res;
    public List<List<Integer>> subsets1(int[] nums) {
        res = new ArrayList<>();
        recur1(nums, 0, new ArrayList<>());
        return res;
    }

    private void recur1(int[] nums, int i, List<Integer> subset) {
        // Base Case: 复制 subset 给结果
        if (i == nums.length) {
            // copy list!!!
            res.add(new ArrayList<>(subset));
            return;
        }
        // (1) 不包含当前值
        recur1(nums, i + 1, subset);
        // (2) 包含当前值
        subset.add(nums[i]);
        recur1(nums, i + 1, subset);
        subset.remove(subset.size() - 1);
    }

    /************* Solution 2: Backtracking by Picking 1st->2nd->3rd...num until out of range  ****************/
    /**
     * 思路参考 Solution 1 of
     * 90. Subsets II (https://leetcode.com/problems/subsets-ii/description/)
     *
     * Time: O(N X 2^N)   Space: O(N) by call stack
     */
    public List<List<Integer>> subsets(int[] nums) {
        res = new ArrayList<>();
        recur(nums, 0, new ArrayList<>());
        return res;
    }

    private void recur(int[] nums, int start, List<Integer> subset) {
        res.add(new ArrayList<>(subset));
        for (int i = start; i < nums.length; i++) {
            subset.add(nums[i]);
            recur(nums, i + 1, subset);
            subset.remove(subset.size() - 1);
        }
    }

    public static void main(String[] args) {
        Subsets solution = new Subsets();
        System.out.println(solution.subsets(new int[]{1,2,3})); // [[], [3], [2], [2, 3], [1], [1, 3], [1, 2], [1, 2, 3]]
        System.out.println(solution.subsets(new int[]{0})); // [[], [0]]
    }
}
