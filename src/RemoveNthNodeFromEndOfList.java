import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 19. Remove Nth Node From End of List (https://leetcode.com/problems/remove-nth-node-from-end-of-list/description/)
 */
public class RemoveNthNodeFromEndOfList {

    /************ Solution 1: One Pass by Two Pointers (slow & fast) ***************/
    /**
     * slow 实际是要移除的 node 的前一个 node，有可能是 dummy！
     *
     * Time: O(L)   Space: O(1)
     */
    public ListNode removeNthFromEnd1(ListNode head, int n) {
        ListNode dummy = new ListNode(0, head);
        ListNode fast = dummy;
        for (int i = 0; i < n; i++) fast = fast.next;
        ListNode slow = dummy;
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return dummy.next;
    }

    /************ Similar 的另一种：fast更往后一个！***************/
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode slow = dummy, fast = head;
        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        slow.next = slow.next.next;
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
        RemoveNthNodeFromEndOfList solution = new RemoveNthNodeFromEndOfList();
        ListNode p1 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode( 4, new ListNode(5)))));
        print(solution.removeNthFromEnd(p1, 5)); // [2,3,4,5]
        print(p1); // [1,2,3,4,5] 因为上一行是 dummy.next，原 linked list 没变！
        print(solution.removeNthFromEnd(p1, 1)); // [1,2,3,4]
        print(p1); // [1,2,3,4]
        print(solution.removeNthFromEnd(p1, 3)); // [1,3,4]
        print(p1); // [1,3,4]
    }
}
