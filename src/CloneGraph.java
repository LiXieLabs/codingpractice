import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 133. Clone Graph (https://leetcode.com/problems/clone-graph/description/)
 */
public class CloneGraph {

    /************** Solution 1: BFS ************************/
    /**
     * Time: O(V + E) = min O(N) max O(N^2)
     * Space: O(N).
     * Space occupied by the indexToCopiedNode - O(N) and in addition to that
     * Space occupied by the queue - O(W) where W is the width of the graph.
     * Overall, the space complexity would be O(N).
     */
    public Node cloneGraph1(Node node) {
        // 小心 edge case！！！
        if (node == null) return null;
        // key 是 original node 也可以
        Map<Integer, Node> mapIdToNode = new HashMap<>();
        Deque<Node> queue = new ArrayDeque<>();
        Node newNode = new Node(node.val);
        queue.offer(node);
        mapIdToNode.put(node.val, newNode);
        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            Node copy = mapIdToNode.get(curr.val);
            for (Node neighbor : curr.neighbors) {
                if (!mapIdToNode.containsKey(neighbor.val)) {
                    mapIdToNode.put(neighbor.val, new Node(neighbor.val));
                    // 如果 mapIdToNode 本来没有这个 neighbor，则是第一次遇到这个 neighbor，
                    // 而不是遍历过的点，则需要加入 queue
                    queue.offer(neighbor);
                }
                // 如果 mapIdToNode 本来已经有这个 neighbor 了，则是连回去那条线
                // 如果 mapIdToNode 本来没有这个 neighbor，则是连出去那条线
                // 但是两种情况，都要连线，因为是双向的 adj list
                mapIdToNode.get(neighbor.val).neighbors.add(copy);
            }
        }
        return newNode;
    }

    /**************** Solution 2: Recur DFS **********************/
    /**
     * Time: O(V + E) = min O(N) max O(N^2)
     * Space: O(N).
     * Space occupied by the map - O(N) and in addition to that
     * Space occupied by the recur call stack - O(D) where D is the width of the graph.
     * Overall, the space complexity would be O(N).
     */
    Map<Integer, Node> map;
    public Node cloneGraph2(Node node) {
        if (node == null) return null;
        map = new HashMap<>();
        Node newNode = new Node(node.val);
        map.put(node.val, newNode);
        recur2(node);
        return newNode;
    }

    private void recur2(Node curr) {
        Node copy = map.get(curr.val);
        for (Node neighbor : curr.neighbors) {
            if (!map.containsKey(neighbor.val)) {
                map.put(neighbor.val, new Node(neighbor.val));
                recur2(neighbor);
            }
            map.get(neighbor.val).neighbors.add(copy);
        }
    }

    /**************** Solution 3: Another Recur DFS **********************/
    /**
     * 进 recur 再 init node 也可以
     *
     * Time: O(V + E) = min O(N) max O(N^2)
     * Space: O(N).
     * Space occupied by the map - O(N) and in addition to that
     * Space occupied by the recur call stack - O(D) where D is the width of the graph.
     * Overall, the space complexity would be O(N).
     */
    Map<Integer, Node> map3;
    public Node cloneGraph(Node node) {
        if (node == null) return null;
        map3 = new HashMap<>();
        return recur3(node);
    }

    private Node recur3(Node node) {
        Node copy = new Node(node.val);
        map3.put(node.val, copy);
        for (Node neighbor : node.neighbors) {
            // ⚠️注意⚠️ 不能 ncopy = map3.getOrDefault(neighbor.val, recur3(neighbor));
            // 因为这样无论如何，recur3(neighbor) 都会被执行，陷入死循环！！！
            if (!map3.containsKey(neighbor.val)) {
                recur3(neighbor);
            }
            copy.neighbors.add(map.get(neighbor.val));
        }
        return copy;
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
        Node res = solution.cloneGraph(n1); // [[2,4],[1,3],[2,4],[1,3]]
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