import java.util.HashMap;
import java.util.Map;

/**
 * 106. Construct Binary Tree from Inorder and Postorder Traversal
 * (https://leetcode.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/description/)
 */
public class ConstructBinaryTreeFromInorderAndPostorderTraversal {

    int[] inorder, postorder;
    int rootIdx;
    Map<Integer, Integer> valToIdx;

    /************** Solution 1: Recursive ******************/
    /**
     * rootIdx 是当前 postorder 中位置，按照顺序递减
     * valToIdx 是 inorder 中 value to index 的 map
     *
     * 参考 105. Construct Binary Tree from Preorder and Inorder Traversal
     * (https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/description/)
     *
     * Time: O(N)
     * 空间换时间优化 => 先遍历inorder，build value to index map，O(N^2) -> O(N)
     *
     * Space: O(Map) + O(Stack) = O(node) + O(depth) = O(N) + O(logN) = O(N)
     */
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        this.inorder = inorder;
        this.postorder = postorder;
        rootIdx = postorder.length - 1;
        valToIdx = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) valToIdx.put(inorder[i], i);
        return recur(0, inorder.length - 1);
    }

    private TreeNode recur(int l, int r) {
        if (l > r) return null;
        TreeNode root = new TreeNode(postorder[rootIdx--]);
        if (l == r) return root;
        int i = valToIdx.get(root.val);
        root.right = recur(i + 1, r);
        root.left = recur(l, i - 1);
        return root;
    }
}
