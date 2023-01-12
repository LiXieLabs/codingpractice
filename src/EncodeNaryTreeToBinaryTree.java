import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 431. Encode N-ary Tree to Binary Tree (https://leetcode.com/problems/encode-n-ary-tree-to-binary-tree/description/)
 */
public class EncodeNaryTreeToBinaryTree {

    /*************** Solution 1: BFS Level Order Traversal ***************/
    /**
     * 同一个parent的children用right连接
     * 同一个parent的children的第一个child连接到parent.left
     *
     * * 注意！！！
     * （1）可以优化为 currLevel = List<Pair<Node431,TreeNode>>
     * （2）TODO：Solution 2 by DFS
     */

    // Time: O(N)   Space: O(L) worst O(N)
    // Encodes an n-ary tree to a binary tree.
    public TreeNode encode(Node431 root) {
        if (root == null) return null;
        TreeNode rootCopy = new TreeNode(root.val);
        List<Node431> currLevelOriginal = new ArrayList<>();
        List<TreeNode> currLevelCopy= new ArrayList<>();
        currLevelOriginal.add(root);
        currLevelCopy.add(rootCopy);
        while (!currLevelOriginal.isEmpty()) {
            List<Node431> nextLevelOriginal = new ArrayList<>();
            List<TreeNode> nextLevelCopy = new ArrayList<>();
            for (int i = 0; i < currLevelOriginal.size(); i++) {
                Node431 original = currLevelOriginal.get(i);
                if (original.children == null) continue;
                TreeNode copy = currLevelCopy.get(i);
                TreeNode prevChildCopy = null;
                for (int j = 0; j < original.children.size(); j++) {
                    Node431 childOriginal = original.children.get(j);
                    TreeNode childCopy = new TreeNode(childOriginal.val);
                    if (prevChildCopy == null) {
                        copy.left = childCopy;
                    } else {
                        prevChildCopy.right = childCopy;
                    }
                    prevChildCopy = childCopy;
                    nextLevelOriginal.add(childOriginal);
                    nextLevelCopy.add(childCopy);
                }
            }
            currLevelOriginal = nextLevelOriginal;
            currLevelCopy = nextLevelCopy;
        }
        return rootCopy;
    }

    // Time: O(N)   Space: O(L) worst O(N)
    // Decodes your binary tree to an n-ary tree.
    public Node431 decode(TreeNode root) {
        if (root == null) return null;
        Node431 rootCopy = new Node431(root.val, new ArrayList<>());
        List<TreeNode> currLevelOriginal = new ArrayList<>();
        List<Node431> currLevelCopy = new ArrayList<>();
        currLevelOriginal.add(root);
        currLevelCopy.add(rootCopy);
        while (!currLevelOriginal.isEmpty()) {
            List<TreeNode> nextLevelOriginal = new ArrayList<>();
            List<Node431> nextLevelCopy = new ArrayList<>();
            for (int i = 0; i < currLevelOriginal.size(); i++) {
                TreeNode original = currLevelOriginal.get(i);
                Node431 copy = currLevelCopy.get(i);
                TreeNode childOriginal = original.left;
                while (childOriginal != null) {
                    Node431 childCopy = new Node431(childOriginal.val, new ArrayList<>());
                    copy.children.add(childCopy);
                    nextLevelOriginal.add(childOriginal);
                    nextLevelCopy.add(childCopy);
                    childOriginal = childOriginal.right;
                }
            }
            currLevelOriginal = nextLevelOriginal;
            currLevelCopy = nextLevelCopy;
        }
        return rootCopy;
    }

    public static void main(String[] args) {
        EncodeNaryTreeToBinaryTree solution = new EncodeNaryTreeToBinaryTree();
        SerializeAndDeserializeBinaryTree check = new SerializeAndDeserializeBinaryTree();

        Node431 root1 = new Node431(1, new ArrayList<>(Arrays.asList(
                new Node431(2, new ArrayList<>(Arrays.asList(
                        new Node431(5),
                        new Node431(6),
                        new Node431(7)
                ))),
                new Node431(3, new ArrayList<>()),
                new Node431(4, new ArrayList<>(Arrays.asList(
                        new Node431(8),
                        new Node431(9),
                        new Node431(10)
                )))
        )));
        TreeNode serialized1 = solution.encode(root1);
        Node431 root1again1 = solution.decode(serialized1);
        TreeNode serializedAgain1 = solution.encode(root1again1);
        System.out.println("same: " + check.serialize(serialized1).equals(check.serialize(serializedAgain1)));
    }
}

class Node431 {
    public int val;
    public List<Node431> children;

    public Node431() {}

    public Node431(int _val) {
        val = _val;
    }

    public Node431(int _val, List<Node431> _children) {
        val = _val;
        children = _children;
    }
};
