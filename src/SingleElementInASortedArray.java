/**
 * 540. Single Element in a Sorted Array (https://leetcode.com/problems/single-element-in-a-sorted-array/description/)
 */
public class SingleElementInASortedArray {

    /************* Solution 1: Binary Search and Move based on Pattern ***************/
    /**
     * 01 23 4 56 78
     * 11 22 3 44 55
     * pattern is:
     * before single number, 成对数字的indices是 even - odd
     * after single number, 成对数字的indices是 odd - even
     *
     * Time: O(logN)   Space: O(1)
     */
    public int singleNonDuplicate1(int[] nums) {
        int lo = 0, hi = nums.length - 1;
        while (lo <= hi) {
            int mid = (lo + hi) >> 1;
            // 如果nums[mid]和前后数字都不相等，找到结果了，返回
            if (mid - 1 >= 0 && nums[mid - 1] == nums[mid]) {
                // 如果跟前一个相等，根据pattern移动
                if (mid % 2 == 1) {
                    lo = mid + 1;
                } else {
                    hi = mid - 2;
                }
            } else if (mid + 1 < nums.length && nums[mid + 1] == nums[mid]) {
                // 如果跟后一个相等，根据pattern移动
                if (mid % 2 == 1) {
                    hi = mid - 1;
                } else {
                    lo = mid + 2;
                }
            } else {
                return nums[mid];
            }
        }
        return -1;
    }

    /************** Solution 2: Binary Search and move according to pattern 优化 ***************/
    /**
     * 与 solution 1 一样，优化只遍历 even indices
     *
     * Time: O(log(N/2)) - O(logN)   Space: O(1)
     */
    public int singleNonDuplicate2(int[] nums) {
        int lo = 0, hi = nums.length - 1;
        while (lo < hi) {
            int mid = (lo + hi) >> 1;
            if (mid % 2 == 1) mid--;
            if (nums[mid] == nums[mid + 1]) { // 不用check边界，因为lo<hi，等于时mid才可能为边界，此时已经跳出while了
                lo = mid + 2;
            } else {
                hi = mid;
            }
        }
        return nums[lo];
    }

    /************** Solution 3: Binary Search and move according to pattern 优化 2 ***************/
    /**
     * 与 solution 1 一样，优化跳过跟 mid 一样的位置！！！
     *
     * Time: O(log(N/2)) - O(logN)   Space: O(1)
     */
    public int singleNonDuplicate(int[] nums) {
        int lo = 0, hi = nums.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int nex = mid == nums.length - 1 ? Integer.MAX_VALUE : nums[mid + 1];
            int pre = mid == 0 ? Integer.MIN_VALUE : nums[mid - 1];
            if (nums[mid] != nex && nums[mid] != pre) return nums[mid];
            if (mid % 2 == 1 && nums[mid] == pre || mid % 2 == 0 && nums[mid] == nex) {
                // (1 - mid % 2) => 偶数位多往后跳一位！
                lo = mid + 1 + (1 - mid % 2);
            } else {
                // (1 - mid % 2) => 偶数位多往前跳一位！
                hi = mid - 1 - (1 - mid % 2);
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        SingleElementInASortedArray solution = new SingleElementInASortedArray();
        System.out.println(solution.singleNonDuplicate(new int[]{1,1,2,3,3,4,4,8,8})); // 2
        System.out.println(solution.singleNonDuplicate(new int[]{3,3,7,7,10,11,11})); // 10
        System.out.println(solution.singleNonDuplicate(new int[]{1,1,2,2,3})); // 3
        System.out.println(solution.singleNonDuplicate(new int[]{1,2,2,3,3})); // 1
    }
}
