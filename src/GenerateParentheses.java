import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * 22. Generate Parentheses (https://leetcode.com/problems/generate-parentheses/)
 */
public class GenerateParentheses {

    /********** Solution 1: 一种 Recursive Backtracking ****************/
    /**
     * StringBuilder && l starts from n && r starts from 0;
     *
     * Time/Space: TODO: Catalan Number (1/(n+1)) x C(2n, n) <= 4^n / (n x sqrt(n)) << 2^(2n) x n
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

    /********** Solution 2: 另一种 Recursive Backtracking ****************/
    /**
     * ArrayList && l starts from n && r starts from n;
     *
     * Time/Space: TODO: Catalan Number (1/(n+1)) x C(2n, n) <= 4^n / (n x sqrt(n)) << 2^(2n) x n
     */
    public List<String> generateParenthesis(int n) {
        res = new ArrayList<>();
        recur2(n, n, new ArrayList<>());
        return res;
    }

    private void recur2(int l, int r, List<String> cur) {
        if (l == 0 && r == 0) {
            res.add(String.join("", cur));
            return;
        }
        if (l > 0) {
            cur.add("(");
            recur2(l - 1, r, cur);
            cur.remove(cur.size() - 1);
        }
        if (l < r) {
            cur.add(")");
            recur2(l, r - 1, cur);
            cur.remove(cur.size() - 1);
        }
    }

    /********** Solution 3: Iterative DFS by Stack ****************/
    /**
     * Time/Space: Same as Solution 1
     */
    public List<String> generateParenthesis3(int n) {
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
        System.out.println(solution.generateParenthesis(0)); // []
        System.out.println(solution.generateParenthesis(1)); // [()]
        System.out.println(solution.generateParenthesis(2)); // [(()), ()()]
        System.out.println(solution.generateParenthesis(3)); // [((())), (()()), (())(), ()(()), ()()()]
    }
}
