import java.util.HashSet;
import java.util.Set;

/**
 * 791. Custom Sort String (https://leetcode.com/problems/custom-sort-string/description/)
 */
public class CustomSortString {

    /************* Solution 1: 遍历 order => s => order ***************/
    /**
     *  (1) 将 order 放入 hashSet
     *  (2) 遍历 s，如果 char 在 order hashSet 里，统计个数
     *            否则，直接计入结果
     *  (3) 遍历 order，循环计入结果
     *  这种方法，结果是 非 order char + order char
     *
     *  Time: O(2 x Order + S)   Space: O(26)
     */
    public String customSortString1(String order, String s) {
        int[] count = new int[26];
        Set<Character> orderSet = new HashSet<>();
        for (Character c : order.toCharArray()) {
            orderSet.add(c);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!orderSet.contains(c)) {
                sb.append(c);
            } else {
                count[c - 'a']++;
            }
        }
        for (int i = 0; i < order.length(); i++) {
            char c = order.charAt(i);
            while (count[c - 'a']-- > 0) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /************* Solution 2: 遍历 s => order => counter ***************/
    /**
     *  (1) 遍历 s，无论 char 是否在 order hashSet 里，统计个数
     *  (2) 遍历 order 中的 char，循环计入结果
     *  (3) 遍历 counter 中的 char，还没计入结果的计入结果
     *  这种方法，结果是 order char + 非 order char
     *
     *  Time: O(S + Order + Counter)   Space: O(26)
     */
    public String customSortString(String order, String s) {
        int[] count = new int[26];
        for (Character c : s.toCharArray()) {
            count[c - 'a']++;
        }
        StringBuilder sb = new StringBuilder();
        for (Character c : order.toCharArray()) {
            while (count[c - 'a']-- > 0) {
                sb.append(c);
            }
        }
        for (int i = 0; i < 26; i++) {
            while (count[i]-- > 0) {
                sb.append((char) (i + 'a'));
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        CustomSortString solution = new CustomSortString();
        System.out.println(solution.customSortString("cba", "abcd"));
        System.out.println(solution.customSortString("cbafg", "abcd"));
    }
}
