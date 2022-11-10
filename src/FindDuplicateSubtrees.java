import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindDuplicateSubtrees {

    private List<TreeNode> result;
    private Map<String, Integer> counter;
    private Integer currId;

    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
//        this.result = new ArrayList<>();
//        this.counter = new HashMap<>();
//        dfs1(root);
//        return this.result;
        List<TreeNode> res = new ArrayList<>();
        this.currId = 1;
        dfs2(root, new HashMap<String, Integer>(), new HashMap<Integer, Integer>(), res);
        return res;
    }

    // Post-order traversal (serial is path)
    // O(N^2) 2nd N is because of String hashing, and if number is scarce but large, it could take long time
    // To reduce string hashing cost, we can hash on serial id, instead of hash on serial directly (refer to dfs2 method)
    private String dfs1(TreeNode node) {
        if (node == null) return "#";
        String currPath =  node.val + "+" + dfs1(node.left) + "+" + dfs1(node.right);
        counter.put(currPath, counter.getOrDefault(currPath, 0) + 1);
        if (counter.get(currPath) == 2) {
            result.add(node);
        }
        return currPath;
    }

    // O(N) as hashing on serial id
    // and instead of using class variable, we are passing res and map down
    private int dfs2(TreeNode node, Map<String, Integer> serialToId, Map<Integer, Integer> idToCount, List<TreeNode> res) {
        if (node == null) return 0;
        String currSerial =  node.val + "+" + dfs2(node.left, serialToId, idToCount, res) + "+" + dfs2(node.right, serialToId, idToCount, res);
        // serial (path) => id
        int serialId = serialToId.getOrDefault(currSerial, this.currId);
        serialToId.put(currSerial, serialId);
        if (serialId == this.currId) this.currId++; // currId is used, need to increment to next id
        // id => count
        idToCount.put(serialId, idToCount.getOrDefault(serialId, 0) + 1);
        if (idToCount.get(serialId) == 2) res.add(node);
        return serialId;
    }

    public static void main(String[] args) {
        FindDuplicateSubtrees solution = new FindDuplicateSubtrees();
        TreeNode root1 = new TreeNode(1,
                new TreeNode(2,
                        new TreeNode(4), null),
                new TreeNode(3,
                        new TreeNode(2,
                                new TreeNode(4), null),
                        new TreeNode(4))
        );
        TreeNode root2 = new TreeNode(2,
                new TreeNode(1,
                        new TreeNode(11), null),
                new TreeNode(11,
                        new TreeNode(1), null)
        );
        solution.findDuplicateSubtrees(root1);
    }
}
