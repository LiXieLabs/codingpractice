import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 362. Design Hit Counter
 * https://leetcode.com/problems/design-hit-counter/
 */
public class DesignHitCounter {

    // solution 1
    private List<Integer> times;
    private Map<Integer, Integer> counter;
    // solution 2
    private Deque<int[]> queue;
    private int total;

    public DesignHitCounter() {
        // solution 1
        times = new ArrayList<>();
        times.add(0);
        counter = new HashMap<>();
        counter.put(0, 0);
        // solution 2
        queue = new ArrayDeque<>();
        total = 0;
    }

    /*********** Solution 1: Prefix Sum + Binary Search ****************/
    /**
     * 适用于getHits的timestamp不连续递增的情况
     *
     * hit
     * Time: O(1)
     *
     * getHits
     * Time: O(logN) where N is # of distinct timestamps
     *
     * Space: O(N) where N is # of distinct timestamps
     */

    public void hit1(int timestamp) {
        if (times.get(times.size() - 1) != timestamp) {
            int preCount = counter.get(times.get(times.size() - 1));
            times.add(timestamp);
            counter.put(timestamp, preCount + 1);
        } else {
            counter.put(timestamp, counter.get(timestamp) + 1);
        }
    }

    public int getHits1(int timestamp) {
        return search(timestamp) - search(Math.max(timestamp - 300, 0));
    }

    /**
     * find times[i] that all times[:i] (inclusive) <= time && times[i+1:] > time
     */
    private int search(int time) {
        int lo = 0, hi = times.size() - 1;
        while (lo < hi) {
            int mid = (lo + hi + 1) / 2;
            if (times.get(mid) > time) {
                hi = mid - 1;
            } else {
                lo = mid;
            }
        }
        return counter.get(times.get(lo));
    }

    /*********** Solution 2: Deque ****************/
    /**
     * 注意！！！timestamp在两个call之间都是单调递增的！！！
     * 因此，可以用queue维护timestamps，超过300s窗口的可以完全舍弃！！！
     *
     * hit
     * Time: O(1)
     *
     * getHits
     * Time: O(N) where N is # of distinct timestamps
     *
     * Space: O(N) where N is # of distinct timestamps
     */

    public void hit(int timestamp) {
        if (!queue.isEmpty() && queue.peekLast()[0] == timestamp) {
            queue.getLast()[1]++;
        } else {
            queue.offer(new int[]{timestamp, 1});
        }
        total++;
    }

    public int getHits(int timestamp) {
        while (!queue.isEmpty() && queue.peek()[0] <= timestamp - 300) {
            total -= queue.poll()[1];
        }
        return total;
    }

    public static void main(String[] args) {
        DesignHitCounter solution = new DesignHitCounter();
        solution.hit(1);
        solution.hit(2);
        solution.hit(3);
        System.out.println(solution.getHits(4));
        solution.hit(300);
        System.out.println(solution.getHits(300));
        System.out.println(solution.getHits(301));
    }
}
