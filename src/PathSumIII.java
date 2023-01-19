import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 437. Path Sum III (https://leetcode.com/problems/path-sum-iii/description/)
 */
public class PathSumIII {

    /****************** Solution 1: Carry prefix sum list from root to leaf ****************/
    /**
     * Time: O(N^2)   Space: O(N)
     */
    private int res1;
    public int pathSum1(TreeNode root, int targetSum) {
        res1 = 0;
        if (root != null) recur(root, targetSum, new ArrayList<>(Arrays.asList(0l)));
        return res1;
    }

    // 注意！！！List<Long>不然sum会溢出整型，参见TC root3
    private void recur(TreeNode curr, int targetSum, List<Long> prefixSums) {
        long currSum = prefixSums.get(prefixSums.size() - 1) + curr.val;
        for (long prefixSum : prefixSums) {
            if (currSum - prefixSum == targetSum) res1++;
        }
        prefixSums.add(currSum);
        if (curr.left != null) recur(curr.left, targetSum, prefixSums);
        if (curr.right != null) recur(curr.right, targetSum, prefixSums);
        prefixSums.remove(prefixSums.size() - 1);
    }

    /*************************** Solution 2: Prefix Sum One Pass 优化 *************************/
    /**
     * 完全等同于 Tree 版的 Solution 3 of
     * 560. Subarray Sum Equals K (https://leetcode.com/problems/subarray-sum-equals-k/description/)
     *
     * 用HashMap保存该路径上prev prefix sum => prevSum : count
     * currSum - prevSum == targetSum
     * 则 currSum - targetSum == prevSum => 在HashMap counter中查找
     * 再将 currSum 作为 prevSum 更新到 counter 中
     * 该 subtree 遍历完，需要 backtracking 将 currSum 从 counter 中移除
     *
     * Time: O(N)   Space: O(N)
     */
    private int res2;
    private Map<Long, Integer> counter;
    public int pathSum2(TreeNode root, int targetSum) {
        res2 = 0;
        counter = new HashMap<>();
        counter.put(0l, 1); // 为了 currSum == targetSum 可以计入结果
        if (root != null) recur2(root, targetSum, 0l);
        return res2;
    }

    private void recur2(TreeNode curr, int targetSum, long currSum) {
        currSum += curr.val;
        res2 += counter.getOrDefault(currSum - targetSum, 0);
        counter.put(currSum, counter.getOrDefault(currSum, 0) + 1);
        if (curr.left != null) recur2(curr.left, targetSum, currSum);
        if (curr.right != null) recur2(curr.right, targetSum, currSum);
        counter.put(currSum, counter.get(currSum) - 1);
    }

    public static void main(String[] args) {
        PathSumIII solution = new PathSumIII();

        TreeNode root1 = new TreeNode(10,
                new TreeNode(5,
                        new TreeNode(3,
                                new TreeNode(3),
                                new TreeNode(-2)),
                        new TreeNode(2,
                                null,
                                new TreeNode(1))),
                new TreeNode(-3,
                        null,
                        new TreeNode(11))
        );
        System.out.println(solution.pathSum2(root1, 8));


        TreeNode root2 = new TreeNode(1);
        System.out.println(solution.pathSum2(root2, 1));

        TreeNode root3 = new TreeNode(1000000000,
                new TreeNode(1000000000,
                        new TreeNode(294967296,
                                new TreeNode(1000000000,
                                        new TreeNode(1000000000,
                                                new TreeNode(1000000000),
                                                null),
                                        null),
                                null),
                        null),
                null);
        System.out.println(solution.pathSum2(root3, 1));
    }
}
