import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 145. Binary Tree Postorder Traversal (https://leetcode.com/problems/binary-tree-postorder-traversal/description/)
 */
public class BinaryTreePostorderTraversal {

    /******************** Solution 1: recursive *************************/
    /**
     * 也可以用同一个method，每次initiate一个list，左右结果addAll()
     */
    public List<Integer> postorderTraversal1(TreeNode root) {
        List<Integer> res = new LinkedList<>();
        recur(root, res);
        return res;
    }

    public void recur(TreeNode root, List<Integer> res) {
        if (root == null) return;
        recur(root.left, res);
        recur(root.right, res);
        res.add(root.val); // -----> 在这加！
    }

    /**
     * Solution 2 & 3 & 4 are based on the facts:
     * Tree:                   [1,2,3,4,5,6,7]
     * Postorder:              [4,5,2,6,7,3,1]
     * Reverse of Postorder:   [1,3,7,6,2,5,4]
     * Preorder (right=>left): [1,3,7,6,2,5,4]
     * 按照 preorder，但是先右后左！
     */

    /******************** Solution 2: DFS by Stack *************************/
    public List<Integer> postorderTraversal2(TreeNode root) {
        List<Integer> res = new LinkedList<>();
        if (root == null) return res;
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode curr = stack.pop();
            res.add(curr.val); // -----> 在这加！
            if (curr.left != null) stack.push(curr.left);
            if (curr.right != null) stack.push(curr.right);
        }
        // reverse list
        Collections.reverse(res);
        return res;
    }

    /******************* Solution 3: Iterative *************************/
    public List<Integer> postorderTraversal3(TreeNode root) {
        List<Integer> res = new LinkedList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                res.add(root.val); // -----> 在这加！
                stack.push(root);
                root = root.right;
            }
            root = stack.pop();
            root = root.left;
        }
        // reverse list
        Collections.reverse(res);
        return res;
    }

    /******************** Solution 4: Morris Traversal ********************/
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        while (root != null) {
            if (root.right == null) {
                res.add(root.val); // -----> 在这加！
                root = root.left;
            } else {
                TreeNode predecessor = root.right;
                while (predecessor.left != null && predecessor.left != root) {
                    predecessor = predecessor.left;
                }
                if (predecessor.left == null) {
                    res.add(root.val); // -----> 在这加！
                    predecessor.left = root;
                    root = root.right;
                } else {
                    predecessor.left = null;
                    root = root.left;
                }
            }
        }
        Collections.reverse(res);
        return res;
    }

    public static String print(List<Integer> input) {
        return input.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    public static void main(String[] args) {
        BinaryTreePostorderTraversal solution = new BinaryTreePostorderTraversal();
        // TC#1: 3,2,1
        TreeNode root1 = new TreeNode(1, null, new TreeNode(2, new TreeNode(3), null));
        System.out.println(print(solution.postorderTraversal(root1)));
        // TC#2: 4,5,2,6,7,3,1
        TreeNode root2 = new TreeNode(1,
                new TreeNode(2, new TreeNode(4), new TreeNode(5)),
                new TreeNode(3, new TreeNode(6), new TreeNode(7))
        );
        System.out.println(print(solution.postorderTraversal(root2)));
    }
}
