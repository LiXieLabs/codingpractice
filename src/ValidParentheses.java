import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

class ValidParentheses {

    public boolean isValid(String s) {

        // Deque stack/queue methods
        // Stack 都是 first/head 操作；Queue 插入是 last/tail 操作， 取出是 first/head 操作；
        // https://docs.oracle.com/javase/7/docs/api/java/util/Deque.html

        Deque<Integer> stack = new ArrayDeque<>();
        Map<Character, Character> dict = new HashMap<>();
        dict.put(')', '(');
        dict.put(']', '[');
        dict.put('}', '{');
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(' || s.charAt(i) == '[' || s.charAt(i) == '{') {
                stack.push(i);
            } else if (!stack.isEmpty() && dict.get(s.charAt(i)).equals(s.charAt(stack.peekFirst()))) {
                stack.pop();
            } else {
                return false;
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        ValidParentheses solution = new ValidParentheses();
        System.out.println(solution.isValid("()[]{}"));
        System.out.println(solution.isValid("(]"));
        System.out.println(solution.isValid("[()"));
    }

}