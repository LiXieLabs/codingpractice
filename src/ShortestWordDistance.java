/**
 * 243. Shortest Word Distance (https://leetcode.com/problems/shortest-word-distance/description/)
 */
public class ShortestWordDistance {

    /********** Solution 1: Intuitive Two Pointers ***************/
    /**
     * Time: O(2 * L * N) L is wordsDict len & N is length of word1 and word2
     * Space: O(1)
     */
    public int shortestDistance1(String[] wordsDict, String word1, String word2) {
        int i = 0, j = 0;
        while (!word1.equals(wordsDict[i])) i++;
        while (!word2.equals(wordsDict[j])) j++;
        int res = wordsDict.length;
        while (i < wordsDict.length && j < wordsDict.length) {
            res = Math.min(res, Math.abs(i - j));
            if (i < j) {
                do { i++; } while (i < wordsDict.length && !word1.equals(wordsDict[i]));
            } else {
                do { j++; } while (j < wordsDict.length && !word2.equals(wordsDict[j]));
            }
        }
        return res;
    }

    /******************* Solution 2: One Pass *******************/
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
        System.out.println(solution.shortestDistance(new String[]{"practice", "makes", "perfect", "coding", "makes"}, "coding", "practice")); // 3
        System.out.println(solution.shortestDistance(new String[]{"practice", "makes", "perfect", "coding", "makes"}, "makes", "coding")); // 1
    }
}

