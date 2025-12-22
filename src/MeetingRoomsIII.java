import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 2402. Meeting Rooms III (https://leetcode.com/problems/meeting-rooms-iii/description/)
 */
public class MeetingRoomsIII {

    /********** Solution 1: Sort Meeting + 2 Min-heap to keep idle & busy rooms **********/
    /**
     * busy = PQ[rmIdx, rmEnd]
     * 也可以 rmEnd 单独记录在一个 rmToEnd 的 array 里面！！！
     * 小心！！！因为 delay，rmEnd 可能超出 int 范围，必须用 long！！！
     *
     * ⚠️注意⚠️
     * index 必须是 int
     * PQ with array, 类型必须一致
     * int -> long => long myLong = myInt;
     * long -> int => int myInt = (int) long;
     *
     * meeting rooms = N, meetings = M
     * Sort meetings = MlogM
     * Init idle rooms = NlogN
     * For loop M x heap operation logN = MlogN
     * Time Complexity: O(MlogM + NlogN + MlogN) --- assume M >> N ----> O(MlogM)
     * Space Complexity: O(N)
     */
    public int mostBooked(int n, int[][] meetings) {
        // 1 int
        // res: room index with max count

        // 1 map -> 1 array i = room index, arr[i] = meeting count
        // map: room -> count

        // 1 heap with secondary sort - busy
        // map: room -> cur_end
        // sort: room -> sort by cur_end
        //       same cur_end -> sort by room index

        // 1 heap - idle
        // sort: room index

        long rmMaxCnt = 0;
        long[] cnt = new long[n];
        Comparator<long[]> first = Comparator.comparingLong(i -> i[1]);
        Comparator<long[]> second = Comparator.comparingLong(i -> i[0]);
        PriorityQueue<long[]> busy = new PriorityQueue<>(first.thenComparing(second));
        PriorityQueue<Long> idle = new PriorityQueue<>();

        Arrays.sort(meetings, Comparator.comparingInt(i -> i[0]));
        for (long i = 0; i < n; i++) idle.offer(i);

        for (int[] meeting : meetings) {
            // ⚠️注意⚠️！！！必须先把 idle 的全 poll 出来，并且 offer 进 idle，
            // 不然不知道哪个 idle 的 room index 最小，当前 heap 顶的 end 最早，但是不一定 room index 最小！！！
            while (!busy.isEmpty() && busy.peek()[1] <= meeting[0]) {
                idle.offer(busy.poll()[0]);
            }
            long rmTaken, rmEnd;
            if (!idle.isEmpty()) {
                rmTaken = idle.poll();
                rmEnd = meeting[1];
            } else {
                long[] earliestEnd = busy.poll();
                rmTaken = earliestEnd[0];
                rmEnd = earliestEnd[1] + meeting[1] - meeting[0];
            }
            busy.offer(new long[]{rmTaken, rmEnd});
            cnt[(int) rmTaken]++;
            if (cnt[(int) rmTaken] > cnt[(int) rmMaxCnt] || cnt[(int) rmTaken] == cnt[(int) rmMaxCnt] && rmTaken < rmMaxCnt) {
                rmMaxCnt = rmTaken;
            }
        }
        return (int) rmMaxCnt;
    }

    public static void main(String[] args) {
        MeetingRoomsIII solution = new MeetingRoomsIII();
        System.out.println(solution.mostBooked(2, new int[][]{{0,10},{1,5},{2,7},{3,4}})); // 0
        System.out.println(solution.mostBooked(3, new int[][]{{1,20},{2,10},{3,5},{4,9},{6,8}})); // 1
        System.out.println(solution.mostBooked(4, new int[][]{{18,19},{3,12},{17,19},{2,13},{7,10}})); // 0
    }
}
