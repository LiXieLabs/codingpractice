import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CourseScheduleII {

    /*********** Solution 1: Topological Sort by Kahn's Algo ************/
    /**
     * https://en.wikipedia.org/wiki/Topological_sorting#Kahn.27s_algorithm
     * Topological Sort 用于解决 DAG(有向无环图) 任务排序，可用 Kahn's Algo 实现，参见上述链接
     *
     * 该方法实际上是Iterative BFS
     *
     * Time Complexity: O(|V|+|E|)   Space Complexity: O(|V|+|E|)
     * 搜索入度为0=>O(V) + 每个点进出queue一次=>O(V) + 每个出点入度-1=>O(E)
     */
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
        int[] res = new int[numCourses];
        int i = 0;
        while (!zeroIn.isEmpty()) {
            List<Integer> nextZeroIn = new ArrayList<>();
            for (int cur : zeroIn) {
                res[i++] = cur;
                for (int nex : out.getOrDefault(cur, Collections.emptyList())) {
                    in[nex]--;
                    if (in[nex] == 0) nextZeroIn.add(nex);
                }
            }
            zeroIn = nextZeroIn;
        }
        return i == numCourses ? res : new int[0];
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