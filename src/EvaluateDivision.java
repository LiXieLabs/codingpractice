import javafx.util.Pair;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class EvaluateDivision {

    /**************** Solution 1: Directed + Weighted Graph BFS ***************/
    /**
     * 权重有向图的BFS
     * Image a/b = k as a link between node a and b
     * The weight from a to b is k, the reverse link is 1/k.
     * Query is to find a path between two nodes.
     * 本来是无源有向图，每一个query相当于给定起点(源)，找终点。
     *
     * Let N be the number of input equations (N edges) and M be the number of queries.
     * Time: O(M * N)
     * Space: O(N)
     */
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {

        // 先建图记录outbound edges
        Map<String, Map<String, Double>> outbounds = new HashMap<>();
        for (int i = 0; i < equations.size(); i++) {
            List<String> equation = equations.get(i);
            String x = equation.get(0), y = equation.get(1);
            if (!outbounds.containsKey(x)) outbounds.put(x, new HashMap<>());
            outbounds.get(x).put(y, values[i]);
            if (!outbounds.containsKey(y)) outbounds.put(y, new HashMap<>());
            outbounds.get(y).put(x, 1.0 / values[i]);
        }

        // 对于每一组BFS
        double[] res = new double[queries.size()];
        for (int i = 0; i < queries.size(); i++) {
            List<String> query = queries.get(i);
            String start = query.get(0), end = query.get(1);
            if (!outbounds.containsKey(start) || !outbounds.containsKey(end)) {
                res[i] = -1.0;
                continue;
            }
            if (start.equals(end)) {
                res[i] = 1.0;
                continue;
            }
            Set<String> visited = new HashSet<>();
            Deque<Pair<String, Double>> queue = new ArrayDeque<>();
            visited.add(start);
            queue.add(new Pair<>(start, 1.0));
            boolean found = false;
            while (!queue.isEmpty() && !found) {
                Pair<String, Double> curr = queue.poll();
                String currStr = curr.getKey();
                Double currMulti = curr.getValue();
                for (Map.Entry<String, Double> entry : outbounds.get(currStr).entrySet()) {
                    String nextStr = entry.getKey();
                    Double nextMulti = currMulti * entry.getValue();
                    if (nextStr.equals(end)) {
                        res[i] = nextMulti;
                        found = true;
                        break;
                    } else if (!visited.contains(nextStr)) {
                        visited.add(nextStr);
                        queue.offer(new Pair<>(nextStr, nextMulti));
                    }
                }
            }
            if (!found) res[i] = -1.0;
        }
        return res;
    }

    /************ Solution 2: TODO: UNION FIND *************************/
    //https://app.gitbook.com/o/-LEzdBqqw8FawHA3mleT/s/-LEzdBqr7mNEEfIbMstL/bfs-dfs/399.-evaluate-division

    /************ Solution 3: TODO DP *************************/
    //https://app.gitbook.com/o/-LEzdBqqw8FawHA3mleT/s/-LEzdBqr7mNEEfIbMstL/bfs-dfs/399.-evaluate-division

    public static void print(double[] input) {
        System.out.println("[" + Arrays.stream(input).mapToObj(String::valueOf).collect(Collectors.joining(",")) + "]");
    }

    public static void main(String[] args) {
        EvaluateDivision solution = new EvaluateDivision();
        print(solution.calcEquation(
                Arrays.asList(Arrays.asList("a","b"), Arrays.asList("b","c")),
                new double[]{2.0,3.0},
                Arrays.asList(Arrays.asList("a","c"), Arrays.asList("b","a"), Arrays.asList("a","e"), Arrays.asList("a","a"), Arrays.asList("x","x"))));
        print(solution.calcEquation(
                Arrays.asList(Arrays.asList("a","b"), Arrays.asList("b","c"), Arrays.asList("bc","cd")),
                new double[]{1.5,2.5,5.0},
                Arrays.asList(Arrays.asList("a","c"), Arrays.asList("c","b"), Arrays.asList("bc","cd"), Arrays.asList("cd","bc"))));
        print(solution.calcEquation(
                Arrays.asList(Arrays.asList("a","b")),
                new double[]{0.5},
                Arrays.asList(Arrays.asList("a","b"), Arrays.asList("b","a"), Arrays.asList("a","c"), Arrays.asList("x","y"))));
    }
}
