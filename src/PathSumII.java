import java.util.ArrayList;
import java.util.List;

/**
 * 113. Path Sum II (https://leetcode.com/problems/path-sum-ii/description/)
 */
public class PathSumII {

    /************ Solution 1: Recursive Pre-order Traversal / Backtracking **************/
    /**
     * 带 path 不太好写成 iterative
     * 写成 recur backtracking 比较方便
     *
     * Time: O(travesal + list copy for each leaf in worst case) = O(N + logN X N / 2) = O(NlogN) worst O(N^2)
     * Space: O(logN) by stack + O(NlogN) by list copy into res
     */
    List<List<Integer>> res;
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        res = new ArrayList<>();
        recur(root, targetSum, 0, new ArrayList<>());
        return res;
    }

    private void recur(TreeNode currNode, int targetSum, int currSum, List<Integer> path) {
        if (currNode == null) return;
        currSum += currNode.val;
        path.add(currNode.val);
        if (currNode.left == null && currNode.right == null) {
            if (targetSum == currSum) res.add(new ArrayList<>(path)); // 注意！！！一定要 new ArrayList<>()！！！否则会变
        } else {
            recur(currNode.left, targetSum, currSum, path);
            recur(currNode.right, targetSum, currSum, path);
        }
        path.remove(path.size() - 1); // 注意！！！相当于 backtracking！！！回溯一定要移除路径，path终将包含整个preorder traversal path
    }

    public static void main(String[] args) {
        PathSumII solution = new PathSumII();

        TreeNode root1 = new TreeNode(5,
                new TreeNode(4,
                        new TreeNode(11,
                                new TreeNode(7),
                                new TreeNode(2)),
                        null),
                new TreeNode(8,
                        new TreeNode(13),
                        new TreeNode(4,
                                new TreeNode(5),
                                new TreeNode(1)))
        );
        System.out.println(solution.pathSum(root1, 22)); //[[5, 4, 11, 2], [5, 8, 4, 5]]
    }
}
