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
        Set<String> currLevel = new HashSet<>();
        currLevel.add(s);
        List<String> res = new ArrayList<>();
        while (res.isEmpty()) {
            Set<String> nextLevel = new HashSet<>();
            for (String cur : currLevel) {
                // ⚠️注意⚠️ 必须在下一层检查，因为已经通过上一层 HashSet 去重过了！
                // 不然会 TLE 因为对于重复 string，太多 isValid check 了！
                if (isValid(cur)) res.add(cur);
                for (int i = 0; i < cur.length(); i++) {
                    if (cur.charAt(i) != '(' && cur.charAt(i) == ')') continue;
                    // skip duplicates: removing the 2nd '(' in "((..." yields same as removing the 1st '('
                    if (i > 0 && cur.charAt(i) == cur.charAt(i - 1)) continue;
                    String nextString = cur.substring(0, i) + cur.substring(i + 1);
                    nextLevel.add(nextString);
                }
            }
            currLevel = nextLevel;
        }
        return res;
    }

    /**
     * 去重 generate combination！！！
     * 40. Combination Sum II (https://leetcode.com/problems/combination-sum-ii/description/)
     * 301. Remove Invalid Parentheses (https://leetcode.com/problems/remove-invalid-parentheses/description/)
     */

    /***************** Solution 2: DFS without HashSet memo 类似 backtracking ***********************/
    /**
     * Time: O(N * 2^(l+r))
     * N for string copy at each recur call.
     *
     * Space: O(n+output size) where n = s.length()
     */
    List<String> res;

    public List<String> removeInvalidParentheses(String s) {
        // pre-handling: 统计 l 个需要删除的 '(' 和 r 个需要删除的 ')'
        int l = 0, r = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') { // 多余的左括号
                l++;
            } else if (c == ')' && l == 0) { // 多余的右括号
                r++;
            } else if (c == ')') { // match 到的右括号
                l--;
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
            // 先移除右括号，(i == start || s.charAt(i-1) != ')') 连续的只移除第一个！
            // 再移除左括号，(i == start || s.charAt(i-1) != '(') 连续的只移除第一个！可以有 r == 0, 优先移除右括号！
            if (r > 0 && s.charAt(i) == ')' && (i == start || s.charAt(i-1) != ')')) {
                remove(s.substring(0, i) + s.substring(i+1), i, l, r - 1);
            } else if (l > 0 && s.charAt(i) == '(' && (i == start || s.charAt(i-1) != '(')) {
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