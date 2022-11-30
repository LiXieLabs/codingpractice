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
            if ((mid == 0 || nums[mid - 1] != nums[mid]) && (mid == nums.length - 1 || nums[mid + 1] != nums[mid])) {
                return nums[mid];
            } else if (mid != 0 && nums[mid - 1] == nums[mid]) {
                // 如果跟前一个相等，根据pattern移动
                if (mid % 2 == 1) {
                    lo = mid + 1;
                } else {
                    hi = mid - 2;
                }
            } else {
                // 如果跟后一个相等，根据pattern移动
                if (mid % 2 == 1) {
                    hi = mid - 1;
                } else {
                    lo = mid + 2;
                }
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
    public int singleNonDuplicate(int[] nums) {
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

    public static void main(String[] args) {
        SingleElementInASortedArray solution = new SingleElementInASortedArray();
        System.out.println(solution.singleNonDuplicate(new int[]{1,1,2,3,3,4,4,8,8}));
        System.out.println(solution.singleNonDuplicate(new int[]{3,3,7,7,10,11,11}));
        System.out.println(solution.singleNonDuplicate(new int[]{1,1,2,2,3}));
        System.out.println(solution.singleNonDuplicate(new int[]{1,2,2,3,3}));
    }
}
