import java.util.HashMap;
import java.util.Map;

public class LongestSubstringWithoutRepeatingCharacters {

    /*********** Solution 1: Sliding Window **************/
    public int lengthOfLongestSubstring(String s) {
        int l = 0, r = 0, res = 0;
        Map<Character, Integer> map = new HashMap<>();
        while (r < s.length()) {
            // 右侧先推进一步
            map.put(s.charAt(r), map.getOrDefault(s.charAt(r), 0) + 1);
            // 左侧根据需要推进以达到无重复的条件
            while (map.get(s.charAt(r)) > 1) {
                map.put(s.charAt(l), map.get(s.charAt(l)) - 1);
                l++;
            }
            // 更新最大值
            res = Math.max(res, r - l + 1);
            // 右侧指针更新
            r++;
        }
        return res;
    }

    public static void main(String[] args) {
        LongestSubstringWithoutRepeatingCharacters solution = new LongestSubstringWithoutRepeatingCharacters();
        System.out.println(solution.lengthOfLongestSubstring("abcabcbb"));
        System.out.println(solution.lengthOfLongestSubstring("bbbbb"));
        System.out.println(solution.lengthOfLongestSubstring("pwwkew"));
    }
}
