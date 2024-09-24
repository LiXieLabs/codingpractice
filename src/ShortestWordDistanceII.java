import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 244. Shortest Word Distance II (https://leetcode.com/problems/shortest-word-distance-ii/description/)
 */
public class ShortestWordDistanceII {

    private Map<String, List<Integer>> map;

    /************** Solution 1: String to Index array **************/
    /**
     * Time: N is wordsDict len
     * constructor => O(N)
     * shortest => O(N)
     *
     * Space: O(N)
     */
    public ShortestWordDistanceII(String[] wordsDict) {
        map = new HashMap<>();
        for (int i = 0; i < wordsDict.length; i++) {
            map.putIfAbsent(wordsDict[i], new ArrayList<>());
            map.get(wordsDict[i]).add(i);
        }
    }

    public int shortest(String word1, String word2) {
        List<Integer> lst1 = map.get(word1), lst2 = map.get(word2);
        int p1 = 0, p2 = 0, res = Integer.MAX_VALUE;
        while (p1 < lst1.size() && p2 < lst2.size()) {
            res = Math.min(res, Math.abs(lst1.get(p1) - lst2.get(p2)));
            if (lst1.get(p1) > lst2.get(p2)) {
                p2++;
            } else {
                p1++;
            }
        }
        return res;
    }

    public static void main(String[] args) {

        ShortestWordDistanceII solution1 = new ShortestWordDistanceII(new String[]{"practice", "makes", "perfect", "coding", "makes"});
        System.out.println(solution1.shortest("coding", "practice")); // 3
        System.out.println(solution1.shortest("makes", "coding")); // 1

        ShortestWordDistanceII solution2 = new ShortestWordDistanceII(new String[]{"a", "c", "b", "a"});
        System.out.println(solution2.shortest("a", "b")); // 1
        System.out.println(solution2.shortest("b", "a")); // 1
    }
}
