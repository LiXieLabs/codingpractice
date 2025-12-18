import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 126. Word Ladder II (https://leetcode.com/problems/word-ladder-ii/description/)
 */
public class WordLadderII {

    private static final char[] CANDIDATES = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    /************** Solution 1: Intuitive - BFS build paths - TLE ***********************/
    /**
     * 每层的都带着全部的路径，同一个word，会因为前面 prefix path 不同，多遍历很多遍，
     * 而且不断 copy 导致 TLE !!!
     * 优化方法就是只在结果的时候 copy，只能用 DFS + Backtracking！！！
     *
     * Time: O(N x L x 26^L)   Space: O(NL)
     */
    public List<List<String>> findLadders1(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> res = new ArrayList<>();
        Map<String, Integer> wordMap = new HashMap<>();
        for (int i = 0; i < wordList.size(); i++) {
            wordMap.put(wordList.get(i), i);
        }
        wordMap.put(beginWord, -1);
        if (!wordMap.containsKey(endWord)) return res;
        List<List<Integer>> currLevel = new ArrayList<>();
        currLevel.add(Arrays.asList(-1));
        Set<String> visited = new HashSet<>();
        visited.add(beginWord);
        boolean found = false;
        while (!currLevel.isEmpty() && !found) {
            List<List<Integer>> nextLevel = new ArrayList<>();
            Set<String> nextVisited = new HashSet<>();
            for (List<Integer> words : currLevel) {
                int lastWordIdx = words.get(words.size() - 1);
                String word = lastWordIdx == -1 ? beginWord : wordList.get(lastWordIdx);
                for (int i = 0; i < word.length(); i++) {
                    for (char c : CANDIDATES) {
                        String newWord = word.substring(0, i) + c + word.substring(i + 1);
                        if (endWord.equals(newWord)) found = true;
                        if (wordMap.containsKey(newWord) && !visited.contains(newWord)) {
                            nextVisited.add(newWord);
                            List<Integer> newWords = new ArrayList<>(words);
                            newWords.add(wordMap.get(newWord));
                            nextLevel.add(newWords);
                        }
                    }
                }
            }
            currLevel = nextLevel;
            visited.addAll(nextVisited);
        }
        // build res
        if (found) {
            for (List<Integer> words : currLevel) {
                if (words.get(words.size() - 1).compareTo(wordMap.get(endWord)) == 0) {
                    List<String> cur = new ArrayList<>();
                    for (int i : words) {
                        cur.add(i == -1 ? beginWord : wordList.get(i));
                    }
                    res.add(cur);
                }
            }
        }
        return res;
    }

    /************** Solution 2: BFS build adjacency list + DFS backtracking build result ***********************/
    /**
     * visited 变为每层结束一起更新，主要是为了 a -> b -> d 和 a -> c -> d，
     * 在同一层不同路径到达同一个 word d，adjacency list 关系不会被漏掉
     *
     * Time: O(26NL)  Space: O(NL)
     */
    public List<List<String>> findLadders2(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> res = new ArrayList<>();
        Set<String> wordSet = new HashSet<>(wordList);
        if (!wordSet.contains(endWord)) return res;

        // BFS build adjacency list - parents 必须用 parents 倒着 build，不然 TLE!!!
        List<String> currLevel = new ArrayList<>();
        currLevel.add(beginWord);
        Map<String, List<String>> parents = new HashMap<>();
        Set<String> visited = new HashSet<>();
        visited.add(beginWord);
        boolean found = false;
        while (!currLevel.isEmpty() && !found) {
//            System.out.println("<- new level -> ");
            List<String> nextLevel = new ArrayList<>();
            Set<String> nextVisited = new HashSet<>();
            for (String word : currLevel) {
                for (int i = 0; i < word.length(); i++) { // L
                    for (char c : CANDIDATES) { // 26
                        String newWord = word.substring(0, i) + c + word.substring(i + 1);
                        if (endWord.equals(newWord)) found = true;
                        if (wordSet.contains(newWord) && !visited.contains(newWord)) {
//                            System.out.println(word + " -> " + newWord);
                            parents.putIfAbsent(newWord, new ArrayList<>());
                            parents.get(newWord).add(word);
                            // 小心！！！必须两个都check！！！不然 TC4 会把重复的 child enqueue，导致 TLE!!!
                            // 或者 currLevel && nextLevel 用 HashSet 也行！！！
                            if (!visited.contains(newWord) && !nextVisited.contains(newWord)) {
                                nextLevel.add(newWord);
                            }
                            nextVisited.add(newWord);
                        }
                    }
                }
            }
            currLevel = nextLevel;
            visited.addAll(nextVisited);
        }

        // DFS backtracking build res
        if (found) {
            List<String> path = new ArrayList<>();
            path.add(endWord);
            recur(endWord, beginWord, parents, path, res);
        }
        return res;
    }

    /************** Solution 3: Bi-directional BFS + DFS ***********************/
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> res = new ArrayList<>();
        Set<String> wordSet = new HashSet<>(wordList);
        if (!wordSet.remove(endWord)) return res; // 注意！！！必须 remove beginWord & endWord！！！
        wordSet.remove(beginWord);

        // Bi-directional BFS to build adjacency list - parents
        Map<String, List<String>> parents = new HashMap<>();
        Set<String> beginSet = new HashSet<>(), endSet = new HashSet<>();
        beginSet.add(beginWord);
        endSet.add(endWord);
        boolean found = false, forward = true;
        while (!beginSet.isEmpty() && !endSet.isEmpty() && !found) {
//            System.out.println("<- new level -> ");
            if (beginSet.size() > endSet.size()) {
                Set<String> temp = beginSet;
                beginSet = endSet;
                endSet = temp;
                forward = !forward;
            }
            Set<String> nextSet = new HashSet<>(), visited = new HashSet<>();
            for (String word : beginSet) {
                for (int i = 0; i < word.length(); i++) { // L
                    for (char c : CANDIDATES) { // 26
                        String newWord = word.substring(0, i) + c + word.substring(i + 1);
                        if (endSet.contains(newWord)) {
                            found = true;
                            addEdge(word, newWord, parents, forward);
                        } else if (wordSet.contains(newWord) && !found) {
                            addEdge(word, newWord, parents, forward);
                            if (visited.add(newWord)) {
                                nextSet.add(newWord);
                            }
                        }
                    }
                }
            }
            beginSet = nextSet;
            wordSet.removeAll(visited);
        }
        // DFS backtracking build res
        if (found) {
            List<String> path = new ArrayList<>();
            path.add(endWord);
            recur(endWord, beginWord, parents, path, res);
        }
        return res;
    }

    private void addEdge(String parent, String child, Map<String, List<String>> parents, boolean forward) {
//        if (forward) {
//            System.out.println(parent + " -> " + child);
//        } else {
//            System.out.println(child + " <- " + parent);
//        }
        if (!forward) {
            String temp = parent;
            parent = child;
            child = temp;
        }
        parents.putIfAbsent(child, new ArrayList<>());
        parents.get(child).add(parent);
    }

    private void recur(String word, String beginWord, Map<String, List<String>> parents, List<String> path, List<List<String>> res) {
        if (beginWord.equals(word)) {
            List<String> clonedPath = new ArrayList<>(path);
            Collections.reverse(clonedPath);
            res.add(clonedPath);
            return;
        }
        for (String parent : parents.getOrDefault(word, new ArrayList<>())) {
            path.add(parent);
            // ⚠️注意⚠️不用 check visited 了，因为肯定没有 cycle！！！
            recur(parent, beginWord, parents, path, res);
            path.remove(path.size() - 1);
        }
    }

    public static void main(String[] args) {
        WordLadderII solution = new WordLadderII();
        // TC1 -> [[hit, hot, dot, dog, cog], [hit, hot, lot, log, cog]]
        System.out.println(solution.findLadders("hit", "cog",
                Arrays.asList("hot","dot","dog","lot","log","cog")));

        // TC2 -> [[a, c]]
        System.out.println(solution.findLadders("a", "c",
                Arrays.asList("a", "b", "c")));

        // TC3 -> []
        System.out.println(solution.findLadders("hit", "cog",
                Arrays.asList("hot","dot","dog","lot","log")));

        // TC4 -> [[red, rex, tex, tax], [red, ted, tex, tax], [red, ted, tad, tax]]
        System.out.println(solution.findLadders("red", "tax",
                Arrays.asList("ted","tex","red","tax","tad","den","rex","pee")));
    }

}
