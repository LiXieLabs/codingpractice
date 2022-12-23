import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 366. Find Leaves of Binary Tree (https://leetcode.com/problems/find-leaves-of-binary-tree/description/)
 */
public class FindLeavesOfBinaryTree {

    // https://app.gitbook.com/o/-LEzdBqqw8FawHA3mleT/s/-LEzdBqr7mNEEfIbMstL/tree/366.-find-leaves-of-binary-tree

    /************** Solution 1: Track Height by Recur Post-order Traversal (最优解) ************/
    /**
     * 一个node的Height即是其在res List中的index
     *
     * Time: O(N) as post-order traversal
     * Space: O(H) avg O(logN) worst O(N)
     */
    public List<List<Integer>> findLeaves(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        find(root, res);
        this.print(res);
        return res;
    }

    private int find(TreeNode node, List<List<Integer>> res) {
        if (node == null) return -1;
        int currHeight = Math.max(this.find(node.left, res), this.find(node.right, res)) + 1;
        if (res.size() < currHeight + 1) res.add(new ArrayList<>());
        res.get(currHeight).add(node.val);
        return currHeight;
    }

    /************* Solution 2: Topological Sort *****************/
    /**
     *
     *
     * Time: O(2N) = O(N)   Space: O(2N) = O(N)
     */
    public List<List<Integer>> findLeaves1(TreeNode root) {
        // build adj list  & find initial 0 in-degree nodes
        Map<TreeNode, Integer> out = new HashMap<>();
        Map<TreeNode, TreeNode> parents = new HashMap<>();
        List<TreeNode> zeroIn = new ArrayList<>();
        Deque<TreeNode> queue = new ArrayDeque<>();
        if (root != null) queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode curr = queue.poll();
            if (curr.left == null && curr.right == null) {
                zeroIn.add(curr);
            } else {
                if (curr.left != null) {
                    out.put(curr, out.getOrDefault(curr, 0) + 1);
                    parents.put(curr.left, curr);
                    queue.offer(curr.left);
                }
                if (curr.right != null) {
                    out.put(curr, out.getOrDefault(curr, 0) + 1);
                    parents.put(curr.right, curr);
                    queue.offer(curr.right);
                }
            }
        }

        // peel onion
        List<List<Integer>> res = new ArrayList<>();
        while (!zeroIn.isEmpty()) {
            res.add(new ArrayList<>());
            List<TreeNode> nextZeroIn = new ArrayList<>();
            for (TreeNode curr : zeroIn) {
                res.get(res.size() - 1).add(curr.val);
                TreeNode parent = parents.get(curr);
                if (parent == null) continue;
                out.put(parent, out.get(parent) - 1);
                if (out.get(parent) == 0) nextZeroIn.add(parent);
            }
            zeroIn = nextZeroIn;
        }
        return res;
    }

    private void print(List<List<Integer>> res) {
//        for (List<Integer> lst : res) {
//            System.out.print(Arrays.toString(lst.toArray()));
//        }
        Arrays.stream(res.toArray()).forEach(System.out::println);
    }

    public static void main(String[] args) {
        FindLeavesOfBinaryTree solution = new FindLeavesOfBinaryTree();
        TreeNode root = new TreeNode(1,
                new TreeNode(2,
                        new TreeNode(4), new TreeNode(5)),
                new TreeNode(3)
        );
        System.out.println(solution.findLeaves1(root));
    }
}


