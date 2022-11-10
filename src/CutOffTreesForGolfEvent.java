import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class CutOffTreesForGolfEvent {

    static int[][] directions = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    /********* Solution 1: Priority Queue + BFS + DFS ********************/
    /**
     * Time: O(KlogK + K^2) K ~ R*C
     * Space : O(K)
     */
    public int cutOffTree(List<List<Integer>> forest) {
        int row = forest.size(), col = forest.get(0).size();

        // count total # of trees
        PriorityQueue<int[]> trees = new PriorityQueue<>(Comparator.comparingInt(t -> t[1])); //((t1, t2) -> t1[1] - t2[1])也行
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (forest.get(i).get(j) > 1) trees.add(new int[]{i * col + j, forest.get(i).get(j)});
            }
        }

        // Optional Step
        // Iterative DFS by Stack - check if all the trees are in the same area as origin
        Set<Integer> visited = new HashSet<>();
        Deque<Integer> stack = new ArrayDeque<>();
        int treesConnectedToOrigin = 0;
        stack.push(0);
        visited.add(0);
        while (!stack.isEmpty()) {
            int cur = stack.pop();
            int r = cur / col, c = cur % col;
            if (forest.get(r).get(c) > 1) treesConnectedToOrigin++;
            for (int[] d : directions) {
                int nr = r + d[0], nc = c + d[1];
                int nex = nr * col + nc;
                if (nr >= 0 && nr < row && nc >= 0 && nc < col && forest.get(nr).get(nc) != 0 && !visited.contains(nex)) {
                    stack.push(nex);
                    visited.add(nex);
                }
            }
        }
        if (treesConnectedToOrigin < trees.size()) return -1;

        // BFS to find min steps to cut all trees
        int start = 0, steps = 0;
        while (!trees.isEmpty()) {
            int[] tree = trees.poll();
            int curSteps = minSteps(forest, start, tree[0]);
            if (curSteps == -1) return -1; // optional if DFS check is added!!!
            steps += curSteps;
            start = tree[0];
        }
        return steps;
    }

    public int minSteps(List<List<Integer>> forest, int start, int end) {
        int row = forest.size(), col = forest.get(0).size();
        Deque<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{start, 0});
        Set<Integer> visited = new HashSet<>();
        visited.add(start);
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int r = cur[0] / col, c = cur[0] % col, steps = cur[1];
            if (cur[0] == end) return steps;
            for (int[] d : directions) {
                int nr = r + d[0], nc = c + d[1];
                int nex = nr * col + nc;
                if (nr >= 0 && nr < row && nc >= 0 && nc < col && forest.get(nr).get(nc) != 0 && !visited.contains(nex)) {
                    queue.offer(new int[]{nex, steps + 1});
                    visited.add(nex);
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        CutOffTreesForGolfEvent solution = new CutOffTreesForGolfEvent();
        System.out.println(solution.cutOffTree(
                Arrays.asList(
                        Arrays.asList(1,3,2),
                        Arrays.asList(0,0,4),
                        Arrays.asList(7,5,6)
                )
        ));
    }
}
