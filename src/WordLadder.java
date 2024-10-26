import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 127. Word Ladder (https://leetcode.com/problems/word-ladder/description/)
 */
public class WordLadder {

    private static final char[] candidates = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    /**************** Solution 1: Iterative BFS ************************/
    /**
     * 最短路径，往往用 BFS，因为这样找到的一定是最短的！！！
     *
     * Time: O(26KN) K denotes length of each word, N denotes size of wordSet.
     * 每个 word 最多进 queue 一次，每个 word 处理是 26k 时间。
     * Space: O(N) queue & visited can be in same size as wordSet.
     */
    public int ladderLength1(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList); // or else TLE!!!
        if (!wordSet.contains(endWord) || beginWord.length() != endWord.length()) return 0;
        Set<String> visited = new HashSet<>();
        List<String> currLevel = new ArrayList<>();
        currLevel.add(beginWord);
        int length = 1;
        while (!currLevel.isEmpty()) {
            length++;
            List<String> nextLevel = new ArrayList<>();
            for (String word : currLevel) {
                for (int i = 0; i < word.length(); i++) {
                    for (char c : candidates) {
                        String newWord = word.substring(0, i) + c + word.substring(i + 1);
                        if (endWord.equals(newWord)) return length;
                        if (!visited.contains(newWord) && wordSet.contains(newWord)) {
                            visited.add(newWord);
                            nextLevel.add(newWord);
                        }
                    }
                }
            }
            currLevel = nextLevel;
        }
        return 0;
    }

    /********************** Solution 2: BFS 优化 两端向中间靠拢 bi-directional BFS ***************************/
    /**
     * https://app.gitbook.com/o/-LEzdBqqw8FawHA3mleT/s/-LEzdBqr7mNEEfIbMstL/bfs-dfs/untitled#solution-4-iterative-bfs-jin-yi-bu-you-hua
     * bi-directional BFS 的核心是 b^(d/2) + b^(d/2) < b^d
     */
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList); // or else TLE!!!
        if (!wordSet.contains(endWord)) return 0;
        Set<String> beginSet = new HashSet<>(), endSet = new HashSet<>();
        beginSet.add(beginWord);
        endSet.add(endWord);
        int length = 1;
        while (!beginSet.isEmpty() && !endSet.isEmpty()) {
            length++;
            if (beginSet.size() > endSet.size()) {
                Set<String> temp = beginSet;
                beginSet = endSet;
                endSet = temp;
            }
            Set<String> nextSet = new HashSet<>();
            for (String word : beginSet) {
                for (int i = 0; i < word.length(); i++) {
                    for (char c : candidates) {
                        String newWord = word.substring(0, i) + c + word.substring(i + 1);
                        if (endSet.contains(newWord)) return length;
                        if (wordSet.remove(newWord)) {
                            nextSet.add(newWord);
                        }
                    }
                }
            }
            beginSet = nextSet;
        }
        return 0;
    }

    public static void main(String[] args) {
        WordLadder solution = new WordLadder();
        System.out.println(solution.ladderLength("hit","cog", Arrays.asList("hot","dot","dog","lot","log","cog"))); // 5
        System.out.println(solution.ladderLength("hit","cog", Arrays.asList("hot","dot","dog","lot","log"))); // 0
    }
}
