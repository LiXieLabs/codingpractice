import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 981. Time Based Key-Value Store (https://leetcode.com/problems/time-based-key-value-store/description/)
 */
public class TimeBasedKeyValueStore {

    /********* Solution 1: Binary Search (Bisect Right) **************/
    /**
     * key : List<[timestamp,value]>
     *
     * M is # of set calls
     * Time: set O(1) if ignore hash key time, get O(logM)
     * Space: O(M) by store map
     */
    Map<String, List<Pair<Integer, String>>> store;

    public TimeBasedKeyValueStore() {
        store = new HashMap<>();
    }

    public void set(String key, String value, int timestamp) {
        store.putIfAbsent(key, new ArrayList<>());
        store.get(key).add(new Pair<>(timestamp, value));
    }

    public String get(String key, int timestamp) {
        if (!store.containsKey(key)) return "";
        List<Pair<Integer, String>> pairs = store.get(key);
        int lo = 0, hi = pairs.size();
        while (lo < hi) {
            int mid = (lo + hi) >> 1;
            if (pairs.get(mid).getKey() <= timestamp) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo == 0 ? "" : pairs.get(lo - 1).getValue();
    }

    /********* Solution 2: TreeMap if timestamp is not input in increasing order **************/
    /**
     * in this question, timestamp in set is guaranteed to be "All the timestamps of set are strictly increasing."
     * if this is removed from assumption, i.e. the timestamp can be input in random order,
     * then we cannot use ArrayList anymore, we need a structure to
     * (1) dynamically keep full sorted order (not only min&max value)
     * (2) support binary search
     * solution is TreeMap<timestamp, value> 👇🏻
     *
     * Time: set O(logM) maintain order in TreeMap, get O(logM)
     * Space: O(M) by store map
     */
    Map<String, TreeMap<Integer, String>> advancedStore;

    public TimeBasedKeyValueStore(boolean useTreeMap) {
        advancedStore = new HashMap<>();
    }

    public void set2(String key, String value, int timestamp) {
        advancedStore.putIfAbsent(key, new TreeMap<>());
        advancedStore.get(key).put(timestamp, value);
    }

    public String get2(String key, int timestamp) {
        if (!advancedStore.containsKey(key)) return "";
        Integer ts = advancedStore.get(key).floorKey(timestamp);
        return ts == null ? "" : advancedStore.get(key).get(ts);
    }

    public static void main(String[] args) {
        // use ArrayList
        TimeBasedKeyValueStore solution1 = new TimeBasedKeyValueStore();
        solution1.set("foo", "bar", 1);
        System.out.println(solution1.get("foo", 1));
        System.out.println(solution1.get("foo", 3));
        solution1.set("foo", "bar2", 4);
        System.out.println(solution1.get("foo", 4));
        System.out.println(solution1.get("foo", 5));

        // use TreeMap
        TimeBasedKeyValueStore solution2 = new TimeBasedKeyValueStore(true);
        solution2.set2("foo", "bar", 1);
        System.out.println(solution2.get2("foo", 1));
        System.out.println(solution2.get2("foo", 3));
        solution2.set2("foo", "bar2", 4);
        System.out.println(solution2.get2("foo", 4));
        System.out.println(solution2.get2("foo", 5));
    }
}
