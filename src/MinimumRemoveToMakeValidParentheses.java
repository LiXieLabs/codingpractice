import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class MinimumRemoveToMakeValidParentheses {

    /*********** Solution 1: Stack + One Pass ************/
    /**
     * remain stack 记录所有当前失配的左右括号的 index
     */
    public String minRemoveToMakeValid1(String s) {
        Deque<Integer> remain = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                remain.push(i);
            } else if (s.charAt(i) == ')') {
                if (!remain.isEmpty() && s.charAt(remain.peek()) == '(') {
                    remain.pop();
                } else {
                    remain.push(i);
                }
            }
        }
        Set<Integer> indicesToRemove = new HashSet<>();
        while (!remain.isEmpty()) indicesToRemove.add(remain.pop());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (!indicesToRemove.contains(i)) {
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }

    /*********** Solution 2: left mismatch + right mismatch + Two Pass *************/
    /**
     * 从左至右，找到多余的 ')'
     * 从右至左，找到多余的 '('
     */
    public String minRemoveToMakeValid(String s) {
        Set<Integer> remove = new HashSet<>();
        int mismatchedLeft = 0, i = 0;
        while (i < s.length()) {
            if (s.charAt(i) == '(') {
                mismatchedLeft++;
            } else if (s.charAt(i) == ')') {
                if (mismatchedLeft == 0) {
                    remove.add(i);
                } else {
                    mismatchedLeft--;
                }
            }
            i++;
        }
        int mismatchedRight = 0, j = s.length() - 1;
        while (j >= 0) {
            if (s.charAt(j) == ')') {
                mismatchedRight++;
            } else if (s.charAt(j) == '(') {
                if (mismatchedRight == 0) {
                    remove.add(j);
                } else {
                    mismatchedRight--;
                }
            }
            j--;
        }

        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < s.length(); k++) {
            if (!remove.contains(k)) sb.append(s.charAt(k));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        MinimumRemoveToMakeValidParentheses solution = new MinimumRemoveToMakeValidParentheses();
        System.out.println(solution.minRemoveToMakeValid("lee(t(c)o)de)"));
        System.out.println(solution.minRemoveToMakeValid("a)b(c)d"));
        System.out.println(solution.minRemoveToMakeValid("))(("));
    }
}
