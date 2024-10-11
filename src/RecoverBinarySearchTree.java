import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 99. Recover Binary Search Tree (https://leetcode.com/problems/recover-binary-search-tree/description/)
 */
public class RecoverBinarySearchTree {

    /************** Solution 1: Iterative In-order traversal ************/
    // Time最优
    /**
     * in-order遍历，
     * 第一次遇到数值下降的情况，则把prev赋给n1
     * 每一次遇到数值下降的情况，则把curr赋给n2
     *
     * 两种情况：
     * 1，3，2，4，5，6，7 一次下降
     *   n1 n2
     * 1，7，3，4，5，6，2 两次下降
     *   n1           n2
     */
    public void recoverTree1(TreeNode root) {
        TreeNode n1 = null, n2 = null, prev = null;
        Deque<TreeNode> stack = new ArrayDeque<>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            if (prev != null && root.val <= prev.val) {
                if (n1 == null) {
                    n1 = prev;
                }
                // 不能在else里面，因为有[1,3,2,4]的情况！！！
                n2 = root;
            }
            prev = root;
            root = root.right;
        }
        // Swap
        int temp = n1.val;
        n1.val = n2.val;
        n2.val = temp;
    }

    /************** Solution 2: Morris traversal ************/
    // Space最优
    public void recoverTree2(TreeNode root) {
        TreeNode n1 = null, n2 = null, prev = null, curr = root;
        while (curr != null) {
            if (curr.left == null) {
                if (prev != null && curr.val <= prev.val) {
                    if (n1 == null) {
                        n1 = prev;
                    }
                    n2 = curr;
                }
                prev = curr;
                curr = curr.right;
            } else {
                TreeNode predecessor = curr.left;
                while (predecessor.right != null && predecessor.right != curr) {
                    predecessor = predecessor.right;
                }
                if (predecessor.right == null) {
                    predecessor.right = curr;
                    curr = curr.left;
                } else { // predecessor.right == curr
                    predecessor.right = null;
                    if (prev != null && curr.val <= prev.val) {
                        if (n1 == null) {
                            n1 = prev;
                        }
                        n2 = curr;
                    }
                    prev = curr;
                    curr = curr.right;
                }
            }
        }
        // Swap
        int temp = n1.val;
        n1.val = n2.val;
        n2.val = temp;
    }

    /************** Solution 3: Recursive In-order traversal ************/
    // Code最简单
    TreeNode n1 = null, n2 = null, prev = null;
    public void recoverTree(TreeNode root) {
        recur(root);
        // Swap
        int temp = n1.val;
        n1.val = n2.val;
        n2.val = temp;
    }

    private void recur(TreeNode curr) {
        if (curr == null) return;
        recur(curr.left);
        if (prev != null && prev.val >= curr.val) {
            if (n1 == null) n1 = prev;
            n2 = curr;
        }
        prev = curr;
        recur(curr.right);
    }

    public static void main(String[] args) {
        RecoverBinarySearchTree solution = new RecoverBinarySearchTree();

        // TC1
        TreeNode root1 = new TreeNode(4,
                new TreeNode(7, new TreeNode(1), new TreeNode(3)),
                new TreeNode(6, new TreeNode(5), new TreeNode(2))
        );
        Utils.print(root1); // [4,7,6,1,3,5,2,null,null,null,null,null,null,null,null]
        solution.recoverTree(root1);
        Utils.print(root1); // [4,2,6,1,3,5,7,null,null,null,null,null,null,null,null]

        // TC2
        TreeNode root2 = new TreeNode(3,
                new TreeNode(1),
                new TreeNode(4, new TreeNode(2), null)
        );
        Utils.print(root2); // [3,1,4,null,null,2,null,null,null]
        solution.recoverTree(root2);
        Utils.print(root2); // [2,1,4,null,null,3,null,null,null]
    }
}
