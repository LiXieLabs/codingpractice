import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReorderList {

    /************** Soluion 1: 3 Passes (找中点, reverse list, merge 2 lists **************/
    /**
     * Time: O(3N) = O(N)  Space: O(1)
     */
    public void reorderList(ListNode head) {
        // Step 1
        // 找到中点
        // 1 -> 2 -> 3 -> 4 is 3
        // 1 -> 2 -> 3 -> 4 -> 5 is 3
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        // Step 2
        // reverse second half
        //          null
        //           |
        // 1 -> 2 -> 3 <- 4
        //          null
        //           |
        // 1 -> 2 -> 3 <- 4 <- 5
        ListNode prev = null;
        ListNode curr = slow;
        while (curr != null) {
            ListNode temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }
        // Step 3
        // merge 2 lists
        ListNode tail = new ListNode();;
        ListNode l = head;
        ListNode r = prev;
        // 截止条件 奇数r==null 偶数l==r
        while (r != null && l != r) {
            // connect left
            tail.next = l;
            l = l.next;
            tail = tail.next;
            // connect right
            tail.next = r;
            r = r.next;
            tail = tail.next;
        }
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
        ReorderList solution = new ReorderList();

        ListNode l1 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        solution.reorderList(l1);
        print(l1);

        ListNode l2 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4))));
        solution.reorderList(l2);
        print(l2);
    }
}