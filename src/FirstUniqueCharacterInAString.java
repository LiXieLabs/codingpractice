/**
 * 387. First Unique Character in a String (https://leetcode.com/problems/first-unique-character-in-a-string/description/)
 */
public class FirstUniqueCharacterInAString {

    /************ Solution 1: Counter by 26 字母 Array ****************/
    /**
     * Intuitive solution is 2 loops on s
     * 第一遍count，第二遍count==1则返回i => O(2N)
     *
     * 该方法
     * 第一遍倒着更新1st occur index和count，
     * 第二遍只遍历int[26]，在count==1的char中，找最小的1st occur index
     *
     * Time: O(N)   Space: O(26) = O(1)
     */
    public int firstUniqChar(String s) {
        // 倒着走一遍统计 1st occur index && count
        int[] idx = new int[26];
        int[] cnt = new int[26];
        for (int i = s.length() - 1; i >= 0; i--) {
            idx[s.charAt(i) - 'a'] = i;
            cnt[s.charAt(i) - 'a']++;
        }
        // 对于 count == 1 的 char，找 min 1st occur index
        int res = s.length();
        for (int i = 0; i < 26; i++) {
            if (cnt[i] == 1 && idx[i] < res) {
                res = idx[i];
            }
        }
        return res == s.length() ? -1 : res;
    }

    public static void main(String[] args) {
        FirstUniqueCharacterInAString solution = new FirstUniqueCharacterInAString();
        System.out.println(solution.firstUniqChar("leetcode")); // 0
        System.out.println(solution.firstUniqChar("loveleetcode")); // 2
        System.out.println(solution.firstUniqChar("aabb")); // -1
    }
}
