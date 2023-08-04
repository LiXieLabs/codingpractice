import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 438. Find All Anagrams in a String (https://leetcode.com/problems/find-all-anagrams-in-a-string/description/)
 */
public class FindAllAnagramsInAString {

    /*********** Solution 1: 固定size的sliding window ****************/
    /**
     * sliding window 移动条件是 size == p.length()
     *
     * 统计hashmap counter全等的方式 => 统计尚未归零的key的个数(debt)，全归零时 debt == 0，更新结果
     * 也可以 Map / Array 全等比较, 但是time&space complexity差一些:
     * (1) map1.equals(map2)
     * (2) Arrays.equals(arr1, arr2)
     *
     * Time: O(S)   Space: O(1) max 26 character
     */
    public List<Integer> findAnagrams(String s, String p) {
        // init result
        List<Integer> res = new ArrayList<>();
        // handle special case for better performance
        if (s.length() < p.length()) return res;
        // iterate over p, build target dictionary
        Map<Character, Integer> dict = new HashMap<>(); // count of each char that mismatch between current window of s & p, init with count of each char in p
        for (char c : p.toCharArray()) {
            dict.put(c, dict.getOrDefault(c, 0) + 1);
        }
        // iterate over s, update dictionary and statistics
        int debt = dict.size(); // count of distinct char that are still not matching between current window of s & p
        for (int i = 0; i < s.length(); i++) {
            // update right side of sliding window
            char in = s.charAt(i);
            dict.put(in, dict.getOrDefault(in, 0) - 1); //注意左侧=in=-
            if (dict.get(in) == 0) {
                debt -= 1;
            } else if (dict.get(in) == -1) {
                debt += 1;
            }
            // update left side of sliding window
            if (i - p.length() >= 0) {
                char out = s.charAt(i - p.length());
                dict.put(out, dict.getOrDefault(out, 0) + 1); //注意左侧=out=+
                if (dict.get(out) == 0) {
                    debt -= 1;
                } else if (dict.get(out) == 1){
                    debt += 1;
                }
            }
            // update result
            if (debt == 0) res.add(i - p.length() + 1);
        }
        return res;
    }

    public static void main(String[] args) {
        FindAllAnagramsInAString solution = new FindAllAnagramsInAString();

        System.out.println(solution.findAnagrams("cbaebabacd", "abc")); // 0, 6

        System.out.println(solution.findAnagrams("abab", "ab")); // 0, 1, 2

    }
}
