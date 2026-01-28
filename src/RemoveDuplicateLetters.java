import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 316. Remove Duplicate Letters (https://leetcode.com/problems/remove-duplicate-letters/description/)
 * 1081. Smallest Subsequence of Distinct Characters (https://leetcode.com/problems/smallest-subsequence-of-distinct-characters/description/)
 */
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
     *     （1）则它如果是一个单调递增序列中非末尾的一个，则它后面一定有比他大的了，
     *     移除前面出现那一个，加入现在这一个，不会降低当前结果字符串字典序。
     *     （2）它如果是一个单调递增序列末尾的一个，则替换也没有帮助
     *     因此，这种情况可以直接跳过
     *
     *  ⚠️注意⚠️ 任意时刻，stack 中每个 char 都是 distinct 的！！！
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
                // ⚠️注意⚠️ lastIndex[stack.peek() - 'a'] 一定要跟 i 比较！万一出现在自己跟 i 之间，则会误判！！！
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