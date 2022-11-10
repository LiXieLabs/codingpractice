import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LongestStringChain {

    /******************** Solution 1: Top-Down DFS + Memoization *******************/

    public int longestStrChain1(String[] words) {
        if (words == null || words.length == 0) return 0;
        int maxLen = 1;
        Set<String> wordSet = new HashSet<>();
        Map<String, Integer> visited = new HashMap<>();
        Collections.addAll(wordSet, words);
        for (String word : words) maxLen = Math.max(maxLen, dfs(wordSet, visited, word));
        return maxLen;
    }

    public int dfs(Set<String> wordSet, Map<String, Integer> visited, String curWord) {
        if (!wordSet.contains(curWord)) return 0;
        if (visited.containsKey(curWord)) return visited.get(curWord);
        int maxLen = 0;
        for (int i = 0; i < curWord.length(); i++) {
            String nexWord = curWord.substring(0, i) + curWord.substring(i + 1);
            maxLen = Math.max(maxLen, dfs(wordSet, visited, nexWord));
        }
        visited.put(curWord, maxLen + 1);
        return maxLen + 1;
    }

    /******************** Solution 2: Sort + Bottom DP *******************/
    // Time O(NlogN) for sorting,
    // Time O(NSS) for the for loop, where the second S refers to the string generation and S <= 16.
    // Space O(NS)
    public int longestStrChain2(String[] words) {
        // Arrays.sort(words, (a, b)->a.length() - b.length());
        // Arrays.sort(words, Comparator.comparingInt(w -> w.length()));
        Arrays.sort(words, Comparator.comparingInt(String::length));

        Map<String, Integer> dp = new HashMap<>();
        int maxLen = 0;
        for (String curWord : words) {
            int curMax = 0;
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
