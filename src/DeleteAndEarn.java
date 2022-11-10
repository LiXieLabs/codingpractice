import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class DeleteAndEarn {

    /************ Solution 1: 背包问题，可能为0 ***************/
    /**
     * delete n, 则n全拿，放弃 n-1 和 n+1，实际是背包问题
     * 不存在的 n，可视为背包收益为 0
     *
     * Time: O(N + max - min) === max - min is k ===> O(N + k)
     * Space: O(N) by hashmap
     */
    public int deleteAndEarn1(int[] nums) {
        Map<Integer, Integer> counter = new HashMap<>();
        int min = nums[0], max = nums[0];
        for (int n : nums) {
            counter.put(n, counter.getOrDefault(n, 0) + 1);
            min = Math.min(min, n);
            max = Math.max(max, n);
        }
        int prevprev = 0, prev = 0;
        for (int n = min; n <= max; n++) {
            int temp = prev;
            prev = Math.max(prev, prevprev + n * counter.getOrDefault(n, 0));
            prevprev = temp;
        }
        return prev;
    }

    /************ Solution 2: 稀疏矩阵只遍历不为0的背包 ***************/
    /**
     * 如果和前一个不为 0 的背包相差 1，则需要取舍
     * 不然直接向后累加
     *
     * Time: O(NlogN)    Space: O(N) by hashmap
     */
    public int deleteAndEarn(int[] nums) {
        Map<Integer, Integer> counter = new TreeMap<>();
        for (int n : nums) {
            counter.put(n, counter.getOrDefault(n, 0) + 1);
        }
        int prevprev = 0, prev = 0, prevKey = -1;
        for (Map.Entry<Integer, Integer> entry : counter.entrySet()) {
            int n = entry.getKey(), count = entry.getValue();
            int temp = prev;
            if (prevKey == n - 1) {
                prev = Math.max(prev, prevprev + n * count);
            } else {
                prev += n * count;
            }
            prevKey = n;
            prevprev = temp;
        }
        return prev;
    }

    /**
     * 两种方法可以结合
     * if (max - min < n + n * Math.log(n) / Math.log(2)) => SOLUTION 1
     * else => SOLUTION 2
     *
     * Time Complexity 优化为 O(N + min(k, NlogN))
     */

    public static void main(String[] args) {
        DeleteAndEarn solution = new DeleteAndEarn();
        System.out.println(solution.deleteAndEarn(new int[]{3,4,2})); // 6
        System.out.println(solution.deleteAndEarn(new int[]{2,2,3,3,3,4})); // 9
        System.out.println(solution.deleteAndEarn(new int[]{1,6,3,3,8,4,8,10,1,3})); //43
    }
}