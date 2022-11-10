import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    public static void print(TreeNode root) {
        List<Integer> levelOrder = new ArrayList<>();
        // 小心！！！Deque不允许null value!!!
        List<TreeNode> queue = new ArrayList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            List<TreeNode> next = new ArrayList<>();
            for (TreeNode curr : queue) {
                if (curr != null) {
                    levelOrder.add(curr.val);
                    next.add(curr.left);
                    next.add(curr.right);
                } else {
                    levelOrder.add(null);
                }
            }
            queue = next;
        }
        System.out.println("[" + levelOrder.stream().map(i -> {
            if (i != null) {
                return String.valueOf(i);
            } else {
                return "null";
            }
        }).collect(Collectors.joining(",")) + "]");
    }
}
