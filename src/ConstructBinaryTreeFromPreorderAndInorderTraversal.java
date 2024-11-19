import java.util.HashMap;
import java.util.Map;

/**
 * 105. Construct Binary Tree from Preorder and Inorder Traversal
 * (https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/description/)
 */
public class ConstructBinaryTreeFromPreorderAndInorderTraversal {

    int[] preorder;
    int[] inorder;
    Map<Integer, Integer> valToIdx;
    int rootIdx; // index in preorder

    /************** Solution 1: Recursive ******************/
    /**
     * Preorder traversal follows Root -> Left -> Right
     * Inorder traversal follows Left -> Root -> Right
     *
     * thus, at any time, preorder[0] is root, as values are already UNIQUE
     * we can find the root in inorder which is i, left to it is left subtree's inorder traversal, right to it is the right subtree's inorder traversal
     * then we know the left & right subtree's size, we can get the preorder traversal from preorder array.
     * repeat the flow until the base case.
     *
     * Base cases are
     * (0) left > right -> 0 node, null
     * (1) left == right -> only 1 node, as it is guaranteed there are at least 1 node.
     *
     * Time: O(N)
     * 空间换时间优化 => 先遍历inorder，build value to index map，O(N^2) -> O(N)
     *
     * Space: O(Map) + O(Stack) = O(node) + O(depth) = O(N) + O(logN) = O(logN)
     *
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        this.preorder = preorder;
        this.inorder = inorder;
        valToIdx = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            valToIdx.put(inorder[i], i);
        }
        this.rootIdx = 0;
        return build(0, inorder.length - 1);
    }

    // left & right are indices in inorder
    private TreeNode build(int left, int right) {
        // Base Cases!!!
        if (left > right) return null;
        TreeNode rootNode = new TreeNode(preorder[rootIdx++]);
        if (left == right) return rootNode;
        int i = valToIdx.get(rootNode.val);
        // 2 if conditions checks if subtree is null, if null, doesn't require to continue build subtree!!!
        rootNode.left = build(left, i - 1);
        rootNode.right = build(i + 1, right);
        return rootNode;
    }

    public static void main(String[] args) {
        ConstructBinaryTreeFromPreorderAndInorderTraversal solution = new ConstructBinaryTreeFromPreorderAndInorderTraversal();

        // TC1 => [3,9,20,null,null,15,7,null,null,null,null]
        Utils.print(solution.buildTree(new int[]{3,9,20,15,7}, new int[]{9,3,15,20,7}));

        // TC2 右侧缺失 => [3,null,20,15,7,null,null,null,null]
        Utils.print(solution.buildTree(new int[]{3,20,15,7}, new int[]{3,15,20,7}));

        // TC3 右侧缺失 => [3,20,null,15,7,null,null,null,null]
        Utils.print(solution.buildTree(new int[]{3,20,15,7}, new int[]{15,20,7,3}));

        // TC4 base case => [-1,null,null]
        Utils.print(solution.buildTree(new int[]{-1}, new int[]{-1}));
    }
}
