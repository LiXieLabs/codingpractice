import java.util.Arrays;
import java.util.Comparator;

public class MeetingRooms {

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
        System.out.println(solution.canAttendMeetings(new int[][]{{0,30},{5,10},{15,20}}));
        System.out.println(solution.canAttendMeetings(new int[][]{{7,10},{2,4}}));
    }
}