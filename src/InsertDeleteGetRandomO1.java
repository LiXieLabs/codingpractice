import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * 380. Insert Delete GetRandom O(1) (https://leetcode.com/problems/insert-delete-getrandom-o1/description/)
 */
public class InsertDeleteGetRandomO1 {

    static class RandomizedSet {

        // 需要一个O(1)增删且有顺序的结构,用来Random
        List<Integer> values;
        // 需要一个O(1)增删查的结构
        // ⚠️注意⚠️，主要要解决删除后 idx 变化！把最后一个 element 交换过来，
        // 这样只有这个 last element idx 变化，以确保其他 elements idx 不变！
        Map<Integer, Integer> valToIdx;
        // ⚠️注意⚠️ 需要在class里面，而非constructor里面！！！
        Random rand = new Random();

        public RandomizedSet() {
            valToIdx = new HashMap<>();
            values = new ArrayList<>();
        }

        public boolean insert(int val) {
            if (!valToIdx.containsKey(val)) {
                values.add(val);
                valToIdx.put(val, values.size() - 1);
                return true;
            }
            return false;
        }

        public boolean remove(int val) {
            if (valToIdx.containsKey(val)) {
                int idx = valToIdx.remove(val);
                // 把 last element 换过来！
                // ⚠️注意⚠️如果 idx == values.size() - 1，则要删除的已经是最后一个，也可能只有这一个了，不必交换了！
                if (idx != values.size() - 1) {
                    int last = values.get(values.size() - 1);
                    values.set(idx, last);
                    valToIdx.put(last, idx);
                }
                values.remove(values.size() - 1);
                return true;
            }
            return false;
        }

        public int getRandom() {
            return values.get(rand.nextInt(values.size()));
        }
    }

    // follow-up: allow duplicate
    // solution: hashmap value if set of indices
    static class RandomizedSetAllowDuplicate {

        List<Integer> values;
        Map<Integer, Set<Integer>> valToIndices;
        Random rand = new Random();

        public RandomizedSetAllowDuplicate() {
            values = new ArrayList<>();
            valToIndices = new HashMap<>();
        }

        public boolean insert(int val) {
            boolean present = valToIndices.containsKey(val);
            if (!present) valToIndices.put(val, new HashSet<>());
            // ⚠️注意⚠️ 与原题区别是：无论是否 present，都要 update values & valToIndices！！！
            values.add(val);
            valToIndices.get(val).add(values.size() - 1);
            return !present;
        }

        public boolean remove(int val) {
            if (valToIndices.containsKey(val)) {
                // ⚠️注意⚠️ Set随机取一个的写法!!! set.iterator().next()
                // 但是同一个 set，order 是固定的！！！
                int idx = valToIndices.get(val).iterator().next();
                valToIndices.get(val).remove(idx);
                if (valToIndices.get(val).isEmpty()) valToIndices.remove(val);
                if (idx != values.size() - 1) {
                    int last = values.get(values.size() - 1);
                    values.set(idx, last);
                    valToIndices.get(last).remove(values.size() - 1);
                    valToIndices.get(last).add(idx);
                }
                values.remove(values.size() - 1);
                return true;
            }
            return false;
        }

        public int getRandom() {
            return values.get(rand.nextInt(values.size()));
        }
    }
}
