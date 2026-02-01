import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WordBreak {

    /*************** Solution 1: 1D Bottom-up DP ************************/
    /**
     * 完全一样
     * 139. Word Break (https://leetcode.com/problems/word-break/)
     * 140. Word Break II (https://leetcode.com/problems/word-break-ii/)
     * 472. Concatenated Words (https://leetcode.com/problems/concatenated-words/description/)
     *
     * dp[i] 表示 s[0:i] 能否被分解为 wordDict 中 word 的组合
     * i 这里理解为 char 之间的缝隙，因此 dp[0] 是 ""，总是 true
     * 对于 0 <= j < i 的缝隙，dp[i] = dp[j] && wordSet.contains(s[j:i])
     *
     * N is s length
     * Time: o(n^2 x L) 2 Nested loops * substring computation at each iteration
     * Space: O(n) by dp array
     */
    public boolean wordBreak1(String s, List<String> wordDict) {
        Set<String> wordSet = new HashSet<>(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i && !dp[i]; j++) {
                dp[i] = dp[j] && wordSet.contains(s.substring(j, i));
            }
        }
        return dp[s.length()];
    }

    /*************** 另一种 1D Bottom-up DP ************************/
    public boolean wordBreak2(String s, List<String> wordDict) {
        Set<String> wordSet = new HashSet<>(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[s.length()] = true;
        for (int start = s.length() - 1; start >= 0; start--) {
            for (int i = start; i < s.length() && !dp[start]; i++) {
                dp[start] = wordSet.contains(s.substring(start, i + 1)) && dp[i + 1];
            }
        }
        return dp[0];
    }

    /*************** Solution 2: Top-down DP by recur + memo ************************/
    /**
     * n is s length
     * Time: o(n^2 x L) N个i * N个j * substring computation at each iteration
     * Space: O(n) by memo & call stack depth
     */
    Set<String> wordSet;
    Map<Integer, Boolean> memo;
    public boolean wordBreak3(String s, List<String> wordDict) {
        wordSet = new HashSet<>(wordDict);
        memo = new HashMap<>();
        memo.put(0, true);
        return recurBreak(s, s.length());
    }

    private boolean recurBreak(String s, int i) {
        if (!memo.containsKey(i)) {
            boolean canBreak = false;
            for (int j = 0; j < i && !canBreak; j++) {
                canBreak = wordSet.contains(s.substring(j, i)) && recurBreak(s, j);
            }
            memo.put(i, canBreak);
        }
        return memo.get(i);
    }

    /*************** 另一种 Top-down DP by recur + memo ************************/
    Boolean[] memoArr;
    Set<String> words;
    String s;

    public boolean wordBreak4(String s, List<String> wordDict) {
        memoArr = new Boolean[s.length() + 1];
        memoArr[s.length()] = true;
        words = new HashSet<>(wordDict);
        this.s = s;
        return recur(0);
    }

    private boolean recur(int start) {
        if (memoArr[start] == null) {
            boolean found = false;
            for (int i = start; i < s.length() && !found; i++) {
                found = words.contains(s.substring(start, i + 1)) && recur(i + 1);
            }
            memoArr[start] = found;
        }
        return memoArr[start];
    }

    /*************** Solution 3: BFS + Trie ************************/
    /**
     * 类似于 Jump Game，从 s[0] 开始，沿着 Trie 把所有 word 标记为 true
     * 再从所有 word 的下一个位置开始继续扩展！！！
     *
     * n = length of the string
     * L = maximum word length in wordDict
     * D = total number of characters across all dictionary words
     * Time: O(Build Trie + loop count x min(L, n - start)) = O(D + n x L)
     * Space: O(queue + visited + Trie) = O(n + n + D) = O(n + D)
     */
    TrieNode139 root;

    public boolean wordBreak(String s, List<String> wordDict) {
        root = new TrieNode139();
        for (String w : new HashSet<>(wordDict)) {
            add(w);
        }
        Deque<Integer> queue = new ArrayDeque<>();
        queue.offer(0);
        Set<Integer> visited = new HashSet<>();
        visited.add(0);
        while (!queue.isEmpty()) {
            int start = queue.poll();
            TrieNode139 curr = root;
            for (int end = start; end < s.length(); end++) {
                char c = s.charAt(end);
                if (curr.children[c - 'a'] == null) break;
                curr = curr.children[c - 'a'];
                if (curr.isWord) {
                    if (end == s.length() - 1) return true;
                    if (!visited.contains(end + 1)) {
                        visited.add(end + 1);
                        queue.offer(end + 1);
                    }
                }
            }
        }
        return false;

    }

    private void add(String word) {
        TrieNode139 curr = root;
        for (char c : word.toCharArray()) {
            if (curr.children[c - 'a'] == null) {
                curr.children[c - 'a'] = new TrieNode139();
            }
            curr = curr.children[c - 'a'];
        }
        curr.isWord = true;
    }

    public static void main(String[] args) {
        WordBreak solution = new WordBreak();
        System.out.println(solution.wordBreak("leetcode", Arrays.asList("leet","code")));
        System.out.println(solution.wordBreak("applepenapple", Arrays.asList("apple","pen")));
        System.out.println(solution.wordBreak("catsandog", Arrays.asList("cats","dog","sand","and","cat")));
    }
}

class TrieNode139 {

    TrieNode139[] children;
    boolean isWord;

    public TrieNode139() {
        children = new TrieNode139[26];
    }
}