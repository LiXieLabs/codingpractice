import javafx.util.Pair;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 112. Path Sum (https://leetcode.com/problems/path-sum/description/)
 */
public class PathSum {

    /******* Solution 1: Recursive Pre-order Traversal ************/
    /**
     * Time: O(N)  Space: O(logN) by recur stack
     */
    public boolean hasPathSum1(TreeNode root, int targetSum) {
        return recur(root, targetSum, 0);
    }

    private boolean recur(TreeNode curr, int targetSum, int currSum) {
        if (curr == null) return false;
        currSum += curr.val;
        if (curr.left == null && curr.right == null) return currSum == targetSum;
        return recur(curr.left, targetSum, currSum) ||
                recur(curr.right, targetSum, currSum);

    }

    /******* Solution 2: DFS Pre-order Traversal ************/
    /**
     * Time: O(N)  Space: O(logN) by stack
     */
    public boolean hasPathSum2(TreeNode root, int targetSum) {
        if (root == null) return false;
        Deque<Pair<TreeNode, Integer>> stack = new ArrayDeque<>();
        stack.push(new Pair<>(root, root.val));
        while (!stack.isEmpty()) {
            Pair<TreeNode, Integer> curr = stack.pop();
            TreeNode currNode = curr.getKey();
            int currSum = curr.getValue();
            if (currNode.left == null && currNode.right == null && currSum == targetSum) return true;
            if (currNode.right != null) stack.push(new Pair<>(currNode.right, currNode.right.val + currSum));
            if (currNode.left != null) stack.push(new Pair<>(currNode.left, currNode.left.val + currSum));
        }
        return false;
    }

    /******* Solution 3: Iterative Pre-order Traversal ************/
    /**
     * Time: O(N)  Space: O(logN) by stack
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return false;
        Deque<Pair<TreeNode, Integer>> stack = new ArrayDeque<>();
        TreeNode currNode = root;
        int currSum = 0;
        while (currNode != null || !stack.isEmpty()) {
            while (currNode != null) {
                currSum += currNode.val;
                if (currNode.left == null && currNode.right == null & currSum == targetSum) return true;
                stack.push(new Pair<>(currNode, currSum));
                currNode = currNode.left;
            }
            Pair<TreeNode, Integer> curr = stack.pop();
            currNode = curr.getKey();
            currSum = curr.getValue();
            currNode = currNode.right;
        }
        return false;
    }

    public static void main(String[] args) {
        PathSum solution = new PathSum();

        TreeNode root1 = new TreeNode(5,
                new TreeNode(4,
                        new TreeNode(11,
                                new TreeNode(7),
                                new TreeNode(2)),
                        null),
                new TreeNode(8,
                        new TreeNode(13),
                        new TreeNode(4,
                                null,
                                new TreeNode(1)))
        );
        System.out.println(solution.hasPathSum(root1, 22)); // true

        TreeNode root2 = new TreeNode(5,
                new TreeNode(4,
                        new TreeNode(11,
                                new TreeNode(7),
                                new TreeNode(2)),
                        null),
                new TreeNode(8,
                        new TreeNode(13),
                        new TreeNode(4,
                                null,
                                new TreeNode(1)))
        );
        System.out.println(solution.hasPathSum(root1, 3)); // false

        System.out.println(solution.hasPathSum(null, 1)); // false

        System.out.println(solution.hasPathSum(new TreeNode(1), 1)); // true

    }
}
