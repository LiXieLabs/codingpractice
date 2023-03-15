/**
 * 669. Trim a Binary Search Tree (https://leetcode.com/problems/trim-a-binary-search-tree/description/)
 */
public class TrimABinarySearchTree {

    /************** Solution 1: Recur Post-order Traversal *******************/
    /**
     * Time: O(N)   Space: O(H) worst O(N)
     */
    public TreeNode trimBST(TreeNode root, int low, int high) {
        if (root == null) return root;
        if (root.val < low) {
            return trimBST(root.right, low, high);
        } else if (root.val > high) {
            return trimBST(root.left, low, high);
        }
        root.left = trimBST(root.left, low, high);
        root.right = trimBST(root.right, low, high);
        return root;
    }

    private static void print(TreeNode root) {
        SerializeAndDeserializeBinaryTree printer = new SerializeAndDeserializeBinaryTree();
        System.out.println(printer.serialize(root));
    }

    public static void main(String[] args) {
        TrimABinarySearchTree solution = new TrimABinarySearchTree();

        TreeNode root1 = new TreeNode(1,
                new TreeNode(0),
                new TreeNode(2)
        );
        print(solution.trimBST(root1, 1, 2));

        TreeNode root2 = new TreeNode(3,
                new TreeNode(0,
                        null,
                        new TreeNode(2,
                                new TreeNode(1),
                                null)
                ),
                new TreeNode(4)
        );
        print(solution.trimBST(root2, 1, 3));
    }
}
