import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 301. Remove Invalid Parentheses (https://leetcode.com/problems/remove-invalid-parentheses/description/)
 */
public class RemoveInvalidParentheses {

    /***************** Solution 1: BFS by HashSet ********************/
    /**
     * Time: O(N * 2^(l+r))
     * N denotes s length, for each parenthesis l + r, there are 2 options (stay or remove), so totally 2^(l+r) combinations
     * each combination, we need run isValid take O(N) to validate
     *
     * Space: O(N!)
     * 第一层是 n-ary，第二层是 (n-1)-ary，第三层是（n-2)-ary...
     * leaves count is N X (N-1) X (N-2) X...X 1 = O(N!)
     *
     * Recur DFS + Memo 也可以
     */
    public List<String> removeInvalidParentheses1(String s) {
        Set<String> queue = new HashSet<>();
        queue.add(s);
        List<String> res = new ArrayList<>();
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
            if (!res.isEmpty()) return res;
            queue = nextQueue;
        }
        return res;
    }

    /***************** Solution 2: DFS without HashSet memo 类似 backtracking ***********************/
    /**
     * Time: O(N * 2^(l+r))
     * N for string copy at each recur call.
     *
     * Space: O((l+r)^2)
     * recur depth + string storage = (l+r) * (l+r) = O((l+r)^2)
     */
    List<String> res;

    public List<String> removeInvalidParentheses(String s) {
        // pre-handling: 统计 l 个需要删除的 '(' 和 r 个需要删除的 ')'
        int l = 0, r = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                l++;
            } else if (c == ')') {
                if (l == 0) {
                    r++;
                } else {
                    l--;
                }
            }
        }
        res = new ArrayList<>();
        // ⚠️注意⚠️要记录 start，每次只从移除过的位置后面开始继续移除！！！
        remove(s, 0, l, r);
        return res;
    }

    private void remove(String s, int start, int l, int r) {
        if (l == 0 && r == 0) {
            if (isValid(s)) res.add(s);
            return;
        }
        for (int i = start; i < s.length(); i++) {
            // 先移除右括号，(i == 0 || s.charAt(i-1) != ')') 连续的只移除第一个！
            // 再移除左括号，(i == 0 || s.charAt(i-1) != '(') 连续的只移除第一个！可以有 r == 0, 优先移除右括号！
            if (r > 0 && s.charAt(i) == ')' && (i == 0 || s.charAt(i-1) != ')')) {
                remove(s.substring(0, i) + s.substring(i+1), i, l, r - 1);
            } else if (l > 0 && s.charAt(i) == '(' && (i == 0 || s.charAt(i-1) != '(')) {
                remove(s.substring(0, i) + s.substring(i+1), i, l - 1, r);
            }
        }
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
        print(solution.removeInvalidParentheses("()())()")); // [()()(),(())()]
        print(solution.removeInvalidParentheses("(a)())()")); // [(a)()(),(a())()]
        print(solution.removeInvalidParentheses(")(")); // []
        print(solution.removeInvalidParentheses(")b))")); // [b]
    }
}