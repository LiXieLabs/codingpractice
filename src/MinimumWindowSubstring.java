import java.util.HashMap;
import java.util.Map;

/**
 * 76. Minimum Window Substring (https://leetcode.com/problems/minimum-window-substring/description/)
 */
public class MinimumWindowSubstring {

    /****************** Solution 1: Sliding Window *********************/
    /**
     * 可以优化: 遍历s，只将t中有的字母及其对应index存储，并且遍历
     * e.g. S = "ABCDDDDDDEEAFFBC" T = "ABC"
     * filtered_S = [(0, 'A'), (1, 'B'), (2, 'C'), (11, 'A'), (14, 'B'), (15, 'C')]
     *
     * Time: O(T + 2S)    Space: O(T)
     */
    public String minWindow(String s, String t) {
        // Special Case 提前结束
        if (s.length() < t.length()) return "";

        // build initial counter from t
        // remains denotes 哪些字母还没有被完全包含进 window，起始值为全部 t 中的字母
        Map<Character, Integer> counter = new HashMap<>();
        for (char c : t.toCharArray()) {
            counter.put(c, counter.getOrDefault(c, 0) + 1);
        }
        int remains = counter.size();

        // l 指向当前 window 左边界 (inclusive), r 指向当前 window 右边界 (inclusive)
        String res = s + t;
        int l = 0;
        for (int r = 0; r < s.length(); r++) {
            // 更新右边界
            char right = s.charAt(r);
            if (!counter.containsKey(right)) continue;
            counter.put(right, counter.get(right) - 1); // 将其 counter 对应值递减
            if (counter.get(right) == 0) remains -= 1; // 一旦到达 0，说明 window 第一次包含了 t 中全部的该字母，remain递减
            // 更新左边界
            char left = s.charAt(l);
            while (l < r && (!counter.containsKey(left) || counter.get(left) < 0)) {
                // 左边界不在 t 中，或者在，但是数量 < 0, 说明当前 window 有相比 t 多余的该字母，可以向右移动
                if (counter.getOrDefault(left, 0) < 0) {
                    counter.put(left, counter.get(left) + 1);
                }
                left = s.charAt(++l);
            }
            // 根据当前 window 情况，更新最优解
            if (remains == 0 && r - l + 1 < res.length()) {
                res = s.substring(l, r + 1);
            }

        }
        return res.length() > s.length() ? "" : res;
    }

    public static void main(String[] args) {
        MinimumWindowSubstring solution = new MinimumWindowSubstring();
        System.out.println(solution.minWindow("ADOBECODEBANC", "ABC"));
        System.out.println(solution.minWindow("a", "a"));
        System.out.println(solution.minWindow("a", "aa"));
    }
}
