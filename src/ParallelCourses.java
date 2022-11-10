import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParallelCourses {

    /************ Solution 1: Topological Sort ***************/
    /**
     * Time: O(V + E)
     * Space: in + out = O(V + E)
     */
    public int minimumSemesters(int n, int[][] relations) {
        Map<Integer, List<Integer>> out = new HashMap<>();
        int[] in = new int[n + 1];
        for (int[] r : relations) {
            out.putIfAbsent(r[0], new ArrayList<>());
            out.get(r[0]).add(r[1]);
            in[r[1]]++;
        }

        List<Integer> zeroIn = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (in[i] == 0) zeroIn.add(i);
        }

        int semester = 0, complete = 0;
        while (!zeroIn.isEmpty()) {
            semester++;
            List<Integer> nextZeroIn = new ArrayList<>();
            for (int cur : zeroIn) {
                complete++;
                // 一定要 getOrDefault(cur, new ArrayList<>())，因为有可能没有出边，为null！！！
                for (int nex : out.getOrDefault(cur, new ArrayList<>())) {
                    in[nex]--;
                    if (in[nex] == 0) nextZeroIn.add(nex);
                }
            }
            zeroIn = nextZeroIn;
        }
        // for TC2，需要检测环形，则不可能完成！！！
        return complete == n ? semester : -1;
    }

    public static void main(String[] args) {
        ParallelCourses solution = new ParallelCourses();
        // TC1
        System.out.println(solution.minimumSemesters(3, new int[][]{{1, 3}, {2, 3}}));
        // TC2
        System.out.println(solution.minimumSemesters(3, new int[][]{{1, 2}, {2, 3}, {3, 1}}));
    }
}