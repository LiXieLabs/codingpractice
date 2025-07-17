import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 2402. Meeting Rooms III (https://leetcode.com/problems/meeting-rooms-iii/description/)
 */
public class MeetingRoomsIII {

    /********** Solution 1: Sort Meeting + 2 Min-heap to keep free & busy rooms **********/
    /**
     * rooms[i] 用来记录当前 room i 的会议结束时间，如果 free 则为 0
     * 小心！！！rooms[i] 可能超出 int 范围，必须用 long！！！
     *
     * meeting rooms = N, meetings = M
     * Sort meetings = MlogM
     * Init free rooms = NlogN
     * For loop M x heap operation logN = MlogN
     * Time Complexity: O(MlogM + NlogN + MlogN) --- assume M >> N ----> O(MlogM)
     * Space Complexity: O(N)
     */
    public int mostBooked1(int n, int[][] meetings) {
        Arrays.sort(meetings, Comparator.comparingInt(i -> i[0]));
        long[] rooms = new long[n]; // long处理！！！
        int[] counts = new int[n];
        int maxIdx = 0;
        PriorityQueue<Integer> free = new PriorityQueue<>();
        Comparator<Long> first = Comparator.comparingLong(i -> rooms[i.intValue()]); // long处理！！！
        Comparator<Long> second = Comparator.comparingLong(i -> i); // long！！！
        PriorityQueue<Long> busy = new PriorityQueue<>(first.thenComparing(second)); // long处理！！！
        for (int i = 0; i < n; i++) {
            free.offer(i);
        }
        for (int[] meeting : meetings) {
            // 先把 busy 中所有已经结束的变成 free
            while (!busy.isEmpty() && rooms[busy.peek().intValue()] <= meeting[0]) { // long处理！！！
                int i = busy.poll().intValue(); // long处理！！！
                rooms[i] = 0;
                free.offer(i);
            }
            int i;
            if (!free.isEmpty()) {
                // 先试图从 free 中分配 meeting room
                i = free.poll();
                rooms[i] = meeting[1];
            } else {
                // 没有则 delay 并从 busy 中分配 meeting room
                i = busy.poll().intValue(); // long处理！！！
                rooms[i] = meeting[1] - meeting[0] + rooms[i];
            }
            busy.offer((long) i); // long处理！！！
            counts[i]++;
            if (counts[i] > counts[maxIdx] || counts[i] == counts[maxIdx] && i < maxIdx) {
                maxIdx = i;
            }
        }
        return maxIdx;
    }


    /********** Solution 2: Another version of Solution 1 **********/
    /**
     * Same as Solution 1, just merged long[rooms] for tracking end time to the busy Heap.
     *
     * Time Complexity: O(MlogM + NlogN + MlogN) --- assume M >> N ----> O(MlogM)
     * Space Complexity: O(N)
     */
    public int mostBooked(int n, int[][] meetings) {
        PriorityQueue<Integer> idle = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            idle.offer(i);
        }
        Comparator<long[]> comp1 = Comparator.comparingLong(i -> i[0]);
        Comparator<long[]> comp2 = Comparator.comparingLong(i -> i[1]);
        PriorityQueue<long[]> busy = new PriorityQueue<>(comp1.thenComparing(comp2));
        Arrays.sort(meetings, Comparator.comparingInt(i -> i[0]));
        int resRm = 0;
        long[] count = new long[n];
        for (int[] meeting : meetings) {
            while (!busy.isEmpty() && busy.peek()[0] <= (long) meeting[0]) {
                idle.offer((int) busy.poll()[1]);
            }
            int curRm;
            if (!idle.isEmpty()) {
                curRm = idle.poll();
                busy.offer(new long[]{(long) meeting[1], curRm});
            } else {
                long[] next = busy.poll();
                curRm = (int) next[1];
                busy.offer(new long[]{next[0] + (long) meeting[1] - meeting[0], curRm});
            }
            count[curRm]++;
            if (count[curRm] > count[resRm] || count[curRm] == count[resRm] && curRm < resRm) {
                resRm = curRm;
            }
        }
        return resRm;
    }

    public static void main(String[] args) {
        MeetingRoomsIII solution = new MeetingRoomsIII();
        System.out.println(solution.mostBooked(2, new int[][]{{0,10},{1,5},{2,7},{3,4}})); // 0
        System.out.println(solution.mostBooked(3, new int[][]{{1,20},{2,10},{3,5},{4,9},{6,8}})); // 1
        System.out.println(solution.mostBooked(4, new int[][]{{18,19},{3,12},{17,19},{2,13},{7,10}})); // 0
    }
}
