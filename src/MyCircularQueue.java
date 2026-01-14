public class MyCircularQueue {

    /**************** Solution 1: Array + Rolling Index by Modulus **************/
    /**
     * head + tail + size + capacity
     * capacity can be replaced by queue.length;
     * tail can be replaced by head & size calculation;
     *       tail move -> by dequeue     head move-> by enqueue
     *        |                           |
     * 【 0,  1,  2,  3,  4,  5,  6,  7,  8,  0 】
     *
     * Time: O(1)   Space: O(K)
     */
    int[] queue;
    int head, size;

    public MyCircularQueue(int k) {
        queue = new int[k];
        head = -1;
        size = 0;
    }

    public boolean enQueue(int value) {
        if (isFull()) return false;
        // 小心 head 必须停在最后 enqueue 处，不然 rear 会错！！！
        head = (head + 1) % queue.length;
        queue[head] = value;
        size++;
        return true;
    }

    public boolean deQueue() {
        if (isEmpty()) return false;
        size--;
        return true;
    }

    public int Front() {
        return isEmpty() ? -1 : queue[getTailIdx()];
    }

    public int Rear() {
        return isEmpty() ? -1 : queue[head];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == queue.length;
    }

    private int getTailIdx() {
        // 一种典型算法！
        return (queue.length + (head - size + 1)) % queue.length;
    }
}
