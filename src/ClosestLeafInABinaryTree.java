import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 742. Closest Leaf in a Binary Tree (https://leetcode.com/problems/closest-leaf-in-a-binary-tree/)
 */
public class ClosestLeafInABinaryTree {

    /************ Solution 1: DFS + BFS ****************/
    /**
     * 类似 863. All Nodes Distance K in Binary Tree (https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/)
     *
     * DFS to convert to graph by building adj list
     * BFS from kNode to find the closest leaf
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
        // ⚠️⚠️⚠️ 注意 ⚠️⚠️⚠️ parents 只需要 root 到 kNode 这一段的！！！
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
            for (TreeNode next : Arrays.asList(curr.left, curr.right, parents.get(curr.val))) {
                if (next != null && !visited.contains(next.val)) {
                    queue.offer(next);
                    visited.add(next.val);
                }
            }
        }
        return -1;
    }

    // 双 BFS 更快！
    public int findClosestLeaf2(TreeNode root, int k) {
        Map<TreeNode, TreeNode> parents = new HashMap<>();
        List<TreeNode> currLevel = new ArrayList<>();
        currLevel.add(root);
        TreeNode found = null;
        while (found == null) {
            List<TreeNode> nextLevel = new ArrayList<>();
            for (TreeNode curr : currLevel) {
                if (curr.val == k) {
                    found = curr;
                    break;
                }
                if (curr.left != null) {
                    parents.put(curr.left, curr);
                    nextLevel.add(curr.left);
                }
                if (curr.right != null) {
                    parents.put(curr.right, curr);
                    nextLevel.add(curr.right);
                }
            }
            currLevel = nextLevel;
        }

        Set<Integer> visited = new HashSet<>();
        visited.add(k);
        currLevel = new ArrayList<>();
        currLevel.add(found);
        while (true) {
            List<TreeNode> nextLevel = new ArrayList<>();
            for (TreeNode curr : currLevel) {
                if (curr.left == null && curr.right == null) return curr.val;
                if (curr.left != null && visited.add(curr.left.val)) {
                    nextLevel.add(curr.left);

                }
                if (curr.right != null && visited.add(curr.right.val)) {
                    nextLevel.add(curr.right);
                }
                if (parents.get(curr) != null && visited.add(parents.get(curr).val)) {
                    nextLevel.add(parents.get(curr));
                }
            }
            currLevel = nextLevel;
        }
    }

    public static void main(String[] args) {
        ClosestLeafInABinaryTree solution = new ClosestLeafInABinaryTree();

        TreeNode root1 = new TreeNode(1,
                new TreeNode(3),
                new TreeNode(2)
        );
        System.out.println(solution.findClosestLeaf(root1, 1)); // 3

        TreeNode root2 = new TreeNode(1);
        System.out.println(solution.findClosestLeaf(root2, 1)); // 1


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
        System.out.println(solution.findClosestLeaf(root3, 2)); // 3
    }

}
