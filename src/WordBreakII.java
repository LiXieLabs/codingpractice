import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WordBreakII {

    /************** Solution 1: 1D Bottom-up DP *****************/
    /**
     * 完全一样
     * 139. Word Break (https://leetcode.com/problems/word-break/)
     * 140. Word Break II (https://leetcode.com/problems/word-break-ii/)
     * 472. Concatenated Words (https://leetcode.com/problems/concatenated-words/description/)
     * 只不过每个位置的dp不止记录boolean标记能否分解，对于可以分解的，要记录到该点的所有分解方式
     *
     * Time: O(N X 2^N)
     * k size string的分解方式有 2^(k-1) 种 (k-1个中间缝隙，分开或者不分开两种可能)
     * 则一共 O(2^0 + 2^1 + 2^2 + ... + 2^N) = O(2^N)
     * substring computation at each iteration = O(N)
     *
     * Space: O(W X 2^N)
     * W is avg length of each word
     */
    public List<String> wordBreak1(String s, List<String> wordDict) {
        Set<String> wordSet = new HashSet<>(wordDict);
        List<List<String>> dp = new ArrayList<>();
        dp.add(Collections.singletonList(""));
        for (int i = 1; i <= s.length(); i++) {
            List<String> curr = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                String word = s.substring(j, i);
                if (wordSet.contains(word)) {
                    for (String prev : dp.get(j)) {
                        curr.add(prev.isEmpty() ? word : prev + " " + word);
                    }
                }
            }
            dp.add(curr);
        }
        return dp.get(s.length());
    }

    /*************** Solution 2: Top-down DP by recur + memo ************************/
    /**
     * N is s length
     *
     * Time: O(N X 2^N)
     * k size string的分解方式有 2^(k-1) 种 (k-1个中间缝隙，分开或者不分开两种可能) = O(2^N)
     * substring computation at each iteration = O(N)
     *
     * Space: O(W X 2^N) by memo
     * W is avg length of each word
     */
    Set<String> wordSet;
    Map<Integer, List<String>> memo;
    public List<String> wordBreak2(String s, List<String> wordDict) {
        wordSet = new HashSet<>(wordDict);
        memo = new HashMap<>();
        memo.put(0, Collections.singletonList(""));
        return recurBreak(s, s.length());
    }

    private List<String> recurBreak(String s, int i) {
        if (!memo.containsKey(i)) {
            List<String> res = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                String word = s.substring(j, i);
                if (wordSet.contains(word)) {
                    for (String prev : recurBreak(s, j)) {
                        res.add(prev.isEmpty() ? word : prev + " " + word);
                    }
                }
            }
            memo.put(i, res);
        }
        return memo.get(i);
    }

    TrieNode140 root;
    public List<String> wordBreak(String s, List<String> wordDict) {
        wordSet = new HashSet<>(wordDict);
        memo = new HashMap<>();
        memo.put(s.length(), Collections.singletonList(""));
        root = new TrieNode140();
        for (String w : new HashSet<>(wordDict)) add(w);
        return dfs(s, 0);
    }

    private List<String> dfs(String s, int start) {
        if (!memo.containsKey(start)) {
            List<String> res = new ArrayList<>();
            TrieNode140 curr = root;
            for (int end = start; end < s.length(); end++) {
                char c = s.charAt(end);
                if (curr.children[c - 'a'] == null) break;
                curr = curr.children[c - 'a'];
                if (curr.isWord) {
                    List<String> tails = dfs(s, end + 1);
                    String word = s.substring(start, end + 1);
                    for (String tail : tails) {
                        res.add(tail.isEmpty() ? word : word + " " + tail);
                    }
                }
            }
            memo.put(start, res);
        }
        return memo.get(start);
    }

    private void add(String word) {
        TrieNode140 curr = root;
        for (char c : word.toCharArray()) {
            if (curr.children[c - 'a'] == null) curr.children[c - 'a'] = new TrieNode140();
            curr = curr.children[c - 'a'];
        }
        curr.isWord = true;
    }

    public static void main(String[] args) {
        WordBreakII solution = new WordBreakII();
        System.out.println(solution.wordBreak("catsanddog", Arrays.asList("cat","cats","and","sand","dog")));
    }
}

class TrieNode140 {
    TrieNode140[] children = new TrieNode140[26];
    boolean isWord;

    public TrieNode140() {
        children = new TrieNode140[26];
    }

}