import java.util.ArrayList;
import java.util.List;

public class LowestCommonAncestorOfABinarySearchTree {

    /*********** Solution 1: Build Path -> check along path from root until mismatch ********/
    public TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q) {
        List<TreeNode> path1 = findPath(root, p);
        List<TreeNode> path2 = findPath(root, q);
        int i = 0;
        while (i < path1.size() && i < path2.size() && path1.get(i) == path2.get(i)) i++;
        return path1.get(i-1);
    }

    public List<TreeNode> findPath(TreeNode root, TreeNode node) {
        List<TreeNode> path = new ArrayList<>();
        while (root != node) {
            path.add(root);
            if (node.val < root.val) {
                root = root.left;
            } else {
                root = root.right;
            }
        }
        path.add(root);
        return path;
    }

    /************** Solution 2: Iterative **********************/
    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode larger = p.val > q.val ? p : q;
        TreeNode smaller = p.val > q.val ? q : p;
        while (root.val > larger.val || root.val < smaller.val) {
            root = root.val > larger.val ? root.left : root.right;
        }
        return root;
    }

    /************** Solution 3: Recursive **********************/
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode larger = p.val > q.val ? p : q;
        TreeNode smaller = p.val > q.val ? q : p;
        if (root.val > larger.val) return lowestCommonAncestor(root.left, p, q);
        if (root.val < smaller.val) return lowestCommonAncestor(root.right, p, q);
        return root; // smaller.val <= root.val <= larger.val
    }

    public static void main(String[] args) {
        LowestCommonAncestorOfABinarySearchTree solution = new LowestCommonAncestorOfABinarySearchTree();
        TreeNode root = new TreeNode(6,
                new TreeNode(2,
                        new TreeNode(0),
                        new TreeNode(4,
                                new TreeNode(3),
                                new TreeNode(5))),
                new TreeNode(8,
                        new TreeNode(7),
                        new TreeNode(9)));
        System.out.println(solution.lowestCommonAncestor(root, root.left.left, root.right.right).val);
        System.out.println(solution.lowestCommonAncestor(root, root.left.left, root.left.right.right).val);
    }
}
