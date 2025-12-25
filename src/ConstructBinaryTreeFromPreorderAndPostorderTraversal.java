import java.util.HashMap;
import java.util.Map;

/**
 * 889. Construct Binary Tree from Preorder and Postorder Traversal
 * (https://leetcode.com/problems/construct-binary-tree-from-preorder-and-postorder-traversal/description/)
 */
public class ConstructBinaryTreeFromPreorderAndPostorderTraversal {


    int[] preorder, postorder;

    /**************** Solution 1: 遍历 preorder + search idx in postorder ****************/
    /**
     * rootIdx 是当前 preorder 中位置，按照顺序递增
     * valToIdx 是 postorder 中 value to index 的 map
     *
     * 参考 105. Construct Binary Tree from Preorder and Inorder Traversal
     * (https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/description/)
     *
     * Time: O(N)
     * 空间换时间优化 => 先遍历postorder，build value to index map，O(N^2) -> O(N)
     *
     * Space: O(Map) + O(Stack) = O(node) + O(depth) = O(N) + O(logN) = O(N)
     */
    int rootIdx;
    Map<Integer, Integer> valToIdx;

    public TreeNode constructFromPrePost1(int[] preorder, int[] postorder) {
        this.preorder = preorder;
        this.postorder = postorder;
        rootIdx = 0;
        valToIdx = new HashMap<>();
        for (int i = 0; i < postorder.length; i++) valToIdx.put(postorder[i], i);
        return recur(0, postorder.length - 1);
    }

    private TreeNode recur(int l, int r) {
        if (l > r) return null;
        TreeNode root = new TreeNode(preorder[rootIdx++]);
        if (l == r) return root;
        int i = valToIdx.get(preorder[rootIdx]);
        root.left = recur(l, i);
        root.right = recur(i + 1, r - 1);
        return root;
    }

    /**************** Solution 2: Solution 1 空间优化 by 去掉 postorder idx map ****************/
    /**
     * 观察：
     * （1）Since preorder always visits nodes in the order Root → Left → Right,
     *     each recursive call picks the next node from preorder and assigns it as the root of the current subtree.
     * （2）Meanwhile, since postorder follows Left → Right → Root,
     *     a subtree is fully processed when we encounter its root in postorder
     *     => 截止条件 & good to increment postIdx !!!
     *
     * Time: O(N)   Space: O(Stack) = O(N) worst case
     */
    int preIdx, postIdx;

    public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {
        this.preorder = preorder;
        this.postorder = postorder;
        preIdx = 0;
        postIdx = 0;
        return recur();
    }

    private TreeNode recur() {
        TreeNode root = new TreeNode(preorder[preIdx++]);

        if (root.val != postorder[postIdx]) {
            root.left = recur();
        }
        if (root.val != postorder[postIdx]) {
            root.right = recur();
        }
        postIdx++;

        return root;
    }
}
