import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 426. Convert Binary Search Tree to Sorted Doubly Linked List (https://leetcode.com/problems/convert-binary-search-tree-to-sorted-doubly-linked-list/description/)
 */
public class ConvertBinarySearchTreeToSortedDoublyLinkedList {

    /************** Solution 1: Recursive Inorder Traversal ***************/
    /**
     * Time: O(N)   Space: O(H) by recur call stack
     */
    private TreeNode tail;
    public TreeNode treeToDoublyList1(TreeNode root) {
        TreeNode dummy = new TreeNode();
        tail = dummy;
        recur(root);
        TreeNode head = dummy.right;
        if (head != null) {
            head.left = tail;
            tail.right = head;
        }
        return head;
    }

    private void recur(TreeNode curr) {
        if (curr == null) return;
        recur(curr.left);
        tail.right = curr;
        curr.left = tail;
        tail = curr;
        recur(curr.right);
    }

    /************** Solution 2: Iterative Inorder Traversal ***************/
    /**
     * Time: O(N)   Space: O(H) by stack
     */
    public TreeNode treeToDoublyList2(TreeNode root) {
        TreeNode dummy = new TreeNode();
        TreeNode tail = dummy;
        Deque<TreeNode> stack = new ArrayDeque<>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            tail.right = root;
            root.left = tail;
            tail = root;
            root = root.right;
        }
        TreeNode head = dummy.right;
        if (head != null) {
            head.left = tail;
            tail.right = head;
        }
        return head;
    }

    /************** Solution 3: Morris Traversal ***************/
    /**
     * Time: O(N)   Space: O(1)
     */
    public TreeNode treeToDoublyList(TreeNode root) {
        TreeNode dummy = new TreeNode();
        TreeNode tail = dummy;
        while (root != null) {
            if (root.left == null) {
                tail.right = root;
                root.left = tail;
                tail = root;
            } else {
                TreeNode predecessor = root.left;
                while (predecessor.right != null && predecessor.right != root) {
                    predecessor = predecessor.right;
                }
                if (predecessor.right == null) {
                    predecessor.right = root;
                    root = root.left;
                } else {
                    root.left = predecessor;
                    tail = root;
                    root = root.right;
                }
            }
        }
        TreeNode head = dummy.right;
        if (head != null) {
            head.left = tail;
            tail.right = head;
        }
        return head;
    }

    public static void main(String[] args) {
        ConvertBinarySearchTreeToSortedDoublyLinkedList solution = new ConvertBinarySearchTreeToSortedDoublyLinkedList();

        TreeNode root1 = new TreeNode(4,
                new TreeNode(2,
                        new TreeNode(1),
                        new TreeNode(3)),
                new TreeNode(5));
        TreeNode res1 = solution.treeToDoublyList(root1);

        TreeNode root2 = null;
        TreeNode res2 = solution.treeToDoublyList(root2);

        TreeNode root3 = new TreeNode(0);
        TreeNode res3 = solution.treeToDoublyList(root3);
    }
}
