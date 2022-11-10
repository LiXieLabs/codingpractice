import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InsertIntoASortedCircularLinkedList {

    /********* Solution 1: 2 pointers iteration ***********/
    /**
     * Time: O(N)  Space: O(1)
     *
     * Case1: prev.val <= insertVal && insertVal <= curr.val => 插入正常递增序列中的位置
     * Case2: curr.val < prev.val => 交界处，curr is min， prev is max => 如果 <= min || >= max 则插入
     * Case3: 原本只有一个node，或者所有node大小一样时
     *
     * 优化：
     * Node insert = new Node(insertVal);
     * prev.next = insert;
     * insert.next = curr;
     * 可以替换为
     * prev.next = new Node(insertVal, curr)
     */
    public Node insert(Node head, int insertVal) {
        Node insert = new Node(insertVal);
        if (head == null) {
            insert.next = insert;
            return insert;
        }
        Node prev = head;
        Node curr = head.next;
        while (curr != head) {
            // Case1: prev.val <= insertVal && insertVal <= curr.val => 插入正常递增序列中的位置
            // Case2: curr.val < prev.val => 交界处，curr is min， prev is max => 如果 <= min || >= max 则插入
            if ((prev.val <= insertVal && insertVal <= curr.val) ||
                (curr.val < prev.val && (insertVal <= curr.val || insertVal >= prev.val))) {
                prev.next = insert;
                insert.next = curr;
                return head;
            }
            prev = prev.next;
            curr = curr.next;
        }
        // Case3: 原本只有一个node，或者所有node大小一样时
        prev.next = insert;
        insert.next = curr;
        return head;
    }

    static class Node {
        public int val;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _next) {
            val = _val;
            next = _next;
        }
    }

    private static void print(Node head) {
        List<Integer> res = new ArrayList<>();
        Node curr = head;
        boolean firstTime = true;
        while (curr != head || firstTime) {
            firstTime = false;
            res.add(head.val);
            head = head.next;
        }
        System.out.println("[" + res.stream().map(String::valueOf).collect(Collectors.joining(",")) + "]");
    }

    public static void main(String[] args) {
        InsertIntoASortedCircularLinkedList solution = new InsertIntoASortedCircularLinkedList();

        Node tail1 = new Node(1);
        Node head1 = new Node(3, new Node(5, tail1));
        tail1.next = head1;
        print(head1);
        print(solution.insert(head1, 0));
        print(solution.insert(head1, 2));
        print(solution.insert(head1, 4));
        print(solution.insert(head1, 6));
        System.out.println();

        Node head2 = new Node(1);
        head2.next = head2;
        print(head2);
        print(solution.insert(head2, 1));
        print(solution.insert(head2, 2));
        System.out.println();

        Node tail3 = new Node(1);
        Node head3 = new Node(3, new Node(4, tail3));
        tail3.next = head3;
        print(head3);
        print(solution.insert(head3, 2));
        System.out.println();

        print(solution.insert(null, 1));
    }
}