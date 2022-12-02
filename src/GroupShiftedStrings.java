import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 249. Group Shifted Strings (https://leetcode.com/problems/group-shifted-strings/description/)
 */
public class GroupShiftedStrings {

    /************ Solution 1: Hashing Algo of Shifted Strings ******************/
    /**
     * 所有同一group的string必须经过某种hashing algo指向同一哈希值
     * hashing algo is:
     * 对于任一string，将第一个字母shift为'a'，求得offset，后面char都按照这个offset shift
     * 最后的哈希值即为该string在同group中以'a'开始的那个string，设为originalString
     *
     * Time: O(N X K)   Space: O(N X K)
     * where N is string.length(), K is max length of a string
     */
    public List<List<String>> groupStrings(String[] strings) {
        Map<String, List<String>> map = new HashMap<>();
        for (String s : strings) {
            String originalStr = getOriginalStr(s);
            map.putIfAbsent(originalStr, new ArrayList<>());
            map.get(originalStr).add(s);
        }
        return new ArrayList<>(map.values());
    }

    private String getOriginalStr(String s) {
        if (s.length() == 0 || s.charAt(0) == 'a') return s;
        int offset = s.charAt(0) - 'a';
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            sb.append((char) ('a' + (c - 'a' - offset + 26) % 26));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        GroupShiftedStrings solution = new GroupShiftedStrings();
        System.out.println(solution.groupStrings(new String[]{"abc","bcd","acef","xyz","az","ba","a","z"}));
        System.out.println(solution.groupStrings(new String[]{"a"}));
    }
}
