import java.util.ArrayList;
import java.util.List;

/**
 * 208. Implement Trie (Prefix Tree)
 * (https://leetcode.com/problems/implement-trie-prefix-tree/description/)
 */
public class ImplementTrie {

    TrieNode root;
    List<String> resultBuffer;
    private static final int SUGGESTION_SIZE = 3;

    public ImplementTrie() {
        root = new TrieNode('0');
    }

    public void insert(String word) {
        TrieNode curr = root;
        for (Character c : word.toCharArray()) {
            if (curr.children[c - 'a'] == null) curr.children[c - 'a'] = new TrieNode(c);
            curr = curr.children[c - 'a'];
        }
        curr.end = true;
    }

    public boolean search(String word) {
        TrieNode curr = root;
        for (Character c : word.toCharArray()) {
            if (curr.children[c - 'a'] == null) return false;
            curr = curr.children[c - 'a'];
        }
        return curr.end;
    }

    public boolean startsWith(String prefix) {
        TrieNode curr = root;
        for (Character c : prefix.toCharArray()) {
            if (curr.children[c - 'a'] == null) return false;
            curr = curr.children[c - 'a'];
        }
        return true;
    }

    public List<String> getWordsStartingWith(String prefix) {
        TrieNode curr = root;
        resultBuffer = new ArrayList<>();
        for (Character c : prefix.toCharArray()) {
            if (curr.children[c - 'a'] == null) return resultBuffer;
            curr = curr.children[c - 'a'];
        }
        dfsWithPrefix(curr, prefix);
        return resultBuffer;
    }

    private void dfsWithPrefix(TrieNode curr, String prefix) {
        if (resultBuffer.size() == SUGGESTION_SIZE) return;
        if (curr.end) resultBuffer.add(prefix);
        for (char c = 'a'; c <= 'z'; c++) {
            if (curr.children[c - 'a'] != null) {
                dfsWithPrefix(curr.children[c - 'a'], prefix + c);
            }
        }
    }

    public static void main(String[] args) {
        ImplementTrie solution = new ImplementTrie();
        solution.insert("apple");
        System.out.println(solution.search("apple"));
        System.out.println(solution.search("app"));
        System.out.println(solution.startsWith("app"));
        solution.insert("app");
        System.out.println(solution.search("app"));
    }
}
