import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FindOriginalArrayFromDoubledArray {

    public int[] findOriginalArray(int[] changed) {
        if (changed.length % 2 != 0) return new int[]{}; // return new int[0]; 也可以

        List<Integer> result = new ArrayList<>();
        Arrays.sort(changed);
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : changed) {
            if (n % 2 == 0 && map.getOrDefault(n / 2, 0) > 0) {
                map.put(n / 2, map.get(n / 2) - 1);
                result.add(n / 2);
            } else {
                map.put(n, map.getOrDefault(n, 0) + 1);
            }
        }
        return result.size() == changed.length / 2 ? result.stream().mapToInt(i->i).toArray() : new int[]{};
    }

    public void printIntArray(int[] array) {
        String s = "[" + Arrays.stream(array).mapToObj(String::valueOf).collect(Collectors.joining(",")) + "]";
        System.out.println(s);

    }

    public static void main(String[] args) {
        FindOriginalArrayFromDoubledArray solution = new FindOriginalArrayFromDoubledArray();
        solution.printIntArray(solution.findOriginalArray(new int[]{1, 3, 4, 2, 6, 8}));
        solution.printIntArray(solution.findOriginalArray(new int[]{0, 0}));
        solution.printIntArray(solution.findOriginalArray(new int[]{6, 3, 0, 1}));
        solution.printIntArray(solution.findOriginalArray(new int[]{1}));
    }
}
