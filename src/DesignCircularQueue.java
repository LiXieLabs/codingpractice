import java.util.concurrent.locks.ReentrantLock;

public class DesignCircularQueue {

    // TODO: make solutions thread safe
    // https://leetcode.com/problems/design-circular-queue/solution/

    /******** Solution 1: Array + i denotes head + modulus ***********/
    int[] queue;
    int i, count, capacity;

    public DesignCircularQueue(int k) {
        queue = new int[k];
        i = 0;
        count = 0;
        capacity = k;
    }

    public boolean enQueue(int value) {
        if (count == capacity) return false;
        queue[(i + count++) % capacity] = value;
        return true;
    }

    public boolean deQueue() {
        if (count == 0) return false;
        i = (i + 1) % capacity;
        count--;
        return true;
    }

    public int Front() {
        return isEmpty() ? -1 : queue[i];
    }

    public int Rear() {
        return isEmpty() ? -1 : queue[(i + count - 1) % capacity];
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public boolean isFull() {
        return count == capacity;
    }

    /***************** Solution 2: Linked List *******************/
//    DCQNode head, tail;
//    int count, capacity;
//
//    public DesignCircularQueue(int k) {
//        head = new DCQNode(-1);
//        tail = new DCQNode(-1);
//        head.next = tail;
//        tail.prev = head;
//        count = 0;
//        capacity = k;
//    }
//
//    public boolean enQueue(int value) {
//        if (count == capacity) return false;
//        DCQNode node = new DCQNode(value);
//        tail.prev.next = node;
//        node.prev = tail.prev;
//        tail.prev = node;
//        node.next = tail;
//        count++;
//        return true;
//    }
//
//    public boolean deQueue() {
//        if (count == 0) return false;
//        head.next.next.prev = head;
//        head.next = head.next.next;
//        count--;
//        return true;
//    }
//
//    public int Front() {
//        return isEmpty() ? -1 : head.next.v;
//    }
//
//    public int Rear() {
//        return isEmpty() ? -1 : tail.prev.v;
//    }
//
//    public boolean isEmpty() {
//        return count == 0;
//    }
//
//    public boolean isFull() {
//        return count == capacity;
//    }

    public static void main(String[] args) {
        DesignCircularQueue solution = new DesignCircularQueue(3);
        System.out.println(solution.Front());
        System.out.println(solution.Rear());
        System.out.println(solution.enQueue(1));
        System.out.println(solution.enQueue(2));
        System.out.println(solution.enQueue(3));
        System.out.println(solution.enQueue(4));
        System.out.println(solution.Front());
        System.out.println(solution.Rear());
        System.out.println(solution.deQueue());
        System.out.println(solution.enQueue(4));
        System.out.println(solution.Front());
        System.out.println(solution.Rear());
        System.out.println(solution.isFull());
        System.out.println(solution.deQueue());
        System.out.println(solution.deQueue());
        System.out.println(solution.deQueue());
        System.out.println(solution.isEmpty());
    }
}

class DCQNode {
    int v;
    DCQNode prev;
    DCQNode next;

     public DCQNode(int v) {
         this.v = v;
         prev = null;
         next = null;
     }
}