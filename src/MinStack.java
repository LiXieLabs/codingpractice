import java.util.ArrayDeque;
import java.util.Deque;

public class MinStack {

    /**** Solution 1: Each node 记录到该点为止的最小值 **************/
//    private Deque<int[]> stack;
//
//    public MinStack() {
//        stack = new ArrayDeque<>();
//    }
//
//    public void push(int val) {
//        int preMin = stack.isEmpty() ? Integer.MAX_VALUE : stack.peek()[1];
//        int curMin = Math.min(preMin, val);
//        stack.push(new int[]{val, curMin});
//    }
//
//    public void pop() {
//        stack.pop();
//    }
//
//    public int top() {
//        return stack.peek()[0];
//    }
//
//    public int getMin() {
//        return stack.peek()[1];
//    }

    /************* Solution 2: Two Stacks ********************/
    /**
     * Time: O(1) for all operations
     * Space: O(N)
     *
     * 优化：
     * （1）只记录最小值，形成一个单调递减的minStack，
     *     但不是普通的Monotonic Stack，因为Monotonic Stack每个值都要进去，同时维护单调性
     *     这里不用每个都进去，只要不断刷新最小值即可
     * （2）对于连续重复的最小值，在minStack中记录出现次数以节省空间
     *
     */
    private Deque<Integer> stack;
    private Deque<int[]> minStack;

    public MinStack() {
        stack = new ArrayDeque<>();
        minStack = new ArrayDeque<>();
    }

    public void push(int val) {
        stack.push(val);
        if (minStack.isEmpty() || minStack.peek()[0] > val) {
            minStack.push(new int[]{val, 1});
        } else if (minStack.peek()[0] == val) {
            minStack.peek()[1]++;
        }
    }

    public void pop() {
        int val = stack.pop();
        if (val == minStack.peek()[0]) {
            minStack.peek()[1]--;
            if (minStack.peek()[1] == 0) {
                minStack.pop();
            }
        }
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minStack.peek()[0];
    }

    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.println(minStack.getMin());
        minStack.pop();
        System.out.println(minStack.top());
        System.out.println(minStack.getMin());
    }
}