import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 * 23. Merge k Sorted Lists (https://leetcode.com/problems/merge-k-sorted-lists/description/)
 */
public class MergeKSortedLists {

    /********** Solution 1: K pointers + Min Heap *************/
    /**
     * a data structure to dynamically track the smallest number among k current elements.
     * -> k size min heap
     *
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
            // 此处也要 null check！！！
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
    ListNode[] lists;

    public ListNode mergeKLists(ListNode[] lists) {
        this.lists = lists;
        return merge(0, lists.length - 1);
    }

    private ListNode merge(int l, int r) {
        // Handle base cases
        if (l > r) return null;
        if (l == r) return lists[l];
        // Divide
        int mid = l + (r - l) / 2;
        ListNode l1 = merge(l, mid), l2 = merge(mid, r);
        // Conquer (Same as 21. Merge Two Sorted Lists (https://leetcode.com/problems/merge-two-sorted-lists/description/))
        ListNode dummy = new ListNode();
        ListNode curr = dummy;
        while (l1 != null && l2 != null ) {
            if (l1.val <= l2.val) {
                curr.next = l1;
                l1 = l1.next;
            } else {
                curr.next = l2;
                l2 = l2.next;
            }
            curr = curr.next;
        }
        curr.next = l1 != null ? l1 : l2;
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
        // TC1: []
        print(solution.mergeKLists(new ListNode[]{null}));
        // TC2: []
        print(solution.mergeKLists(new ListNode[]{}));
        // TC3: [1,1,2,3,4,4,5,6]
        ListNode l1 = new ListNode(1, new ListNode(4, new ListNode(5)));
        ListNode l2 = new ListNode(1, new ListNode(3, new ListNode(4)));
        ListNode l3 = new ListNode(2, new ListNode(6));
        print(solution.mergeKLists(new ListNode[]{l1, l2, l3}));
    }
}
