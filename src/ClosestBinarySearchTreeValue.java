import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 270. Closest Binary Search Tree Value (https://leetcode.com/problems/closest-binary-search-tree-value/)
 */
public class ClosestBinarySearchTreeValue {

    /**************** Solution 1: Iterative Inorder Traversal by Stack *****************/
    /**
     * Time: O(H + Target) worst O(N)   Space: O(H) by stack
     */
    public int closestValue1(TreeNode root, double target) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        int prev = Integer.MIN_VALUE;
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            if (prev < target && target <= root.val)
                return Math.abs(target - prev) < Math.abs(target - root.val) ? prev : root.val;
            prev = root.val;
            root = root.right;
        }
        return prev;
    }

    /**************** Solution 2: Binary Search in BST *****************/
    /**
     * Time: O(H) worst O(N)   Space: O(1)
     */
    public int closestValue(TreeNode root, double target) {
        int res = root.val;
        double minDiff = Math.abs(target - res);
        while (root != null) {
            double newDiff = Math.abs(target - root.val);
            if (newDiff < minDiff) {
                res = root.val;
                minDiff = newDiff;
            }
            if (root.val < target) {
                root = root.right;
            } else if (root.val > target) {
                root = root.left;
            } else {
                return res;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        ClosestBinarySearchTreeValue solution = new ClosestBinarySearchTreeValue();

        TreeNode root1 = new TreeNode(4,
                new TreeNode(2,
                        new TreeNode(1),
                        new TreeNode(3)),
                new TreeNode(5));
        System.out.println(solution.closestValue(root1, 3.714286));

        TreeNode root2 = new TreeNode(1);
        System.out.println(solution.closestValue(root2, 3.714286));

        TreeNode root3 = new TreeNode(1, null, new TreeNode(2));
        System.out.println(solution.closestValue(root3, 3.714286));

    }
}
