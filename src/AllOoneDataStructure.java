import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 432. All O`one Data Structure (https://leetcode.com/problems/all-oone-data-structure/description/)
 */
public class AllOoneDataStructure {

    /*********** Solution 1: HashMap + Double Linked List ****************/
    /**
     * 需要：
     * （1）一个数据结构 string key -> its count => HashMap
     * （2）一个数据结构 （2.1）O(1) 保持 counts sorted
     *                （2.2）可以 O(1) 定位到某个 count
     *                （2.3）维护该 count 的所有 keys，O(1) 添加/删除
     *                => double linked list of count & its keys
     */
    Node432 head, tail;
    Map<String, Node432> counter;

    public AllOoneDataStructure() {
        // head and tail are sentinels!!! don't make them the same node!!!
        head = new Node432(-1, "");
        tail = new Node432(-1, "");
        head.next = tail;
        tail.prev = head;
        counter = new HashMap<>();
    }

    public void inc(String key) {
        if (counter.containsKey(key)) {
            Node432 node = counter.get(key);
            if (node.next.count != node.count + 1) {
                insertNode(node.count + 1, key, node);
            } else {
                node.next.keys.add(key);
            }
            counter.put(key, node.next);
            node.keys.remove(key);
            if (node.keys.isEmpty()) {
                removeNode(node);
            }
        } else {
            if (head.next != null && head.next.count == 1) {
                head.next.keys.add(key);
            } else {
                insertNode(1, key, head);
            }
            counter.put(key, head.next);
        }
    }

    public void dec(String key) {
        Node432 node = counter.get(key);
        if (node.count != 1) {
            if (node.prev.count != node.count - 1) {
                insertNode(node.count - 1, key, node.prev);
            } else {
                node.prev.keys.add(key);
            }
            counter.put(key, node.prev);
        } else {
            counter.remove(key);
        }
        node.keys.remove(key);
        if (node.keys.isEmpty()) {
            removeNode(node);
        }
    }

    public String getMaxKey() {
        return tail.prev.keys.iterator().next();
    }

    public String getMinKey() {
        return head.next.keys.iterator().next();
    }

    private void insertNode(int cnt, String key, Node432 pre) {
        Node432 cur = new Node432(cnt, key);
        cur.next = pre.next;
        pre.next = cur;
        cur.prev = pre;
        if (cur.next != null) cur.next.prev = cur;
    }

    private void removeNode(Node432 node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public static void main(String[] args) {
        AllOoneDataStructure solution = new AllOoneDataStructure();

        System.out.println(solution.getMaxKey()); // ""
        System.out.println(solution.getMinKey()); // ""
        solution.inc("hello");
        solution.inc("hello");
        System.out.println(solution.getMaxKey()); // hello
        System.out.println(solution.getMinKey()); // hello
        solution.inc("leet");
        System.out.println(solution.getMaxKey()); // hello
        System.out.println(solution.getMinKey()); // leet
    }
}

class Node432 {

    int count;
    Node432 prev, next;
    Set<String> keys;

    public Node432(int cnt, String key) {
        count = cnt;
        prev = null;
        next = null;
        keys = new HashSet<>();
        keys.add(key);
    }
}
