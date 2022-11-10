import java.util.ArrayDeque;
import java.util.Deque;

public class FlattenBinaryTreeToLinkedList {

    /*********** Solution 1: Iterative DFS by stack *************/
    /**
     * Time: O(N) visit each node once
     * Space: O(logN) by stack
     */
    public void flatten1(TreeNode root) {
        if (root == null) return;

        TreeNode dummy = new TreeNode(0);
        TreeNode prev = dummy;

        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode curr = stack.pop();
            if (curr.right != null) stack.push(curr.right);
            if (curr.left != null) stack.push(curr.left);
            curr.left = null;
            prev.right = curr;
            prev = curr;
        }
    }

    /*********** Solution 2: Recursive *************/
    /**
     * Time: O(N) visit each node once
     * Space: O(logN) by recur call stack
     */
    public void flatten2(TreeNode root) {
        recurFlattenAndReturnTail(root);
    }

    private TreeNode recurFlattenAndReturnTail(TreeNode root) {
        if (root == null || root.left == null && root.right == null) return root;
        TreeNode leftTail = recurFlattenAndReturnTail(root.left);
        TreeNode rightTail = recurFlattenAndReturnTail(root.right);
        if (leftTail != null) {
            leftTail.right = root.right;
            root.right = root.left;
            root.left = null;
        }
        return rightTail == null ? leftTail : rightTail;
    }

    /*********** Solution 3: Morris Traversal 的变种 *************/
    /**
     * 经典的 Morris Traversal 会在第一次经过 curr 时，把 curr 的 left subtree 的 rightMost 连接到 curr 上.
     * 这里，我们直接把 rightMost 连接到 curr.right, 同时把 curr.left 挪至 curr.right, 且 curr.left 变 null，完成变形.
     * 则经典 Morris Traversal 中的三种情况的处理方式 (两种 curr=curr.right, 一种 curr=curr.left), 都统一为 curr=curr.right.
     *
     * Time: O(2N) = O(N) visit each node twice
     * 第一次作为currNode，第二次穿越rightMost.right
     *
     * Space: O(1)
     */
    public void flatten(TreeNode root) {
        while (root != null) {
            if (root.left != null) {
                TreeNode predecessor = root.left;
                while (predecessor.right != null) {
                    predecessor = predecessor.right;
                }
                predecessor.right = root.right;
                root.right = root.left;
                root.left = null;
            }
            root = root.right;
        }
    }

    public static void main(String[] args) {
        FlattenBinaryTreeToLinkedList solution = new FlattenBinaryTreeToLinkedList();

        TreeNode root1 = new TreeNode(1,
                new TreeNode(2,
                        new TreeNode(3),
                        new TreeNode(4)),
                new TreeNode(5,
                        null,
                        new TreeNode(6)));
        solution.flatten(root1);
        Utils.print(root1);

        TreeNode root2 = new TreeNode(0);
        solution.flatten(root2);
        Utils.print(root2);

        TreeNode root3 = null;
        solution.flatten(root3);
        Utils.print(root3);
    }
}
