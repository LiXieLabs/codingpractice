import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordLadder {

    private static final String[] candidates =
            new String[]{"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};

    /**************** Solution 1: Iterative BFS ************************/
    /**
     * Time: O(26KN) K denotes length of each word, N denotes size of wordSet.
     * Space: O(N) queue & visited can be in same size as wordSet.
     */
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>();
        for (String word : wordList) {
            wordSet.add(word);
        }
        if (!wordSet.contains(endWord) || beginWord.length() != endWord.length()) return 0;
        int l = beginWord.length();
        Set<String> visited = new HashSet<>();
        int step = 0;
        List<String> queue = new ArrayList<>();
        queue.add(beginWord);
        visited.add(beginWord);
        while (!queue.isEmpty()) {
            List<String> nextQueue = new ArrayList<>();
            for (String curr : queue) {
                if (curr.equals(endWord)) return step + 1;
                for (int i = 0; i < l; i++) {
                    for (String c : candidates) {
                        String next = curr.substring(0, i) + c + curr.substring(i+1);
                        if (wordSet.contains(next) && !visited.contains(next)) {
                            visited.add(next);
                            nextQueue.add(next);
                        }
                    }
                }
            }
            queue = nextQueue;
            step++;
        }
        return 0;
    }

    /********************** Solution 2: TODO: BFS 优化 两端向中间靠拢 ***************************/
    // https://app.gitbook.com/o/-LEzdBqqw8FawHA3mleT/s/-LEzdBqr7mNEEfIbMstL/bfs-dfs/untitled#solution-4-iterative-bfs-jin-yi-bu-you-hua

    public static void main(String[] args) {
        WordLadder solution = new WordLadder();
        System.out.println(solution.ladderLength("hit","cog", Arrays.asList("hot","dot","dog","lot","log","cog")));
        System.out.println(solution.ladderLength("hit","cog", Arrays.asList("hot","dot","dog","lot","log")));
    }
}
