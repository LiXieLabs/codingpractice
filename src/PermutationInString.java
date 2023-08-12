/**
 * 567. Permutation in String (https://leetcode.com/problems/permutation-in-string/)
 */
public class PermutationInString {

    /*********** Solution 1: Sliding Window with Fixed Size ******************/
    /**
     * 类似 438. Find All Anagrams in a String (https://leetcode.com/problems/find-all-anagrams-in-a-string/description/)
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
            if (counter[in] == 0) debt++;
            counter[in]--;
            if (counter[in] == 0) debt--;
            if (i >= s1.length()) {
                // 左侧出去
                int out = s2.charAt(i - s1.length()) - 'a';
                if (counter[out] == 0) debt++;
                counter[out]++;
                if (counter[out] == 0) debt--;
            }
            // 比较 s1 和 window 是否全等
            // 注意！！！在 if 外面，不然 TC#3 会 fail
            if (debt == 0) return true;
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
