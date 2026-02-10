import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReverseLinkedList {

    /********* Solution 1: Iterative ************/
    /**
     * Time: O(N)  Space: O(1)
     */
    public ListNode reverseList1(ListNode head) {
        ListNode prev = null, curr = head;
        while (curr != null) {
            ListNode temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }
        return prev;
    }

    /********* Solution 2: Recursive ************/
    /**
     * Time: O(N)  Space: O(N) by recur stack
     */
    public ListNode reverseList(ListNode head) {
        // ⚠️注意⚠️ 先 check head == null，不然 head.next 会 NPE!!!
        if (head == null || head.next == null) return head;
        ListNode newHead = reverseList(head.next);
        // ⚠️关键点⚠️ head.next 是 newTail！！！
        head.next.next = head;
        head.next = null;
        return newHead;
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
        ReverseLinkedList solution = new ReverseLinkedList();
        ListNode l1 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        print(solution.reverseList(l1));
    }
}