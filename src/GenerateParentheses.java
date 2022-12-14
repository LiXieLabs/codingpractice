import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * 22. Generate Parentheses (https://leetcode.com/problems/generate-parentheses/)
 */
public class GenerateParentheses {

    /********** Solution 1: Recursive Backtracking ****************/
    /**
     * Time/Space: TODO: Catalan Number (1/(n+1)) x C(2n, n) <= 4^n / (n x sqrt(n))
     */
    private List<String> res;
    public List<String> generateParenthesis1(int n) {
        res = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        recur(sb, n, 0);
        return res;
    }

    private void recur(StringBuilder cur, int l, int r) {
        if (l == 0 && r == 0) {
            res.add(cur.toString());
            return;
        }
        if (l > 0) {
            cur.append("(");
            recur(cur, l - 1, r + 1);
            cur.deleteCharAt(cur.length() - 1);
        }
        if (r > 0) {
            cur.append(")");
            recur(cur, l, r - 1);
            cur.deleteCharAt(cur.length() - 1);
        }
    }

    /********** Solution 2: Iterative DFS by Stack ****************/
    /**
     * Time/Space: Same as Solution 1
     */
    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        Deque<String> strStack = new ArrayDeque<>();
        Deque<int[]> lrStack = new ArrayDeque<>();
        strStack.push("");
        lrStack.push(new int[]{n, 0});
        while (!strStack.isEmpty()) {
            String cur = strStack.pop();
            int[] lr = lrStack.pop();
            if (lr[0] == 0 && lr[1] == 0) {
                res.add(cur);
                continue;
            }
            if (lr[0] > 0) {
                strStack.push(cur + "(");
                lrStack.push(new int[]{lr[0] - 1, lr[1] + 1});
            }
            if (lr[1] > 0) {
                strStack.push(cur + ")");
                lrStack.push(new int[]{lr[0], lr[1] - 1});
            }
        }
        return res;
    }

    public static void main(String[] args) {
        GenerateParentheses solution = new GenerateParentheses();
        System.out.println(solution.generateParenthesis(0));
        System.out.println(solution.generateParenthesis(1));
        System.out.println(solution.generateParenthesis(2));
        System.out.println(solution.generateParenthesis(3));
    }
}
