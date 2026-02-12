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
    List<List<Integer>> res;

    public List<List<Integer>> findLeaves(TreeNode root) {
        res = new ArrayList<>();
        recur(root);
        return res;
    }

    private int recur(TreeNode curr) {
        if (curr == null) return 0;
        int h = Math.max(recur(curr.left), recur(curr.right));
        if (h > res.size() - 1) res.add(new ArrayList<>());
        res.get(h).add(curr.val);
        return h + 1;
    }

    /************* Solution 2: Topological Sort *****************/
    /**
     * Time: O(2N) = O(N)   Space: O(2N) = O(N)
     */
    public List<List<Integer>> findLeaves1(TreeNode root) {
        // build adj list  & find initial 0 in-degree nodes
        Map<TreeNode, Integer> in = new HashMap<>();
        Map<TreeNode, TreeNode> out = new HashMap<>();
        List<TreeNode> zeroIn = new ArrayList<>();
        Deque<TreeNode> queue = new ArrayDeque<>();
        if (root != null) queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode curr = queue.poll();
            if (curr.left == null && curr.right == null) {
                zeroIn.add(curr);
            } else {
                if (curr.left != null) {
                    in.put(curr, in.getOrDefault(curr, 0) + 1);
                    out.put(curr.left, curr);
                    queue.offer(curr.left);
                }
                if (curr.right != null) {
                    in.put(curr, in.getOrDefault(curr, 0) + 1);
                    out.put(curr.right, curr);
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
                TreeNode parent = out.get(curr);
                if (parent == null) continue;
                in.put(parent, in.get(parent) - 1);
                if (in.get(parent) == 0) nextZeroIn.add(parent);
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


