import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConcatenatedWords {

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
     * Time: o(N^3) 2 Nested loops * substring computation at each iteration
     * Space: O(N) by dp array
     */
    public List<String> findAllConcatenatedWordsInADict1(String[] words) {
        Set<String> wordSet = new HashSet<>(Arrays.asList(words)); // NOTE
        List<String> res = new ArrayList<>();
        for (String word : words) { // 注意！！！因为要操作Set，需要遍历words！！！
            wordSet.remove(word); // 也可以按照长度遍历words，判断后添加到wordSet，避免remove
            boolean[] dp = new boolean[word.length() + 1];
            dp[0] = true;
            for (int i = 1; i < word.length() + 1; i++) {
                for (int j = 0; j < i && !dp[i]; j++) {
                    dp[i] = dp[j] && wordSet.contains(word.substring(j, i));
                }
            }
            if (dp[word.length()]) res.add(word);
            wordSet.add(word);
        }
        return res;
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
    Map<Integer, Boolean> memo;
    Set<String> wordSet;
    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        wordSet = new HashSet<>(Arrays.asList(words)); // NOTE
        List<String> res = new ArrayList<>();
        for (String word : words) { // 注意！！！因为要操作Set，需要遍历words！！！
            wordSet.remove(word);
            memo = new HashMap<>();
            memo.put(0, true);
            if (recurBreak(word, word.length())) res.add(word);
            wordSet.add(word);
        }
        return res;
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

    public static void main(String[] args) {
        ConcatenatedWords solution = new ConcatenatedWords();
        System.out.println(solution.findAllConcatenatedWordsInADict(new String[]{"cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"}));
        System.out.println(solution.findAllConcatenatedWordsInADict(new String[]{"cat","dog","catdog"}));
    }
}
