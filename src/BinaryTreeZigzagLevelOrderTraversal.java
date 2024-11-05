import javafx.util.Pair;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 103. Binary Tree Zigzag Level Order Traversal (https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/description/)
 */
class BinaryTreeZigzagLevelOrderTraversal {

    /******************** Solution 1: BFS + Collections.reverse ********************/
    /**
     * Time: O(N)  Space: O(N)
     */
    public List<List<Integer>> binaryTreeZigzagLevelOrderTraversal1(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;
        List<TreeNode> curr = new ArrayList<>();
        curr.add(root);
        boolean reverse = false;
        while (!curr.isEmpty()) {
            List<TreeNode> next = new ArrayList<>();
            List<Integer> curLevel = new ArrayList<>();
            for (TreeNode n : curr) {
                curLevel.add(n.val);
                if (n.left != null) next.add(n.left);
                if (n.right !=  null) next.add(n.right);
            }
            if (reverse) {
                Collections.reverse(curLevel);
            }
            reverse = !reverse;
            res.add(curLevel);
            curr = next;
        }
        return res;
    }

    /******************** Solution 2: BFS Deque queue + LinkedList ********************/
    /**
     * Time: O(N)  Space: O(N)
     */
    public List<List<Integer>> binaryTreeZigzagLevelOrderTraversal2(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;
        Deque<TreeNode> currQueue = new ArrayDeque<>();
        currQueue.offer(root);
        boolean reverse = false;
        while (!currQueue.isEmpty()) {
            Deque<TreeNode> nextQueue = new ArrayDeque<>();
            // 小心!!! 必须 LinkedList 才能用 addFirst/Last!!!
            LinkedList<Integer> curLevel = new LinkedList<>();
            for (TreeNode n : currQueue) {
                if (reverse) {
                    curLevel.addFirst(n.val);
                } else {
                    curLevel.addLast(n.val);
                }
                if (n.left != null) nextQueue.add(n.left);
                if (n.right !=  null) nextQueue.add(n.right);
            }
            reverse = !reverse;
            res.add(curLevel);
            currQueue = nextQueue;
        }
        return res;
    }

    /******************** Solution 3: DFS Deque stack + LinkedList ********************/
    /**
     * Time: O(N)  Space: O(N)
     */
    public List<List<Integer>> binaryTreeZigzagLevelOrderTraversal(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;
        Deque<Pair<TreeNode, Integer>> currStack = new ArrayDeque<>();
        currStack.push(new Pair<>(root, 0));
        while (!currStack.isEmpty()) {
            Pair<TreeNode, Integer> p = currStack.pop();
            TreeNode n = p.getKey();
            int level = p.getValue();
            if (res.size() - 1 < level) res.add(new LinkedList<>());
            if (level % 2 == 0) {
                // 注意!!! 必须 cast 成 LinkedList
                ((LinkedList<Integer>) res.get(level)).addLast(n.val);
            } else {
                // 注意!!! 必须 cast 成 LinkedList
                ((LinkedList<Integer>) res.get(level)).addFirst(n.val);
            }
            if (n.right != null) currStack.push(new Pair(n.right, level + 1));
            if (n.left != null) currStack.push(new Pair(n.left, level + 1));
        }
        return res;
    }

    private static void print(List<List<Integer>> input) {
        System.out.println("[" +
                input.stream()
                        .map(level -> "[" + level.stream().map(String::valueOf).collect(Collectors.joining(",")) + "]")
                        .collect(Collectors.joining(","))
                + "]");
    }

    public static void main(String[] args) {
        BinaryTreeZigzagLevelOrderTraversal solution = new BinaryTreeZigzagLevelOrderTraversal();

        // [[3],[20,9],[15,7]]
        TreeNode tree1 = new TreeNode(3,
                new TreeNode(9),
                new TreeNode(20,
                        new TreeNode(15),
                        new TreeNode(7)));
        print(solution.binaryTreeZigzagLevelOrderTraversal(tree1));

        // [[1]]
        TreeNode tree2 = new TreeNode(1);
        print(solution.binaryTreeZigzagLevelOrderTraversal(tree2));

        // []
        TreeNode tree3 = null;
        print(solution.binaryTreeZigzagLevelOrderTraversal(tree3));
    }
}


