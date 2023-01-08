import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 636. Exclusive Time of Functions (https://leetcode.com/problems/exclusive-time-of-functions/description/)
 */
public class ExclusiveTimeOfFunctions {

    /*********** Solution 1: Simulate by keep tracking of CPU time + Stack keeps ids of running function **************/
    /**
     * 分段统计！！！遇到新的start先把正在运行的程序的时间计入结果！！！
     * start2 == temp end1, 因此遇到一个新的start，立即结算stack top的task的execution time，并将新的task更新到stack top
     * 遇到end，则直接pop stack top并更新其对应id的execution id
     *
     * * 注意！！！
     *   start time==2 是时间块 2 的起点
     *   end time==2 是时间块 2 的终点
     *   因此他们 start==2 && end==2 的 execution time = 1 而非 0！！！
     *
     * Time: O(L)   Space: O(L/2) = O(L)
     */
    public int[] exclusiveTime1(int n, List<String> logs) {
        int[] res = new int[n];
        Deque<Integer> stack = new ArrayDeque<>(); // stack only keeps id
        int prevTime = 0;
        for (String log : logs) {
            String[] l = log.split(":"); // [id, start/end, timestamp]
            int id = Integer.parseInt(l[0]), currTime = Integer.parseInt(l[2]);
            if ("start".equals(l[1])) {
                if (!stack.isEmpty()) {
                    res[stack.peek()] += currTime - prevTime;
                }
                stack.push(id);
                prevTime = currTime;
            } else {
                res[stack.pop()] += currTime + 1 - prevTime;
                prevTime = currTime + 1;
            }
        }
        return res;
    }

    /*********** Solution 1: Stack keeps ids & startTime of running function **************/
    /**
     * 预先刨去停运时间+仅当遇到end计入结果！！！
     * 遇到一个新的start，先将新的task的id和startTime更新到stack top
     * 遇到end，先pop stack top并更新其对应id的execution id，再将该段时间从剩下的stack top的task的总运行时间中预先刨去
     *
     * Time: O(L)   Space: O(L/2) = O(L)
     */
    public int[] exclusiveTime(int n, List<String> logs) {
        int[] res = new int[n];
        Deque<int[]> stack = new ArrayDeque<>(); // stack only keeps id
        for (String log : logs) {
            String[] l = log.split(":"); // [id, start/end, timestamp]
            int id = Integer.parseInt(l[0]), currTime = Integer.parseInt(l[2]);
            if ("start".equals(l[1])) {
                stack.push(new int[]{id, currTime});
            } else {
                int[] top = stack.pop();
                res[top[0]] += currTime + 1 - top[1];
                if (!stack.isEmpty()) {
                    res[stack.peek()[0]] -= currTime + 1 - top[1];
                }
            }
        }
        return res;
    }

    private static void print(int[] input) {
        System.out.println(
                "[" + Arrays.stream(input).boxed().map(String::valueOf)
                        .collect(Collectors.joining(",")) + "]");
    }

    public static void main(String[] args) {
        ExclusiveTimeOfFunctions solution = new ExclusiveTimeOfFunctions();
        print(solution.exclusiveTime(2, Arrays.asList("0:start:0","1:start:2","1:end:5","0:end:6")));
        print(solution.exclusiveTime(1, Arrays.asList("0:start:0","0:start:2","0:end:5","0:start:6","0:end:6","0:end:7")));
        print(solution.exclusiveTime(2, Arrays.asList("0:start:0","0:start:2","0:end:5","1:start:6","1:end:6","0:end:7")));
    }
}
