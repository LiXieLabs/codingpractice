import java.util.ArrayDeque;
import java.util.Deque;

public class KthSmallestElementInABST {

    /************* Solution 1: Iterative Inorder Traversal ************/
    /**
     * Time: O(logN + k)   Space: O(Height) = average O(logN) worst O(N)
     */
    public int kthSmallest1(TreeNode root, int k) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        while (true) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            if (--k == 0) return root.val;
            root = root.right;
        }
    }

    /************ Solution 2: Recursive Inorder Traversal *************/
    /**
     * Time: O(k)   Space: O(Height) by recur call stack = average O(logN) worst O(N)
     */
    int k;
    public int kthSmallest(TreeNode root, int k) {
        this.k = k;
        return dfs(root);
    }

    /**
     * If the BST is modified often (i.e., we can do insert and delete operations)
     * and you need to find the kth smallest frequently, how would you optimize?
     *
     * https://leetcode.com/problems/kth-smallest-element-in-a-bst/solution/
     *
     * 本质是design一个有 (1) insert (2) delete (3) find kth smallest 的结构
     * 不加优化分别是         O(H)       O(H)       O(H+K)
     * 用类似LRU Cache的链表实现
     * 则变化为              O(H)       O(H)       O(K)
     * 类似数据库中的 B tree -> B+ tree 优化
     */

    private Integer dfs(TreeNode curr) {
        if (curr == null) return null;
        Integer res = dfs(curr.left);
        if (res != null) return res;
        if (--k == 0) return curr.val;
        return dfs(curr.right);
    }

    public static void main(String[] args) {
        KthSmallestElementInABST solution = new KthSmallestElementInABST();
        // TC1
        System.out.println(solution.kthSmallest(
                new TreeNode(3,
                        new TreeNode(1, null, new TreeNode(2)),
                        new TreeNode(4))
                , 1));
        // TC2
        System.out.println(solution.kthSmallest(
                new TreeNode(5,
                        new TreeNode(3,
                                new TreeNode(2, new TreeNode(1), null),
                                new TreeNode(4)),
                        new TreeNode(6))
                , 3));
    }
}