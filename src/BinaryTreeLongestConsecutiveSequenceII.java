/**
 * 549. Binary Tree Longest Consecutive Sequence II (https://leetcode.com/problems/binary-tree-longest-consecutive-sequence-ii/)
 */
public class BinaryTreeLongestConsecutiveSequenceII {

    /*********** Solution 1: Bottom Up DFS by recur Post-order ***************/
    /**
     * Time: O(N)   Space: avg O(logN) worst O(N)
     */
    private int maxLen;
    public int longestConsecutive(TreeNode root) {
        maxLen = 0;
        recur(root);
        return maxLen;
    }

    // 0: increase; 1: decrease from leaf to root
    private int[] recur(TreeNode curr) {
        // Base case
        if (curr == null) return new int[]{0, 0};
        // 分治左右
        int[] left = recur(curr.left);
        int[] right = recur(curr.right);
        // 与curr比较，得到包含curr的increase/decrease结果
        // 用于更新全局maxLen和返回结果
        int increase = 1, decrease = 1;
        if (curr.left != null) {
            if (curr.left.val + 1 == curr.val) {
                increase = Math.max(increase, left[0] + 1);
            } else if (curr.left.val - 1 == curr.val) {
                decrease = Math.max(decrease, left[1] + 1);
            }
        }
        if (curr.right != null) {
            if (curr.right.val + 1 == curr.val) {
                increase = Math.max(increase, right[0] + 1);
            } else if (curr.right.val - 1 == curr.val) {
                decrease = Math.max(decrease, right[1] + 1);
            }
        }

        // 优化前：处理跨过curr的情况
        // 只用于更新全局maxLen，不用于返回结果
//        if (curr.left != null && curr.right != null) {
//            // 左->curr->右递增
//            if (curr.left.val + 1 == curr.val && curr.val + 1 == curr.right.val) {
//                maxLen = Math.max(maxLen, left[0] + 1 + right[1]);
//            }
//            // 左->curr->右递减
//            if (curr.left.val - 1 == curr.val && curr.val - 1 == curr.right.val) {
//                maxLen = Math.max(maxLen, left[1] + 1 + right[0]);
//            }
//        }
//        maxLen = Math.max(maxLen, Math.max(decrease, increase));

        // 优化后：不用单独处理跨过curr的情况！！！
        maxLen = Math.max(maxLen, decrease + increase - 1);
        return new int[]{increase, decrease};
    }

    public static void main(String[] args) {
        BinaryTreeLongestConsecutiveSequenceII solution = new BinaryTreeLongestConsecutiveSequenceII();

        TreeNode root1 = new TreeNode(1,
                new TreeNode(2),
                new TreeNode(3)
        );
        System.out.println(solution.longestConsecutive(root1)); // 2

        TreeNode root2 = new TreeNode(2,
                new TreeNode(1),
                new TreeNode(3)
        );
        System.out.println(solution.longestConsecutive(root2)); // 3
    }
}
