import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * 20. Valid Parentheses (https://leetcode.com/problems/valid-parentheses/description/)
 */
class ValidParentheses {

    private static final Map<Character, Character> dict;
    static {
        dict = new HashMap<>();
        dict.put(')', '(');
        dict.put(']', '[');
        dict.put('}', '{');
    }

    /****************** Solution 1: Hash + Stack ***********************/
    /**
     * Time: O(N)  Space: O(N)
     */
    public boolean isValid(String s) {

        // Deque stack/queue methods
        // Stack 都是 first/head 操作；Queue 插入是 last/tail 操作， 取出是 first/head 操作；
        // https://docs.oracle.com/javase/7/docs/api/java/util/Deque.html

        Deque<Character> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if (dict.containsKey(c)) {
                // stack.pop() 不是 null safe，stack.peek()是的！！！
                if (stack.isEmpty() || stack.pop() != dict.get(c)) return false;
//                if (stack.peek() != dict.get(c)) return false;
//                stack.pop();
            } else {
                stack.push(c);
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        ValidParentheses solution = new ValidParentheses();
        System.out.println(solution.isValid("()[]{}")); // true
        System.out.println(solution.isValid("(]")); // false
        System.out.println(solution.isValid("[()")); // false
    }

}