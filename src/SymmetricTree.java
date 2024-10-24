import java.util.ArrayList;
import java.util.List;

/**
 * 101. Symmetric Tree (https://leetcode.com/problems/symmetric-tree/description/)
 */
public class SymmetricTree {

    /**************** Solution 1: Iterative - Level Order Traversal ********************/
    public boolean isSymmetric1(TreeNode root) {
        List<TreeNode> currLevel = new ArrayList<>();
        currLevel.add(root);
        while (!currLevel.isEmpty()) {
            List<TreeNode> nextLevel = new ArrayList<>();
            List<String> lst = new ArrayList<>();
            for (TreeNode node : currLevel) {
                lst.add(node == null ? "n" : String.valueOf(node.val));
                if (node != null) {
                    nextLevel.add(node.left);
                    nextLevel.add(node.right);
                }
            }
            int l = 0, r = lst.size() - 1;
            while (l <= r) {
                // Integer equals不是null safe的
                // 如果用Integer需要类似solution2里面recur终止条件那样判断！！！
                if (!lst.get(l++).equals(lst.get(r--))) return false;
            }
            currLevel = nextLevel;
        }
        return true;
    }

    /******************* Solution 2: Recursive ***********************/
    public boolean isSymmetric(TreeNode root) {
        return root == null || recur(root.left, root.right);
    }

    private boolean recur(TreeNode l, TreeNode r) {
        if (l == null && r == null) return true;
        if (l == null || r == null) return false;
        if (!recur(l.left, r.right)) return false;
        if (!recur(l.right, r.left)) return false;
        return l.val == r.val;
    }

    public static void main(String[] args) {
        SymmetricTree solution = new SymmetricTree();

        TreeNode root1 = new TreeNode(1,
                new TreeNode(2, new TreeNode(3), null),
                new TreeNode(2, null, new TreeNode(3)));
        System.out.println(solution.isSymmetric(root1)); // true

        TreeNode root2 = new TreeNode(1,
                new TreeNode(2, new TreeNode(3), null),
                new TreeNode(2, new TreeNode(3), null));
        System.out.println(solution.isSymmetric(root2)); // false
    }
}
