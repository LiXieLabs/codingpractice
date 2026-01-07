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
    Map<String, Node432> map;

    public AllOoneDataStructure() {
        head = new Node432(0, "");
        tail = new Node432(0, "");
        head.next = tail;
        tail.prev = head;
        map = new HashMap<>();
    }

    public void inc(String key) {
        Node432 curr = map.getOrDefault(key, head);
        if (curr.next.count != curr.count + 1) {
            Node432 incr = new Node432(curr.count + 1);
            curr.next.prev = incr;
            incr.next = curr.next;
            incr.prev = curr;
            curr.next = incr;
        }
        curr.next.keys.add(key);
        map.put(key, curr.next);
        if (curr.keys.contains(key)) {
            curr.keys.remove(key);
            if (curr.keys.isEmpty()) {
                curr.prev.next = curr.next;
                curr.next.prev = curr.prev;
            }
        }
    }

    public void dec(String key) {
        Node432 curr = map.get(key);
        if (curr.prev.count != curr.count - 1) {
            Node432 decr = new Node432(curr.count - 1);
            curr.prev.next = decr;
            decr.prev = curr.prev;
            curr.prev = decr;
            decr.next = curr;
        }
        curr.prev.keys.add(key);
        map.put(key, curr.prev);
        if (curr.keys.contains(key)) {
            curr.keys.remove(key);
            if (curr.keys.isEmpty()) {
                curr.prev.next = curr.next;
                curr.next.prev = curr.prev;
            }
        }
    }

    public String getMaxKey() {
        return tail.prev.keys.iterator().next();
    }

    public String getMinKey() {
        return head.next.keys.iterator().next();
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

    public Node432(int cnt) {
        count = cnt;
        prev = null;
        next = null;
        keys = new HashSet<>();
    }

    public Node432(int cnt, String key) {
        count = cnt;
        prev = null;
        next = null;
        keys = new HashSet<>();
        keys.add(key);
    }
}
