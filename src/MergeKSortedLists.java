import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class MergeKSortedLists {

    /********** Solution 1: K pointers + Min Heap *************/
    /**
     * Time: O(NlogK)   Space: O(K)
     */
    public ListNode mergeKLists1(ListNode[] lists) {
        ListNode dummy = new ListNode();
        ListNode curr = dummy;
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>(Comparator.comparingInt(node -> node.val));
        for (ListNode head : lists) {
            // 小心！！！必须要 null check！！！
            // 不然 solution.mergeKLists(new ListNode[]{null}) 会 NPE
            if (head != null) minHeap.offer(head);
        }
        while (!minHeap.isEmpty()) {
            ListNode minNode = minHeap.poll();
            curr.next = minNode;
            curr = curr.next;
            if (minNode.next != null) minHeap.offer(minNode.next);
        }
        return dummy.next;
    }

    /********** Solution 2: Divide & Conquer ***************/
    /**
     * Time: O(NlogK)
     * T(K) = 2T(K/2) + O(N) = 2^xT(K/2^x) + O(xN) ---- x = logK ----> O(K + NlogK) = O(NlogK)
     *
     * Space: O(logK) by recur stack
     */
    public ListNode mergeKLists(ListNode[] lists) {
        return merge(lists, 0, lists.length);
    }

    private ListNode merge(ListNode[] lists, int i, int j) {
        // Handle base cases
        if (i == j) return null;
        if (i + 1 == j) return lists[i];
        // Divide
        int mid = (i + j) / 2;
        ListNode l = merge(lists, i, mid);
        ListNode r = merge(lists, mid, j);
        // Conquer
        ListNode dummy = new ListNode();
        ListNode curr = dummy;
        while (l != null && r != null ) {
            if (l.val <= r.val) {
                curr.next = l;
                l = l.next;
            } else {
                curr.next = r;
                r = r.next;
            }
            curr = curr.next;
        }
        curr.next = l != null ? l : r;
        return dummy.next;
    }

    private static void print(ListNode head) {
        List<Integer> res = new ArrayList<>();
        while (head != null) {
            res.add(head.val);
            head = head.next;
        }
        System.out.println("[" + res.stream().map(String::valueOf).collect(Collectors.joining(",")) + "]");
    }

    public static void main(String[] args) {
        MergeKSortedLists solution = new MergeKSortedLists();
        print(solution.mergeKLists(new ListNode[]{null}));
        print(solution.mergeKLists(new ListNode[]{}));
        ListNode l1 = new ListNode(1, new ListNode(4, new ListNode(5)));
        ListNode l2 = new ListNode(1, new ListNode(3, new ListNode(4)));
        ListNode l3 = new ListNode(2, new ListNode(6));
        print(solution.mergeKLists(new ListNode[]{l1, l2, l3}));
    }
}
