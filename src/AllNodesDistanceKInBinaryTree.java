import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 863. All Nodes Distance K in Binary Tree (https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/)
 */
public class AllNodesDistanceKInBinaryTree {

    /************ Solution 1: DFS + BFS ****************/
    /**
     * 类似 742. Closest Leaf in a Binary Tree (https://leetcode.com/problems/closest-leaf-in-a-binary-tree/)
     *
     * DFS to convert to graph by building adj list
     * BFS from kNode to find the closest leaf
     *
     * 需要跨root查找的需要看作graph而非tree
     * tree的adj list只需要parent，因为TreeNode结构left&right已经自动记录了其他相邻node
     *
     * Time: O(N)   Space: O(N)
     */
    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        // DFS to build adj list until k is found - map from child to parent
        Map<Integer, TreeNode> parents = new HashMap<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        boolean found = false;
        while (!found) {
            TreeNode curr = stack.pop();
            if (curr == target) found = true;
            if (curr.right != null) {
                parents.put(curr.right.val, curr);
                stack.push(curr.right);
            }
            if (curr.left != null) {
                parents.put(curr.left.val, curr);
                stack.push(curr.left);
            }
        }

        // BFS to find k-th level
        List<TreeNode> curLevel = new ArrayList<>();
        curLevel.add(target);
        Set<Integer> visited = new HashSet<>();
        visited.add(target.val);
        int depth = 0;
        // 注意！！！!curLevel.isEmpty() 是为了防止 k 比 Tree DEPTH 大，如 TestCase2
        while (depth++ < k && !curLevel.isEmpty()) {
            List<TreeNode> nexLevel = new ArrayList<>();
            for (TreeNode cur : curLevel) {
                for (TreeNode nex : Arrays.asList(cur.left, cur.right, parents.getOrDefault(cur.val, null))) {
                    // 注意！！！别忘了 check visited
                    if (nex != null && !visited.contains(nex.val)) {
                        nexLevel.add(nex);
                        visited.add(nex.val);
                    }
                }
            }
            curLevel = nexLevel;
        }
        return curLevel.stream().map(n -> n.val).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        AllNodesDistanceKInBinaryTree solution = new AllNodesDistanceKInBinaryTree();

        // TC1
        TreeNode target1 = new TreeNode(5,
                new TreeNode(6),
                new TreeNode(2,
                        new TreeNode(7),
                        new TreeNode(4)));
        TreeNode root1 = new TreeNode(3,
                target1,
                new TreeNode(1,
                        new TreeNode(0),
                        new TreeNode(8)));
        System.out.println(solution.distanceK(root1, target1, 2));

        // TC2
        TreeNode target2 = new TreeNode(1);
        TreeNode root2 = target2;
        System.out.println(solution.distanceK(root2, target2, 3));
    }
}
