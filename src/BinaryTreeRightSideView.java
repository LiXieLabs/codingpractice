import javafx.util.Pair;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class BinaryTreeRightSideView {

    /************ Solution 1: BFS level order traversal **************/
    /**
     * Time: O(N)  Space: O(leaves) = O((N+1)/2) = O(N)
     */
    public List<Integer> rightSideView1(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode curr = queue.poll();
                if (i == size - 1) res.add(curr.val);
                if (curr.left != null) queue.offer(curr.left);
                if (curr.right != null) queue.offer(curr.right);
            }
        }
        return res;
    }

    /************ Solution 2: 先遍历右侧的 Recursive DFS **************/
    /**
     * Recursive Preorder Traversal - right first
     *
     * Time: O(N)  Space: O(height) = O(logN)
     */
    List<Integer> res;

    public List<Integer> rightSideView2(TreeNode root) {
        res = new ArrayList<>();
        dfs(root, 0);
        return res;
    }

    private void dfs(TreeNode curr, int level) {
        if (curr == null) return;
        if (level == res.size()) res.add(curr.val);
        if (curr.right != null) dfs(curr.right, level + 1);
        if (curr.left != null) dfs(curr.left, level + 1);
    }

    /************ Solution 3: 先遍历右侧的 Iterative DFS **************/
    /**
     * Iterative Preorder Traversal - right first
     *
     * Time: O(N)  Space: O(height) = O(logN)
     */
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Deque<Pair<TreeNode, Integer>> stack = new ArrayDeque<>();
        int depth = 0;
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                if (depth == res.size()) res.add(root.val);
                stack.push(new Pair<>(root, depth++));
                root = root.right;
            }
            Pair<TreeNode, Integer> pair = stack.pop();
            root = pair.getKey().left;
            depth = pair.getValue() + 1;
        }
        return res;
    }

    public static void main(String[] args) {
        BinaryTreeRightSideView solution = new BinaryTreeRightSideView();
        // TC1
        System.out.println(solution.rightSideView(
                new TreeNode(1,
                        new TreeNode(2, null, new TreeNode(5)),
                        new TreeNode(3, null, new TreeNode(4)))));
        // TC2
        System.out.println(solution.rightSideView(
                new TreeNode(1, null, new TreeNode(3))));
        // TC3
        System.out.println(solution.rightSideView(null));
    }
}