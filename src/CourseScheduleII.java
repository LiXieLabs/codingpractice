import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CourseScheduleII {

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        Map<Integer, List<Integer>> out = new HashMap<>();
        int[] in = new int[numCourses];
        for (int[] c : prerequisites) {
            out.putIfAbsent(c[1], new ArrayList<>());
            out.get(c[1]).add(c[0]);
            in[c[0]]++;
        }
        List<Integer> zeroIn = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            if (in[i] == 0) zeroIn.add(i);
        }
        List<Integer> res = new ArrayList<>();
        while (!zeroIn.isEmpty()) {
            List<Integer> nextZeroIn = new ArrayList<>();
            for (int cur : zeroIn) {
                res.add(cur);
                for (int nex : out.getOrDefault(cur, new ArrayList<>())) {
                    in[nex]--;
                    if (in[nex] == 0) nextZeroIn.add(nex);
                }
            }
            zeroIn = nextZeroIn;
        }
        return res.size() == numCourses ? res.stream().mapToInt(Integer::intValue).toArray() : new int[0];
    }

    private static void print(int[] input) {
        System.out.println("[" + Arrays.stream(input).mapToObj(String::valueOf).collect(Collectors.joining(",")) + "]");
    }

    public static void main(String[] args) {
        CourseScheduleII solution = new CourseScheduleII();
        print(solution.findOrder(2, new int[][]{{1,0}}));
        print(solution.findOrder(4, new int[][]{{1,0},{2,0},{3,1},{3,2}}));
        print(solution.findOrder(1, new int[][]{}));
    }
}