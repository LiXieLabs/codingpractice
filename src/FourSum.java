import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 18. 4Sum (https://leetcode.com/problems/4sum/)
 */
public class FourSum {

    /************ Solution 1: Sort + 3Sum by 2 Pointers **************/
    /**
     * Based on 3Sum solution 1
     * <p>
     * Time: O(NlogN) + O(N^3) = O(N^3)
     * Space: O(N)
     */
    public List<List<Integer>> fourSum1(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < nums.length - 3; i++) {
            if (i != 0 && nums[i] == nums[i - 1]) continue;
            for (int j = i + 1; j < nums.length - 2; j++) {
                if (j != i + 1 && nums[j] == nums[j - 1]) continue;
                // 小心！！！用int的话会导致 TC3 res 不为空！！！
                long twoSum = (long) target - nums[i] - nums[j];
                int l = j + 1, r = nums.length - 1;
                while (l < r) {
                    if (nums[l] + nums[r] <= twoSum) {
                        if (nums[l] + nums[r] == twoSum) {
                            res.add(Arrays.asList(nums[i], nums[j], nums[l], nums[r]));
                        }
                        do {
                            l++;
                        } while (l < r && nums[l] == nums[l - 1]);
                    } else {
                        do {
                            r--;
                        } while (l < r && nums[r] == nums[r + 1]);
                    }
                }
            }

        }
        return res;
    }

    /************ Solution 2: 3Sum by HashSet + Sort **************/
    /**
     * Based on 3Sum solution 2
     * <p>
     * Time: O(NlogN) + O(N^3) = O(N^3)
     * Space: O(N)
     */
    public List<List<Integer>> fourSum2(int[] nums, int target) {
        Set<List<Integer>> res = new HashSet<>();
        Set<Integer> seen1stNum = new HashSet<>();
        for (int i = 0; i < nums.length - 3; i++) {
            if (!seen1stNum.add(nums[i])) continue;
            Set<Integer> seen2ndNum = new HashSet<>();
            for (int j = i + 1; j < nums.length - 2; j++) {
                if (!seen2ndNum.add(nums[j])) continue;
                Set<Long> seenRemain = new HashSet<>();
                for (int k = j + 1; k < nums.length; k++) {
                    // 小心！！！用int的话会导致 TC3 res 不为空！！！
                    long remain = (long) target - nums[i] - nums[j] - nums[k];
                    if (seenRemain.contains(remain)) {
                        List<Integer> cur = Arrays.asList(nums[i], nums[j], nums[k], (int) remain);
                        Collections.sort(cur);
                        res.add(cur);
                    } else {
                        seenRemain.add((long) nums[k]);
                    }
                }
            }
        }
        return new ArrayList<>(res);
    }

    /************ Solution 3: Generalize Solution 1 & 2 to kSum **************/
    /**
     * Time: O(N^(k-1)) = O(N^3)
     * Space: O(k) by recur stack, worst case O(N)
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        return kSum(nums, target, 4, 0);
    }

    // long target - or else TC3 fail
    private List<List<Integer>> kSum(int[] nums, long target, int k, int start) {
        List<List<Integer>> res = new ArrayList<>();
        if (k == 2) {
            return twoSumBy2Pointers(nums, target, start);
//            return twoSumByHashSet(nums, target, start);
        }
        for (int i = start; i < nums.length - k + 1; i++) {
            if (i != start && nums[i] == nums[i - 1]) continue;
            for (List<Integer> lst : kSum(nums, target - nums[i], k - 1, i + 1)) {
                // 注意必须 new ArrayList<>() => 不然 abstractList Error when adding all
                res.add(new ArrayList<>(Arrays.asList(nums[i])));
                res.get(res.size() - 1).addAll(lst);
            }
        }
        return res;
    }

    // long target - or else TC3 fail
    private List<List<Integer>> twoSumBy2Pointers(int[] nums, long target, int start) {
        List<List<Integer>> res = new ArrayList<>();
        int l = start, r = nums.length - 1;
        while (l < r) {
            if (nums[l] + nums[r] <= target) {
                if (nums[l] + nums[r] == target) {
                    res.add(Arrays.asList(nums[l], nums[r]));
                }
                do {
                    l++;
                } while (l < r && nums[l] == nums[l - 1]);
            } else {
                do {
                    r--;
                } while (l < r && nums[r] == nums[r + 1]);
            }
        }
        return res;
    }

    // long target - or else TC3 fail
    private List<List<Integer>> twoSumByHashSet(int[] nums, long target, int start) {
        Set<List<Integer>> res = new HashSet<>();
        Set<Long> seenRemain = new HashSet<>();
        for (int i = start; i < nums.length; i++) {
            long remain = target - nums[i];
            if (seenRemain.contains(remain)) {
                res.add(Arrays.asList((int) remain, nums[i]));
            } else {
                seenRemain.add((long) nums[i]);
            }
        }
        return new ArrayList<>(res);
    }

    public static void main(String[] args) {
        FourSum solution = new FourSum();

        // TC1
        System.out.println(solution.fourSum(new int[]{1,0,-1,0,-2,2}, 0));
        // [[-2, -1, 1, 2], [-2, 0, 0, 2], [-1, 0, 0, 1]]

        // TC2
        System.out.println(solution.fourSum(new int[]{2,2,2,2,2}, 8));
        // [[2, 2, 2, 2]]

        // TC3 - 小心！！！用int的话会导致res不为空！！！
        System.out.println(solution.fourSum(new int[]{1000000000,1000000000,1000000000,1000000000}, -294967296));
        // []
    }
}
