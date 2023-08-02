import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 742. Closest Leaf in a Binary Tree (https://leetcode.com/problems/closest-leaf-in-a-binary-tree/)
 */
public class ClosestLeafInABinaryTree {

    /************ Solution 1: DFS + BFS ****************/
    /**
     * DFS to convert to graph by building adj list
     * BFS from kNode to find closest leaf
     *
     * 需要跨root查找的需要看作graph而非tree
     * tree的adj list只需要parent，因为TreeNode结构left&right已经自动记录了其他相邻node
     *
     * Time: O(N)   Space: O(N)
     */
    public int findClosestLeaf(TreeNode root, int k) {
        // DFS to find k node and build parents
        Map<Integer, TreeNode> parents = new HashMap<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        TreeNode kNode = null;
        while (kNode == null) {
            TreeNode curr = stack.pop();
            if (curr.val == k) kNode = curr;
            for (TreeNode next : Arrays.asList(curr.right, curr.left)) {
                if (next != null) {
                    parents.put(next.val, curr);
                    stack.push(next);
                }
            }
        }

        // BFS from kNode
        Set<Integer> visited = new HashSet<>();
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(kNode);
        visited.add(kNode.val);
        while (!queue.isEmpty()) {
            TreeNode curr = queue.poll();
            if (curr.left == null && curr.right == null) return curr.val;
            for (TreeNode next : Arrays.asList(curr.left, curr.right, parents.getOrDefault(curr.val, null))) {
                if (next != null && !visited.contains(next.val)) {
                    queue.offer(next);
                    visited.add(next.val);
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        ClosestLeafInABinaryTree solution = new ClosestLeafInABinaryTree();

        TreeNode root1 = new TreeNode(1,
                new TreeNode(3),
                new TreeNode(2)
        );
        System.out.println(solution.findClosestLeaf(root1, 1));

        TreeNode root2 = new TreeNode(1);
        System.out.println(solution.findClosestLeaf(root2, 1));


        TreeNode root3 = new TreeNode(1,
                new TreeNode(2,
                        new TreeNode(4,
                                new TreeNode(5,
                                        new TreeNode(6),
                                        null),
                                null),
                        null),
                new TreeNode(3)
        );
        System.out.println(solution.findClosestLeaf(root3, 2));

    }

}
