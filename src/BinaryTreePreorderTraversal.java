import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class BinaryTreePreorderTraversal {

    /******************** Solution 1: recursive *************************/
    /**
     * 也可以用同一个method，每次initiate一个list，左右结果addAll()
     * 也可以用LinkedList，操作相同
     */
    public List<Integer> preorderTraversal1(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        recur(root, res);
        return res;
    }

    public void recur(TreeNode root, List<Integer> res) {
        if (root == null) return;
        res.add(root.val);
        recur(root.left, res);
        recur(root.right, res);
    }

    /******************* Solution 2: DFS by Stack *************************/
    public List<Integer> preorderTraversal2(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode curr = stack.pop();
            res.add(curr.val);
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
                res.add(root.val);
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
                res.add(root.val);
                root = root.right;
            } else {
                TreeNode predecessor = root.left;
                while (predecessor.right != null && predecessor.right != root) {
                    predecessor = predecessor.right;
                }
                if (predecessor.right == null) {
                    res.add(root.val);
                    predecessor.right = root;
                    root = root.left;
                } else {
                    // predecessor == root
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
        TreeNode root1 = new TreeNode(1, null, new TreeNode(2, new TreeNode(3), null));
        System.out.println(print(solution.preorderTraversal(root1)));
        TreeNode root2 = new TreeNode(1,
                new TreeNode(2, new TreeNode(4), new TreeNode(5)),
                new TreeNode(3, new TreeNode(6), new TreeNode(7))
        );
        System.out.println(print(solution.preorderTraversal(root2)));
    }
}
