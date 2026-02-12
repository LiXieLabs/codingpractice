/**
 * 333. Largest BST Subtree (https://leetcode.com/problems/largest-bst-subtree/description/)
 */
public class LargestBSTSubtree {

    /************ Solution 1: Post-order Traversal ******************/
    /**
     * ⚠️注意⚠️ 只能用 98. Validate Binary Search Tree (https://leetcode.com/problems/validate-binary-search-tree/description/)
     * 的 Solution 1: Post-order Traversal，不能用 Solution 4: In-order Traversal
     * 因为 Solution 4 只维护了全局 prev，只能用于判断整棵树是不是 BST，不能帮助判断 BST subtree
     *
     * recur returns int[]{min, max, size}
     *
     * Time: O(N)   Space: O(H) by recur call stack, worst case O(N)
     */
    public int largestBSTSubtree(TreeNode root) {
        return recur(root)[2];
    }

    private int[] recur(TreeNode curr) {
        // Integer.MAX_VALUE, Integer.MIN_VALUE 保证了上层 validBST=true
        if (curr == null) return new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE, 0};
        int[] left = recur(curr.left);
        int[] right = recur(curr.right);
        if (left[1] < curr.val && curr.val < right[0]) {
            return new int[]{Math.min(left[0], curr.val), Math.max(right[1], curr.val), left[2] + right[2] + 1};
        } else {
            // Integer.MIN_VALUE, Integer.MAX_VALUE 保证了上层 validBST=false
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
