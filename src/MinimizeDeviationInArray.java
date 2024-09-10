import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * 1675. Minimize Deviation in Array (https://leetcode.com/problems/minimize-deviation-in-array/description/)
 */
public class MinimizeDeviationInArray {

    /********************* Solution 1: Greedy + Max-Heap ************************/
    /**
     * 先全部X2变为，加入heap，按照最大值/2，直到最大值为奇数，
     * 最终状态不一定为最优解(Deviation最小)，但是中间一定会经过最优解
     *
     * 对于奇数，比如 3，loop is 3 => 6 => 3 => 6 => 3 => 6 ...
     * 对于偶数，比如 16，loop is 16 => 8 => 4 => 2 => 1 => 2 ...
     *
     * 奇数先 x2，再 /2，就遍历了这个数字所有的可能性
     * 偶数一直 /2 直到奇数，就遍历了这个数字所有的可能性
     *
     * Let N be the length of nums, and M be the largest number in nums.
     * In the worst case when M is the power of 2, there are log(M) possible values for M.
     * Therefore, in the worst case, the total possible candidate number (denoted by K) is K=Nlog(M).
     *
     * Time Complexity: O(KlogN)=O(Nlog(M)log(N)).
     * In worst case, we need to push every candidate number into heap, and the size of heap is O(N).
     * Hence, the total time complexity is O(Klog(N))=O(Nlog(M)log(N)).
     *
     * Space Complexity: O(N), since there are at most N elements in heap.
     */
    public int minimumDeviation1(int[] nums) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((n1, n2) -> n2 - n1);
        int minVal = Integer.MAX_VALUE;
        for (int n : nums) {
            if (n % 2 == 1) n *= 2;
            maxHeap.offer(n);
            minVal = Math.min(minVal, n);
        }
        int minDiv = maxHeap.peek() - minVal;
        while (maxHeap.peek() % 2 == 0) {
            int curMax = maxHeap.poll();
            curMax /= 2;
            minVal = Math.min(minVal, curMax);
            maxHeap.offer(curMax);
            minDiv = Math.min(minDiv, maxHeap.peek() - minVal);
        }
        return minDiv;
    }

    /******** Solution 2: Generate all possible numbers from each number + Solution 2 from 632. Smallest Range Covering Elements from K Lists *******/
    public int minimumDeviation2(int[] nums) {
        List<List<Integer>> input = new ArrayList<>();
        for (int n : nums) {
            List<Integer> possible = new ArrayList<>();
            if (n % 2 == 0) {
                possible.add(n);
                while (n % 2 == 0) {
                    n /= 2;
                    possible.add(n);
                }
            } else {
                possible.add(n * 2);
                possible.add(n);
            }
            Collections.reverse(possible);
            input.add(possible);
        }

        SmallestRangeCoveringElementsFromKLists solution2 = new SmallestRangeCoveringElementsFromKLists();
        int[] res = solution2.smallestRange(input);
        return res[1] - res[0];
    }

    /******** Solution 3: Generate all possible numbers from each number + Solution 1 from 632. Smallest Range Covering Elements from K Lists *******/
    public int minimumDeviation(int[] nums) {
        int k = nums.length;
        List<int[]> sorted = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            int n = nums[i];
            if (nums[i] % 2 == 0) {
                sorted.add(new int[]{n, i});
                while (n % 2 == 0) {
                    n /= 2;
                    sorted.add(new int[]{n, i});
                }
            } else {
                sorted.add(new int[]{n * 2, i});
                sorted.add(new int[]{n, i});
            }
        }
        sorted.sort(Comparator.comparingInt(i -> i[0]));

        // Sliding Window to find min diff window that still can contain numbers from k groups
        int l = 0, r = 0, minDiv = Integer.MAX_VALUE;
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
            if (counter.size() == k && sorted.get(r)[0] - sorted.get(l)[0] < minDiv) {
                minDiv = sorted.get(r)[0] - sorted.get(l)[0];
            }
            // 右推右侧
            r++;
        }
        return minDiv;
    }

    public static void main(String[] args) {
        MinimizeDeviationInArray solution = new MinimizeDeviationInArray();
        System.out.println(solution.minimumDeviation(new int[]{8,1,2,1})); // 0
        System.out.println(solution.minimumDeviation(new int[]{1,2,3,4})); // 1
        System.out.println(solution.minimumDeviation(new int[]{3,5})); // 1
        System.out.println(solution.minimumDeviation(new int[]{4,1,5,20,3})); // 3
        System.out.println(solution.minimumDeviation(new int[]{2,10,8})); // 3
    }
}
