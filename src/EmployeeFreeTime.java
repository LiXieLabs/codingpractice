import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class EmployeeFreeTime {

    /******************* Solution 1: minHeap by comparing interval start time *****************/
    /**
     * Time: O(NlogK)   Space: O(K)
     * where N denotes total number of intervals, K denotes employee counter (i.e. schedule.size())
     *
     * schedule 实际上是一个 2D array
     * minHeap keep int[] of (row, col)
     * row denotes employee id, col denotes current interval is being pointed to.
     *
     * keep track of prevEnd, init to smallest start
     * keep polling currStart from minHeap
     * if (currStart > prevEnd) => found a common free interval => update res
     * update prevEnd and offer if this user still has work interval
     */
    public List<Interval> employeeFreeTime(List<List<Interval>> schedule) {
        // Initialize minHeap
        int employeeCount = schedule.size();
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(p -> schedule.get(p[0]).get(p[1]).start));
        for (int i = 0; i < employeeCount; i++) minHeap.offer(new int[]{i, 0});

        // Poll smallest start interval from minHeap
        // If (currStart > prevEnd) => found a common free time => update res
        // update prevEnd and offer if this user still has work interval
        List<Interval> res = new ArrayList<>();
        int prevEnd = schedule.get(minHeap.peek()[0]).get(minHeap.peek()[1]).start;
        while (!minHeap.isEmpty()) {
            int[] p = minHeap.poll();
            Interval curr = schedule.get(p[0]).get(p[1]);
            if (curr.start > prevEnd && prevEnd != Integer.MIN_VALUE) {
                res.add(new Interval(prevEnd, curr.start));
            }
            prevEnd = Math.max(prevEnd, curr.end);
            if (p[1] + 1 < schedule.get(p[0]).size()) {
                minHeap.offer(new int[]{p[0], p[1] + 1});
            }
        }
        return res;
    }


    // Definition for an Interval.
    private static class Interval {
        public int start;
        public int end;

        public Interval() {}

        public Interval(int _start, int _end) {
            start = _start;
            end = _end;
        }
    }

    private static void print(List<Interval> input) {
        System.out.println("[" + input.stream().map(i -> "[" + i.start + "," + i.end + "]").collect(Collectors.joining(",")) + "]");
    }

    public static void main(String[] args) {
        EmployeeFreeTime solution = new EmployeeFreeTime();
        print(solution.employeeFreeTime(
                Arrays.asList(
                        Arrays.asList(new Interval(1, 2), new Interval(5, 6)),
                        Arrays.asList(new Interval(1,3)),
                        Arrays.asList(new Interval(4,10)))
        ));
        print(solution.employeeFreeTime(
                Arrays.asList(
                        Arrays.asList(new Interval(1, 3), new Interval(6, 7)),
                        Arrays.asList(new Interval(2,4)),
                        Arrays.asList(new Interval(2,5), new Interval(9,12)))
        ));
    }
}
