import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class KClosestPointsToOrigin {

    /*************************** Solution 1: K size Max-Heap ********************************/
    /**
     * Time: O(NlogK)   Space: O(K)
     */
    public int[][] kClosest1(int[][] points, int k) {
        // use lambda + Long.compare to avoid overflow
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(
                (i, j) -> Long.compare(dist(points[j]), dist(points[i])) // max-heap by distance
        );

        for (int i = 0; i < points.length; i++) {
            maxHeap.offer(i);
            if (maxHeap.size() > k) maxHeap.poll();
        }

        int[][] res = new int[k][2];
        for (int i = 0; i < k; i++) {
            res[i] = points[maxHeap.poll()];
        }
        return res;
    }

    // use long to avoid overflow
    // use x * x, instead of (long) Math.pow(x, 2)
    private long dist(int[] p) {
        long x = p[0], y = p[1];
        return x * x + y * y;
    }

    /*************************** Solution 2: K smallest by QuickSelect ********************************/
    /**
     * Time: O(N) average O(N^2) worst  Space: O(1)
     */
    public int[][] kClosest(int[][] points, int k) {
        if (k >= points.length) return points;
        quickSelect(points, 0, points.length - 1, k);
        int[][] res = new int[k][2];
        System.arraycopy(points, 0, res, 0, k);
        return res;
    }

    private void quickSelect(int[][] arr, int lo, int hi, int k) {
        while (lo <= hi) {
            int p = partition(arr, lo, hi);
            int leftSize = p - lo + 1;
            if (leftSize == k) {
                return;
            } else if (leftSize > k) {
                hi = p - 1;
            } else {
                lo = p + 1;
                k -= leftSize;
            }
        }
    }

    private int partition(int[][] arr, int lo, int hi) {
        int pivot = ThreadLocalRandom.current().nextInt(hi - lo + 1) + lo;
        swap(arr, pivot, hi);

        long pivotDist = dist(arr[hi]);
        int store = lo;

        for (int i = lo; i < hi; i++) {
            if (dist(arr[i]) <= pivotDist) {
                swap(arr, store++, i);
            }
        }
        swap(arr, store, hi);
        return store;
    }

    private void swap(int[][] arr, int i, int j) {
        int[] tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static String print(int[][] input) {
        return "[" + Arrays.stream(input).map(pair -> "["+pair[0]+","+pair[1]+"]").collect(Collectors.joining(",")) + "]";
    }

    public static void main(String[] args) {
        KClosestPointsToOrigin solution = new KClosestPointsToOrigin();
        System.out.println(print(solution.kClosest(new int[][]{{1,3},{-2,2}}, 1)));
        System.out.println(print(solution.kClosest(new int[][]{{3,3},{5,-1},{-2,4}}, 2)));
        System.out.println(print(solution.kClosest(new int[][]{{-5,4},{-6,-5},{4,6}}, 2)));
    }
}
