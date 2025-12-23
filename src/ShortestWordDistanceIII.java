/**
 * 245. Shortest Word Distance III (https://leetcode.com/problems/shortest-word-distance-iii/description/)
 */
public class ShortestWordDistanceIII {

    /*********** Solution 1: Two Pointers + 分开处理 ***************/
    /**
     * word1 == word2 => 交替更新 p1 和 p2（i.e. 谁小更新谁）
     * word1 != word2 => 243. Shortest Word Distance (https://leetcode.com/problems/shortest-word-distance/description/)
     *
     * Time: O(L X N) where wordsDict.length == N, word avg length = L
     * Space: O(1)
     */
    public int shortestWordDistance1(String[] wordsDict, String word1, String word2) {
        int res = wordsDict.length, p1 = -1, p2 = -1;
        boolean same = word1.equals(word2); // 比较结果设为 boolean，减少反复比较时间！
        for (int i = 0; i < wordsDict.length; i++) {
            String w = wordsDict[i];
            if (word1.equals(w) && same) { // word1 == word2 的情况单独判断！
                if (p1 <= p2) {
                    if (p2 != -1) res = Math.min(res, i - p2);
                    p1 = i;
                } else {
                    res = Math.min(res, i - p1);
                    p2 = i;
                }
            } else if (word1.equals(w)) {
                if (p2 != -1) res = Math.min(res, i - p2);
                p1 = i;
            } else if (word2.equals(w)) {
                if (p1 != -1) res = Math.min(res, i - p1);
                p2 = i;
            }
        }
        return res;
    }

    /*********** Solution 2: Solution 1 的一点改进 ***************/
    /**
     * word1 == word2 => 保持 p1 在 p2 后面。
     * word1 != word2 => 243. Shortest Word Distance (https://leetcode.com/problems/shortest-word-distance/description/)
     *
     * Time: O(L X N) where wordsDict.length == N, word avg length = L
     * Space: O(1)
     */
    public int shortestWordDistance(String[] wordsDict, String word1, String word2) {
        int res = wordsDict.length, p1 = -1, p2 = -1;
        boolean same = word1.equals(word2);
        for (int i = 0; i < wordsDict.length; i++) {
            String w = wordsDict[i];
            if (word1.equals(w)) {
                if (same) {
                    p1 = p2;
                    p2 = i;
                } else {
                    p1 = i;
                }
            } else if (word2.equals(w)) {
                p2 = i;
            }
            if (p1 != -1 && p2 != -1) res = Math.min(res, Math.abs(p2 - p1));
        }
        return res;
    }


    public static void main(String[] args) {
        ShortestWordDistanceIII solution = new ShortestWordDistanceIII();
        System.out.println(solution.shortestWordDistance(
                new String[]{"practice", "makes", "perfect", "coding", "makes"},
                "makes", "coding")); // 1
        System.out.println(solution.shortestWordDistance(
                new String[]{"practice", "makes", "perfect", "coding", "makes"},
                "makes", "makes")); // 3
    }
}
