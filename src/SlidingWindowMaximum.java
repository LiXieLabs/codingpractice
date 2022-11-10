import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class SlidingWindowMaximum {

    /************* Solution 1: Max Heap + Lazy Poll ********************/
    /**
     * Space: O(N)
     * Time: worst case O(NlogN) best case O(NlogK)
     * worst case O(NlogN) best case O(NlogK)
     * worst case => O(k) build 1st k max-heap + O((N-k)*logN) nums in increasing order = O(NlogN)
     * best case => O(k) build 1st k max-heap + O((N-k)*logk) nums in decreasing order = O(Nlogk)
     */
    public int[] maxSlidingWindow1(int[] nums, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>((i1, i2) -> nums[i2] - nums[i1]);
        int[] res = new int[nums.length - k + 1];
        for (int i = 0; i < nums.length; i++) {
            while (heap.peek() <= i - k) {
                heap.poll();
            }
            heap.offer(i);
            if (i - k + 1 >= 0) {
                res[i - k + 1] = nums[heap.peek()];
            }
        }
        return res;
    }

    /************* Solution 2: Monotonic Deque *******************/
    /**
     * Space Complexity: O(N-k+1) for res, O(k) for deque
     * Time Complexity: Amortized O(N)
     * Each element enter and leave queue for once => each is processed twice.
     *
     * （1）Deque右边操作维护一个自左向右(自底向上)单调递减queue
     * （2）Deque左边操作维护queue中元素都在window内
     * （3）每次添加新元素之前
     *            A. 首先将左边超出window范围的元素(如果有)pop
     *            B. 接着将右边比当前新元素小的都pop，维护单减，
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        Deque<Integer> deque = new ArrayDeque<>();
        int[] res = new int[nums.length - k + 1];
        for (int i = 0; i < nums.length; i++) {
            while (deque.size() > 0 && deque.peek() <= i - k) {
                deque.poll();
            }
            while (deque.size() > 0 && nums[deque.peekLast()] <= nums[i]) {
                deque.pollLast();
            }
            deque.offer(i);
            if (i - k + 1 >= 0) {
                res[i - k + 1] = nums[deque.peek()];
            }
        }
        return res;
    }

    public static String print(int[] res) {
        return "[" + Arrays.stream(res).mapToObj(String::valueOf).collect(Collectors.joining(",")) + "]";
    }

    public static void main(String[] args) {
        SlidingWindowMaximum solution = new SlidingWindowMaximum();
        System.out.println(print(solution.maxSlidingWindow(new int[]{1,3,-1,-3,5,3,6,7}, 3)));
        System.out.println(print(solution.maxSlidingWindow(new int[]{1,-1}, 1)));
    }
}
