import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

/**
 * 716. Max Stack (https://leetcode.com/problems/max-stack/description/)
 */
public class MaxStack {

    /************ Solution 1: Stack + PriorityQueue + Lazy Update by id & HashSet (最优解) *****************/
    /**
     * Stack 记录 stack 顺序
     * PriorityQueue (MaxHeap) 记录最大值
     * => 注意此处不能像LC155只用stack记录到某一点最大值，因为popMax不按stack顺序，要随时得知stack某一位置之前变动后的最大值
     * => 需要更track之前的所有排序
     *
     * HashSet (removed) 记录从 stack / maxHeap 分别已经 pop / popMax 出去的 id，方便对方进行 lazy update
     * pop / peek / popMax / peekMax 时 stack / maxHeap 首先检验自己的 top element id 是否已经被对方加入 removed
     * 直到顶端尚未被 removed，再进行常规操作
     *
     * Time:
     * push - O(push to stack) + O(push to heap) = O(1) + O(logN) = O(logN)
     *
     * pop / popMax - amortized O(logN)
     * pop from stack O(1) results in later pop from heap (OlogN)
     * pop from heap O(logN) results in later pop from stack O(1)
     *
     * top - O(1), excluding the time cost related to popMax calls we discussed above.
     * peekMax - O(logN), excluding the time cost related to pop calls we discussed above.
     *
     * Space: O(N)
     */
    Deque<int[]> stack; // keep track of stack => push order
    PriorityQueue<int[]> maxHeap; // keep track of max number in stack => sort order
    Set<Integer> removed;
    int id;

    public MaxStack() {
        stack = new ArrayDeque<>();
        maxHeap = new PriorityQueue<>((a, b) -> b[0] == a[0] ? b[1] - a[1] : b[0] - a[0]);
        removed = new HashSet<>();
        id = 0;
    }

    public void push(int x) {
        stack.push(new int[]{x, id});
        maxHeap.offer(new int[]{x, id++});
    }

    public int pop() {
        int[] res;
        do {
            res = stack.pop();
        } while (!removed.add(res[1]));
        return res[0];
    }

    public int top() {
        while (removed.contains(stack.peek()[1])) {
            stack.pop();
        }
        return stack.peek()[0];
    }

    public int popMax() {
        int[] res;
        do {
            res = maxHeap.poll();
        } while (!removed.add(res[1]));
        return res[0];
    }

    public int peekMax() {
        while (removed.contains(maxHeap.peek()[1])) {
            maxHeap.poll();
        }
        return maxHeap.peek()[0];
    }

    /************ Solution 2: TreeSet (不是最优解，但是有助于理解 TreeSet) *****************/
    /**
     * 需要记录两个顺序
     * (1) push order -> stack => 对应操作是 push/pop/top
     * (2) sort number -> sorted => 对应操作是 push/popMax/peekMax
     *
     * 难点是在一个顺序被pop/popMax时，另一个顺序也要被索引到并更新
     * 这意味着需要一个数据结构，保持全部顺序，且任意元素可以被随时索引找到并删除更新顺序 => TreeSet/TreeMap
     *
     * 注意！！！
     * HashSet比较默认用hashcode()，即使用reference比较是否是相同元素，因此int[]即使元素相同也被认为是不同element
     * TreeSet比较默认用init时定义的comparator的compareTo，元素完全相同，则被认为相等，可以用来移除相同内容，不同地址的Object/int[]
     *
     * Time: O(logN)  Space: O(N)
     */
    TreeSet<int[]> pushOrder; // keep track of stack => push order
    TreeSet<int[]> sortOrder; // keep track of max number in stack => sort order
    int ids;

    public MaxStack(boolean solution2) {
        Comparator<int[]> comparator = (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0];
        pushOrder = new TreeSet<>(comparator);
        sortOrder = new TreeSet<>(comparator);
        ids = 0;
    }

    public void push2(int x) {
        pushOrder.add(new int[]{ids, x});
        sortOrder.add(new int[]{x, ids++});
    }

    public int pop2() {
        int[] pair = pushOrder.pollLast();
        sortOrder.remove(new int[]{pair[1], pair[0]});
        return pair[1];
    }

    public int top2() {
        return pushOrder.last()[1];
    }

    public int popMax2() {
        int[] pair = sortOrder.pollLast();
        pushOrder.remove(new int[]{pair[1], pair[0]});
        return pair[0];
    }

    public int peekMax2() {
        return sortOrder.last()[0];
    }

    public static void main(String[] args) {
        // Lazy Update
        MaxStack solution1 = new MaxStack();
        solution1.push(5);
        solution1.push(1);
        solution1.push(5);
        System.out.println(solution1.top());
        System.out.println(solution1.popMax());
        System.out.println(solution1.top());
        System.out.println(solution1.peekMax());
        System.out.println(solution1.pop());
        System.out.println(solution1.top());

        // Use TreeSet
        MaxStack solution2 = new MaxStack(true);
        solution2.push2(5);
        solution2.push2(1);
        solution2.push2(5);
        System.out.println(solution2.top2());
        System.out.println(solution2.popMax2());
        System.out.println(solution2.top2());
        System.out.println(solution2.peekMax2());
        System.out.println(solution2.pop2());
        System.out.println(solution2.top2());
    }
}
