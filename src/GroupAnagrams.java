import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 49. Group Anagrams (https://leetcode.com/problems/group-anagrams/description/)
 */
public class GroupAnagrams {

    /************ Solution 1: Hash by counter in alphabetical order *********/
    /**
     * 另一种方法是
     * char[] ca = s.toCharArray()
     * Arrays.sort(ca) => 用作 hash
     * 但是时间复杂度NKlogK，会超时
     *
     * N is the length of strs, and K is the maximum length of a string in strs.
     * Time Complexity: O(NK), counting each string is linear in the size of the string, and we count every string.
     * Space Complexity: O(NK), the total information content stored in ans.
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> res = new HashMap<>();
        for (String s : strs) {
            String hash = build(s);
            res.putIfAbsent(hash, new ArrayList<>());
            res.get(hash).add(s);
        }
        // set to list by constructor
        return new ArrayList<>(res.values());
    }

    public String build(String s) {
        // 用 int[26] 也可以
        Map<Character,Integer> counter = new HashMap<>();
        for (Character c : s.toCharArray()) {
            counter.put(c, counter.getOrDefault(c, 0) + 1);
        }
        StringBuilder sb = new StringBuilder();
        // int和char比较，可以直接操作， 不用cast
        // 也可以不带char，直接按字母顺序，用；连接即可
        for (int i = 'a'; i <= 'z'; i++) {
            Character c = (char) i;
            if (counter.containsKey(c)) {
                sb.append(c);
                sb.append(counter.get(c));
            }
        }
        return sb.toString();
    }

    public static void print(List<List<String>> input) {
        System.out.println("["
                + input.stream()
                .map(lst -> "[" + String.join("," , lst) + "]")
                .collect(Collectors.joining(","))
                + "]");
    }

    public static void main(String[] args) {
        GroupAnagrams solution = new GroupAnagrams();
        print(solution.groupAnagrams(new String[]{"eat","tea","tan","ate","nat","bat"}));
        print(solution.groupAnagrams(new String[]{""}));
        print(solution.groupAnagrams(new String[]{"a"}));
    }
}