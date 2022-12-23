import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 545. Boundary of Binary Tree (https://leetcode.com/problems/boundary-of-binary-tree/description/)
 */
public class BoundaryOfBinaryTree {

    /**************** Solution 1: LeftBorder + Leaves + RightBorder ****************/
    /**
     * find left boundary & leaves by recur pre-order traversal
     * find right boundary by recur post-order traversal
     *
     * Time: O(2N)     Space: O(H) by recur stack
     */
    List<Integer> res;
    public List<Integer> boundaryOfBinaryTree1(TreeNode root) {
        res = new ArrayList<>();
        if (root == null) return res;
        res.add(root.val);
        if (root.left == null && root.right == null) return res;
        findBoundary(root.left, true);
        findLeaves(root);
        findBoundary(root.right, false);
        return res;
    }

    private void findBoundary(TreeNode curr, boolean isLeftBondary) {
        if (curr == null || curr.left == null && curr.right == null) return;
        if (isLeftBondary) {
            res.add(curr.val);
            if (curr.left != null) {
                findBoundary(curr.left, isLeftBondary);
            } else {
                findBoundary(curr.right, isLeftBondary);
            }
        } else {
            if (curr.right != null) {
                findBoundary(curr.right, isLeftBondary);
            } else {
                findBoundary(curr.left, isLeftBondary);
            }
            res.add(curr.val);
        }
    }

    private void findLeaves(TreeNode curr) {
        if (curr.left == null & curr.right == null) res.add(curr.val);
        if (curr.left != null) findLeaves(curr.left);
        if (curr.right != null) findLeaves(curr.right);
    }

    /**************** Solution 2: Pre-order Traversal + Status Flag ****************/
    /**
     * status:
     * 0 - root
     * 1 - left boundary
     * 2 - right boundary
     * 3 - root's left subtree's middle nodes
     * 4 - root's right subtree's middle nodes
     *
     * left is used to hold root + leftBoundary + root's left subtree's leaves (顺序先左后右)
     * right is used to hold rightBoundary + root's right subtree's leaves (顺序先右后左)
     * left + right.reverse => result
     *
     * Time: O(N ~ 2N)     Space: O(H) by recur stack
     */
    List<Integer> left, right;
    public List<Integer> boundaryOfBinaryTree(TreeNode root) {
        left = new ArrayList<>();
        right = new ArrayList<>();
        find(root, 0);
        Collections.reverse(right);
        left.addAll(right);
        return left;
    }

    private void find(TreeNode curr, int status) {
        if (curr == null) return;
        if (status == 0) { // curr is root
            left.add(curr.val);
            find(curr.left, 1);
            find(curr.right, 2);
        } else if (status == 1) { // curr is left boundary
            left.add(curr.val);
            if (curr.left != null) {
                find(curr.left, 1);
                find(curr.right, 3);
            } else {
                find(curr.right, 1);
            }
        } else if (status == 2) { // curr is right boundary
            right.add(curr.val);
            if (curr.right != null) {
                find(curr.right, 2);
                find(curr.left, 4);
            } else {
                find(curr.left, 2);
            }
        } else  { // curr is middle nodes (could be leaves)
            if (curr.left == null && curr.right == null) { // leaves
                List<Integer> subtree = status == 3 ? left : right;
                subtree.add(curr.val);
            } else if (status == 3) {
                find(curr.left, status);
                find(curr.right, status);
            } else {
                find(curr.right, status);
                find(curr.left, status);
            }
        }
    }

    public static void main(String[] args) {
        BoundaryOfBinaryTree solution = new BoundaryOfBinaryTree();

        TreeNode root1 = new TreeNode(1,
                null,
                new TreeNode(2,
                        new TreeNode(3),
                        new TreeNode(4)));
        System.out.println(solution.boundaryOfBinaryTree(root1)); // [1,3,4,2]

        TreeNode root2 = new TreeNode(1,
                new TreeNode(2,
                        new TreeNode(4),
                        new TreeNode(5,
                                new TreeNode(7),
                                new TreeNode(8))),
                new TreeNode(3,
                        new TreeNode(6,
                                new TreeNode(9),
                                new TreeNode(10)),
                        null));
        System.out.println(solution.boundaryOfBinaryTree(root2)); // [1,2,4,7,8,9,10,6,3]
    }
}
