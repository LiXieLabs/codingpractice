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
            List<Integer> currVal = new ArrayList<>();
            for (TreeNode curr : currLevel) {
                if (curr == null) {
                    currVal.add(null);
                } else {
                    currVal.add(curr.val);
                    nextLevel.add(curr.left);
                    nextLevel.add(curr.right);
                }
            }
            int l = 0, r = currVal.size() - 1;
            while (l < r) {
                // 不能用 equals，因为不是 null safe 的！！！
                if (currVal.get(l++) != currVal.get(r--)) return false;
            }
            currLevel = nextLevel;
        }
        return true;
    }

    /******************* Solution 2: Recursive - Post Order Traversal ***********************/
    public boolean isSymmetric(TreeNode root) {
        return root == null || recur(root.left, root.right);
    }

    private boolean recur(TreeNode l, TreeNode r) {
        if (l == null && r == null) return true; // Base case 1
        if (l == null || r == null) return false; // Base case 2
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
