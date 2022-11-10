import java.util.ArrayList;
import java.util.List;

public class ShortestPathVisitingAllNodes {

    /************** Solution 1: BFS + Bit Manipulation/Mask to track state *****************/
    /**
     * 数据规模很小 1 <= NodeCount <= 12 => 一般说明该题需要2^N复杂度，是search
     * 是最短路径，优先考虑BFS
     *
     * 几个难点：
     * （1）可以重复访问点和路径，不能单纯记录visited node，需要记录visited state以避免重复访问路径
     *     (node, visitedNodes) 来标记一个 state，表示当前在node点，且已经visited过visitedNodes这个集合
     *  (2) visitedNode需要是一个hashable value
     *      用正整数int每一位标记对应node是否访问过，1为访问过，0为未访问过
     *      比如4个nodes，则访问过0，2就标记为0101
     *      相应的 allVisitedState = (1 << n) - 1 == 1111
     *  (3) 一共N个Node，一共2^N个states(0000~1111)
     *      visitState用一个N x 2^N array标记即可，既可以限制空间，还比map快
     *
     *  Time: O(N X 2^N) 每种点和状态组合遍历一遍
     *  Space: O(N X 2^N) by visitedStates array
     */
    public int shortestPathLength(int[][] graph) {
        int n = graph.length;
        int allVisitedState = (1 << n) - 1;
        int[][] visited = new int[n][1 << n];
        List<int[]> currQueue = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            currQueue.add(new int[]{i, 1 << i});
        }
        int steps = 0;
        while (!currQueue.isEmpty()) {
            List<int[]> nextQueue = new ArrayList<>();
            for (int[] curr : currQueue) {
                int currNode = curr[0];
                int currState = curr[1];
                if (currState == allVisitedState) return steps;
                // 小心！！！不能只在for里面提前判断，
                // 不然new int[][]{{}}表示只有node0的情况没有nextNode找不到解！！！
                if (visited[currNode][currState] == 1) continue;
                visited[currNode][currState] = 1;
                for (int nextNode : graph[currNode]) {
                    int nextState = currState | (1 << nextNode);
                    nextQueue.add(new int[]{nextNode, nextState});
                }
            }
            currQueue = nextQueue;
            steps++;
        }
        return -1;
    }

    public static void main(String[] args) {
        ShortestPathVisitingAllNodes solution = new ShortestPathVisitingAllNodes();
        System.out.println(solution.shortestPathLength(new int[][]{{1,2,3},{0},{0},{0}}));
        System.out.println(solution.shortestPathLength(new int[][]{{1},{0,2,4},{1,3,4},{2},{1,2}}));
        System.out.println(solution.shortestPathLength(new int[][]{{}}));
    }
}
