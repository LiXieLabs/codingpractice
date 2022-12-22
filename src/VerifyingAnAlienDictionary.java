import java.util.HashMap;
import java.util.Map;

/**
 * 953. Verifying an Alien Dictionary (https://leetcode.com/problems/verifying-an-alien-dictionary/description/)
 */
public class VerifyingAnAlienDictionary {

    /************** Solution 1: Compare Adjacent Word **************/
    /**
     * 对于每一对相邻的words，找到第一个char mismatch的位置index
     * 如果：
     * （1）index比word1和word2都短，且word1.charAt(index)>word2.charAt(index)
     * （2）index比两者任一长度长，且word1.length()>word2.length()
     * 则不满足sorted
     *
     * Time: O(WL)
     * W is words.length, L is avg len of word in words
     *
     * Space: O(26) = O(1)
     */
    public boolean isAlienSorted1(String[] words, String order) {
        Map<Character, Integer> orders = new HashMap<>();
        for (int i = 0; i < order.length(); i++) orders.put(order.charAt(i), i);

        for (int i = 1; i < words.length; i++) {
            int index = findFirstMismatch(words[i - 1], words[i]);
            if (index < words[i - 1].length() && index < words[i].length()
                    && orders.get(words[i - 1].charAt(index)) > orders.get(words[i].charAt(index))
                    || (index >= words[i - 1].length() || index >= words[i].length())
                    && words[i - 1].length() > words[i].length())
                return false;
        }
        return true;
    }

    /************** Solution 2: Solution 1 优化 **************/
    /**
     * HashMap变int[]
     */
    public boolean isAlienSorted(String[] words, String order) {
        int[] orders = new int[26];
        for (int i = 0; i < order.length(); i++) orders[order.charAt(i) - 'a'] = i;

        for (int i = 1; i < words.length; i++) {
            int index = findFirstMismatch(words[i - 1], words[i]);
            if (index < words[i - 1].length() && index < words[i].length()
                    && orders[words[i - 1].charAt(index) - 'a'] > orders[words[i].charAt(index) - 'a']
                    || (index >= words[i - 1].length() || index >= words[i].length())
                    && words[i - 1].length() > words[i].length())
                return false;
        }
        return true;
    }

    private int findFirstMismatch(String s1, String s2) {
        int i = 0;
        while (i < s1.length() && i < s2.length()) {
            if (s1.charAt(i) != s2.charAt(i)) return i;
            i++;
        }
        return i;
    }

    public static void main(String[] args) {
        VerifyingAnAlienDictionary solution = new VerifyingAnAlienDictionary();
        System.out.println(solution.isAlienSorted(new String[]{"hello","leetcode"}, "hlabcdefgijkmnopqrstuvwxyz"));
        System.out.println(solution.isAlienSorted(new String[]{"word","world","row"}, "worldabcefghijkmnpqstuvxyz"));
        System.out.println(solution.isAlienSorted(new String[]{"apple","app"}, "abcdefghijklmnopqrstuvwxyz"));
    }
}
