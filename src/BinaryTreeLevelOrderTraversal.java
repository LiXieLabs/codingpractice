import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 102. Binary Tree Level Order Traversal (https://leetcode.com/problems/binary-tree-level-order-traversal/description/)
 */
public class BinaryTreeLevelOrderTraversal {

    /************* Solution 1: Iterative level-order traversal ******************/
    public List<List<Integer>> levelOrder1(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            List<Integer> currLevel = new ArrayList<>();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                currLevel.add(node.val);
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            res.add(currLevel);
        }
        return res;
    }

    /************* Solution 2: Recursive DFS + level number ******************/
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        recur(root, res, 0);
        return res;
    }

    private void recur(TreeNode node, List<List<Integer>> res, int level) {
        if (node == null) return;
        if (level == res.size()) res.add(new ArrayList<>());
        res.get(level).add(node.val);
        recur(node.left, res, level + 1);
        recur(node.right, res, level + 1);
    }

    public static void print(List<List<Integer>> input) {
        System.out.println("[" +
                input.stream()
                        .map(level -> "[" + level.stream().map(String::valueOf).collect(Collectors.joining(",")) + "]")
                        .collect(Collectors.joining(","))
                + "]");
    }

    public static void main(String[] args) {
        BinaryTreeLevelOrderTraversal solution = new BinaryTreeLevelOrderTraversal();

        TreeNode root1 = new TreeNode(3,
                new TreeNode(9),
                new TreeNode(20, new TreeNode(15), new TreeNode(7)));
        print(solution.levelOrder(root1)); // [[3],[9,20],[15,7]]

        TreeNode root2 = null;
        print(solution.levelOrder(root2)); // []

        TreeNode root3 = new TreeNode(3);;
        print(solution.levelOrder(root3)); // [[3]]
    }

}
