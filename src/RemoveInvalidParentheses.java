import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RemoveInvalidParentheses {

    /***************** Solution 1: BFS by HashSet ********************/
    /**
     * Time: O(N * 2^N)
     * N denotes s length, for each parenthesis, there are 2 options (stay or remove), so totally 2^N combinations
     * each combination, we need run isValid take O(N) to validate
     *
     * Space: O(N!)
     * leaves count be N X (N-1) X (N-2) X...X 1 = O(N!)
     *
     * Recur DFS + Memo 也可以
     */
    public List<String> removeInvalidParentheses(String s) {
        Set<String> queue = new HashSet<>();
        queue.add(s);
        Set<String> res = new HashSet<>();
        while (!queue.isEmpty()) {
            Set<String> nextQueue = new HashSet<>();
            for (String cur : queue) {
                if (isValid(cur)) res.add(cur);
                for (int i = 0; i < cur.length(); i++) {
                    if (cur.charAt(i) == '(' || cur.charAt(i) == ')') {
                        String nextString = cur.substring(0, i) + cur.substring(i+1, cur.length());
                        nextQueue.add(nextString);
                    }
                }
            }
            if (!res.isEmpty()) return new ArrayList<>(res);
            queue = nextQueue;
        }
        return new ArrayList<>(res);
    }

    public boolean isValid(String s) {
        int count = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                count++;
            } else if (c == ')') {
                if (count-- == 0) return false;
            }
        }
        return count == 0;
    }

    public static void print(List<String> input) {
        System.out.println("[" + String.join(",", input) + "]");
    }

    public static void main(String[] args) {
        RemoveInvalidParentheses solution = new RemoveInvalidParentheses();
        print(solution.removeInvalidParentheses("()())()"));
        print(solution.removeInvalidParentheses("(a)())()"));
        print(solution.removeInvalidParentheses(")("));
    }
}