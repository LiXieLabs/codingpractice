/**
 * 243. Shortest Word Distance (https://leetcode.com/problems/shortest-word-distance/description/)
 */
public class ShortestWordDistance {

    /********** Solution 1: One Pass ***************/
    /**
     * Time: O(L * N) L is wordsDict len & N is length of word1 and word2
     * Space: O(1)
     */
    public int shortestDistance(String[] wordsDict, String word1, String word2) {
        int p1 = -wordsDict.length, p2 = -wordsDict.length, res = wordsDict.length;
        for (int i = 0; i < wordsDict.length; i++) {
            if (word1.equals(wordsDict[i])) {
                res = Math.min(res, i - p2);
                p1 = i;
            }
            if (word2.equals(wordsDict[i])) {
                res = Math.min(res, i - p1);
                p2 = i;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        ShortestWordDistance solution = new ShortestWordDistance();
        System.out.println(solution.shortestDistance(new String[]{"practice", "makes", "perfect", "coding", "makes"}, "coding", "practice"));
        System.out.println(solution.shortestDistance(new String[]{"practice", "makes", "perfect", "coding", "makes"}, "makes", "coding"));
    }
}

