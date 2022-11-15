import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/*********** Solution: Trie ****************/
/**
 * Solution 1:
 * TrieNode只记录以改点为结尾的sentence和time
 * 每次input，需要从当前prefix节点开始，recur DFS找到所有以当前prefix开始的sentence和time，计入secondary sort的heap
 *
 * Solution 2:
 * TrieNode用map记录所有经过该点，即以该点为prefix的，sentences和times
 * 每次input，只需要将当前prefix节点的map用heap进行secondary sort，节省了recur DFS时间
 * 但是为了维护map，每次input #才能知道新的sentence，需要走一遍添加新sentence
 *
 * Solution 1 vs 2
 * Solution 2 节省了查找时间
 * 但是map需要redundant空间，且句子结束需要添加
 */
public class DesignSearchAutocompleteSystem {

    SearchAutocompleteSystemNode root;
    SearchAutocompleteSystemNode curr;
    StringBuilder prefix;

    public DesignSearchAutocompleteSystem(String[] sentences, int[] times) {
        root = new SearchAutocompleteSystemNode();
        for (int i = 0; i < times.length; i++) {
            /**** only for solution 1 (start) ****/
            SearchAutocompleteSystemNode curr = root;
            for (char c : sentences[i].toCharArray()) {
                if (curr.children[indexOf(c)] == null) {
                    curr.children[indexOf(c)] = new SearchAutocompleteSystemNode();
                }
                curr = curr.children[indexOf(c)];
            }
            curr.times = times[i];
            curr.sentence = sentences[i];
            /**** only for solution 1 (end) ****/

            /**** only for solution 2 (start) ****/
            add(sentences[i], times[i]);
            /**** only for solution 2 (start) ****/
        }
        this.curr = root;
        this.prefix = new StringBuilder();
    }

    public List<String> input(char c) {
        if (c == '#') {
            /**** only for solution 1 (start) ****/
            curr.times++;
            curr.sentence = prefix.toString();
            /**** only for solution 1 (end) ****/

            /**** only for solution 2 (start) ****/
            add(prefix.toString(), 1);
            /**** only for solution 2 (end) ****/

            prefix = new StringBuilder();
            curr = root;
            return new ArrayList<>();
        } else {
            prefix.append(c);
            if (curr.children[indexOf(c)] == null) {
                curr.children[indexOf(c)] = new SearchAutocompleteSystemNode();
                curr = curr.children[indexOf(c)];
                return new ArrayList<>();
            } else {
                curr = curr.children[indexOf(c)];
                /**** only for solution 1 (start) ****/
//                return search1(curr, 3);
                /**** only for solution 1 (end) ****/

                /**** only for solution 2 (start) ****/
                return search2(3);
                /**** only for solution 2 (end) ****/
            }
        }
    }

    /**** only for solution 1 (start) ****/
    Comparator<SearchAutocompleteSystemNode> first = Comparator.comparingInt(n -> n.times);
    Comparator<SearchAutocompleteSystemNode> second = (n1, n2) -> (n2.sentence.compareTo(n1.sentence));
    PriorityQueue<SearchAutocompleteSystemNode> heap = new PriorityQueue<>(first.thenComparing(second));
    private List<String> search1(SearchAutocompleteSystemNode root, int k) {
        List<String> result = new ArrayList<>();
        recur(root, k);
        while (!heap.isEmpty()) {
            result.add(heap.poll().sentence);
        }
        Collections.reverse(result);
        return result;
    }

    private void recur(SearchAutocompleteSystemNode root, int k) {
        if (root.times > 0) {
            heap.offer(root);
            if (heap.size() > k) heap.poll();
        }
        for (SearchAutocompleteSystemNode child : root.children) {
            if (child != null) recur(child, k);
        }
    }
    /**** only for solution 1 (end) ****/

    /**** only for solution 2 (start) ****/
    private void add(String sentence, int time) {
        SearchAutocompleteSystemNode curr = root;
        for (char c : sentence.toCharArray()) {
            if (curr.children[indexOf(c)] == null) {
                curr.children[indexOf(c)] = new SearchAutocompleteSystemNode();
            }
            curr = curr.children[indexOf(c)];
            curr.map.put(sentence, curr.map.getOrDefault(sentence, 0) + time);
        }
    }

    private List<String> search2(int k) {
        PriorityQueue<String> heap = new PriorityQueue<>((s1, s2) ->
                !curr.map.get(s1).equals(curr.map.get(s2)) ?
                        curr.map.get(s1) - curr.map.get(s2) :
                        s2.compareTo(s1));
        for (String s : curr.map.keySet()) {
            heap.offer(s);
            if (heap.size() > k) heap.poll();
        }
        List<String> result = new ArrayList<>();
        while (!heap.isEmpty()) {
            result.add(heap.poll());
        }
        Collections.reverse(result);
        return result;
    }
    /**** only for solution 2 (end) ****/

    private int indexOf(char c) {
        return c == ' ' ? 26 : c - 'a';
    }

    public static void main(String[] args) {
        DesignSearchAutocompleteSystem solution = new DesignSearchAutocompleteSystem(
                new String[]{"i love you", "island", "iroman", "i love leetcode"},
                new int[]{5, 3, 2, 2}
        );
        System.out.println(solution.input('i'));
        System.out.println(solution.input(' '));
        System.out.println(solution.input('a'));
        System.out.println(solution.input('#'));
        System.out.println(solution.input('i'));
        System.out.println(solution.input(' '));
        System.out.println(solution.input('a'));
    }
}

class SearchAutocompleteSystemNode {

    SearchAutocompleteSystemNode[] children;

    // only for solution 1
    int times;
    String sentence;

    // only for solution 2
    Map<String, Integer> map;

    public SearchAutocompleteSystemNode() {
        children = new SearchAutocompleteSystemNode[27];
        // only for solution 1
        times = 0;
        // only for solution 2
        map = new HashMap<>();
    }
}
