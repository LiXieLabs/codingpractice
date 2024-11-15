import java.util.ArrayList;
import java.util.List;

/**
 * 104. Maximum Depth of Binary Tree (https://leetcode.com/problems/maximum-depth-of-binary-tree/description/)
 */
public class MaximumDepthOfBinaryTree {

    /*********** Soluion 1: Iterative BFS ***************/
    /**
     * Time: O(N)
     * Space: O(leaves) = O((N+1)/2) = O(N)
     */
    public int maxDepth1(TreeNode root) {
        if (root == null) return 0;
        int depth = 0;
        List<TreeNode> currLevel = new ArrayList<>();
        currLevel.add(root);
        while (!currLevel.isEmpty()) {
            List<TreeNode> nextLevel = new ArrayList<>();
            for (TreeNode curr : currLevel) {
                if (curr.left != null) nextLevel.add(curr.left);
                if (curr.right != null) nextLevel.add(curr.right);
            }
            currLevel = nextLevel;
            depth++;
        }
        return depth;
    }

    /************ Solution 2: Recursive DFS *************/
    /**
     * Time: O(N)
     * Space: O(stack) = O(depth) = average O(logN) - worst O(N)
     */
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    public static void main(String[] args) {
        MaximumDepthOfBinaryTree solution = new MaximumDepthOfBinaryTree();

        // 0
        System.out.println(solution.maxDepth(null));

        // 2
        System.out.println(solution.maxDepth(new TreeNode(1, null, new TreeNode(2))));

        // 3
        System.out.println(solution.maxDepth(new TreeNode(3,
                new TreeNode(9),
                new TreeNode(20,
                        new TreeNode(15),
                        new TreeNode(7)))));
    }
}

