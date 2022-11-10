import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupAnagrams {

    /************ Solution 1: Hash by counter in alphabetical order *********/
    /**
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
        Map<Character,Integer> counter = new HashMap<>();
        for (Character c : s.toCharArray()) {
            counter.put(c, counter.getOrDefault(c, 0) + 1);
        }
        StringBuilder sb = new StringBuilder();
        // int和char比较，可以直接操作， 不用cast
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