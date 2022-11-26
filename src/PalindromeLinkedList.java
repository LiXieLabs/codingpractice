public class PalindromeLinkedList {

    /************** Soluion 1: 3 Passes (找中点, reverse list, 左右比较 **************/
    /**
     * Time: O(N)  Space: O(1)
     */
    public boolean isPalindrome(ListNode head) {
        // Step 1
        // 找到中点
        // 1 -> 2 -> 3 -> 4 is 3
        // 1 -> 2 -> 3 -> 4 -> 5 is 3
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        // Step 2
        // reverse second half
        // 1 -> 2 -> 3 <- 4
        // 1 -> 2 -> 3 <- 4 <- 5
        ListNode prev = null, curr = slow;
        while (curr != null) {
            ListNode temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }
        // 左右比较
        ListNode l = head, r = prev;
        while (r != null) {
            if (l.val != r.val) return false;
            l = l.next;
            r = r.next;
        }
        return true;
    }

    public static void main(String[] args) {
        PalindromeLinkedList solution = new PalindromeLinkedList();

        ListNode l1 = new ListNode(1, new ListNode(2, new ListNode(2, new ListNode(1))));
        System.out.println(solution.isPalindrome(l1));

        ListNode l2 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(2, new ListNode(1)))));
        System.out.println(solution.isPalindrome(l2));

        ListNode l3 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(1))));
        System.out.println(solution.isPalindrome(l3));

        ListNode l4 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(1)))));
        System.out.println(solution.isPalindrome(l4));
    }
}
