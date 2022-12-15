/**
 * 333. Largest BST Subtree (https://leetcode.com/problems/largest-bst-subtree/description/)
 */
public class LargestBSTSubtree {

    /************ Solution 1: Post-order Traversal ******************/
    /**
     * Time: O(N)   Space: O(H) by recur call stack, worst case O(N)
     */
    public int largestBSTSubtree(TreeNode root) {
        return recur(root)[2];
    }

    private int[] recur(TreeNode curr) {
        if (curr == null) return new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE, 0};
        int[] left = recur(curr.left);
        int[] right = recur(curr.right);
        if (left[1] < curr.val && curr.val < right[0]) {
            return new int[]{Math.min(left[0], curr.val), Math.max(right[1], curr.val), left[2] + right[2] + 1};
        } else {
            return new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE, Math.max(left[2], right[2])};
        }
    }

    public static void main(String[] args) {
        LargestBSTSubtree solution = new LargestBSTSubtree();
        TreeNode root1 = new TreeNode(10,
                new TreeNode(5,
                        new TreeNode(1),
                        new TreeNode(8)),
                new TreeNode(15,
                        null,
                        new TreeNode(7))
        );
        System.out.println(solution.largestBSTSubtree(root1));
    }
}
