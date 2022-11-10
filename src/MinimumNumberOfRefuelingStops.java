import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class MinimumNumberOfRefuelingStops {

    /************* Solution 1: BFS => MLE ******************/
    /**
     *
     */
    public int minRefuelStops1(int target, int startFuel, int[][] stations) {
        List<int[]> queue = new ArrayList<>();
        queue.add(new int[]{-1, startFuel});
        int stop = 0;
        while (!queue.isEmpty()) {
            List<int[]> nextQueue = new ArrayList<>();
            for (int[] curr : queue) {
                int prevStation = curr[0];
                int prevFuel = curr[1];
                if (prevFuel >= target) return stop;
                for (int i = prevStation + 1; i < stations.length && stations[i][0] <= prevFuel; i++) {
                    nextQueue.add(new int[]{i, prevFuel + stations[i][1]});
                }
            }
            queue = nextQueue;
            stop++;
        }
        return -1;
    }

    /************ Solution 2: 1D DP ****************/
    /**
     * dp[t] denotes max fuels (i.e. furthest distance) can get by refueling t times.
     * dp[0] is startFuel
     *
     * for each station stations[i]
     * 到达之前，最多可能 refuel i 次，比如 stations[3]，前面只有 stations[0], stations[1], stations[2], 即使每个都加油了，t = 3
     * 所以从 t = i 开始遍历
     * 对于每一个 dp[t]，如果 dp[t] >= stations[i][0]， 则加油 t 次，可以到达 stations[i]
     * 如果在 stations[i] 加油，则加油了 t + 1 次，总油量为 dp[t] + stations[i][1]，跟当前 dp[t + 1] 比较且更新
     * 倒着更新，是防止当前 stations[i] 被重复加油
     *
     * Time: O(N^2)   Space: O(N)
     */
    public int minRefuelStops2(int target, int startFuel, int[][] stations) {
        long[] dp = new long[stations.length + 1];
        dp[0] = startFuel;
        for (int i = 0; i < stations.length; i++) {
            for (int t = i; t >= 0 && dp[t] >= stations[i][0]; t--) {
                dp[t + 1] = Math.max(dp[t + 1], dp[t] + stations[i][1]);
            }
        }
        for (int i = 0; i < dp.length; i++) {
            if (dp[i] >= target) return i;
        }
        return -1;
    }

    /************ Solution 3: Greedy Algo by Priority Queue ****************/
    /**
     * i 是下一个可能到达的加油站
     * stop 是目前停靠过几个加油站
     * maxHeap 记录当前可到达且未停靠过的所有加油站中油量最大的一个的油量
     *
     * 每次更新maxHeap，之后加最大油量，循环直到总油量超过target，或者maxHeap为空（还没到达，但是无油可加）
     *
     * Time: O(NlogN)   Space: O(N)
     */
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>();
        int i = 0, stop;
        for (stop = 0; startFuel < target; stop++) {
            while (i < stations.length && stations[i][0] <= startFuel) {
                maxHeap.offer(-stations[i++][1]);
            }
            if (maxHeap.isEmpty()) return -1;
            startFuel += -maxHeap.poll();
        }
        return stop;
    }

    public static void main(String[] args) {
        MinimumNumberOfRefuelingStops solution = new MinimumNumberOfRefuelingStops();
        System.out.println(solution.minRefuelStops(100, 10, new int[][]{{10, 60}, {20, 30}, {30, 30}, {60, 40}}));
        System.out.println(solution.minRefuelStops(1, 1, new int[][]{}));
        System.out.println(solution.minRefuelStops(100, 1, new int[][]{{10, 100}}));
    }
}
