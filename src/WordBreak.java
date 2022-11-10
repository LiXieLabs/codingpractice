import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WordBreak {

    /*************** Solution 1: 1D Bottom-up DP ************************/
    /**
     * dp[i] 表示 s[0:i] 能否被分解为 wordDict 中 word 的组合
     * i 这里理解为 char 之间的缝隙，因此 dp[0] 是 ""，总是 true
     * 对于 0 <= j < i 的缝隙，dp[i] = dp[j] && wordSet.contains(s[j:i])
     *
     * N is s length
     * Time: o(N^3) 2 Nested loops * substring computation at each iteration
     * Space: O(N) by dp array
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

    /*************** Solution 2: Top-down DP by recur + memo ************************/
    /**
     * N is s length
     * Time: o(N^3) N个i * N个j * substring computation at each iteration
     * Space: O(N) by memo & call stack depth
     */
    Set<String> wordSet;
    Map<Integer, Boolean> memo;
    public boolean wordBreak(String s, List<String> wordDict) {
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

    public static void main(String[] args) {
        WordBreak solution = new WordBreak();
        System.out.println(solution.wordBreak("leetcode", Arrays.asList("leet","code")));
        System.out.println(solution.wordBreak("applepenapple", Arrays.asList("apple","pen")));
        System.out.println(solution.wordBreak("catsandog", Arrays.asList("cats","dog","sand","and","cat")));
    }
}