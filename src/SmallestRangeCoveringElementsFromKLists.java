import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class SmallestRangeCoveringElementsFromKLists {

    /********************* Solution 1: Merge K sorted array by min-heap + Sliding Window *****************/
    /**
     * Time: O(KlogK) + O(NlogK) + O(N)
     * Space: O(N) for sorted array, O(K) for heap and pointers
     */
    public int[] smallestRange1(List<List<Integer>> nums) {
        // Merged K sorted array by K size min-heap
        int k = nums.size();
        int[] pointers = new int[k];
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(Comparator.comparingInt(p -> nums.get(p).get(pointers[p])));
        // O(log1+log2+...+logK) = O(logK!) = O(KlogK)
        for (int i = 0; i < pointers.length; i++) {
            minHeap.offer(i);
        }
        // O(NlogK)
        List<int[]> sorted = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            int p = minHeap.poll();
            sorted.add(new int[]{nums.get(p).get(pointers[p]), p});
            if (pointers[p] + 1 < nums.get(p).size()) {
                pointers[p]++;
                minHeap.offer(p);
            }
        }
        // Sliding Window to find min diff window that still can contain numbers from k groups
        int l = 0, r = 0;
        int[] res = new int[]{sorted.get(0)[0], sorted.get(sorted.size()-1)[0]};
        Map<Integer, Integer> counter = new HashMap<>();
        while (r < sorted.size()) {
            // 更新右侧
            counter.put(sorted.get(r)[1], counter.getOrDefault(sorted.get(r)[1], 0) + 1);
            // 保持numbers cover k groups条件下，尽量将左侧向右推
            while (l < r && counter.get(sorted.get(l)[1]) > 1) {
                counter.put(sorted.get(l)[1], counter.get(sorted.get(l)[1]) - 1);
                l++;
            }
            // 更新最优解
            if (counter.size() == k && sorted.get(r)[0] - sorted.get(l)[0] < res[1] - res[0]) {
                res = new int[]{sorted.get(l)[0], sorted.get(r)[0]};
            }
            // 右推右侧
            r++;
        }
        return res;
    }

    /********* Solution 2: Min-heap keep checking min value until exhausted + Keep tracking of max value *************/
    /**
     * Time: O(KlogK) + O(NlogK)
     * Space: O(K) for heap and pointers
     *
     * maxVal 记录当前排头 k 个 value 中最大的
     * minHeap 顶端是当前排头 k 个 value 中最小的
     */
    public int[] smallestRange(List<List<Integer>> nums) {
        int k = nums.size(), maxVal = Integer.MIN_VALUE;
        int[] pointers = new int[k];
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(Comparator.comparingInt(p -> nums.get(p).get(pointers[p])));
        // O(log1+log2+...+logK) = O(logK!) = O(KlogK)
        for (int i = 0; i < nums.size(); i++) {
            minHeap.offer(i);
            maxVal = Math.max(maxVal, nums.get(i).get(0));
        }
        int[] res = new int[]{0, Integer.MAX_VALUE};
        // O(NlogK)
        while (minHeap.size() == k) {
            int p = minHeap.poll();
            if (maxVal - nums.get(p).get(pointers[p]) < res[1] - res[0]) {
                res = new int[]{nums.get(p).get(pointers[p]), maxVal};
            }
            if (pointers[p] + 1 < nums.get(p).size()) {
                pointers[p]++;
                maxVal = Math.max(maxVal, nums.get(p).get(pointers[p]));
                minHeap.offer(p);
            }
        }
        return res;
    }

    public static void print(int[] input) {
        System.out.println("[" + Arrays.stream(input).mapToObj(String::valueOf).collect(Collectors.joining(",")) + "]");
    }

    public static void main(String[] args) {
        SmallestRangeCoveringElementsFromKLists solution = new SmallestRangeCoveringElementsFromKLists();
        print(solution.smallestRange(
                Arrays.asList(
                        Arrays.asList(4,10,15,24,26),
                        Arrays.asList(0,9,12,20),
                        Arrays.asList(5,18,22,30)
                )));
        print(solution.smallestRange(
                Arrays.asList(
                        Arrays.asList(1,2,3),
                        Arrays.asList(1,2,3),
                        Arrays.asList(1,2,3)
                )));
        print(solution.smallestRange(
                Arrays.asList(
                        Arrays.asList(10, 10),
                        Arrays.asList(11, 11)
                )));
    }

}
