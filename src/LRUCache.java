import java.util.HashMap;
import java.util.Map;

public class LRUCache {

    int capacity;
    Map<Integer, LRUNode> map;
    LRUNode dummyHead;
    LRUNode dummyTail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        dummyHead = new LRUNode();
        dummyTail = new LRUNode();
        dummyHead.next = dummyTail;
        dummyTail.next = dummyHead;
    }

    public int get(int key) {
        if (map.containsKey(key)) {
            LRUNode node = map.get(key);
            removeNode(node);
            addToHead(node);
            return node.val;
        }
        return -1;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            LRUNode node = map.get(key);
            removeNode(node);
            addToHead(node);
            node.val = value;
        } else {
            LRUNode node = new LRUNode(key, value);
            addToHead(node);
            map.put(key, node);
            if (map.size() > capacity) {
                LRUNode tail = dummyTail.prev;
                removeNode(tail);
                map.remove(tail.key);
            }
        }
    }

    private void removeNode(LRUNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void addToHead(LRUNode node) {
        dummyHead.next.prev = node;
        node.next = dummyHead.next;
        dummyHead.next = node;
        node.prev = dummyHead;
    }

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2);
        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(cache.get(1));
        cache.put(3, 3);
        System.out.println(cache.get(2));
        cache.put(4, 4);
        System.out.println(cache.get(1));
        System.out.println(cache.get(3));
        System.out.println(cache.get(4));
    }
}

class LRUNode {

    int key;
    int val;
    LRUNode prev;
    LRUNode next;

    public LRUNode() {}

    public LRUNode(int key, int val) {
        this.key = key;
        this.val = val;
    }

    public LRUNode(int key, int val, LRUNode prev, LRUNode next) {
        this.key = key;
        this.val = val;
        this.prev = prev;
        this.next = next;
    }

}