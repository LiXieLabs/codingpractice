public class BrowserHistory {

    /********* Solution 1: Double Linked List *************/
    static class Node {
        String url;
        Node prev, next;

        public Node(String url) {
            this.url = url;
        }
    }

    Node head, curr;

    public BrowserHistory(String homepage) {
        head = new Node(homepage);
        curr = head;
    }

    // O(1)
    public void visit(String url) {
        Node next = new Node(url);
        curr.next = next;
        next.prev = curr;
        curr = next;
    }

    // O(N)
    public String back(int steps) {
        while (steps-- > 0 && curr.prev != null) {
            curr = curr.prev;
        }
        return curr.url;
    }

    // O(N)
    public String forward(int steps) {
        while (steps-- > 0 && curr.next != null) {
            curr = curr.next;
        }
        return curr.url;
    }

    public static void main(String[] args) {
        BrowserHistory solution = new BrowserHistory("home");
        solution.visit("1.com");
        solution.visit("2.com");
        solution.visit("3.com");
        System.out.println(solution.back(1));
        System.out.println(solution.back(1));
        System.out.println(solution.forward(1));
        solution.visit("4.com");
        System.out.println(solution.forward(2));
        System.out.println(solution.back(2));
        System.out.println(solution.back(7));
    }
}
