import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RemoveNthNodeFromEndOfList {

    /************ Solution 1: One Pass by Two Pointers (slow & fast) ***************/
    /**
     * Time: O(L)   Space: O(1)
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0, head);
        ListNode p1 = dummy;
        for (int i = 0; i < n; i++) p1 = p1.next;
        ListNode p2 = dummy;
        while (p1.next != null) {
            p1 = p1.next;
            p2 = p2.next;
        }
        p2.next = p2.next.next;
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
        print(solution.removeNthFromEnd(p1, 5));
        print(p1);
        print(solution.removeNthFromEnd(p1, 1));
        print(p1);
        print(solution.removeNthFromEnd(p1, 3));
        print(p1);
    }
}
