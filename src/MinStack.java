import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 155. Min Stack (https://leetcode.com/problems/min-stack/)
 */
public class MinStack {

    /**** Solution 1: Two Stacks, 一个是regular的，一个monotonic的 **************/
//    Deque<Integer> stack;
//    Deque<Integer> minStack;
//
//    public MinStackCopy() {
//        stack = new ArrayDeque<>();
//        minStack = new ArrayDeque<>();
//    }
//
//    public void push(int val) {
//        stack.push(val);
//        if (minStack.isEmpty() || val <= minStack.peek()) {
//            minStack.push(val);
//        }
//    }
//
//    public void pop() {
//        int val = stack.pop();
//        if (!minStack.isEmpty() && minStack.peek() == val) {
//            minStack.pop();
//        }
//    }
//
//    public int top() {
//        return stack.peek();
//    }
//
//    public int getMin() {
//        return minStack.peek();
//    }

    /************* Solution 2: Solution 1 优化 ********************/
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
//
//    public void push(int val) {
//        stack.push(val);
//        if (minStack.isEmpty() || minStack.peek()[0] > val) {
//            minStack.push(new int[]{val, 1});
//        } else if (minStack.peek()[0] == val) {
//            minStack.peek()[1]++;
//        }
//    }
//
//    public void pop() {
//        int val = stack.pop();
//        if (val == minStack.peek()[0]) {
//            minStack.peek()[1]--;
//            if (minStack.peek()[1] == 0) {
//                minStack.pop();
//            }
//        }
//    }
//
//    public int top() {
//        return stack.peek();
//    }
//
//    public int getMin() {
//        return minStack.peek()[0];
//    }

    /************* Solution 3: Solution 2 优化 ********************/
    /**
     * Time: O(1) for all operations
     * Space: O(N)
     *
     * 优化：
     * （1）不记录最小值出现次数，只记录其第一次出现时 regular stack 的 size，即相当于它的 index
     *     因为它从 regular stack 出去之前，它前面的所有数字不会发生变化，可以认为这个 index 是固定的
     *     只有当 pop 出来的 val 和 index 都 match minStack's top，minStack 才 pop！！！
     *
     * 数据结构 + init 和 solution 2 一样，此处省略
     */
    public void push(int val) {
        stack.push(val);
        if (minStack.isEmpty() || val < minStack.peek()[1]) {
            minStack.push(new int[]{stack.size() - 1, val});
        }
    }

    public void pop() {
        int val = stack.pop();
        if (minStack.peek()[0] == stack.size() && minStack.peek()[1] == val) {
            minStack.pop();
        }
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minStack.peek()[1];
    }

    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.println(minStack.getMin()); // -3
        minStack.pop();
        System.out.println(minStack.top()); // 0
        System.out.println(minStack.getMin()); // -2
    }
}