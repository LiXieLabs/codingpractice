import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LowestCommonAncestorOfABinaryTree {

    /*********** Solution 1: Build Path -> check along path from root until mismatch ********/
    /**
     * Space Complexity: O(NlogN)
     * Complete binary tree with N nodes has (N+1) / 2 leaves.
     * Each in stack stores with a path to it of length logN
     * Thus, total is (N+1) / 2 * logN ~ O(NlogN)
     * Time Complexity: Avg:O(N+E)+O(logN)=>O(N)
     */
    public TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q) {
        List<TreeNode> path1 = findPath(root, p);
        List<TreeNode> path2 = findPath(root, q);
        int i = 0;
        while (path1.size() - i - 1 >= 0 && path2.size() - i - 1 >= 0
                && path1.get(path1.size() - i - 1) == path2.get(path2.size() - i - 1)) {
            i++;
        }
        return path1.get(path1.size() - i);
    }

    public List<TreeNode> findPath(TreeNode root, TreeNode node) {
        if (root == null) return null;
        if (root == node) {
            List<TreeNode> res = new ArrayList<>();
            res.add(root);
            return res;
        }
        List<TreeNode> leftPath = findPath(root.left, node);
        if (leftPath != null) {
            leftPath.add(root);
            return leftPath;
        }
        List<TreeNode> rightPath = findPath(root.right, node);
        if (rightPath != null) {
            rightPath.add(root);
            return rightPath;
        }
        return null;
    }

    /*********** Solution 2: Optimize Solution 1 - Build Path by HashMap in 1 iterate  ********/
    /**
     * Space Complexity: O(N)
     * In the worst case space utilized by the stack, the parent pointer dictionary and the ancestor set,
     * would be N each, since the height of a skewed binary tree could be N.
     * Time Complexity: O(N)
     * where N is the number of nodes in the binary tree. In the worst case we might be visiting all the nodes of the binary tree.
     */
    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        Map<TreeNode, TreeNode> parent = new HashMap<>();
        parent.put(root, null);
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        while (!parent.containsKey(p) || !parent.containsKey(q)) {
            TreeNode curr = stack.pop();
            if (curr.right != null) {
                parent.put(curr.right, curr);
                stack.push(curr.right);
            }
            if (curr.left != null) {
                parent.put(curr.left, curr);
                stack.push(curr.left);
            }
        }
        Set<TreeNode> path = new HashSet<>();
        while (p != null) {
            path.add(p);
            p = parent.get(p);
        }
        while (!path.contains(q)) {
            q = parent.get(q);
        }
        return q;
    }

    // backtracking 找 path，只需一遍
    List<TreeNode> path1, path2;
    TreeNode p, q;

    public TreeNode lowestCommonAncestor3(TreeNode root, TreeNode p, TreeNode q) {
        path1 = null;
        path2 = null;
        this.p = p;
        this.q = q;
        recur(root, new ArrayList<>());

        int i = 0, m = Math.min(path1.size(), path2.size());
        while (i < m && path1.get(i) == path2.get(i)) {
            i++;
        }
        return path1.get(i - 1);

    }

    private void recur(TreeNode curr, List<TreeNode> path) {
        if (curr == null) return;
        path.add(curr);
        // 找到 p q 不能提前结束，有可能 subtree 里面还有另一个
        if (curr == p) path1 = new ArrayList<>(path);
        if (curr == q) path2 = new ArrayList<>(path);
        if (path1 == null || path2 == null) recur(curr.left, path);
        if (path1 == null || path2 == null) recur(curr.right, path);
        path.remove(path.size() - 1);
    }

    /*********** Solution 3: Recursive Post-order Traversal ********/
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;
        if (root == p || root == q) return root;
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null) return root;
        return left != null ? left : right;
    }

    /*********** Solution 4: Iterative Post-order Traversal ********/
    // 有点复杂。。。还没看。。。
    // https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/solution/

    public static void main(String[] args) {
        LowestCommonAncestorOfABinaryTree solution = new LowestCommonAncestorOfABinaryTree();
        TreeNode root = new TreeNode(3,
                new TreeNode(5,
                        new TreeNode(6),
                        new TreeNode(2,
                                new TreeNode(7),
                                new TreeNode(4))),
                new TreeNode(1,
                        new TreeNode(0),
                        new TreeNode(8)));
        System.out.println(solution.lowestCommonAncestor(root, root.left.left, root.right.right).val);
        System.out.println(solution.lowestCommonAncestor(root, root.left.left, root.left.right.right).val);
    }
}
