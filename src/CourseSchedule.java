import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseSchedule {

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

    public boolean canFinish(int numCourses, int[][] prerequisites) {
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
        int complete = 0;
        while (!zeroIn.isEmpty()) {
            List<Integer> nextZeroIn = new ArrayList<>();
            for (int cur : zeroIn) {
                complete++;
                for (int nex : out.getOrDefault(cur, new ArrayList<>())) {
                    in[nex]--;
                    if (in[nex] == 0) nextZeroIn.add(nex);
                }
            }
            zeroIn = nextZeroIn;
        }
        return complete == numCourses;
    }

    public static void main(String[] args) {
        CourseSchedule solution = new CourseSchedule();
        System.out.println(solution.canFinish(2, new int[][]{{1, 0}}));
        System.out.println(solution.canFinish(2, new int[][]{}));
        System.out.println(solution.canFinish(2, new int[][]{{1, 0}, {0, 1}}));
    }
}