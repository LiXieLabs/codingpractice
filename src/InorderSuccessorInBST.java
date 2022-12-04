import java.util.ArrayDeque;
import java.util.Deque;

public class InorderSuccessorInBST {

    /************** Solution 1: Iterative Inorder Traversal ****************/
    /**
     * Time: O(H + N)   Space: O(H)
     */
    public TreeNode inorderSuccessor1(TreeNode root, TreeNode p) {
        TreeNode prev = null, curr = root;
        Deque<TreeNode> stack = new ArrayDeque<>();
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            if (prev == p) return curr;
            prev = curr;
            curr = curr.right;
        }
        return null;
    }

    /**************** Solution 2: Recursive Inorder Traversal ***************/
    /**
     * Time: O(N)   Space: O(H) by recur stack
     */
    TreeNode prev;
    public TreeNode inorderSuccessor2(TreeNode root, TreeNode p) {
        prev = null;
        return recurInorder(root, p);
    }

    public TreeNode recurInorder(TreeNode root, TreeNode p) {
        if (root == null) return null;
        TreeNode left = recurInorder(root.left, p);
        if (left != null) return left;
        if (prev == p) return root;
        prev = root;
        return recurInorder(root.right, p);
    }

    /************* Solution 3: Morris Traversal **************************/
    /**
     * Time: O(3N) = O(N), each node/edge is visited 3 times - find predecessor/inorder traversal/disconnect
     * Space: O(1)
     */
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        TreeNode prev = null, curr = root;
        while(curr != null) {
            if (curr.left == null) {
                if (prev == p) return curr;
                prev = curr;
                curr = curr.right;
            } else {
                TreeNode predecessor = curr.left;
                while (predecessor.right != null && predecessor.right != curr) {
                    predecessor = predecessor.right;
                }
                if (predecessor.right == null) {
                    predecessor.right = curr;
                    curr = curr.left;
                } else {
                    predecessor.right = null;
                    if (prev == p) return curr;
                    prev = curr;
                    curr = curr.right;
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        InorderSuccessorInBST solution = new InorderSuccessorInBST();

        TreeNode p1 = new TreeNode(1);
        TreeNode root1 = new TreeNode(2,
                p1,
                new TreeNode(3));
        TreeNode r1 = solution.inorderSuccessor(root1, p1);
        System.out.println(r1 == null ? "null" : r1.val);

        TreeNode p2 = new TreeNode(6);
        TreeNode root2 = new TreeNode(5,
                new TreeNode(3,
                        new TreeNode(2,
                                new TreeNode(1),
                                null),
                        new TreeNode(4)),
                p2);
        TreeNode r2 = solution.inorderSuccessor(root2, p2);
        System.out.println(r2 == null ? "null" : r2.val);
    }
}
