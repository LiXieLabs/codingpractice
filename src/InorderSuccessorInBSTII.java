public class InorderSuccessorInBSTII {

    /************* Solution 1: Iteration along height ************/
    /**
     * Time: O(H) ~ O(logN) worst O(N)
     * Space: O(1)
     */
    public Node510 inorderSuccessor(Node510 node) {
        Node510 curr = node;
        if (node.right != null) {
            curr = curr.right;
            while (curr.left != null) {
                curr = curr.left;
            }
        } else {
            curr = curr.parent;
            while (curr != null && curr.val < node.val) {
                curr = curr.parent;
            }
        }
        return curr;

        // else里面可以替换为
        // while (curr.parent != null && x == x.parent.right) x = x.parent;
        // return curr.parent;
    }

    private static void print(Node510 node) {
        System.out.println(node == null ? "null" : node.val);
    }

    public static void main(String[] args) {
        InorderSuccessorInBSTII solution = new InorderSuccessorInBSTII();

        Node510 node1 = new Node510(1);
        Node510 node2 = new Node510(2);
        Node510 node3 = new Node510(3);
        Node510 node4 = new Node510(4);
        Node510 node5 = new Node510(5);
        Node510 node6 = new Node510(6);

        node5.left = node3;

        node3.left = node2;
        node3.right = node4;
        node3.parent = node5;

        node5.right = node6;

        node6.parent = node5;

        node2.left = node1;
        node2.parent = node3;

        node1.parent = node2;

        node4.parent = node3;

        print(solution.inorderSuccessor(node1)); // 2
        print(solution.inorderSuccessor(node2)); // 3
        print(solution.inorderSuccessor(node3)); // 4
        print(solution.inorderSuccessor(node4)); // 5
        print(solution.inorderSuccessor(node5)); // 6
        print(solution.inorderSuccessor(node6)); // null

    }
}

class Node510 {
    public int val;
    public Node510 left;
    public Node510 right;
    public Node510 parent;

    public Node510(int val) {
        this.val = val;
    }
}