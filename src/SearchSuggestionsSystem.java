import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

public class SearchSuggestionsSystem {

    /************** Solution 1: 每个TrieNode有一个size=3的suggestion TreeSet 存储以该点为prefix的最小三个String *******************/
    public List<List<String>> suggestedProducts1(String[] products, String searchWord) {
        // build trie - Time: O(M*log3) = O(M) where M is total number of char in products
        SearchNode root = new SearchNode('0');
        for (String product : products) {
            SearchNode curr = root;
            for (char c : product.toCharArray()) {
                if (curr.children[c - 'a'] == null) curr.children[c - 'a'] = new SearchNode(c);
                curr = curr.children[c - 'a'];
                curr.addSuggestion(product);
            }
        }

        // search in trie - Time: O(N) where N is searchWord's length
        List<List<String>> res = new ArrayList<>();
        SearchNode curr = root;
        for (char c : searchWord.toCharArray()) {
            if (curr != null) curr = curr.children[c - 'a'];
            res.add(curr != null ? new ArrayList<>(curr.suggestion) : new ArrayList<>());
        }
        return res;
    }

    /************* Solution 2: 按照Trie存储，dfs lexicographically from prefix *******************/
    public List<List<String>> suggestedProducts2(String[] products, String searchWord) {
        // build trie - Time: O(M) where M is total number of char in products
        ImplementTrie trie = new ImplementTrie();
        for (String product : products) {
            trie.insert(product);
        }

        // search in trie - O(len(prefix)) + dfs to find at most 3 words = O(N) + O(1) = O(N)
        List<List<String>> res = new ArrayList<>();
        StringBuilder prefix = new StringBuilder();
        for (char c : searchWord.toCharArray()) {
            prefix.append(c);
            res.add(trie.getWordsStartingWith(prefix.toString()));
        }
        return res;
    }

    /******************** Solution 3: Binary Search Prefix ***********************/
    /**
     * Time: Sort + N * Query = O(NlogN) + O(MlogN)
     * where N is number of products, M is length of searchWord
     *
     * Space: O(N)
     */
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        // sort products, 再将 product:index 放入treemap
        List<List<String>> res = new ArrayList<>();
        TreeMap<String, Integer> map = new TreeMap<>();
        Arrays.sort(products);
        List<String> productsList = Arrays.asList(products);
        for (int i = 0; i < products.length; i++) {
            map.put(products[i], i);
        }

        // 对于每个prefix，找到对应的index range，取sublist计入结果
        String key = "";
        for (char c : searchWord.toCharArray()) {
            key += c;
            String ceiling = map.ceilingKey(key); // ceiling >= prefix
            String floor = map.floorKey(key + "~"); // floor <= prefix
            if (ceiling == null || floor == null) break;
            res.add(productsList.subList(map.get(ceiling), Math.min(map.get(ceiling) + 3, map.get(floor) + 1)));
        }
        // 补齐后面match不到的
        while (res.size() < searchWord.length()) res.add(new ArrayList<>());
        return res;
    }

    public static void main(String[] args) {
        SearchSuggestionsSystem solution = new SearchSuggestionsSystem();
        System.out.println(solution.suggestedProducts(new String[]{"mobile","mouse","moneypot","monitor","mousepad"}, "mouse"));
        System.out.println(solution.suggestedProducts(new String[]{"mobile","mouse","moneypot","monitor","mousepad"}, "abc"));
        System.out.println(solution.suggestedProducts(new String[]{"havana"}, "havana"));
    }
}

class SearchNode {

//    public char c;
    public SearchNode[] children;
    public TreeSet<String> suggestion;

    public SearchNode(char c) {
//        this.c = c;
        children = new SearchNode[26];
        suggestion = new TreeSet<>();
    }

    public void addSuggestion(String product) {
        suggestion.add(product);
        if (suggestion.size() > 3) {
            suggestion.pollLast();
        }
    }
}
