import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 582. Kill Process (https://leetcode.com/problems/kill-process/description/)
 */
public class KillProcess {

    /***************** Solution 1: HashMap to build adjList + Iterative BFS/DFS *************/
    /**
     * Time: O(N)   Space: O(N)
     */
    public List<Integer> killProcess(List<Integer> pid, List<Integer> ppid, int kill) {
        Map<Integer, List<Integer>> adjList = new HashMap<>();
        for (int i = 0; i < pid.size(); i++) {
            if (ppid.get(i) != 0) {
                adjList.putIfAbsent(ppid.get(i), new ArrayList<>());
                adjList.get(ppid.get(i)).add(pid.get(i));
            }
        }
        List<Integer> res = new ArrayList<>();
        Deque<Integer> queue = new ArrayDeque<>();
        queue.offer(kill);
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            res.add(curr);
            for (int child : adjList.getOrDefault(curr, new ArrayList<>())) {
                queue.offer(child);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        KillProcess solution = new KillProcess();
        System.out.println(solution.killProcess(Arrays.asList(1,3,10,5), Arrays.asList(3,0,5,3), 5)); // [5, 10]
        System.out.println(solution.killProcess(Collections.singletonList(1), Collections.singletonList(0), 1)); // [1]
    }
}
