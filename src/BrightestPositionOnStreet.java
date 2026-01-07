import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.TreeMap;

/**
 *
 */
public class BrightestPositionOnStreet {

    /*************** Solution 1: Build & Sort [start,end] array + end min heap to track current brightness **************/
    /**
     * (1) Build & Sort [start,end] array
     * (2) Iterate over start & polling end < start => heap size is current brightness
     *
     * Time: O(NlogN)   Space: O(N)
     */
    public int brightestPosition1(int[][] lights) {
        int n = lights.length;
        int[][] ranges = new int[n][2];
        for (int i = 0; i < n; i++) {
            ranges[i][0] = lights[i][0] - lights[i][1];
            ranges[i][1] = lights[i][0] + lights[i][1];
        }
        Comparator<int[]> first = Comparator.comparingInt(i -> i[0]);
        Comparator<int[]> second = Comparator.comparingInt(i -> i[1]);
        Arrays.sort(ranges, first.thenComparing(second));

        int maxBrightness = 0;
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(Comparator.comparingInt(i -> ranges[i][1]));
        for (int i = 0; i < ranges.length; i++) {
                int start = ranges[i][0];
                minHeap.add(i);
                while (!minHeap.isEmpty() && ranges[minHeap.peek()][1] < start) {
                    minHeap.poll();
                }
                maxBrightness = Math.max(maxBrightness, minHeap.size());
        }
        return maxBrightness;
    }

    /**************** Solution 2: Line Sweep ************/
    /**
     * merge [start,end] array & end min heap to a TreeMap,
     * where the key is the start or end position
     * and the value is the brightness change at this position, positive for start, negative for end.
     *
     * Time: O(NlogN)   Space: O(N)
     */
    public int brightestPosition(int[][] lights) {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int[] light : lights) {
            int start = light[0] - light[1];
            int end = light[0] + light[1];
            map.merge(start, 1, Integer::sum);
            // ⚠️注意⚠️ end + 1 不然 start 和 end 重合会消除！！！
            map.merge(end + 1, -1, Integer::sum);
        }
        int curBrightness = 0, maxBrightness = 0, maxBrightnessPosition = 0;
        // ⚠️注意⚠️ 可以用 var！！！
        for (var entry : map.entrySet()) {
            curBrightness += entry.getValue();
            if (curBrightness > maxBrightness) {
                maxBrightness = curBrightness;
                maxBrightnessPosition = entry.getKey();
            }
        }
        return maxBrightnessPosition;
    }
}
