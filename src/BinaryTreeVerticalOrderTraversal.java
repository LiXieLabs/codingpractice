import javafx.util.Pair;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BinaryTreeVerticalOrderTraversal {

    /************ Solution 1: BFS + Map from column id to node value list, root has col == 0 *************/
    /**
     * Time: O(N) Space: O(N)
     */
    public List<List<Integer>> verticalOrder(TreeNode root) {
        // 小心corner base，不然就要在while loop里面加null check
        if (root == null) return new ArrayList<>();

        Map<Integer, List<Integer>> map = new HashMap<>();
        int minKey = 0;
        Deque<Pair<Integer, TreeNode>> queue = new ArrayDeque<>();
        queue.offer(new Pair<>(0, root));
        while (!queue.isEmpty()) {
            Pair<Integer, TreeNode> curr = queue.poll();
            int col = curr.getKey();
            minKey = Math.min(minKey, col);
            TreeNode cur = curr.getValue();
            map.putIfAbsent(col, new ArrayList<>());
            map.get(col).add(cur.val);
            if (cur.left != null) queue.offer(new Pair<>(col - 1, cur.left));
            if (cur.right != null) queue.offer(new Pair<>(col + 1, cur.right));
        }
        List<List<Integer>> res = new ArrayList<>();
        while (map.containsKey(minKey)) {
            res.add(map.get(minKey++));
        }
        return res;
    }

    public static void print(List<List<Integer>> input) {
        System.out.println("[" + input.stream()
                .map(column -> "[" + column.stream().map(String::valueOf).collect(Collectors.joining(",")) + "]")
                .collect(Collectors.joining(",")) + "]");
    }

    public static void main(String[] args) {
        BinaryTreeVerticalOrderTraversal solution = new BinaryTreeVerticalOrderTraversal();

        TreeNode root1 = new TreeNode(3,
                new TreeNode(9),
                new TreeNode(20,
                        new TreeNode(15),
                        new TreeNode(7)));
        print(solution.verticalOrder(root1));

        TreeNode root2 = new TreeNode(3,
                new TreeNode(9,
                        new TreeNode(4),
                        new TreeNode(0)),
                new TreeNode(8,
                        new TreeNode(1),
                        new TreeNode(7)));
        print(solution.verticalOrder(root2));

        TreeNode root3 = new TreeNode(3,
                new TreeNode(9,
                        new TreeNode(4),
                        new TreeNode(0,
                                null,
                                new TreeNode(2))),
                new TreeNode(8,
                        new TreeNode(1,
                                new TreeNode(5),
                                null),
                        new TreeNode(7)));
        print(solution.verticalOrder(root3));
    }
}
