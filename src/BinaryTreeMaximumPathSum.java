public class BinaryTreeMaximumPathSum {

    int maxSum;

    /******************** Solution 1: Recur DFS 携带可继续经过当前点的路径最大值，同时更新全局最大值 *********************/
    /**
     * Time: O(N)   Space: O(logN) by call stack
     */
    public int maxPathSum(TreeNode root) {
        maxSum = Integer.MIN_VALUE;
        recurCalc(root);
        return maxSum;
    }

    // recur写法一
    private int recurCalc(TreeNode root) {
        if (root == null) return 0; // 小心！！！不能用Integer.MIN_VALUE, TC3会溢出！！！
        int left = recurCalc(root.left);
        int right = recurCalc(root.right);
        int res = Math.max(root.val, Math.max(left + root.val, right + root.val)); // 包含root的三种情况的最大值，向下传递
        maxSum = Math.max(maxSum, Math.max(root.val + left + right, res)); // 包含root的三种情况 + 跨过root的情况，更新全局最大值
        return res;
    }

    // recur写法二
    private int recurCalcAlternative(TreeNode root) {
        if (root == null) return 0;
        int left = Math.max(recurCalcAlternative(root.left), 0); // 如果左侧最大路径<=0，则只要root更优
        int right = Math.max(recurCalcAlternative(root.right), 0); // 如果右侧最大路径<=0，则只要root更优
        maxSum = Math.max(maxSum, root.val + left + right); // 包含root的三种情况 + 跨过root的情况，更新全局最大值
        return Math.max(root.val + left, root.val + right); // 包含root的三种情况的最大值，向下传递
    }

    public static void main(String[] args) {
        BinaryTreeMaximumPathSum solution = new BinaryTreeMaximumPathSum();

        // TC1
        TreeNode root1 = new TreeNode(1,
                new TreeNode(2),
                new TreeNode(3));
        System.out.println(solution.maxPathSum(root1));

        // TC2
        TreeNode root2 = new TreeNode(-10,
                new TreeNode(9),
                new TreeNode(20,
                        new TreeNode(15),
                        new TreeNode(7)));
        System.out.println(solution.maxPathSum(root2));

        // TC3
        TreeNode root3 = new TreeNode(-1);
        System.out.println(solution.maxPathSum(root3));
    }
}