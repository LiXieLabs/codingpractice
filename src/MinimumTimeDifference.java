import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinimumTimeDifference {

    /***************** Solution 1: Transfer + NlogN Sort + 转一圈相邻相减 ******************/
    public int findMinDifference(List<String> timePoints) {
        int[] timeInts = new int[timePoints.size()];
        for (int i = 0; i < timePoints.size(); i++) {
            timeInts[i] = timeStrToInt(timePoints.get(i));
        }
        Arrays.sort(timeInts);

        int minDiff = 24 * 60;
        for (int i = 0; i < timePoints.size(); i++) {
            if (i == timePoints.size() - 1) {
                minDiff = Math.min(minDiff, timeInts[0] + 24 * 60 - timeInts[i]);
            } else {
                minDiff = Math.min(minDiff, timeInts[i+1] - timeInts[i]);
            }
            if (minDiff == 0) return 0;
        }
        return minDiff;
    }

    /***************** Solution 2: Transfer + Bucket Sort + 转一圈相邻相减 ******************/
    public int findMinDifference2(List<String> timePoints) {
        boolean[] minuteBucket = new boolean[24 * 60]; // 一天最多24*60分钟 => bucket sort
        for (String timePoint : timePoints) {
            int minute = timeStrToInt(timePoint);
            if (minuteBucket[minute]) return 0; // 同一个分钟值第二次出现，minDiff=0，直接返回
            minuteBucket[minute] = true;
        }

        int minDiff = 24 * 60, prev = -1, first = -1, last = -1;
        for (int i = 0; i < 24 * 60; i++) {
            if (minuteBucket[i]) {
                if (first != -1) minDiff = Math.min(minDiff, i - prev);
                if (first == -1) first = i;
                last = i;
                prev = i;
            }
        }
        minDiff = Math.min(minDiff, first + 24 * 60 - last);
        return minDiff;
    }

    public int timeStrToInt(String time) throws NumberFormatException {
        int result = 0;
        String[] timeParts = time.split(":");
        result += 60 * Integer.parseInt(timeParts[0]);
        result += Integer.parseInt(timeParts[1]);
        return result;
    }

    public static void main(String[] args) {
        MinimumTimeDifference solution = new MinimumTimeDifference();
        System.out.println(solution.findMinDifference2(Arrays.asList("23:59","00:00")));
        System.out.println(solution.findMinDifference2(Arrays.asList("00:00","23:59","00:00")));
    }
}
