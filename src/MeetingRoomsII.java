import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

/**
 * 253. Meeting Rooms II (https://leetcode.com/problems/meeting-rooms-ii/description/)
 */
public class MeetingRoomsII {

    /******************** Solution 1: Iterate over sorted start and put end in heap ************/
    /**
     * Time: O(NlogN) for both sort and each enter and exit heap.
     * Space: O(N) worst case, each meeting has a new room and enters heap.
     */
    public int minMeetingRooms1(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(i -> i[0]));
        PriorityQueue<Integer> rooms = new PriorityQueue<>();
        for (int[] i : intervals) {
            // ⚠️注意⚠️！！！如果用 while 的话，就是一直在监测实时 meeting room 情况，
            // 这样的话，需要维护一个全局最大值 max，作为最后结果！！！参考下面 comment 里面的实现！！！
            // 此处，用 if 实际就是让出一间 meeting room，放新的 end time，最后 rooms.size() 就是全局最大值，即一共需要多少 room！！！
            if (!rooms.isEmpty() && i[0] >= rooms.peek()) {
                rooms.poll();
            }
            rooms.offer(i[1]);
        }
        return rooms.size();

//        Arrays.sort(intervals, Comparator.comparingInt(i -> i[0]));
//        int max = 0;
//        PriorityQueue<Integer> ends = new PriorityQueue<>();
//        for (int[] interval : intervals) {
//            while (!ends.isEmpty() && ends.peek() <= interval[0]) {
//                ends.poll();
//            }
//            ends.offer(interval[1]);
//        }
//        return ends.size();
    }

    /********************* Solution 2: Chronological ordering ******************/
    /**
     * 算法
     * （1）start时间排序，对应每一个会议
     * （2）end时间排序，对应每一个房间
     * （3）遍历会议的start时间，end时间指向当前最早end的room，
     *     最开始假设每个会议一个房间，每当找到一对start>=end意味着可以少用一个房间，则房间总数减1
     * Time: O(NlogN)  Space: O(N)
     */
    public int minMeetingRooms2(int[][] intervals) {
        int[] starts = new int[intervals.length];
        int[] ends = new int[intervals.length];
        for (int i = 0; i < intervals.length; i++) {
            starts[i] = intervals[i][0];
            ends[i] = intervals[i][1];
        }
        Arrays.sort(starts);
        Arrays.sort(ends);

        // ⚠️注意⚠️从 intervals.length 开始往下减比较好！
        // 不然从 0 开始往上累加，需要每次先移动 ends 指针找到第一个 还没结束的 room，并将 rooms--
        // 参考 comment 里面的实现！！！
        int ei = 0, rooms = intervals.length;
        for (int si = 0; si < intervals.length; si++) {
            if (starts[si] >= ends[ei]) {
                rooms--;
                ei++;
            }
        }
        return rooms;

//        int n = intervals.length;
//        int[] starts = new int[n], ends = new int[n];
//        for (int i = 0; i < n; i++) {
//            starts[i] = intervals[i][0];
//            ends[i] = intervals[i][1];
//        }
//        Arrays.sort(starts);
//        Arrays.sort(ends);
//
//        int j = 0, cur = 0, max  = 0;
//        for (int i = 0; i < n; i++) {
//            while (starts[i] >= ends[j]) {
//                j++;
//                cur--;
//            }
//            cur++;
//            max = Math.max(max, cur);
//        }
//        return max;
    }

    /********************* Solution 3: 类似skyline，分开s,e按时间扫描 ******************/
    /**
     * 注意！！！有的Pair, p.fst是p.getKey(); p.snd是p.getValue();
     */
    public int minMeetingRooms3(int[][] intervals) {
        List<Pair<Integer, Boolean>> times = new ArrayList<>(); // isStart, start/endTime
        for (int[] i : intervals) {
            times.add(new Pair(i[0], true));
            times.add(new Pair(i[1], false));
        }
        Collections.sort(times, new Comparator<Pair<Integer, Boolean>>() {
            @Override
            public int compare(Pair<Integer, Boolean> p1, Pair<Integer, Boolean> p2) {
                if (!p1.getKey().equals(p2.getKey())) {
                    return p1.getKey() - p2.getKey();
                } else {
                    return Boolean.compare(p1.getValue(), p2.getValue());
                }
            }
        });
        int maxRoom = 0, curRoom = 0;
        for (Pair<Integer, Boolean> pair : times) {
            if (pair.getValue()) { // isStart
                maxRoom = Math.max(maxRoom, ++curRoom);
            } else {
                curRoom--;
            }
        }
        return maxRoom;
    }

    /********************* Solution 4: TreeMap to track the change of room numbers in time order ******************/
    /**
     * Time: O(NlogN)   Space: O(N)
     */
    public int minMeetingRooms(int[][] intervals) {
        Map<Integer, Integer> map = new TreeMap<>();
        for (int[] i : intervals) {
            map.put(i[0], map.getOrDefault(i[0], 0) + 1);
            map.put(i[1], map.getOrDefault(i[1], 0) - 1);
        }
        int maxRoom = 0, curRoom = 0;
        for (int change : map.values()) {
            maxRoom = Math.max(maxRoom, curRoom += change);
        }
        return maxRoom;
    }

    public static void main(String[] args) {
        MeetingRoomsII solution = new MeetingRoomsII();
        System.out.println(solution.minMeetingRooms(new int[][]{{0,30},{5,10},{15,20}})); // 2
        System.out.println(solution.minMeetingRooms(new int[][]{{7,10},{2,4}})); // 1
        System.out.println(solution.minMeetingRooms(new int[][]{{5,8},{6,8}})); // 2
        System.out.println(solution.minMeetingRooms(new int[][]{{2,15},{36,45},{9,29},{16,23},{4,9}})); // 2
    }
}
