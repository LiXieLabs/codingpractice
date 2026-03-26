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

    private Integer dfs(TreeNode curr) {
        if (curr == null) return null;
        Integer res = dfs(curr.left);
        if (res != null) return res;
        if (--k == 0) return curr.val;
        return dfs(curr.right);
    }

    // 也可以无返回值，用全局 i 标记范围点！
    int i, res;

    public int kthSmallest2(TreeNode root, int k) {
        i = 1;
        this.k = k;
        recur(root);
        return res;
    }

    private void recur(TreeNode curr) {
        if (curr == null) return;
        recur(curr.left);
        if (i++ == k) {
            res = curr.val;
            return;
        }
        recur(curr.right);
    }

    /**
     * FOLLOW UP!!!
     * If the BST is modified often (i.e., we can do insert and delete operations)
     * and you need to find the kth smallest frequently, how would you optimize?
     *
     * https://leetcode.com/problems/kth-smallest-element-in-a-bst/solution/
     *
     * 方案 1：在每个节点维护 size（子树节点数）+ 用平衡 BST
     * 需要维护
     * 对每个节点加字段：
     * size = left.size + right.size + 1
     *
     * 查询 kth smallest（O(log n)）
     * 设 leftSize = size(node.left)：
     * 如果 k == leftSize + 1：答案是当前节点
     * 如果 k <= leftSize：去左子树
     * 否则：去右子树找第 k - (leftSize + 1) 小
     *
     * 更新（insert/delete）怎么快？
     * 插入 / 删除时沿路径更新 size
     * 但为了保证复杂度，需要平衡：AVL / Red-Black / Treap / Splay 都行
     * 这样：
     * insert/delete：O(log n)
     * kth query：O(log n)
     *
     * 面试里可以说：“用带 size 的 balanced BST（Order Statistic Tree）”。
     *
     * 本质是design一个有 (1) insert (2) delete (3) find kth smallest 的结构
     * 不加优化分别是         O(H)       O(H)       O(H+K)
     * 用类似LRU Cache的链表实现
     * 则变化为              O(H)       O(H)       O(K)
     * 类似数据库中的 B tree -> B+ tree 优化
     */

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