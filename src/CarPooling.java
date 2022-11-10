import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

class CarPooling {

    /**************** Solution 1: Priority Queue to track offBoard passengers in distance order ****************/
    /**
     * Time: O(NlogN) Space: O(N)
     */
    public boolean carPooling1(int[][] trips, int capacity) {
        Comparator<int[]> first = Comparator.comparingInt(t -> t[1]);
        Comparator<int[]> second = Comparator.comparingInt(t -> t[2]);
        Arrays.sort(trips, first.thenComparing(second));

        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(t -> t[0]));

        int passengers = 0;
        for (int[] trip : trips) {
            while (!minHeap.isEmpty() && minHeap.peek()[0] <= trip[1]) {
                int[] offBoard = minHeap.poll();
                passengers -= offBoard[1];
            }
            if (passengers + trip[0] > capacity) return false;
            passengers += trip[0];
            minHeap.offer(new int[]{trip[2], trip[0]});
        }
        return true;
    }

    /**************** Solution 2: TreeMap to track the change of capacity in time order *********************/
    /**
     * Time: O(NlogN) Space: O(N)
     */
    public boolean carPooling(int[][] trips, int capacity) {
        Map<Integer, Integer> map = new TreeMap<>();
        for (int[] trip : trips) {
            map.put(trip[1], map.getOrDefault(trip[1], 0) + trip[0]);
            map.put(trip[2], map.getOrDefault(trip[2], 0) - trip[0]);
        }

        int passengers = 0;
        for (int change : map.values()) {
            passengers += change;
            if (passengers > capacity) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        CarPooling solution = new CarPooling();
        System.out.println(solution.carPooling(new int[][]{{2,1,5},{3,3,7}}, 4));
        System.out.println(solution.carPooling(new int[][]{{2,1,5},{3,3,7}}, 5));
    }
}