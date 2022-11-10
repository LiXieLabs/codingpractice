import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LFUCache {

    Map<Integer, LFUNode> nodes;
    Map<Integer, LFUNode> freqs;
    LFUNode head, tail;
    int capacity;

    /************* Solution 1: Double linked list + HashMap **************/
    /**
     *          freqs[2]                     freqs[1]
     *            |                             |
     * HEAD <=> Node(3,3) <=> Node(2,2) <=> Node(1,1) <=> TAIL
     *            |              |              |
     *          nodes[3]      nodes[2]       nodes[1]
     *
     * 同样freq的链表部分，LRU靠近tail
     */
    public LFUCache(int capacity) {
        nodes = new HashMap<>();
        freqs = new HashMap<>();
        head = new LFUNode(-1, -1, 0);
        tail = new LFUNode(-1, -1, 0);
        head.next = tail;
        tail.prev = head;
        // 为了第一次添加的点可以找到freq-1的head
        freqs.put(0, tail);
        this.capacity = capacity;
    }

    public int get(int key) {
        if (!nodes.containsKey(key)) return -1;
        LFUNode node = nodes.get(key);
        LFUNode newNode = new LFUNode(key, node.val, node.freq + 1);
        insert(newNode);
        remove(node);
        return node.val;
    }

    public void put(int key, int value) {
        // 小心！！！起始capacity==0情况，如果试图移除LFU会错！！！
        if (capacity == 0) return;
        if (nodes.containsKey(key)) {
            LFUNode node = nodes.get(key);
            LFUNode newNode = new LFUNode(key, value, node.freq + 1);
            insert(newNode);
            remove(node);
        } else {
            // 小心！！！需要先移除LFU/LRU再添加！！！
            if (nodes.size() == capacity) {
                LFUNode removeNode = tail.prev;
                remove(removeNode);
                nodes.remove(removeNode.key);
            }
            LFUNode newNode = new LFUNode(key, value, 1);
            insert(newNode);
        }
    }

    private void insert(LFUNode node) {
        // 找到当前node的freq对应的head
        // 如果还没有则找到freq-1的head，新添加点freq==1的有tail，已存在点freq+1的还没移除一定有
        LFUNode currHead = freqs.containsKey(node.freq) ?
                freqs.get(node.freq) : freqs.get(node.freq - 1);

        currHead.prev.next = node;
        node.prev = currHead.prev;
        currHead.prev = node;
        node.next = currHead;

        nodes.put(node.key, node);
        freqs.put(node.freq, node);
    }

    private void remove(LFUNode node) {
        // 如果要移除的node是其freq的head，需要找到新的head
        // 或者如果是唯一一个，需要直接从freqs map移除
        if (freqs.get(node.freq) == node) {
            if (node.next.freq == node.freq) {
                freqs.put(node.freq, node.next);
            } else {
                freqs.remove(node.freq);
            }
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void print() {
        List<List<Integer>> res = new ArrayList<>();
        LFUNode curr = head.next;
        while (curr != tail) {
            res.add(Arrays.asList(curr.key, curr.val));
            curr = curr.next;
        }
        System.out.println(res);
    }

    public static void main(String[] args) {
        System.out.println("———————— cache1 ————————");
        LFUCache cache1 = new LFUCache(2);
        cache1.print();

        cache1.put(1, 1);
        cache1.print();

        cache1.put(2, 2);
        cache1.print();

        System.out.println(cache1.get(1));
        cache1.print();

        cache1.put(3, 3);
        cache1.print();

        System.out.println(cache1.get(2));
        cache1.print();

        System.out.println(cache1.get(3));
        cache1.print();

        cache1.put(4, 4);
        cache1.print();

        System.out.println(cache1.get(1));
        cache1.print();

        System.out.println(cache1.get(3));
        cache1.print();

        System.out.println(cache1.get(4));
        cache1.print();

        System.out.println("———————— cache2 ————————");
        LFUCache cache2 = new LFUCache(0);
        cache2.print();

        cache2.put(0, 0);
        cache2.print();

        System.out.println(cache2.get(0));
        cache2.print();
    }
}

class LFUNode {

    int key, val, freq;
    LFUNode prev, next;

    public LFUNode(int key, int val, int freq) {
        this.key = key;
        this.val = val;
        this.freq = freq;
        prev = null;
        next = null;
    }
}