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

    /****** Solution 2: Backtracking by Picking 1st -> 2nd -> 3rd num in subset ... until out of range  ********/
    /**
     * 思路参考 Solution 1 of
     * 90. Subsets II (https://leetcode.com/problems/subsets-ii/description/)
     *
     * Time: O(N X 2^N)   Space: O(N) by call stack
     */
    public List<List<Integer>> subsets2(int[] nums) {
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

    /****** Solution 3: Similar as Solution 2 but add at the end of nums, not beginning  ********/
    /**
     * Time: O(N X 2^N)   Space: O(N) by call stack
     */
    int[] nums;

    public List<List<Integer>> subsets3(int[] nums) {
        this.nums = nums;
        res = new ArrayList<>();
        recur(new ArrayList<>(), 0);
        return res;
    }

    private void recur(List<Integer> path, int start) {
        // base case -> deep copy + add to res
        if (start >= nums.length) {
            res.add(new ArrayList<>(path));
            return;
        }
        // ⚠️注意⚠️必须包含 i == nums.length ！！！
        // 不然只有 nums[-1] 包含情况才能被写入 res！！！
        for (int i = start; i <= nums.length; i++) {
            if (i < nums.length) path.add(nums[i]);
            recur(path, i + 1);
            if (i < nums.length) path.remove(path.size() - 1);
        }
    }

    /************* Solution 4: Iterative Solution - 类似 BFS / Binary Tree Level Order Traversal ****************/
    /**
     * 每个数字存在两种情况，在 or 不在 subset 里面
     * Iterative version of Solution 1
     *
     * initial: []
     * nums[0]: [] [1]
     * nums[1]: [] [1] [2] [1,2]
     * nums[2]: [] [1] [2] [1,2] [3] [1,3] [2,3] [1,2,3]
     *
     * Time: O(N X 2^N)   Space: O(1) without excluding result
     */
    public List<List<Integer>> subsets(int[] nums) {
        res = new ArrayList<>();
        res.add(new ArrayList<>());
        for (int n : nums) {
            int size = res.size();
            for (int i = 0; i < size; i++) {
                List<Integer> path = res.get(i);
                path.add(n);
                res.add(new ArrayList<>(path));
                path.remove(path.size() - 1);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Subsets solution = new Subsets();
        System.out.println(solution.subsets(new int[]{1,2,3})); // [[], [3], [2], [2, 3], [1], [1, 3], [1, 2], [1, 2, 3]]
        System.out.println(solution.subsets(new int[]{0})); // [[], [0]]
    }
}
