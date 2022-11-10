import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class MaximumNumberOfEventsThatCanBeAttended {

    /************************ Solution 1: Line Sweep by Time + MinHeap ***********************/
    /**
     * Time: O(NlogN)  Space: O(N)
     *
     * ***优化***：
     * 也可以 inplace sort events Arrays.sort(events, (e1, e2) -> Integer.compare(e1[0], e2[0]));
     * 省略 pending minHeap
     */
    public int maxEvents(int[][] events) {
        // 按照开始时间升序排列 - O(NlogN)
        PriorityQueue<int[]> pending = new PriorityQueue<>(Comparator.comparingInt(e -> e[0]));
        for (int[] event : events) {
            pending.offer(event);
        }
        // 按照结束时间升序排列 - O(NlogN)
        PriorityQueue<Integer> ongoing = new PriorityQueue<>();
        int count = 0, today = 1;
        while (!pending.isEmpty() || !ongoing.isEmpty()) {
            // 把开始进行的event从pending移入ongoing
            while (!pending.isEmpty() && pending.peek()[0] <= today) {
                ongoing.offer(pending.poll()[1]);
            }
            // 把ongoing里面已经结束的去掉
            while (!ongoing.isEmpty() && ongoing.peek() < today) {
                ongoing.poll();
            }
            // 参加ongoing结束最早的
            if (!ongoing.isEmpty()) {
                ongoing.poll();
                count++;
            }
            // 如果没有进行中的，则直接跳到下一个pending event的第一天
            if (ongoing.isEmpty() && !pending.isEmpty()) {
                today = pending.peek()[0];
            } else {
                today++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        MaximumNumberOfEventsThatCanBeAttended solution = new MaximumNumberOfEventsThatCanBeAttended();
        System.out.println(solution.maxEvents(new int[][]{{1,2},{2,3},{3,4}}));
        System.out.println(solution.maxEvents(new int[][]{{1,2},{2,3},{3,4},{1,2}}));
        System.out.println(solution.maxEvents(new int[][]{{1,2},{1,3},{2,2}}));
    }
}