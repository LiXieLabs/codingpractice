public class TrieNode {

    char c;
    TrieNode[] children;
    boolean end;

    public TrieNode(char c) {
        this.c = c;
        this.children = new TrieNode[26];
        this.end = false;
    }
}
