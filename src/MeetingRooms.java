import java.util.Arrays;
import java.util.Comparator;

/**
 * 252. Meeting Rooms (https://leetcode.com/problems/meeting-rooms/description/)
 */
public class MeetingRooms {

    /*********** Solution 1: Array Sort ********************/
    /**
     * Time: O(NlogN)  Space: O(1)
     */
    public boolean canAttendMeetings(int[][] intervals) {
        if (intervals.length <= 1) return true;
        Arrays.sort(intervals, Comparator.comparingInt(i -> i[0]));
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < intervals[i-1][1]) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        MeetingRooms solution = new MeetingRooms();
        System.out.println(solution.canAttendMeetings(new int[][]{{0,30},{5,10},{15,20}})); // false
        System.out.println(solution.canAttendMeetings(new int[][]{{7,10},{2,4}})); // true
    }
}