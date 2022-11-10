import java.util.ArrayDeque;
import java.util.Deque;

public class DecodeString {

    /********************* Solution 1: 2 Stacks && push at 1st digit **************************/
    /**
     * Time Complexity: O(maxK⋅N),
     * where maxK is the maximum value of K and N is the length of a given string s.
     * We traverse a string of size N and iterate K times to decode each pattern of form K[string].
     * This gives us worst case time complexity as O(maxK⋅N).
     *
     * Space Complexity: O(M + N),
     * where M is the number of letters(a-z)
     * and N is the number of digits(0-9) in string s.
     * In worst case, the maximum size of stringStack and countStack could be M and N respectively.
     *
     * cntStack顶端放置当前层的count，初始为[0]
     * strStack顶端放置当前层的stringBuilder，初始为[""]
     *
     * (1) 遇到数字
     *     如果是第一个digit，开始新的一层
     *     否则，累加当前层数字
     * (2) 遇到字母
     *     累进当前层stringBuilder
     * (3) 遇到右括号
     *     开始decode当前层，并且累进到新的strStack栈顶，即上一层
     */
    public String decodeString(String s) {
        Deque<Integer> cntStack = new ArrayDeque<>();
        cntStack.push(0);
        Deque<StringBuilder> strStack = new ArrayDeque<>();
        strStack.push(new StringBuilder());

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                if (i - 1 < 0 || !Character.isDigit(s.charAt(i - 1))) {
                    cntStack.push(c - '0');
                    strStack.push(new StringBuilder());
                } else {
                    int cnt = cntStack.pop();
                    cnt = cnt * 10 + (c - '0');
                    cntStack.push(cnt);
                }
            } else if (Character.isLetter(c)) {
                strStack.peek().append(c);
            } else if (c == ']') {
                int cnt = cntStack.pop();
                String str = strStack.pop().toString();
                StringBuilder tmp = new StringBuilder();
                while (cnt-- > 0) {
                    tmp.append(str);
                }
                strStack.peek().append(tmp.toString());
            }
        }
        return strStack.pop().toString();
    }

    /********************* Solution 2: 2 Stacks && push at "[" **************************/
    // Solution 2 at https://leetcode.com/problems/decode-string/solution/

    public static void main(String[] args) {
        DecodeString solution = new DecodeString();
        System.out.println(solution.decodeString("3[a]2[bc]"));
        System.out.println(solution.decodeString("3[a2[c]]"));
        System.out.println(solution.decodeString("2[abc]3[cd]ef"));
        System.out.println(solution.decodeString("ab2[c2[d]e]10[2[g]f]h"));
    }
}