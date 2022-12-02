import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * 1207. Unique Number of Occurrences (https://leetcode.com/problems/unique-number-of-occurrences/description/)
 */
public class UniqueNumberOfOccurrences {

    /*********** Solution 1: HashMap as counter + HashSet from map.values() ***************/
    /**
     * Time: O(N)   Space: O(N)
     */
    public boolean uniqueOccurrences(int[] arr) {
        Map<Integer, Integer> counter = new HashMap<>();
        for (int n : arr) {
            counter.put(n, counter.getOrDefault(n, 0) + 1);
        }
        return new HashSet<>(counter.values()).size() == counter.size();
    }

    public static void main(String[] args) {
        UniqueNumberOfOccurrences solution = new UniqueNumberOfOccurrences();
        System.out.println(solution.uniqueOccurrences(new int[]{1,1,1,2,2,3}));
        System.out.println(solution.uniqueOccurrences(new int[]{1,2,3}));
    }
}