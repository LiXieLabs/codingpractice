import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 336. Palindrome Pairs (https://leetcode.com/problems/palindrome-pairs/)
 */
public class PalindromePairs {

    /***************** Solution 1: TRIE **********************/
    /**
     * Step 1: 每个word正序build TRIE
     *   每个TRIE node需要记录
     *     a. children
     *     b. words passing thru
     *     c. word ending at this node
     * Step 2: 每个word倒序在TRIE中查找
     *   假设 word 是 A，TRIE 中 match 到的是 B，则有三种情况:
     *     case#1. A 和 B 同时结束 (e.g. A="abcd", B="dcba")
     *     case#2. A 结束了，B 还没结束 (e.g. A="a", B="aa")
     *     case#3. A 还没结束，B 结束了 (e.g. A="a", B="")
     *
     * L is length of longest word in words
     * N is number of words
     * Time: O(NL + N*L*L) = O(NL^2)
     * Space: O(N * L letters in total) = O(NL)
     */
    public List<List<Integer>> palindromePairs(String[] words) {
        // iterate over list and build TRIE
        // each TRIE node requires children & words passing thru & word ending at this node
        TrieNode336 root = new TrieNode336();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            TrieNode336 curr = root;
            curr.wordsPass.add(i); // 注意！！！这个也得加！！！
            for (Character c : word.toCharArray()) {
                if (curr.children[c - 'a'] == null) curr.children[c - 'a'] = new TrieNode336();
                curr = curr.children[c - 'a'];
                curr.wordsPass.add(i);
            }
            curr.wordEnd = i;
        }

        // iterate over list and reverse iterate each word
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            TrieNode336 curr = root;
            int j = word.length() - 1;
            while (j >= 0 && curr.children[word.charAt(j) - 'a'] != null) {
                // handle case#3
                // 实际在 match word[j+1:]，而非 word[j:]，主要是为了包含 ["", "a"] 的情况！
                if (curr.wordEnd != -1 && isPalindrome(word, 0, j)) {
                    res.add(Arrays.asList(curr.wordEnd, i));
                }
                curr = curr.children[word.charAt(j--) - 'a'];
            }
            // handle case#3
            // 在 match word[j+1:]，而非 word[j:]，所以出循环后可能还有一次 match！
            if (j >= 0 && curr.wordEnd != -1 && isPalindrome(word, 0, j)) {
                res.add(Arrays.asList(curr.wordEnd, i));
            }
            // handle case#1 & case#2
            if (j < 0 && !curr.wordsPass.isEmpty()) {
                for (int k : curr.wordsPass) {
                    if (k != i && isPalindrome(words[k], word.length(), words[k].length() - 1)) {
                        res.add(Arrays.asList(k, i));
                    }
                }
            }
        }

        return res;

    }

    private boolean isPalindrome(String word, int l, int r) {
        while (l < r) {
            if (word.charAt(l++) != word.charAt(r--)) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        PalindromePairs solution = new PalindromePairs();

        // TC1    [[1, 0], [0, 1]]
        System.out.println(solution.palindromePairs(new String[]{"a",""}));
        // TC2   [[1, 0], [0, 1], [3, 2], [2, 4]]
        System.out.println(solution.palindromePairs(new String[]{"abcd","dcba","lls","s","sssll"}));
        // TC3   [[3, 0], [4, 0], [5, 0], [1, 3], [2, 4], [0, 5]]
        System.out.println(solution.palindromePairs(new String[]{"a","b","c","ab","ac","aa"}));
    }
}

class TrieNode336 {

    TrieNode336[] children;
    List<Integer> wordsPass; // 多个 string 可能有一样的 prefix，所以是 list
    int wordEnd; // 因为 unique string list, 所以 single value

    public TrieNode336() {
        this.children = new TrieNode336[26];
        this.wordsPass = new ArrayList<>();
        this.wordEnd = -1;
    }

}