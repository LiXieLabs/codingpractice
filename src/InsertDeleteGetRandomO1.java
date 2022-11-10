import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class InsertDeleteGetRandomO1 {

    static class RandomizedSet {

        // 需要一个O(1)增删查的结构
        Map<Integer, Integer> map;
        // 需要一个O(1)增删且有顺序的结构,用来Random
        List<Integer> lst;
        // 需要在class里面，而非constructor里面
        Random rand = new Random();

        public RandomizedSet() {
            map = new HashMap<>();
            lst = new ArrayList<>();
        }

        public boolean insert(int val) {
            if (map.containsKey(val)) return false;
            lst.add(val);
            map.put(val, lst.size() - 1);
            return true;
        }

        public boolean remove(int val) {
            if (!map.containsKey(val)) return false;
            int curr = map.remove(val);
            int last = lst.size() - 1;
            lst.set(curr, lst.get(last));
            lst.remove(last);
            // NOTE!!! 必须有if check!!!
            if (last != curr) map.put(lst.get(curr), curr);
            return true;
        }

        public int getRandom() {
            return lst.get(rand.nextInt(lst.size()));
        }
    }

    // follow-up: allow duplicate
    // solution: hashmap value if set of indices
    static class RandomizedSetAllowDuplicate {

        Map<Integer, Set<Integer>> map;
        List<Integer> lst;
        Random rand = new Random();

        public RandomizedSetAllowDuplicate() {
            map = new HashMap<>();
            lst = new ArrayList<>();
        }

        public boolean insert(int val) {
            boolean contain = map.containsKey(val);
            if (!contain) map.put(val, new HashSet<>());
            lst.add(val);
            map.get(val).add(lst.size() - 1);
            return !contain;
        }

        public boolean remove(int val) {
            if (!map.containsKey(val)) return false;
            // NOTE!!! Set随机取一个的写法!!! set.iterator().next()
            int curr = map.get(val).iterator().next();
            map.get(val).remove(curr);
            if (map.get(val).isEmpty()) map.remove(val);
            int last = lst.size() - 1;
            lst.set(curr, lst.get(last));
            lst.remove(last);
            if (last != curr) {
                map.get(lst.get(curr)).remove(last);
                map.get(lst.get(curr)).add(curr);
            }
            return true;
        }

        public int getRandom() {
            return lst.get(rand.nextInt(lst.size()));
        }
    }

}
