import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collector;

public class ArrayOfDoubledPairs {

    // Solution 1: Sort array by abs val (NlogN) + Iterate and find (N)
    public boolean canReorderDoubled(int[] arr) {
//        // Ways of transfer int[] to Integer[] / List<Integer>
//        int[] data = {1,2,3,4,5,6,7,8,9,10};
//        // To boxed array
//        Integer[] what = Arrays.stream(data).boxed().toArray(Integer[]::new);
//        Integer[] ever = IntStream.of(data).boxed().toArray(Integer[]::new);
//        // To boxed list
//        List<Integer> you = Arrays.stream(data).boxed().collect(Collectors.toList());
//        List<Integer> like = IntStream.of(data).boxed().collect(Collectors.toList());
        Integer[] intArr = Arrays.stream(arr).boxed().toArray(Integer[]::new);
//        Arrays.sort(intArr, (n1, n2) -> (Integer.compare(Math.abs(n1), Math.abs(n2))));
        Arrays.sort(intArr, Comparator.comparingInt(Math::abs));
        Map<Integer, Integer> counter = new HashMap<>();
        for (int n : intArr) {
            if (n % 2 == 0 && counter.getOrDefault(n / 2, 0) > 0) {
                counter.put(n / 2, counter.get(n / 2) - 1);
            } else {
                counter.put(n, counter.getOrDefault(n, 0) + 1);
            }
        }
        return counter.values().stream().allMatch(v -> v == 0);
    }

    // Solution 2: Count numbers and sort (NlogK) + Iterate in natural order (K) 【正负数分开处理！！！】
    public boolean canReorderDoubled1(int[] arr) {
        Map<Integer, Integer> counter = new TreeMap<>();
        for (int n : arr) {
            counter.put(n, counter.getOrDefault(n, 0) + 1);
        }
        for (int n : counter.keySet()) { // ⭐️⭐️⭐️ TreeMap keySet是有顺序的 in ascending order ！！！⭐️⭐️⭐️
            if (counter.get(n) == 0) continue;
            int want = n > 0 ? n * 2 : n / 2;
            if (n < 0 && n % 2 != 0 || counter.get(n) > counter.getOrDefault(want, 0)) {
                return false;
            }
            counter.put(want, counter.get(want) - counter.get(n));
        }
        return true;
    }

    public static void main(String[] args) {
        ArrayOfDoubledPairs solution = new ArrayOfDoubledPairs();
        System.out.println(solution.canReorderDoubled1(new int[]{3,1,3,6}));
        System.out.println(solution.canReorderDoubled1(new int[]{2,1,2,6}));
        System.out.println(solution.canReorderDoubled1(new int[]{4,-2,2,-4}));
    }

}
