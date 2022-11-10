import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CopyListWithRandomPointer {

    /************* Solution 1: 3 Pass ****************/
    /**
     * Time: O(3N) = O(N)   Space: O(1)
     *
     * 第一遍构造
     * Orig(1) -> Copy(1) -> Orig(2) -> Copy(2) -> ...
     * 第二遍构造random pointers
     * original.next.random = original.random.next;
     * 第三遍复原orig和copy的两个lists
     */
    public Node copyRandomList(Node head) {
        Node curr = head;
        while (curr != null) {
            Node copy = new Node(curr.val);
            Node temp = curr.next;
            curr.next = copy;
            copy.next = temp;
            curr = temp;
        }
        curr = head;
        while (curr != null) {
            curr.next.random = curr.random != null ? curr.random.next : null;
            curr = curr.next.next;
        }
        Node dummy = new Node(0);
        Node prev = dummy;
        curr = head;
        while (curr != null) {
            prev.next = curr.next;
            prev = prev.next;
            curr.next = prev.next;
            curr = prev.next;
        }
        return dummy.next;
    }

    static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    private static void print(Node head) {
        List<int[]> res = new ArrayList<>();
        while (head != null) {
            int random = head.random != null ? head.random.val : -1;
            res.add(new int[]{head.val, random});
            head = head.next;
        }
        System.out.println("[" +
                res.stream().map(p -> "[" +
                        Arrays.stream(p).mapToObj(String::valueOf).collect(Collectors.joining(","))
                        + "]")
                        .collect(Collectors.joining(",")) + "]");
    }

    public static void main(String[] args) {
        CopyListWithRandomPointer solution = new CopyListWithRandomPointer();
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        n1.random = n3;
        n3.random = n2;
        print(n1);
        print(solution.copyRandomList(n1));
    }
}