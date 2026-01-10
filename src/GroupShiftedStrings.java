import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 249. Group Shifted Strings (https://leetcode.com/problems/group-shifted-strings/description/)
 */
public class GroupShiftedStrings {

    /************ Solution 1: Hashing Algo of Shifted Strings => 最优解！！！ ******************/
    /**
     * 所有同一group的string必须经过某种hashing algo指向同一哈希值
     * hashing algo is:
     * 对于任一string，将第一个字母shift为'z'，求得offset，后面char都按照这个offset shift
     * 最后的哈希值即为该string在同group中以'a'开始的那个string，设为originalString
     *
     * ⚠️注意⚠️ s.charAt(0) 如果是左移到 'a', 则 offset 有可能负数，有点麻烦，需要：
     * int offset = s.charAt(0) - 'a';
     * for (char c : s.toCharArray()) {
     *   sb.append((char) ('a' + (c - 'a' - offset + 26) % 26));
     *
     * Time: O(N X K)   Space: O(N X K)
     * where N is string.length(), K is max length of a string
     */
    public List<List<String>> groupStrings(String[] strings) {
        Map<String, List<String>> idToStr = new HashMap<>();
        for (String s : strings) {
            String id = getId(s);
            idToStr.putIfAbsent(id, new ArrayList<>());
            idToStr.get(id).add(s);
        }
        return new ArrayList<>(idToStr.values());
    }

    private String getId(String s) {
        StringBuilder sb = new StringBuilder();
        sb.append('z');
        int diff = 'z' - s.charAt(0);
        for (int i = 1; i < s.length(); i++) {
            sb.append((char) ('a' + (s.charAt(i) - 'a' + diff) % 26));
        }
        return sb.toString();
    }

    /***************** Solution 2: 直接把差值做成 Hash Key ********************/
    /**
     * 容易想到，但是空间复杂度较差，需要分隔符，而且数字有可能两位，占位大！
     */
    public List<List<String>> groupStrings2(String[] strings) {
        Map<String, List<String>> dict = new HashMap<>();
        for (String s : strings) {
            String key = buildKey(s);
            dict.putIfAbsent(key, new ArrayList<>());
            dict.get(key).add(s);
        }
        return new ArrayList<>(dict.values());
    }

    private String buildKey(String s) {
        List<String> lst = new ArrayList<>();
        for (int i = 1; i < s.length(); i++) {
            int diff = s.charAt(i) - s.charAt(i - 1);
            if (diff < 0) diff += 26; // za 的情况，diff == -25 < 0，+26 -> 1
            lst.add(String.valueOf(diff));
        }
        return String.join(",", lst); // s.length() == 1 -> lst.size() == 0 -> ""
    }

    public static void main(String[] args) {
        GroupShiftedStrings solution = new GroupShiftedStrings();
        System.out.println(solution.groupStrings(new String[]{"abc","bcd","acef","xyz","az","ba","a","z"})); // [[a, z], [abc, bcd, xyz], [az, ba], [acef]]
        System.out.println(solution.groupStrings(new String[]{"a"})); // [[a]]
    }
}
