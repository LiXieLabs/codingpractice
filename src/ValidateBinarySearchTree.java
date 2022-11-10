import java.util.ArrayDeque;
import java.util.Deque;

public class ValidateBinarySearchTree {

    /************* Solution 1: Recursive Post-order Traversal **************/
    /**
     * 返回值 {subtree最小值，subtree最大值，该subtree是否是valid BST}
     * base case 不能是 root == null, return {MAX_INT, MIN_INT, 1}, 因为有等于最大最小值情况！！！
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
    public boolean isValidBST(TreeNode root) {
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
    // Need a class variable to hold prev!!!
    // https://leetcode.com/problems/validate-binary-search-tree/solution/

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
        System.out.println(solution.isValidBST(root1));
        System.out.println(solution.isValidBST(root2));
    }
}
