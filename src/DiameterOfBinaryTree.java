/**
 * 543. Diameter of Binary Tree (https://leetcode.com/problems/diameter-of-binary-tree/description/)
 */
public class DiameterOfBinaryTree {

    /********** Solution 1: Recur Post-order Traversal *************/
    /**
     * Time: O(N)   Space: O(H) avg O(logN) worst O(N)
     */
    int maxNodeCnt;
    public int diameterOfBinaryTree(TreeNode root) {
        maxNodeCnt = 0;
        recur(root);
        return maxNodeCnt - 1;
    }

    private int recur(TreeNode curr) {
        if (curr == null) return 0;
        int left = recur(curr.left);
        int right = recur(curr.right);
        maxNodeCnt = Math.max(maxNodeCnt, left + right + 1);
        return Math.max(left, right) + 1;
    }

    public static void main(String[] args) {
        DiameterOfBinaryTree solution = new DiameterOfBinaryTree();
        TreeNode root1 = new TreeNode(1,
                new TreeNode(2,
                        new TreeNode(4),
                        new TreeNode(5)),
                new TreeNode(3));
        System.out.println(solution.diameterOfBinaryTree(root1)); // 3
    }
}
