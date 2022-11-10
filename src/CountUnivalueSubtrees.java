public class CountUnivalueSubtrees {

    /*************** Solution 1: Recursive DFS *****************/
    /**
     * count() returns int[] 标记了 {该root开始的subtree是否满足univalue，该root开始的subtree有多少个univalue的subtree}
     * 第一个值，1则当前subtree满足univalue，0则不满足univalue
     *
     * Time: O(N)   Space: O(H) = average O(logN) worst O(N)
     */
    public int countUnivalSubtrees(TreeNode root) {
        return count(root)[1];
    }

    public int[] count(TreeNode root) {
        if (root == null) return new int[]{0, 0};
        if (root.left == null && root.right == null) return new int[]{1, 1};
        int[] left = count(root.left);
        int[] right = count(root.right);
        int res = left[1] + right[1];
        if ((root.left == null || left[0] == 1 && root.left.val == root.val)
                && (root.right == null || right[0] == 1 && root.right.val == root.val)) {
            return new int[]{1, res + 1};
        }
        return new int[]{0, res};
    }

    public static void main(String[] args) {
        CountUnivalueSubtrees solution = new CountUnivalueSubtrees();
        // TC1
        System.out.println(solution.countUnivalSubtrees(
                new TreeNode(5,
                        new TreeNode(1,
                                new TreeNode(5),
                                new TreeNode(5)),
                        new TreeNode(5,
                                null,
                                new TreeNode(5)))
        ));
        // TC2
        System.out.println(solution.countUnivalSubtrees(null));
        // TC3
        System.out.println(solution.countUnivalSubtrees(
                new TreeNode(5,
                        new TreeNode(5,
                                new TreeNode(5),
                                new TreeNode(5)),
                        new TreeNode(5,
                                null,
                                new TreeNode(5)))
        ));
    }
}