import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 428. Serialize and Deserialize N-ary Tree (https://leetcode.com/problems/serialize-and-deserialize-n-ary-tree/description/)
 */
public class SerializeAndDeserializeNaryTree {

    /************** Solution 1: Level-order traversal by iterative BFS *******************/

    // Time: O(N)   Space: O(N)
    // Encodes a tree to a single string.
    public String serialize(Node428 root) {
        List<String> res = new ArrayList<>();
        List<Node428> currLevel = new ArrayList<>(Arrays.asList(root, null)); // ArrayList init with multiple values
        while (!currLevel.isEmpty()) {
            List<Node428> nextLevel = new ArrayList<>();
            for (Node428 curr : currLevel) {
                res.add(curr == null ? "#" : String.valueOf(curr.val));
                if (curr == null) continue;
                if (curr.children != null) nextLevel.addAll(curr.children);
                nextLevel.add(null);
            }
            currLevel = nextLevel;
        }
        return String.join(",", res);
    }

    // Time: O(N)   Space: O(N)
    // Decodes your encoded data to tree.
    public Node428 deserialize(String data) {
        String[] arr = data.split(",");
        if ("#".equals(arr[0])) return null;
        List<Node428> currLevel = new ArrayList<>();
        Node428 root = new Node428(Integer.parseInt(arr[0]), new ArrayList<>());
        currLevel.add(root);
        int i = 2;
        while (i < arr.length) {
            List<Node428> nextLevel = new ArrayList<>();
            for (Node428 curr : currLevel) {
                List<Node428> children = new ArrayList<>();
                while (!"#".equals(arr[i])) {
                    Node428 child = new Node428(Integer.parseInt(arr[i++]), new ArrayList<>());
                    children.add(child);
                    nextLevel.add(child);
                }
                if (!children.isEmpty()) curr.children = new ArrayList<>(children);
                i++;
            }
            currLevel = nextLevel;
        }
        return root;
    }

    public static void main(String[] args) {
        SerializeAndDeserializeNaryTree solution = new SerializeAndDeserializeNaryTree();
        Node428 root1 = new Node428(1,
                new ArrayList<>(Arrays.asList(
                        new Node428(2),
                        new Node428(3, new ArrayList<>(Arrays.asList(
                                new Node428(6),
                                new Node428(7, new ArrayList<>(Arrays.asList(
                                        new Node428(11, new ArrayList<>(Arrays.asList(
                                                new Node428(14)
                                        )))
                                )))
                        ))),
                        new Node428(4, new ArrayList<>(Arrays.asList(
                                new Node428(8, new ArrayList<>(Arrays.asList(
                                        new Node428(12)
                                )))
                        ))),
                        new Node428(5, new ArrayList<>(Arrays.asList(
                                new Node428(9, new ArrayList<>(Arrays.asList(
                                        new Node428(13)
                                ))),
                                new Node428(10)
                        )))
                )));
        String serialized1 = solution.serialize(root1);
        Node428 root1again1 = solution.deserialize(serialized1);
        String serializedAgain1 = solution.serialize(root1again1);
        System.out.println(serialized1);
        System.out.println(serializedAgain1);
        System.out.println("same: " + serialized1.equals(serializedAgain1));
        // 1,#,2,3,4,5,#,#,6,7,#,8,#,9,10,#,#,11,#,12,#,13,#,#,14,#,#,#,#

        String serialized2 = solution.serialize(null);
        Node428 root2again2 = solution.deserialize(serialized2);
        String serializedAgain2 = solution.serialize(root2again2);
        System.out.println(serialized2);
        System.out.println(serializedAgain2);
        System.out.println("same: " + serialized2.equals(serializedAgain2));
        // #,#
    }
}

class Node428 {
    public int val;
    public List<Node428> children;

    public Node428() {}

    public Node428(int _val) {
        val = _val;
    }

    public Node428(int _val, List<Node428> _children) {
        val = _val;
        children = _children;
    }
};