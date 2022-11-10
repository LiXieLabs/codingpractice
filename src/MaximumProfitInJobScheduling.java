import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class MaximumProfitInJobScheduling {

    /******************* Solution 1: DP - Intuitive *************************/
    /**
     * Sort by end time
     * dp[i] denotes up to time point i, the max profit we can make
     * dp[i] = max(dp[i-1], max(profit[j] + dp[startTime[j]] where j denotes the job j that ends at time i))
     *
     * TLE!!! 因为对于end time [1,2,3,10000]中间太多不必要的dp值要更新，但是实际上3->10000都只是延续3的值而已
     * 因此，可以优化dp为end time length，见solution2
     */
    public int jobScheduling1(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        int[][] job = new int[n][3];
        for (int i = 0; i < n; i++) {
            job[i][0] = startTime[i];
            job[i][1] = endTime[i];
            job[i][2] = profit[i];
        }
        Arrays.sort(job, Comparator.comparingInt(j -> j[1]));
        int maxTime = job[n-1][1];

        int[] dp = new int[maxTime+1];
        int j = 0;
        for (int i = 1; i <= maxTime; i++) {
            while (j < n && job[j][1] < i) j++;
            int curMax = dp[i-1];
            while (j < n && job[j][1] == i) {
                curMax = Math.max(curMax, dp[job[j][0]] + job[j][2]);
                j++;
            }
            dp[i] = curMax;
        }
        return dp[maxTime];
    }

    /******************* Solution 2: DP - Optimizatiion *************************/
    /**
     * Sort by end time
     * dp[i+1] denotes up to job i, the max profit we can make.
     * dp[i+1] = max(dp[i], dp[j] + profit[i] where j denotes the slot of the sorted jobs [0:i), where we can insert job i
     * s.t. all previous jobs' endTime <= startTime[i], if j == 0, it means no job finish before job i starts.
     */
    public int jobScheduling2(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        int[][] job = new int[n][3];
        for (int i = 0; i < n; i++) {
            job[i] = new int[]{startTime[i], endTime[i], profit[i]};
        }
        Arrays.sort(job, Comparator.comparingInt(j -> j[1]));

        int[] dp = new int[n + 1];
        for (int i = 0; i < n; i++) {
            // bisect right
            int lo = 0, hi = i;
            while (lo < hi) {
                int mid = (lo + hi) / 2;
                if (job[mid][1] > job[i][0]) {
                    hi = mid;
                } else {
                    lo = mid + 1;
                }
            }
            dp[i+1] = Math.max(dp[i], dp[lo] + job[i][2]);
        }
        return dp[n];
    }

    /******************* Solution 3: DP - Binary Search to TreeMap *************************/
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        int[][] job = new int[n][3];
        for (int i = 0; i < n; i++) {
            job[i] = new int[]{startTime[i], endTime[i], profit[i]};
        }
        Arrays.sort(job, Comparator.comparingInt(j -> j[1]));

        TreeMap<Integer, Integer> dp = new TreeMap<>();
        dp.put(0, 0);
        for (int[] j : job) {
            dp.put(j[1], Math.max(dp.lastEntry().getValue(), dp.floorEntry(j[0]).getValue() + j[2]));
        }
        return dp.lastEntry().getValue();
    }

    public static void main(String[] args) {
        MaximumProfitInJobScheduling solution = new MaximumProfitInJobScheduling();
        System.out.println(solution.jobScheduling(new int[]{1,2,3,3}, new int[]{3,4,5,6}, new int[]{50,10,40,70}));
        System.out.println(solution.jobScheduling(new int[]{1,2,3,4,6}, new int[]{3,5,10,6,9}, new int[]{20,20,100,70,60}));
        System.out.println(solution.jobScheduling(new int[]{1,1,1}, new int[]{2,3,4}, new int[]{5,6,4}));
    }

}
