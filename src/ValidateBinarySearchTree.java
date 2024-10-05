import java.util.ArrayDeque;
import java.util.Deque;

public class ValidateBinarySearchTree {

    /************* Solution 1: Recursive Post-order Traversal **************/
    /**
     * 返回值 {subtree最小值，subtree最大值，该subtree是否是valid BST}
     * base case 不能是 root == null, return {MAX_INT, MIN_INT, 1}, 因为有等于最大最小值情况！！！
     *
     * Time: O(N) each node visited once
     * Space: recur stack O(H), worst O(N)
     */
    public boolean isValidBST1(TreeNode root) {
        if (root == null) return true;
        return validate(root)[2] == 1;
    }

    public int[] validate(TreeNode root) {
        int lMin = root.val, rMax = root.val;
        if (root.left != null) {
            int[] left = validate(root.left);
            if (left[2] == 0 || left[1] >= root.val) {
                return new int[]{0, 0, 0};
            }
            lMin = left[0];
        }
        if (root.right != null) {
            int[] right = validate(root.right);
            if (right[2] == 0 || right[0] <= root.val) {
                return new int[]{0, 0, 0};
            }
            rMax = right[1];
        }
        return new int[]{lMin, rMax, 1};
    }

    /************* Solution 2: Iterative In-order Traversal **************/
    /**
     * Time: O(N) each node visited once
     * Space: stack O(H), worst O(N)
     */
    public boolean isValidBST2(TreeNode root) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        Integer prev = null;
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            if (prev != null && prev >= root.val) return false;
            prev = root.val;
            root = root.right;
        }
        return true;
    }

    /************* Solution 3: Recursive In-order Traversal **************/
    /**
     * 注意 prev 是怎么移动的！！！
     *
     * Time: O(N) each node visited once
     * Space: recur stack O(H), worst O(N)
     */
    Integer prev = null;
    public boolean isValidBST(TreeNode root) {
        prev = null;
        return inorderRecur(root);
    }

    private boolean inorderRecur(TreeNode curr) {
        if (curr == null) return true;
        if (!inorderRecur(curr.left)) return false;
        if (prev != null && prev >= curr.val) return false;
        prev = curr.val;
        return inorderRecur(curr.right);
    }

    public static void main(String[] args) {
        ValidateBinarySearchTree solution = new ValidateBinarySearchTree();
        TreeNode root1 = new TreeNode(5,
                new TreeNode(1),
                new TreeNode(4,
                        new TreeNode(3), new TreeNode(6))
        );
        TreeNode root2 = new TreeNode(4,
                new TreeNode(2,
                        new TreeNode(1), new TreeNode(3)),
                new TreeNode(6,
                        new TreeNode(5), new TreeNode(7))
        );
        System.out.println(solution.isValidBST(root1)); // false
        System.out.println(solution.isValidBST(root2)); // true
    }
}
