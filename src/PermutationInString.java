import java.util.HashMap;
import java.util.Map;

/**
 * 567. Permutation in String (https://leetcode.com/problems/permutation-in-string/)
 */
public class PermutationInString {

    /*********** Solution 1: Sliding Window with Fixed Size ******************/
    /**
     * 类似
     * 76. Minimum Window Substring (https://leetcode.com/problems/minimum-window-substring/description/)
     * 438. Find All Anagrams in a String (https://leetcode.com/problems/find-all-anagrams-in-a-string/description/)
     * 567. Permutation in String (https://leetcode.com/problems/permutation-in-string/)
     * 简单版的
     *
     * Time: O(S1 + S2)     Space: O(1)
     */
    public boolean checkInclusion(String s1, String s2) {
        // iterate over s1 to build counter
        int[] counter = new int[26];
        int debt = 0;
        for (char c : s1.toCharArray()) {
            if (counter[c - 'a'] == 0) debt++;
            counter[c - 'a']++;
        }

        // iterate over s2, keep sliding window of size = s1.length() & update counter
        for (int i = 0; i < s2.length(); i++) {
            // 右侧进来
            int in = s2.charAt(i) - 'a';
            if (counter[in] == 0) debt++; // 多了一个
            counter[in]--;
            if (counter[in] == 0) debt--; // 正好
            if (i >= s1.length()) {
                // 左侧出去
                int out = s2.charAt(i - s1.length()) - 'a';
                if (counter[out] == 0) debt++; // 少了一个
                counter[out]++;
                if (counter[out] == 0) debt--; // 正好
            }
            // 比较 s1 和 window 是否全等
            // 注意！！！在 if 外面，不然 TC#3 会 fail
            if (debt == 0) return true;
        }
        return false;
    }

    /*********** Solution 2: Another Sliding Window with Fixed Size ******************/
    /**
     * 区别是：
     * （1）counter 只记录 s1 里面有的 char
     * （2）遇到 s1 里面没有的 char 直接 l r 都到下一个 重新开始！
     */
    public boolean checkInclusion2(String s1, String s2) {
        if (s1.length() > s2.length()) return false;

        Map<Character, Integer> counter = new HashMap<>();
        for (char c : s1.toCharArray()) {
            counter.put(c, counter.getOrDefault(c, 0) + 1);
        }
        int missing = counter.size();

        int l = 0;
        for (int r = 0; r < s2.length(); r++) {
            char cr = s2.charAt(r);
            if (!counter.containsKey(cr)) {
                while (l < r) {
                    char cl = s2.charAt(l++);
                    if (counter.containsKey(cl)) {
                        counter.put(cl, counter.get(cl) + 1);
                        if (counter.get(cl) == 1) missing++;
                    }
                }
                l++; // 小心！需要到下一个！
            } else {
                counter.put(cr, counter.get(cr) - 1);
                if (counter.get(cr) == 0) missing--;
                if (r - l + 1 > s1.length()) {
                    char cl = s2.charAt(l++);
                    counter.put(cl, counter.get(cl) + 1);
                    if (counter.get(cl) == 1) missing++;
                }
                if (missing == 0) return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        PermutationInString solution = new PermutationInString();
        // TC#1
        System.out.println(solution.checkInclusion("ab", "eidbaooo")); //true
        // TC#2
        System.out.println(solution.checkInclusion("ab", "eidboaooo")); //false
        // TC#3
        System.out.println(solution.checkInclusion("a", "ab")); //true
    }
}
