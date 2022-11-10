import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class KClosestPointsToOrigin {

    /*************************** K size Max-Heap ********************************/
    public int[][] kClosest(int[][] points, int k) {
//        PriorityQueue<int[]> heap = new PriorityQueue<>((p1, p2) -> p2[0] - p1[0]);
//        for (int i = 0; i < points.length; i++) {
//            int dist = getDist(points[i]);
//            if (heap.size() == k && dist < heap.peek()[0]) {
//                heap.poll();
//                heap.offer(new int[]{dist, i});
//            } else if (heap.size() < k) {
//                heap.offer(new int[]{dist, i});
//            }
//        }
//        int[][] res = new int[k][2];
//        int i = 0;
//        while (!heap.isEmpty()) {
//            res[i++] = points[heap.poll()[1]];
//        }
//        return res;
        PriorityQueue<int[]> heap = new PriorityQueue<>((p1, p2) -> getDist(p2) - getDist(p1));
        for (int[] p : points) {
            heap.offer(p);
            if (heap.size() > k) {
                heap.poll();
            }
        }
        int[][] res = new int[k][2];
        while (!heap.isEmpty()) {
            res[--k] = heap.poll();
        }
        return res;
    }

    public int getDist(int[] point) {
        // sqrt((x - 0)^2 + (y - 0)^2) => 到原点距离
        // 省略sqrt，否则有小数点，不好比较
        return (int) Math.pow(point[0], 2) + (int) Math.pow(point[1], 2);
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
