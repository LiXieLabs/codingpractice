import java.util.HashMap;
import java.util.Map;

/**
 * 76. Minimum Window Substring (https://leetcode.com/problems/minimum-window-substring/description/)
 */
public class MinimumWindowSubstring {

    /****************** Solution 1: Sliding Window *********************/
    /**
     * 76. Minimum Window Substring (https://leetcode.com/problems/minimum-window-substring/description/)
     * 438. Find All Anagrams in a String (https://leetcode.com/problems/find-all-anagrams-in-a-string/description/)
     * 567. Permutation in String (https://leetcode.com/problems/permutation-in-string/)
     *
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
        // missing denotes 哪些字母还没有被完全包含进 window，起始值为全部 t 中的字母
        Map<Character, Integer> counter = new HashMap<>();
        for (char c : t.toCharArray()) {
            counter.put(c, counter.getOrDefault(c, 0) + 1);
        }
        int missing = counter.size();

        // l 指向当前 window 左边界 (inclusive), r 指向当前 window 右边界 (inclusive)
        int resIdx = -1, resLen = s.length() + 1, l = 0;
        for (int r = 0; r < s.length(); r++) {
            char c = s.charAt(r);
            if (counter.containsKey(c)) {
                counter.put(c, counter.get(c) - 1);
                if (counter.get(c) == 0) {
                    missing--;
                }
            }
            while (l <= r && (!counter.containsKey(s.charAt(l)) || counter.get(s.charAt(l)) < 0)) {
                if (counter.containsKey(s.charAt(l))) {
                    counter.put(s.charAt(l), counter.get(s.charAt(l)) + 1);
                }
                l++;
            }
            if (missing == 0 && r - l + 1 < resLen) {
                resIdx = l;
                resLen = r - l + 1;
            }
        }
        return resLen <= s.length() ? s.substring(resIdx, resIdx + resLen) : "";
    }

    public static void main(String[] args) {
        MinimumWindowSubstring solution = new MinimumWindowSubstring();
        System.out.println(solution.minWindow("ADOBECODEBANC", "ABC")); // "BANC"
        System.out.println(solution.minWindow("a", "a")); // "a"
        System.out.println(solution.minWindow("a", "aa")); // ""
    }
}
