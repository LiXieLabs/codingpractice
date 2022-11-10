import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class MinimumRemoveToMakeValidParentheses {

    public String minRemoveToMakeValid(String s) {
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

    public static void main(String[] args) {
        MinimumRemoveToMakeValidParentheses solution = new MinimumRemoveToMakeValidParentheses();
        System.out.println(solution.minRemoveToMakeValid("lee(t(c)o)de)"));
        System.out.println(solution.minRemoveToMakeValid("a)b(c)d"));
        System.out.println(solution.minRemoveToMakeValid("))(("));
    }
}
