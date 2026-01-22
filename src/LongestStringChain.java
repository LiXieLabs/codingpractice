import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LongestStringChain {

    /******************** Solution 1: Top-Down DFS + Memoization *******************/
    /**
     * Total N words, avg length is L
     *
     * Time: O(NL^2)
     * Each word is visited once - N
     * Each word, iterate over word - L && each substring concatenation - L = L^2
     *
     * Space: O(NL)
     * memo - O(N)
     * wordSet - O(NL)
     * recur stack - O(L)
     */
    Map<String, Integer> memo;
    Set<String> wordSet;

    public int longestStrChain1(String[] words) {
        memo = new HashMap<>();
        int res = 0;
        wordSet = new HashSet<>();
        Collections.addAll(wordSet, words);
        for (String w : words) {
            res = Math.max(res, recur(w));
        }
        return res;
    }

    private int recur(String w) {
        if (!memo.containsKey(w)) {
            int res = 1;
            for (int i = 0; i < w.length(); i++) {
                String sub = w.substring(0, i) + w.substring(i + 1);
                if (wordSet.contains(sub)) {
                    res = Math.max(res, recur(sub) + 1);
                }
            }
            memo.put(w, res);
        }
        return memo.get(w);
    }

    /******************** Solution 2: Sort + Bottom DP *******************/
    /**
     * Time O(NlogN) for sorting,
     * Time O(NL^2) for the for loop, where the second L refers to the string generation and L <= 16.
     * Space O(NL)
     */
    public int longestStrChain2(String[] words) {
        // Arrays.sort(words, (a, b)->a.length() - b.length());
        // Arrays.sort(words, Comparator.comparingInt(w -> w.length()));
        Arrays.sort(words, Comparator.comparingInt(String::length));

        Map<String, Integer> dp = new HashMap<>();
        int maxLen = 0;
        for (String curWord : words) {
            int curMax = 0;
            // 也可以 if (curWord.length() > words[0].length()) {
            for (int i = 0; i < curWord.length(); i++) {
                String nexWord = curWord.substring(0, i) + curWord.substring(i + 1);
                curMax = Math.max(curMax, dp.getOrDefault(nexWord, 0) + 1);
            }
            dp.put(curWord, curMax);
            maxLen = Math.max(maxLen, curMax);
        }
        return maxLen;
    }

    public static void main(String[] args) {
        LongestStringChain solution = new LongestStringChain();
        System.out.println(solution.longestStrChain2(new String[]{"a","b","ba","bca","bda","bdca"}));
        System.out.println(solution.longestStrChain2(new String[]{"xbc","pcxbcf","xb","cxbc","pcxbc"}));
        System.out.println(solution.longestStrChain2(new String[]{"abcd","dbqca"}));
        System.out.println(solution.longestStrChain2(new String[]{"xzyw","xyw","xzw","w","x","xw"}));
    }
}
