import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class ReconstructItinerary {

    /**
     * https://app.gitbook.com/o/-LEzdBqqw8FawHA3mleT/s/-LEzdBqr7mNEEfIbMstL/bfs-dfs/332.-reconstruct-itinerary
     *
     * 欧拉路径 Eulerian Path
     * 定义：In graph theory, an Eulerian trail (or Eulerian path) is a trail in a finite graph which visits every edge exactly once => 每张票用一次且全部用掉
     * 一个有向图中存在欧拉路径，iff：
     * （1）图是连通的，即图上任意两点之间总有路径连接
     * （2）满足如下二者之一：
     *            A. 欧拉path：
     *                有且只有一个点 出度-入度=1(起点),
     *                有且只有一个点入度-出度=1(终点),
     *                其余点 入度=出度
     *            B. 欧拉cycle：
     *                所有点 入度=出度
     * 该题目已知一定存在欧拉路径，不必判断
     * 已知图中存在欧拉路径，找到一个欧拉路径：
     * （1）Fleury算法
     * （2）Hierholzer算法 (此处用该算法）
     * Hierholzer Algorithm Pseudo-code
     * path = []
     * DFS(u):
     *     while (u 存在未访问过的边 e(u,v)):
     *         mark边e(u,v)为visited
     *         DFS(v)
     *     path.pushleft(u)
     * 本题两点修改：
     * （1）DFS('JFK')固定起始
     * （2）优先遍历lexicographic order小的e(u,v)
     */

    /**
     * 有点像topological sort
     * 只不过topological sort是入度为0的作为新的起点
     * 这个是出度为0的作为终点
     */

    /**
     * 按照lexicographic order尝试visit机场，
     * recursive最先卡住走不动的(也就是无法做origin的)会先被append到itinerary里面，
     * 不管他的它的lexicographic order是怎样的，它一定是后面才visit的机场，
     * 我们只是先给它机会。
     *
     * 比如下面这个例子👇
     * [["JFK","KUL"],["JFK","NRT"],["NRT","JFK"]]
     * dic = {'JFK': ['KUL', 'NRT'], 'NRT': ['JFK']})
     * res = ["JFK","NRT","JFK","KUL"]
     * 我们虽然先给了KUL机会，但是它没有destination与之对应了，
     * 直接append在结果里变成最后一个visit的机场
     */

    /************** Solution 1: Iterative DFS to build Eulerian Path **************/
    /**
     * Time: O(N) build + O(NlogN) sort + O(N) Heirholzer = O(NlogN)
     * Space: O(N) hashmap storage + O(N) stack = O(N)
     */
    public List<String> findItinerary1(List<List<String>> tickets) {
        Map<String, PriorityQueue<String>> outEdges = new HashMap<>();
        for (List<String> ticket : tickets) {
            String s = ticket.get(0), e = ticket.get(1);
            outEdges.putIfAbsent(s, new PriorityQueue<>());
            outEdges.get(s).offer(e);
        }
        List<String> res = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();
        stack.push("JFK");
        while (!stack.isEmpty()) {
            while (outEdges.containsKey(stack.peek())
                    && !outEdges.get(stack.peek()).isEmpty()) {
                stack.push(outEdges.get(stack.peek()).poll());
            }
            res.add(stack.pop());
        }
        Collections.reverse(res);
        return res;
    }

    /************** Solution 2: Recursive DFS to build Eulerian Path **************/
    /**
     * Time: O(N) build + O(NlogN) sort + O(N) Heirholzer = O(NlogN)
     * Space: O(N) hashmap storage + O(N) recur call stack = O(N)
     */
    public List<String> findItinerary(List<List<String>> tickets) {
        Map<String, PriorityQueue<String>> outEdges = new HashMap<>();
        for (List<String> ticket : tickets) {
            String s = ticket.get(0), e = ticket.get(1);
            outEdges.putIfAbsent(s, new PriorityQueue<>());
            outEdges.get(s).offer(e);
        }
        List<String> res = new ArrayList<>();
        recur(res, outEdges, "JFK");
        Collections.reverse(res);
        return res;
    }

    private void recur(List<String> res, Map<String, PriorityQueue<String>> outEdges, String cur) {
        while (outEdges.containsKey(cur) && !outEdges.get(cur).isEmpty()) {
            String nex = outEdges.get(cur).poll();
            recur(res, outEdges, nex);
        }
        res.add(cur);
    }

    private static void print(List<String> input) {
        System.out.println("[" + String.join(" -> ", input) + "]");
    }

    public static void main(String[] args) {
        ReconstructItinerary solution = new ReconstructItinerary();

        print(solution.findItinerary(Arrays.asList(
                Arrays.asList("MUC","LHR"),
                Arrays.asList("JFK","MUC"),
                Arrays.asList("SFO","SJC"),
                Arrays.asList("LHR","SFO"))));

        print(solution.findItinerary(Arrays.asList(
                Arrays.asList("JFK","SFO"),
                Arrays.asList("JFK","ATL"),
                Arrays.asList("SFO","ATL"),
                Arrays.asList("ATL","JFK"),
                Arrays.asList("ATL","SFO"))));

        print(solution.findItinerary(Arrays.asList(
                Arrays.asList("JFK","ATL"),
                Arrays.asList("ATL","JFK"))));

        print(solution.findItinerary(Arrays.asList(
                Arrays.asList("JFK","KUL"),
                Arrays.asList("JFK","NRT"),
                Arrays.asList("NRT","JFK"))));
    }
}
