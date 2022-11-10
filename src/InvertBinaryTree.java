import java.util.ArrayDeque;
import java.util.Deque;

public class InvertBinaryTree {

    /*********** Solution 1: Recursive DFS ****************/
    /**
     * Time: O(N)   Space: O(logN)
     */
    public TreeNode invertTree1(TreeNode root) {
        if (root == null || root.left == null && root.right == null) return root;
        TreeNode newLeft = invertTree(root.left);
        TreeNode newRight = invertTree(root.right);
        root.left = newRight;
        root.right = newLeft;
        return root;
    }

    /*********** Solution 2: Iterative BFS/DFS ***************/
    /**
     * queue 还是 stack 都不影响结果
     *
     * Time: O(N)   Space: O(N or logN)
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null || root.left == null && root.right == null) return root;
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode curr = queue.poll();
            TreeNode temp = curr.left;
            curr.left = curr.right;
            curr.right = temp;
            if (curr.left != null) queue.offer(curr.left);
            if (curr.right != null) queue.offer(curr.right);
        }
        return root;
    }

    public static void main(String[] args) {
        InvertBinaryTree solution = new InvertBinaryTree();

        TreeNode root1 =
                new TreeNode(4,
                        new TreeNode(2,
                                new TreeNode(1),
                                new TreeNode(3)),
                        new TreeNode(7,
                                new TreeNode(6),
                                new TreeNode(9)));
        Utils.print(root1);
        Utils.print(solution.invertTree(root1));

    }
}