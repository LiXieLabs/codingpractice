import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 25. Reverse Nodes in k-Group (https://leetcode.com/problems/reverse-nodes-in-k-group/description/)
 */
public class ReverseNodesInKGroup {

    /********* Solution 1: Iterative ************/
    /**
     * Time: O(2N) = O(N)
     * Space: O(1)
     */
    public ListNode reverseKGroup1(ListNode head, int k) {
        ListNode dummy = new ListNode(0, head);
        ListNode preTail = dummy;
        ListNode curHead = head;
        while (true) {
            ListNode cur = curHead;
            for (int i = 0; i < k - 1 && cur != null; i++) {
                cur = cur.next;
            }
            if (cur == null) break;
            preTail.next = cur;
            ListNode pre = cur.next;
            cur = curHead;
            for (int i = 0; i < k; i++) {
                ListNode tmp = cur.next;
                cur.next = pre;
                pre = cur;
                cur = tmp;
            }
            preTail = curHead;
            curHead = cur;
        }
        return dummy.next;
    }

    /********* Solution 2: Recursive ************/
    /**
     * Time: O(2N) = O(N)
     * Space: O(N/k) as N/k is the recursion stack depth
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        // 先数到K，或者遇到结尾。
        ListNode cur = head;
        for (int i = 0; i < k - 1 && cur != null; i++) {
            cur = cur.next;
        }
        // 如果没数到K，直接返回
        if (cur == null) return head;
        // 把后面的recursive搞好
        ListNode pre = reverseKGroup(cur.next, k);
        // reverse current k group
        cur = head;
        for (int i = 0; i < k; i++) {
            ListNode tmp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = tmp;
        }
        return pre;
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
        ReverseNodesInKGroup solution = new ReverseNodesInKGroup();
        ListNode l1 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        print(solution.reverseKGroup(l1, 2));

        ListNode l2 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        print(solution.reverseKGroup(l2, 5));

        ListNode l3 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        print(solution.reverseKGroup(l3, 1));
    }
}
