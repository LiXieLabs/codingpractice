import java.util.ArrayList;
import java.util.List;

public class PopulatingNextRightPointersInEachNode {

    /*********** Solution 1: Iterative Level-order BFS by Queue ***************/
    /**
     * Regular Level-order BFS by Queue
     *
     * Time: O(N)  Space: O(leaves) = O((N+1)/2) = O(N)
     */
    public Node connect1(Node root) {
        if (root == null) return root;
        List<Node> currLevel = new ArrayList<>();
        currLevel.add(root);
        while (!currLevel.isEmpty()) {
            List<Node> nextLevel = new ArrayList<>();
            Node prev = null;
            for (Node curr : currLevel) {
                if (prev != null) prev.next = curr;
                prev = curr;
                if (curr.left != null) { // 因为 perfect binary tree
                    nextLevel.add(curr.left);
                    nextLevel.add(curr.right);
                }
            }
            currLevel = nextLevel;
        }
        return root;
    }

    /********************** Solution 2: Recursive DFS **************************/
    /**
     * 每到一个点root, 这一层已经连接好了，连接下一层的两种next
     * (1) root.left.next = root.right (连接自身左右child)
     * (2) root.right.next = root.next.left (难点: 即连接以root的parent为对称的右侧subtree中的镜像对称点)
     *
     * Time: O(N)   Space: O(logN) by stack
     */
    public Node connect2(Node root) {
        if (root == null) return root;
        if (root.left != null) {
            root.left.next = root.right;
            if (root.next != null) root.right.next = root.next.left;
            connect(root.left);
            connect(root.right);
        }
        return root;
    }

    /************* Solution 3: Optimized Iterative Level-order BFS with O(1) Space **************/
    /**
     * 本质和 Solution 1 & Solution 2 一样
     * Solution 1 按 level 连接，但是只能通过 list 连接 curr level 的，此处巧妙运用 curr level 关系，连接下一层的！
     * Solution 2 是 DFS，此处每层负责连接下一层，连接完了，找到原来的 head，用 head.left 走到下一层开头开始连接！
     *
     * Time: O(N)   Space: O(1)
     */
    public Node connect(Node root) {
        Node head = root;
        while (head != null && head.left != null) {
            Node curr = head;
            while (curr != null) {
                curr.left.next = curr.right;
                if (curr.next != null) curr.right.next = curr.next.left;
                curr = curr.next;
            }
            head = head.left;
        }
        return root;
    }

    private static class Node {
        int val;
        Node left;
        Node right;
        Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }

    public static void main(String[] args) {
        PopulatingNextRightPointersInEachNode solution = new PopulatingNextRightPointersInEachNode();
        Node res1 = solution.connect(new Node(1,
                new Node(2,
                        new Node(4),
                        new Node(5),
                        null),
                new Node(3,
                        new Node(6),
                        new Node(7),
                        null),
                null));
    }
}

