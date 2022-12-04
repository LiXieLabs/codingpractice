import java.util.ArrayList;
import java.util.List;

/**
 * 297. Serialize and Deserialize Binary Tree (https://leetcode.com/problems/serialize-and-deserialize-binary-tree/description/)
 */
public class SerializeAndDeserializeBinaryTree {

    /************** Solution 1: Level-order traversal by iterative BFS *******************/
    /**
     * TODO: Iterative & Recursive DFS
     */

    // Time: O(N)   Space: O(N)
    public String serialize(TreeNode root) {
        List<String> res = new ArrayList<>();
        List<TreeNode> currLevel = new ArrayList<>();
        currLevel.add(root);
        boolean allNull = root == null;
        while (!allNull) {
            allNull = true;
            List<TreeNode> nextLevel = new ArrayList<>();
            List<String> curr = new ArrayList<>();
            for (TreeNode node : currLevel) {
                curr.add(node == null ? "#" : String.valueOf(node.val));
                if (node == null) continue;
                if (node.left != null || node.right != null) allNull = false;
                nextLevel.add(node.left);
                nextLevel.add(node.right);
            }
            res.addAll(curr);
            currLevel = nextLevel;
        }
        return String.join(",", res);
    }

    // Time: O(N)   Space: O(N)
    public TreeNode deserialize(String data) {
        TreeNode root = null;
        if (data.length() == 0) return root;
        String[] arr = data.split(",");
        List<TreeNode> currLevel = new ArrayList<>();
        int i = 0;
        root = new TreeNode(Integer.parseInt(arr[i++]));
        currLevel.add(root);
        while (i < arr.length) {
            List<TreeNode> nextLevel = new ArrayList<>();
            for (TreeNode n : currLevel) {
                String left = arr[i++];
                if (!"#".equals(left))  {
                    n.left = new TreeNode(Integer.parseInt(left));
                    nextLevel.add(n.left);
                }
                String right = arr[i++];
                if (!"#".equals(right)) {
                    n.right = new TreeNode(Integer.parseInt(right));
                    nextLevel.add(n.right);
                }
            }
            currLevel = nextLevel;
        }
        return root;
    }

    public static void main(String[] args) {
        SerializeAndDeserializeBinaryTree solution = new SerializeAndDeserializeBinaryTree();

        TreeNode root1 = new TreeNode(1,
                new TreeNode(2),
                new TreeNode(3,
                        new TreeNode(4),
                        new TreeNode(5)));
        String str1 = solution.serialize(root1);
        System.out.println(str1); // "1,2,3,#,#,4,5"
        TreeNode res1 = solution.deserialize(str1);
        System.out.println(solution.serialize(res1));

        TreeNode root2 = null;
        String str2 = solution.serialize(root2);
        System.out.println(str2);
        TreeNode res2 = solution.deserialize(str2);
        System.out.println(solution.serialize(res2));
    }
}
