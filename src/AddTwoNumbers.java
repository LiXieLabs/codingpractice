import java.util.ArrayList;
import java.util.List;

/**
 * 2. Add Two Numbers (https://leetcode.com/problems/add-two-numbers/description/)
 */
public class AddTwoNumbers {

    /***************** Solution 1: Simulate *******************/
    /**
     * use dummy node!!!
     *
     * Time: O(max(M,N))   Space: O(max(M,N))
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode();
        ListNode curr = dummy;
        int carry = 0;
        while (l1 != null || l2 != null || carry != 0) {
            if (l1 != null) {
                carry += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                carry += l2.val;
                l2 = l2.next;
            }
            curr.next = new ListNode(carry % 10);
            carry /= 10;
            curr = curr.next;
        }
        return dummy.next;
    }

    public static ListNode build(int[] values) {
        ListNode dummy = new ListNode();
        ListNode curr = dummy;
        for (int v : values) {
            curr.next = new ListNode(v);
            curr = curr.next;
        }
        return dummy.next;
    }

    public static void print(ListNode node) {
        List<String> res = new ArrayList<>();
        while (node != null) {
            res.add(String.valueOf(node.val));
            node = node.next;
        }
        System.out.println( "[" + String.join(",", res) + "]");
    }

    public static void main(String[] args) {
        AddTwoNumbers solution = new AddTwoNumbers();
        print(solution.addTwoNumbers(build(new int[]{2,4,3}), build(new int[]{5,6,4}))); // [7,0,8]
        print(solution.addTwoNumbers(build(new int[]{0}), build(new int[]{0}))); // [0]
        print(solution.addTwoNumbers(build(new int[]{9,9,9,9,9,9,9}), build(new int[]{9,9,9,9}))); // [8,9,9,9,0,0,0,1]
    }
}
