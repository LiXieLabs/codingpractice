import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class FindLeavesOfBinaryTree {

    // https://app.gitbook.com/o/-LEzdBqqw8FawHA3mleT/s/-LEzdBqr7mNEEfIbMstL/tree/366.-find-leaves-of-binary-tree

    // Recursive
    public List<List<Integer>> findLeaves(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        find(root, res);
        this.print(res);
        return res;
    }

    private int find(TreeNode node, List<List<Integer>> res) {
        if (node == null) return -1;
        int currHeight = Math.max(this.find(node.left, res), this.find(node.right, res)) + 1;
        if (res.size() < currHeight + 1) res.add(new ArrayList<>());
        res.get(currHeight).add(node.val);
        return currHeight;
    }

    // Iterative
    private class StackNode {
        TreeNode parent;
        TreeNode child;
        boolean isLeft;

        public StackNode(TreeNode parent, TreeNode curr, boolean isLeft) {
            this.parent = parent;
            this.child = curr;
            this.isLeft = isLeft;
        }
    }

    public List<List<Integer>> findLeaves1(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        TreeNode dummy = new TreeNode(0, root, null);
        while (dummy.left != null) {
            List<Integer> level = new ArrayList<>();
            Stack<StackNode> stack = new Stack<>();
            stack.push(new StackNode(dummy, dummy.left, true));
            while (!stack.empty()) {
                StackNode curr = stack.pop();
                if (curr.child.left == null && curr.child.right == null) {
                    if (curr.isLeft) {
                        curr.parent.left = null;
                    } else {
                        curr.parent.right = null;
                    }
                    level.add(curr.child.val);
                } else {
                    if (curr.child.right != null) stack.push(new StackNode(curr.child, curr.child.right, false));
                    if (curr.child.left != null) stack.push(new StackNode(curr.child, curr.child.left, true));
                }
            }
            res.add(level);
        }
        this.print(res);
        return res;
    }

    private void print(List<List<Integer>> res) {
//        for (List<Integer> lst : res) {
//            System.out.print(Arrays.toString(lst.toArray()));
//        }
        Arrays.stream(res.toArray()).forEach(System.out::println);
    }

    public static void main(String[] args) {
        FindLeavesOfBinaryTree solution = new FindLeavesOfBinaryTree();
        TreeNode root = new TreeNode(1,
                new TreeNode(2,
                        new TreeNode(4), new TreeNode(5)),
                new TreeNode(3)
        );
        solution.findLeaves1(root);
    }
}


