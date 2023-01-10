import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * 480. Sliding Window Median (https://leetcode.com/problems/sliding-window-median/description/)
 */
public class SlidingWindowMedian {

    /*************** Solution 1: Balanced Min & Max Heaps (Priority Queue) **************/
    /**
     * 类似 295. Find Median from Data Stream (https://leetcode.com/problems/find-median-from-data-stream/)
     * MaxHeap holds smaller elements & MinHeap holds larger elements
     * Balanced => maxHeap size - minHeap size = 0 or 1
     *
     * Time: O(NK) as priorityQueue.remove(element) is O(K) => TODO: 1个优化：lazy removal, 不能依赖heap size，需要balance factor
     * Space: O(K)
     */
    public double[] medianSlidingWindow1(int[] nums, int k) {
        double[] res = new double[nums.length - k + 1];
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        // 注意！！！不能用 (a,b)->(b-a)，MIN_INT, MAX_INT会错！！！参考最后一个TC
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        for (int i = 0; i < nums.length; i++) {
            // 移除离开window的element
            if (i - k >= 0) {
                if (nums[i - k] <= maxHeap.peek()) {
                    maxHeap.remove(nums[i - k]);
                    if (!minHeap.isEmpty()) maxHeap.offer(minHeap.poll());
                } else {
                    minHeap.remove(nums[i - k]);
                }
            }
            // 加入新的element
            if (maxHeap.isEmpty() || nums[i] <= maxHeap.peek()) {
                maxHeap.offer(nums[i]);
            } else {
                minHeap.offer(nums[i]);
                maxHeap.offer(minHeap.poll());
            }
            // 维护balance
            if (maxHeap.size() - minHeap.size() > 1) minHeap.offer(maxHeap.poll());
            if (i < k - 1) continue;
            res[i - k + 1] = k % 2 == 0 ? minHeap.peek() / 2.0 + maxHeap.peek() / 2.0 : maxHeap.peek();
        }
        return res;
    }

    /*************** Solution 2: Balanced TreeSets **************/
    /**
     * 类似 Solution 1，但是 TreeSet 的 remove(element) 可以达到 log(N) 时间
     * 注意！！！因为可能存在相等的nums，想让相等的 elements 同时存在在 TreeSet 中，必须用 Comparator 强行认为区分他们。
     *         Comparator<Integer> second 就是这个作用！！！
     *
     * Time: O(NlogK) as treeSet.remove(element) is O(logK)
     * Space: O(K)
     */
    public double[] medianSlidingWindow(int[] nums, int k) {
        double[] res = new double[nums.length - k + 1];
        Comparator<Integer> first = Comparator.comparingInt(i -> nums[i]);
        Comparator<Integer> second = Comparator.naturalOrder(); // 注意！！！相等的element不能使之显示相等，否则TreeSet只能存在一个！！！需要人为将其区分开！！！
        TreeSet<Integer> smaller = new TreeSet<>(first.thenComparing(second)),
                        larger = new TreeSet<>(first.thenComparing(second));
        for (int i = 0; i < nums.length; i++) {
            // 移除离开window的element
            if (i - k >= 0) {
                if (nums[i - k] <= nums[smaller.last()]) {
                    smaller.remove(i - k);
                    if (!larger.isEmpty()) smaller.add(larger.pollFirst());
                } else {
                    larger.remove(i - k);
                }
            }
            // 加入新的element
            if (smaller.isEmpty() || nums[i] <= nums[smaller.last()]) {
                smaller.add(i);
            } else {
                larger.add(i);
                smaller.add(larger.pollFirst());
            }
            // 维护balance
            if (smaller.size() - larger.size() > 1) larger.add(smaller.pollLast());
            if (i < k - 1) continue;
            res[i - k + 1] = k % 2 == 0 ? nums[smaller.last()] / 2.0 + nums[larger.first()] / 2.0 : nums[smaller.last()];
        }
        return res;
    }

    private static void print(double[] input) {
        System.out.println("[" +
                Arrays.stream(input).boxed().map(String::valueOf)
                        .collect(Collectors.joining(",")) + "]");
    }

    public static void main(String[] args) {
        SlidingWindowMedian solution = new SlidingWindowMedian();
        print(solution.medianSlidingWindow(new int[]{1,2,3,4,2,3,1,4,2}, 3)); // [2.0,3.0,3.0,3.0,2.0,3.0,2.0]
        print(solution.medianSlidingWindow(new int[]{1,4,2,3}, 4)); // [2.5]
        print(solution.medianSlidingWindow(new int[]{1,3,-1,-3,5,3,6,7}, 3)); // [1.0,-1.0,-1.0,3.0,5.0,6.0]
        print(solution.medianSlidingWindow(new int[]{1,2,3,4,2,3,1,4,2}, 3)); // [2.0,3.0,3.0,3.0,2.0,3.0,2.0]
        print(solution.medianSlidingWindow(new int[]{2147483647,2147483647}, 2)); // [2147483647]
        print(solution.medianSlidingWindow(new int[]{-2147483648,-2147483648,2147483647,-2147483648,-2147483648,-2147483648,2147483647,2147483647,2147483647,2147483647,-2147483648,2147483647,-2147483648}, 3));
        // [-2147483648,-2147483648,-2147483648,-2147483648,-2147483648,2147483647,2147483647,2147483647,2147483647,2147483647,-2147483648]

    }
}
