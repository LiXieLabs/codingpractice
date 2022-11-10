import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class TopKFrequentWords {

    /***** Solution 1: HashMap to build counter + k-size minHeap with secondary sort *****/
    /**
     * Time: O(NlogK)   Space: O(N)`
     */
    public List<String> topKFrequent(String[] words, int k) {
        // Build counter
        Map<String, Integer> counter = new HashMap<>();
        for (String word : words) {
            counter.put(word, counter.getOrDefault(word, 0) + 1);
        }
        // Build k size minHeap with Secondary Sort
        Comparator<String> first = Comparator.comparingInt(counter::get);
        Comparator<String> second = Collections.reverseOrder(String.CASE_INSENSITIVE_ORDER);
        PriorityQueue<String> minHeap = new PriorityQueue<>(first.thenComparing(second));
        for (String word : counter.keySet()) {
            minHeap.offer(word);
            if (minHeap.size() > k) minHeap.poll();
        }
        // Populate result
        List<String> res = new ArrayList<>();
        while (!minHeap.isEmpty()) res.add(minHeap.poll());
        Collections.reverse(res);
        return res;
    }

    public static void main(String[] args) {
        TopKFrequentWords solution = new TopKFrequentWords();
        System.out.println(solution.topKFrequent(new String[]{"i","love","leetcode","i","love","coding"}, 2));
        System.out.println(solution.topKFrequent(new String[]{"the","day","is","sunny","the","the","the","sunny","is","is"}, 4));
    }
}
