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
     * 该法更优美！！！
     *
     * 类似 567. Permutation in String (https://leetcode.com/problems/permutation-in-string/)
     *
     * Solution 1 & 2
     * 相同点：initial dict/counter 和 debt/missing 是一样的，都只统计 p 中的 char
     * 区别：Solution 1 针对 s 中所有 char 更新 dict & debt，不管 p 中有没有该 char
     *      Solution 2 只针对 p 中有的 char 更新 counter & missing
     *      ⚠️注意⚠️ 对于 Solution 1, 如果 s[i] 在 p 中没有，他就会变成 -1，-2，-3 ...
     *               在第一次变成 -1 时，debt++ 标记它需要被剔除，全部剔除恢复 0 时 debt-- 表示全部剔除！
     *               永远不会变成 1 ！！！
     *
     *               也就是说！s[i] 在 p 里，则 dict.get(s[i]) > 0； 否则，dict.get(s[i]) == 0！
     *               但是他们 share 一样的逻辑，即：
     *               in 的时候，计数--，变 0 则 debt--，变 -1 则 debt++；
     *               out 的时候，计数++，变 0 则 debt--，变 1 则 debt++；
     *
     * Time: O(S)   Space: O(1) max 26 character
     */
    public List<Integer> findAnagrams1(String s, String p) {
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
            dict.put(in, dict.getOrDefault(in, 0) - 1); //注意右侧=in=-
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

    /*********** Solution 2: 固定size的sliding window => counter 和 missing 只统计 p 中的 char ****************/
    /**
     * i 是左侧， j 是右侧
     * 每个时刻保证 s[i:j] inclusive 只有 p 中存在的 char
     * 一旦 s[j] 是 p 里没有的 char，直接 i 追上 j
     *
     * 该法更浅显易懂！！！
     *
     * Time: O(S)   Space: O(1) max 26 character
     */
    public List<Integer> findAnagrams(String s, String p) {
        Map<Character, Integer> counter = new HashMap<>();
        for (char c : p.toCharArray()) {
            counter.put(c, counter.getOrDefault(c, 0) + 1);
        }
        int missing = counter.size();

        List<Integer> res = new ArrayList<>();
        int i = 0, j = 0;
        while (j < s.length()) {
            // p 里没有 s[j]，则直接 i => j
            if (!counter.containsKey(s.charAt(j))) {
                j++;
                while (i < j) {
                    if (counter.containsKey(s.charAt(i))) {
                        counter.put(s.charAt(i), counter.get(s.charAt(i)) + 1);
                        if (counter.get(s.charAt(i)) == 1) missing++;
                    }
                    i++;
                }
            } else { // p 里有 s[j]，则直接 i++
                // 更新右边界 => 加入 s[j]
                counter.put(s.charAt(j), counter.get(s.charAt(j)) - 1);
                if (counter.get(s.charAt(j)) == 0) missing--;
                // 更新左边界 => 剔除 s[i]
                if (j - i + 1 > p.length()) {
                    counter.put(s.charAt(i), counter.get(s.charAt(i)) + 1);
                    if (counter.get(s.charAt(i)) == 1) missing++;
                    i++;
                }
                // 更新结果
                if (missing == 0 && j - i + 1 == p.length()) res.add(i);
                j++;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        FindAllAnagramsInAString solution = new FindAllAnagramsInAString();

        System.out.println(solution.findAnagrams("cbaebabacd", "abc")); // 0, 6

        System.out.println(solution.findAnagrams("abab", "ab")); // 0, 1, 2

    }
}
