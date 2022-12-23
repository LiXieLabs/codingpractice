/**
 * 298. Binary Tree Longest Consecutive Sequence (https://leetcode.com/problems/binary-tree-longest-consecutive-sequence/description/)
 */
public class BinaryTreeLongestConsecutiveSequence {

    /*********** Solution 1: Bottom Up DFS by recur Post-order ***************/
    /**
     * curLen 标记，包含当前node的，以当前node为root的subtree的最长consecutive sequence length
     * maxLen 标记，不一定包含当前node的，以当前node为root的subtree的最长consecutive sequence length
     *
     * 碰到不满足递增，将curLen归1
     * 每次更新全局最优maxLen
     *
     * Time: O(N) same as post-order traversal
     * Space: O(H) avg O(logN) worst O(N)
     */
    public int longestConsecutive1(TreeNode root) {
        return recur1(root)[0];
    }

    private int[] recur1(TreeNode root) {
        if (root == null) return new int[]{0, 0};
        int maxLen, curLen;
        int[] left = recur1(root.left);
        if (left[1] != 0 && root.left.val - 1 == root.val) {
            curLen = left[1] + 1;
        } else {
            curLen = 1;
        }
        maxLen = Math.max(curLen, left[0]);
        int[] right = recur1(root.right);
        if (right[1] != 0 && root.right.val - 1 == root.val) {
            curLen = Math.max(curLen, right[1] + 1);
        } else {
            curLen = Math.max(curLen, 1);
        }
        maxLen = Math.max(maxLen, Math.max(curLen, right[0]));
        return new int[]{maxLen, curLen};
    }

    /*********** Solution 2: Solution 1 优化 ***************/
    /**
     * Time: O(N) same as post-order traversal
     * Space: O(H) avg O(logN) worst O(N)
     */
    int maxLen;
    public int longestConsecutive2(TreeNode root) {
        maxLen = 0;
        recur2(root);
        return maxLen;
    }

    private int recur2(TreeNode root) {
        if (root == null) return 0;
        int leftLen = recur2(root.left) + 1;
        int rightLen = recur2(root.right) + 1;
        if (root.left != null && root.left.val - 1 != root.val) {
            leftLen = 1;
        }
        if (root.right != null && root.right.val - 1 != root.val) {
            rightLen = 1;
        }
        int curLen = Math.max(leftLen, rightLen);
        maxLen = Math.max(maxLen, curLen);
        return curLen;
    }

    /*********** Solution 3: Top Down DFS by recur Pre-order (最优解) ***************/
    /**
     * Time: O(N) same as pre-order traversal
     * Space: O(H) avg O(logN) worst O(N)
     */
    public int longestConsecutive(TreeNode root) {
        maxLen = 0;
        recur3(root, null, 0);
        return maxLen;
    }

    private void recur3(TreeNode curr, TreeNode parent, int curLen) {
        if (curr == null) return;
        curLen = parent != null && parent.val + 1 == curr.val ? curLen + 1 : 1;
        maxLen = Math.max(maxLen, curLen);
        recur3(curr.left, curr, curLen);
        recur3(curr.right, curr, curLen);
    }

    public static void main(String[] args) {
        BinaryTreeLongestConsecutiveSequence solution = new BinaryTreeLongestConsecutiveSequence();

        TreeNode root1 = new TreeNode(1,
                null,
                new TreeNode(3,
                        new TreeNode(2),
                        new TreeNode(4,
                                null,
                                new TreeNode(5))));
        System.out.println(solution.longestConsecutive(root1)); // 3

        TreeNode root2 = new TreeNode(2,
                null,
                new TreeNode(3,
                        new TreeNode(2,
                                new TreeNode(1),
                                null),
                        null));
        System.out.println(solution.longestConsecutive(root2)); // 2

        TreeNode root3 = new TreeNode(3,
                new TreeNode(2,
                        new TreeNode(4,
                                null,
                                new TreeNode(5,
                                        new TreeNode(6),
                                        null)),
                        null),
                new TreeNode(4));
        System.out.println(solution.longestConsecutive(root3)); // 3
    }
}
