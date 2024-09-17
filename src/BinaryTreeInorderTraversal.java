import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 94. Binary Tree Inorder Traversal (https://leetcode.com/problems/binary-tree-inorder-traversal/description/)
 */
public class BinaryTreeInorderTraversal {

    /******************** Solution 1: recursive *************************/
    /**
     * Time: T(N) = 2T(N/2) + 1 = O(N)
     * Space: Average O(logN) Worst O(N) by recur call stack
     * 也可以用同一个method，每次initiate一个list，左右结果addAll()
     */
    public List<Integer> inorderTraversal1(TreeNode root) {
        List<Integer> res = new LinkedList<>();
        recur(root, res);
        return res;
    }

    public void recur(TreeNode root, List<Integer> res) {
        if (root == null) return;
        recur(root.left, res);
        res.add(root.val);
        recur(root.right, res);
    }

    /******************* Solution 2: Iterative *************************/
    /**
     * Time: O(N) Each node is traversed twice, first time put in stack & second time take from stack and put into res
     * Space: Average O(logN) Worst O(N) by recur call stack
     */
    public List<Integer> inorderTraversal2(TreeNode root) {
        List<Integer> res = new LinkedList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            res.add(root.val);
            root = root.right;
        }
        return res;
    }

    /******************** Solution 3: Morris Traversal *************************/
    /**
     * Time: O(3N) = O(N), each node/edge is visited 3 times - find predecessor/inorder traversal/disconnect
     * Space: O(1)
     * 优点: 无recurisve, 无stack, constant space
     * 算法:
     * Step 1: Initialize current as root
     * Step 2: While current is not NULL,
     *           If current does not have left child
     *             a. Add current’s value
     *             b. Go to the right, i.e., current = current.right
     *           Else
     *             In current's left subtree, keep going right until None or == curr
     *             If is None # 1st time visit
     *               a. Connect rightmost to curr
     *               b. Go to left, i.e., current = current.left
     *             Else # 2nd time visit
     *               a. Disconnect rightmost to curr
     *               b. Add current’s value
     *               c. Go to the right, i.e., current = current.right
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new LinkedList<>();
        while (root != null) {
            if (root.left == null) {
                res.add(root.val);
                root = root.right;
            } else {
                TreeNode predecessor = root.left;
                while (predecessor.right != null && predecessor.right != root) {
                    predecessor = predecessor.right;
                }
                if (predecessor.right == null) {
                    predecessor.right = root;
                    root = root.left;
                } else {
                    // predecessor == root
                    predecessor.right = null;
                    res.add(root.val);
                    root = root.right;
                }
            }
        }
        return res;
    }

    public static String print(List<Integer> input) {
        return input.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    public static void main(String[] args) {
        BinaryTreeInorderTraversal solution = new BinaryTreeInorderTraversal();
        // TC1: [1,3,2]
        TreeNode root1 = new TreeNode(1, null, new TreeNode(2, new TreeNode(3), null));
        System.out.println(print(solution.inorderTraversal(root1)));

        // TC2: [4,2,5,1,6,3,7]
        TreeNode root2 = new TreeNode(1,
                new TreeNode(2, new TreeNode(4), new TreeNode(5)),
                new TreeNode(3, new TreeNode(6), new TreeNode(7))
        );
        System.out.println(print(solution.inorderTraversal(root2)));
    }
}
