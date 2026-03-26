import java.util.HashMap;
import java.util.Map;

/**
 * 3. Longest Substring Without Repeating Characters (https://leetcode.com/problems/longest-substring-without-repeating-characters/description/)
 */
public class LongestSubstringWithoutRepeatingCharacters {

    /*********** Solution 1: Sliding Window **************/
    /**
     * Time: O(N)  Space: O(1)
     */
    public int lengthOfLongestSubstring1(String s) {
        int l = 0, res = 0;
        Map<Character, Integer> counter = new HashMap<>();
        for (int r = 0; r < s.length(); r++) {
            // 右侧先推进一步
            char cr = s.charAt(r);
            counter.put(cr, counter.getOrDefault(cr, 0) + 1);
            // 左侧根据需要推进以达到无重复的条件 => 更新 sliding window 以满足条件！
            while (counter.get(cr) > 1) {
                char cl = s.charAt(l++);
                counter.put(cl, counter.get(cl) - 1);
            }
            // 更新最大值
            res = Math.max(res, r - l + 1);
        }
        return res;
    }

    // 优化：左边不用一个一个推进，直接跳到 cr last seen 的 index + 1 即可！
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> lastSeen = new HashMap<>();
        int maxLen = 0, l = 0;
        for (int r = 0; r < s.length(); r++) {
            char cr = s.charAt(r);
            int idx = lastSeen.getOrDefault(cr, -1);
            if (idx >= l) l = idx + 1;
            maxLen = Math.max(maxLen, r - l + 1);
            lastSeen.put(cr, r);
        }
        return maxLen;
    }

    public static void main(String[] args) {
        LongestSubstringWithoutRepeatingCharacters solution = new LongestSubstringWithoutRepeatingCharacters();
        System.out.println(solution.lengthOfLongestSubstring("abcabcbb")); // 3
        System.out.println(solution.lengthOfLongestSubstring("bbbbb")); // 1
        System.out.println(solution.lengthOfLongestSubstring("pwwkew")); // 3
    }
}
