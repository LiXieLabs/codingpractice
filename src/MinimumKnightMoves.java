import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MinimumKnightMoves {

    private static final int[][] directions = {{1,2},{2,1},{-1,2},{-2,1},{1,-2},{2,-1},{-1,-2},{-2,-1}};

    /**************** Solution 1: Iterative BFS *************************/
    // TLE with HashSet visited
    public int minKnightMoves1(int x, int y) {
        List<int[]> queue = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        queue.add(new int[]{0, 0});
        visited.add("0,0");
        int step = 0;
        while (!queue.isEmpty()) {
            List<int[]> nextQueue = new ArrayList<>();
            for (int[] curr : queue) {
                if (curr[0] == x && curr[1] == y) return step;
                for (int[] d : directions) {
                    int[] next = {curr[0] + d[0], curr[1] + d[1]};
                    String key = next[0] + "," + next[1];
                    if (!visited.contains(key)) {
                        visited.add(key);
                        nextQueue.add(next);
                    }
                }
            }
            queue = nextQueue;
            step++;
        }
        return -1;
    }

    /**************** Solution 2: Iterative BFS *************************/
    // 2D array visited to avoid TLE becaused of visited HashSet
    /**
     * Time Complexity: O((max(∣x∣,∣y∣)^2)
     * Due to the nature of BFS, before reaching the target,
     * we will have covered all the neighborhoods that are closer to the start point.
     * The aggregate of these neighborhoods forms a circle,
     * and the area can be approximated by the area of a square with an edge length of max(2∣x∣,2∣y∣).
     * The number of cells within this square would be (max(2∣x∣,2∣y∣))^2
     * Hence, the overall time complexity of the algorithm is O((max(2∣x∣,2∣y∣)^2)=O((max(∣x∣,∣y∣)^2)
     *
     * Space Complexity: O((max(∣x∣,∣y∣)^2)
     * We employed two data structures in the algorithm, i.e. queue and set.
     * - queue contains elements that are situated in at most two different layers (or levels).
     *   In our case, the maximum number of elements at one layer would be 4⋅max(∣x∣,∣y∣),
     *   i.e. the perimeter of the exploration square.
     *   As a result, the space complexity for the queue is O(max(∣x∣,∣y∣)).
     * - set contains every elements that we visited, which is (max(∣x∣,∣y∣))^2
     *   as we estimated in the time complexity analysis.
     *   As a result, the space complexity for the set is O((max(∣x∣,∣y∣)^2)
     */
    public int minKnightMoves2(int x, int y) {
        List<int[]> queue = new ArrayList<>();
        boolean[][] visited = new boolean[605][605];
        queue.add(new int[]{0, 0});
        visited[302][302] = true;
        int step = 0;
        while (!queue.isEmpty()) {
            List<int[]> nextQueue = new ArrayList<>();
            for (int[] curr : queue) {
                if (curr[0] == x && curr[1] == y) return step;
                for (int[] d : directions) {
                    int[] next = {curr[0] + d[0], curr[1] + d[1]};
                    if (!visited[next[0]+302][next[1]+302]) {
                        visited[next[0]+302][next[1]+302] = true;
                        nextQueue.add(next);
                    }
                }
            }
            queue = nextQueue;
            step++;
        }
        return -1;
    }

    /************* Solution 3: TODO: Bidirectional BFS ******************/
    // https://leetcode.com/problems/minimum-knight-moves/solution/ => Solution 3

    /************* Solution 4: DFS with memoization => DP *****************/
    // https://leetcode.com/problems/minimum-knight-moves/solution/ => Solution 4
    /**
     * Time: O(∣x⋅y∣)   Space: O(∣x⋅y∣)
     */
    private Map<String, Integer> memo = new HashMap<>();

    public int minKnightMoves(int x, int y) {
        // 绝对值使得target进入第一象限
        return dfs(Math.abs(x), Math.abs(y));
    }

    public int dfs(int x, int y) {
        // base cases
        if (x == 0 && y == 0) return 0;
        if (x + y == 2) return 2;

        String key = x + "," + y;
        if (!memo.containsKey(key)) {
            memo.put(key, Math.min(dfs(Math.abs(x-1), Math.abs(y-2)), dfs(Math.abs(x-2), Math.abs(y-1))) + 1);
        }
        return memo.get(key);
    }

    public static void main(String[] args) {
        MinimumKnightMoves solution = new MinimumKnightMoves();
        System.out.println(solution.minKnightMoves(2,1));
        System.out.println(solution.minKnightMoves(5,5));

    }
}
