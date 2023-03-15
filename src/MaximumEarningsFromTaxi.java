import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaximumEarningsFromTaxi {

    /************** Solution 1: 1D DP 背包问题 *******************/
    /**
     * dp[e] 表示以 e 为结尾时，最多可以获得多少分
     * dp[e] = max(dp[e-1], dp[s] + e - s + pts) for all sides [s,e,pts] with same e
     *
     * Time: O(R + N)
     * R is rides.length, N is n
     *
     * Space: O(R)
     */
    public long maxTaxiEarnings(int n, int[][] rides) {
        // build endToRides map
        // 也可以 sort by end time👇🏻，再用指针向后移动查找，时间换空间
        // Arrays.sort(rides, (a, b) -> (a[1] - b[1]))
        Map<Integer, List<Integer>> endToRides = new HashMap<>();
        for (int i = 0; i < rides.length; i++) {
            endToRides.putIfAbsent(rides[i][1], new ArrayList<>());
            endToRides.get(rides[i][1]).add(i);
        }

        // 注意！！！必须 long，不然超范围！！！
        long[] dp = new long[n + 1];
        for (int e = 1; e <= n; e++) {
            dp[e] = dp[e - 1];
            for (int i : endToRides.getOrDefault(e, new ArrayList<>())) {
                int s = rides[i][0], pts = rides[i][2];
                dp[e] = Math.max(dp[e], dp[s] + e - s + pts);
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        MaximumEarningsFromTaxi solution = new MaximumEarningsFromTaxi();
        System.out.println(solution.maxTaxiEarnings(5, new int[][]{{2,5,4},{1,5,1}}));
        System.out.println(solution.maxTaxiEarnings(20, new int[][]{{1,6,1},{3,10,2},{10,12,3},{11,12,2},{12,15,2},{13,18,1}})); // 20
    }
}
