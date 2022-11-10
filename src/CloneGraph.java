import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CloneGraph {

    /************** Solution 1: BFS ************************/
    /**
     * Time: O(V + E) = min O(N) max O(N^2)
     * Space: O(N).
     * Space occupied by the indexToCopiedNode - O(N) and in addition to that
     * Space occupied by the queue - O(W) where W is the width of the graph.
     * Overall, the space complexity would be O(N).
     *
     * Recur DFS 也可以！！！
     *
     */
    public Node cloneGraph(Node node) {
        // key 是 original Node 也可以！！！
        Map<Integer, Node> indexToCopiedNode = new HashMap<>();
        Node res = null;
        // 小心 edge case！！！
        if (node == null) return res;
        Deque<Node> queue = new ArrayDeque<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            indexToCopiedNode.putIfAbsent(curr.val, new Node(curr.val));
            Node curCopy = indexToCopiedNode.get(curr.val);
            if (res == null) res = curCopy;
            for (Node next : curr.neighbors) {
                if (!indexToCopiedNode.containsKey(next.val)) {
                    indexToCopiedNode.put(next.val, new Node(next.val));
                    queue.offer(next);
                }
                indexToCopiedNode.get(curr.val).neighbors.add(indexToCopiedNode.get(next.val));
            }
        }
        return res;
    }

    public static void main(String[] args) {
        CloneGraph solution = new CloneGraph();

        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        n1.neighbors.add(n2);
        n1.neighbors.add(n4);
        n2.neighbors.add(n1);
        n2.neighbors.add(n3);
        n3.neighbors.add(n2);
        n3.neighbors.add(n4);
        n4.neighbors.add(n1);
        n4.neighbors.add(n3);
        Node res = solution.cloneGraph(n1);

    }
}

class Node {
    public int val;
    public List<Node> neighbors;
    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
}