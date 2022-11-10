import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import sun.awt.im.InputMethodWindow;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class StepByStepDirectionsFromABinaryTreeNodeToAnother {

    // Solution 1: TLE
    public String getDirections(TreeNode root, int startValue, int destValue) {
        // DFS find start and dest values
        // and keep track of parents
        TreeNode start = null, dest = null;
        Map<Integer, TreeNode> parents = new HashMap<>();
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (stack.size() != 0) {
            TreeNode curr = stack.pop();
            if (curr.val == startValue) {
                start = curr;
            }
            if (curr.val == destValue) {
                dest = curr;
            }
            if (start != null && dest != null) {
                break;
            }
            if (curr.right != null) {
                parents.put(curr.right.val, curr);
                stack.push(curr.right);
            }
            if (curr.left != null) {
                parents.put(curr.left.val, curr);
                stack.push(curr.left);
            }
        }

        // BFS from start to dest
        Set<Integer> visited = new HashSet<>();
        Deque<QueueNode> queue = new ArrayDeque<>();
        queue.add(new QueueNode("", start));
        while (queue.size() != 0) {
            QueueNode curr = queue.poll();
            if (curr.node == dest) return curr.path;
            visited.add(curr.node.val);
            if (curr.node.left != null && !visited.contains(curr.node.left.val)) {
                queue.add(new QueueNode(curr.path + "L", curr.node.left));
            }
            if (curr.node.right != null && !visited.contains(curr.node.right.val)) {
                queue.add(new QueueNode(curr.path + "R", curr.node.right));
            }
            if (parents.containsKey(curr.node.val) && !visited.contains(parents.get(curr.node.val).val)) {
                queue.add(new QueueNode(curr.path + "U", parents.get(curr.node.val)));
            }
        }
        return "not found";
    }

    private class QueueNode {
        String path;
        TreeNode node;
        public QueueNode(String path, TreeNode node) {
            this.path = path;
            this.node = node;
        }
    }

    // Solution 2: Lowest Common Ancestor & Reverse path on the start side

    public String getDirections1(TreeNode root, int startValue, int destValue) {
        StringBuilder sPath = new StringBuilder(), dPath = new StringBuilder();
        dfs(root, startValue, sPath);
        dfs(root, destValue, dPath);
        int i = 0, max = Math.min(sPath.length(), dPath.length()) - 1;
        while (i <= max && sPath.charAt(sPath.length() - i - 1) == dPath.charAt(dPath.length() - i - 1)) {
            i++;
        }
        return sPath.substring(0, sPath.length() - i)
                .replace("L", "U")
                .replace("R", "U")
                + dPath.reverse().substring(i);

          // Regex
//        return sPath.substring(0, sPath.length() - i)
//                .replaceAll("[L|R]", "U")
//                + dPath.reverse().substring(i);

          // str.repeat() only available in Java11...
//        return "U".repeat(s.length() - i) + d.reverse().toString().substring(i);

    }

    private boolean dfs(TreeNode node, int target, StringBuilder path) {
        if (node.val == target) {
            return true;
        }
        if (node.left != null && dfs(node.left, target, path)) {
            path.append("L");
            return true;
        }
        if (node.right != null && dfs(node.right, target, path)) {
            path.append("R");
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        StepByStepDirectionsFromABinaryTreeNodeToAnother solution = new StepByStepDirectionsFromABinaryTreeNodeToAnother();
        TreeNode root = new TreeNode(5,
                new TreeNode(1,
                        new TreeNode(3), null),
                new TreeNode(2,
                        new TreeNode(6), new TreeNode(4))
        );
        System.out.println(solution.getDirections1(root, 3, 6));
    }
}
