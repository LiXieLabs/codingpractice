import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 212. Word Search II (https://leetcode.com/problems/word-search-ii/description/)
 */
public class WordSearchII {

    private static final int[][] DIREC = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    private char[][] board;
    private int r, c;
    private Set<Integer> visited;

    /************* Solution 1: Call 79. Word Search for each word (TLE) *******************/
    /**
     * Time: O(W * R * C * 3^L) where W is words.length, L is the average length of word in words
     * Space: O(L) by recur call stack
     */
    public List<String> findWords1(char[][] board, String[] words) {
        this.board = board;
        r = board.length;
        c = board[0].length;
        List<String> res = new ArrayList<>();
        for (String word : words) {
            if (find(word)) res.add(word);
        }
        return res;
    }

    private String word;
    private boolean find(String word) {
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (board[i][j] == word.charAt(0)) {
                    visited = new HashSet<>();
                    this.word = word;
                    if (recur(i, j, 1)) return true;
                }
            }
        }
        return false;
    }

    private boolean recur(int i, int j, int idx) {
        if (idx == word.length()) return true;
        visited.add(i * c + j);
        for (int[] d : DIREC) {
            int ni = i + d[0], nj = j + d[1];
            if (0 <= ni && ni < r && 0 <= nj && nj < c && !visited.contains(ni * c + nj) && board[ni][nj] == word.charAt(idx)) {
                if (recur(ni, nj, idx + 1)) return true;
            }
        }
        visited.remove(i * c + j);
        return false;
    }

    /************* Solution 2: Backtracking to search on Trie *******************/
    /**
     * Time: O(R * C * 3^L)
     * Space: O(N)
     * where N is the total number of letters in the words,
     * each word also has a copy in trieNode.word, so total space is 2N
     */
    private List<String> res;
    public List<String> findWords(char[][] board, String[] words) {
        TrieNode212 root = new TrieNode212('#');
        for (String w : words) {
            TrieNode212 curr = root;
            for (char c : w.toCharArray()) {
                if (curr.children[c - 'a'] == null) curr.children[c - 'a'] = new TrieNode212(c);
                curr = curr.children[c - 'a'];
            }
            curr.word = w;
        }

        this.board = board;
        r = board.length;
        c = board[0].length;
        res = new ArrayList<>();
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (root.children[board[i][j] - 'a'] != null) {
                    visited = new HashSet<>();
                    recurFind(i, j, root.children[board[i][j] - 'a']);
                }
            }
        }
        return new ArrayList<>(res);
    }

    private void recurFind(int i, int j, TrieNode212 curr) {
        if (curr.word != null) {
            res.add(curr.word);
            curr.word = null; // Required!!! avoid duplicated founded string to be added in res
        }
        visited.add(i * c + j);
        for (int[] d : DIREC) {
            int ni = i + d[0], nj = j + d[1];
            if (0 <= ni && ni < r && 0 <= nj && nj < c
                    && !visited.contains(ni * c + nj) && curr.children[board[ni][nj] - 'a'] != null) {
                recurFind(ni, nj, curr.children[board[ni][nj] - 'a']);
                // Optimization!!! 对于已经match到的word，recursively prune the branches
                if (Arrays.stream(curr.children[board[ni][nj] - 'a'].children).allMatch(Objects::isNull)) {
                    curr.children[board[ni][nj] - 'a'] = null;
                }
            }
        }
        visited.remove(i * c + j);
    }

    public static void main(String[] args) {
        WordSearchII solution = new WordSearchII();
        System.out.println(solution.findWords(new char[][]{
                {'o','a','a','n'},
                {'e','t','a','e'},
                {'i','h','k','r'},
                {'i','f','l','v'}
        }, new String[]{"oath","pea","eat","rain"}));
    }
}

class TrieNode212 {

    char c;
    TrieNode212[] children;
    String word;

    public TrieNode212(char c) {
        this.c = c;
        this.children = new TrieNode212[26];
        this.word = null;
    }
}
