import java.util.ArrayDeque;
import java.util.Deque;

public class RemoveDuplicateLetters {

    /************** Solution 1: Ascending Monotonic Stack **************/
    /**
     * Time: O(N)  Space: O(N)
     *
     * (1) 如果遇到一个char，且它还没出现在stack里面，
     *     先维护栈，当前栈顶比它大的，且非最后一次出现的char，可以移出栈顶，因为后面增加会使得结果字符串字典序变小
     *     直到栈为空，或者栈顶char不满足pop条件
     *
     * (2) 如果遇到一个char，且它已经在当前stack里面，
     *     则它一定是一个单调递增序列中非末尾的一个(不然它不是最后一次出现，会被后面一个比它小的char移出栈顶),
     *     因此移除前面出现那一个，加入现在这一个，不会降低当前结果字符串字典序
     *     因此，这种情况可以直接跳过
     *
     */
    public String removeDuplicateLetters(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        int[] lastIndex = new int[26];
        boolean[] inStack = new boolean[26];
        for (int i = 0; i < s.length(); i++) {
            lastIndex[s.charAt(i) - 'a'] = i;
        }
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!inStack[c - 'a']) {
                while (!stack.isEmpty() && stack.peek() > c && lastIndex[stack.peek() - 'a'] > i) {
                    inStack[stack.pop() - 'a'] = false;
                }
                stack.push(c);
                inStack[c - 'a'] = true;
            }
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pollLast());
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        RemoveDuplicateLetters solution = new RemoveDuplicateLetters();
        System.out.println(solution.removeDuplicateLetters("bcabc"));
        System.out.println(solution.removeDuplicateLetters("cbacdcbc"));
    }
}