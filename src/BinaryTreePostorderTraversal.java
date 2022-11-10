import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
        res.add(root.val);
    }

    /**
     * Solution 2 & 3 are based on facts:
     * Tree:                   [1,2,3,4,5,6,7]
     * Postorder:              [4,5,2,6,7,3,1]
     * Reverse of Postorder:   [1,3,7,6,2,5,4]
     * Preorder (right=>left): [1,3,7,6,2,5,4]
     */

    /******************** Solution 2: DFS by Stack *************************/
    public List<Integer> postorderTraversal2(TreeNode root) {
        List<Integer> res = new LinkedList<>();
        if (root == null) return res;
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode curr = stack.pop();
            res.add(curr.val);
            if (curr.left != null) stack.push(curr.left);
            if (curr.right != null) stack.push(curr.right);
        }
        // reverse list
        Collections.reverse(res);
        return res;
    }

    /******************* Solution 3: Iterative *************************/
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new LinkedList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                res.add(root.val);
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

    public static String print(List<Integer> input) {
        return input.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    public static void main(String[] args) {
        BinaryTreePostorderTraversal solution = new BinaryTreePostorderTraversal();
        TreeNode root1 = new TreeNode(1, null, new TreeNode(2, new TreeNode(3), null));
        System.out.println(print(solution.postorderTraversal(root1)));
        TreeNode root2 = new TreeNode(1,
                new TreeNode(2, new TreeNode(4), new TreeNode(5)),
                new TreeNode(3, new TreeNode(6), new TreeNode(7))
        );
        System.out.println(print(solution.postorderTraversal(root2)));
    }
}
