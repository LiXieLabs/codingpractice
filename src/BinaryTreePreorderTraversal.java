import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 144. Binary Tree Preorder Traversal (https://leetcode.com/problems/binary-tree-preorder-traversal/description/)
 */
public class BinaryTreePreorderTraversal {

    /******************** Solution 1: recursive *************************/
    /**
     * 也可以用同一个method，每次initiate一个list，左右结果addAll()
     * 也可以用LinkedList，操作相同
     */
    List<Integer> res = new ArrayList<>();

    public List<Integer> preorderTraversal1(TreeNode root) {
        res = new ArrayList<>();
        recur(root);
        return res;
    }

    public void recur(TreeNode root) {
        if (root == null) return;
        res.add(root.val); // -----> 在这加！
        recur(root.left);
        recur(root.right);
    }

    /******************* Solution 2: DFS by Stack *************************/
    public List<Integer> preorderTraversal2(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode curr = stack.pop();
            res.add(curr.val); // -----> 在这加！
            if (curr.right != null) stack.push(curr.right);
            if (curr.left != null) stack.push(curr.left);
        }
        return res;
    }

    /******************* Solution 3: Iterative *************************/
    public List<Integer> preorderTraversal3(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                res.add(root.val); // -----> 在这加！
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            root = root.right;
        }
        return res;
    }

    /******************* Solution 4: Morris Traversal *************************/
    /**
     * Time: O(3N) = O(N), each node/edge is visited 3 times - find predecessor/inorder traversal/disconnect
     * Space: O(1)
     * 优点: 无recursive, 无stack, constant space
     * 算法:
     * Step 1: Initialize current as root
     * Step 2: While current is not NULL,
     *           If current does not have left child
     *             a. Add current’s value
     *             b. Go to the right, i.e., current = current.right
     *           Else
     *             In current's left subtree, keep going right until right == null or curr
     *             If is None # 1st time visit
     *               a. Connect rightmost to curr
     *               b. Add current’s value
     *               c. Go to left, i.e., current = current.left
     *             Else # 2nd time visit
     *               a. Disconnect rightmost to curr
     *               b. Go to the right, i.e., current = current.right
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        while (root != null) {
            if (root.left == null) {
                res.add(root.val); // -----> 在这加！
                root = root.right;
            } else {
                TreeNode predecessor = root.left;
                while (predecessor.right != null && predecessor.right != root) {
                    predecessor = predecessor.right;
                }
                if (predecessor.right == null) {
                    res.add(root.val); // -----> 在这加！
                    predecessor.right = root;
                    root = root.left;
                } else { // predecessor.right == root
                    predecessor.right = null;
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
        BinaryTreePreorderTraversal solution = new BinaryTreePreorderTraversal();
        // TC1
        TreeNode root1 = new TreeNode(1, null, new TreeNode(2, new TreeNode(3), null));
        System.out.println(print(solution.preorderTraversal(root1))); // 1,2,3

        // TC2
        TreeNode root2 = new TreeNode(1,
                new TreeNode(2, new TreeNode(4), new TreeNode(5)),
                new TreeNode(3, new TreeNode(6), new TreeNode(7))
        );
        System.out.println(print(solution.preorderTraversal(root2))); // 1,2,4,5,3,6,7
    }
}
