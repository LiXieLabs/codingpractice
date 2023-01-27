import java.util.HashMap;
import java.util.Map;

/**
 * 697. Degree of an Array (https://leetcode.com/problems/degree-of-an-array/description/)
 */
public class DegreeOfAnArray {

    /*************** Solution 1: 2 Pass ****************/
    /**
     * 第一遍 统计 first/last occur idx & degree
     * 第二遍 对于 occur = degree 的数字, 求最小的 last - first idx
     *
     * Time: O(2N) = O(N)   Space: O(N)
     */
    public int findShortestSubArray1(int[] nums) {
        int degree = 0, distance = nums.length;
        Map<Integer, int[]> counter = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (!counter.containsKey(nums[i])) {
                counter.put(nums[i], new int[]{i, i, 1});
            } else {
                counter.get(nums[i])[1] = i;
                counter.get(nums[i])[2]++;
            }
            degree = Math.max(degree, counter.get(nums[i])[2]);
        }
        for (int n : counter.keySet()) {
            int[] c = counter.get(n);
            if (c[2] == degree) {
                distance = Math.min(distance, c[1] - c[0] + 1);
            }
        }
        return distance;
    }

    /*************** Solution 2: 1 Pass ****************/
    /**
     * 只走一遍
     * 先更新 first/last occur idx & degree
     * 如果 occur > 当前 degree, 更新 degree 和 distance
     * 如果 occur = 当前 degree, 取最小的 distance
     *
     * Time: O(N)   Space: O(N)
     */
    public int findShortestSubArray(int[] nums) {
        int degree = 0, distance = nums.length;
        Map<Integer, int[]> counter = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (!counter.containsKey(nums[i])) {
                counter.put(nums[i], new int[]{i, i, 1});
            } else {
                counter.get(nums[i])[1] = i;
                counter.get(nums[i])[2]++;
            }
            if (counter.get(nums[i])[2] > degree) {
                degree = counter.get(nums[i])[2];
                distance = counter.get(nums[i])[1] - counter.get(nums[i])[0] + 1;
            } else if (counter.get(nums[i])[2] == degree) {
                distance = Math.min(distance, counter.get(nums[i])[1] - counter.get(nums[i])[0] + 1);
            }
        }
        return distance;
    }

    public static void main(String[] args) {
        DegreeOfAnArray solution = new DegreeOfAnArray();
        System.out.println(solution.findShortestSubArray(new int[]{1,2,2,3,1}));
        System.out.println(solution.findShortestSubArray(new int[]{1,2,2,3,1,4,2}));
    }
}
