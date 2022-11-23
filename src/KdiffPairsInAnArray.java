import javafx.util.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class KdiffPairsInAnArray {

    /*************** Solution 1: Two Pointers ******************/
    /**
     *
     *
     * Time: Sort + While Loop = O(NlogN) + O(N) = O(NlogN)
     * Space: O(1)
     */
    public int findPairs1(int[] nums, int k) {
        Arrays.sort(nums);
        int l = 0, r = 1, res = 0;
        while (l < nums.length && r < nums.length) {
            if (l != r && nums[r] - nums[l] == k) {
                res += 1;
                do {
                    l++;
                } while (l < nums.length && nums[l] == nums[l - 1]);
            } else if (nums[r] - nums[l] > k) {
                l++;
            } else { // l == r || nums[r] - nums[l] < k
                r++;
            }
        }
        return res;
    }

    /***************** Solution 2: HashMap *********************/
    /**
     * Similar as
     * 1. Two Sum (https://leetcode.com/problems/two-sum/)
     *
     * 对于 n，再hashmap里找:
     * (1) m - n = k => m = n + k
     * (2) n - m = k => m = n - k  (k == 0, 则跟上一个重复，忽略这项)
     * 结束后，将 n 加入hashmap
     *
     * Time: O(N)
     * Space: O(N) 对于每一个 n，都找到一个 n + k，则有 O(N) 种组合
     */
    public int findPairs2(int[] nums, int k) {
        Set<Pair<Integer, Integer>> res = new HashSet<>();
        Set<Integer> pre = new HashSet<>();
        for (int n : nums) {
            if (pre.contains(n + k)) res.add(new Pair<>(n + k, n));
            if (k != 0 && pre.contains(n - k)) res.add(new Pair<>(n, n - k));
            pre.add(n);
        }
        return res.size();
    }

    /***************** Solution 3: HashMap 优化 *********************/
    /**
     * 先 build counter
     * 遍历 counter 中的 n
     * 累加 res 如果:
     * (1) k != 0 && n + k 也在 counter 里面
     * (2) k == 0 && n 的 count > 1
     *
     * Time: O(N)
     * Space: O(N) by HashMap
     */
    public int findPairs(int[] nums, int k) {
        Map<Integer, Integer> counter = new HashMap<>();
        for (int n : nums) {
            counter.put(n, counter.getOrDefault(n, 0) + 1);
        }
        int res = 0;
        for (Map.Entry<Integer, Integer> entry : counter.entrySet()) {
            int key = entry.getKey(), val = entry.getValue();
            if (k > 0 && counter.containsKey(key + k) || k == 0 && val > 1) {
                res++;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        KdiffPairsInAnArray solution = new KdiffPairsInAnArray();
        System.out.println(solution.findPairs(new int[]{1,1,1,2,2}, 0));
        System.out.println(solution.findPairs(new int[]{3,1,4,1,5}, 2));
        System.out.println(solution.findPairs(new int[]{1,2,3,4,5}, 1));
        System.out.println(solution.findPairs(new int[]{1,3,1,5,4}, 0));
    }
}

