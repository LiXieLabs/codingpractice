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
     * Time: O(N)  Space: O(height) = O(logN)
     */
    List<Integer> res;

    public List<Integer> rightSideView(TreeNode root) {
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