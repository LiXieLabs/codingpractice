/**
 * 33. Search in Rotated Sorted Array (https://leetcode.com/problems/search-in-rotated-sorted-array/description/)
 */
public class SearchInRotatedSortedArray {

    /************* Solution 1: Binary Search and Move based on Pattern ***************/
    /**
     * 比 Solution 2 更好理解
     *
     * Time: O(logN)   Space: O(1)
     */
    public int search1(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] == target) return mid;
            if (nums[mid] < target) {
                if (nums[0] <= nums[mid]) { // mid 处于第一个递增区间，一定向右移动
                    lo = mid + 1;
                } else if (nums[0] <= target) { // mid 处于第二个递增区间，target 处于第一个递增区间，向左移动
                    hi = mid - 1;
                } else { // mid 处于第二个递增区间，target 也处于第二个递增区间，向右移动
                    lo = mid + 1;
                }
            } else {
                if (nums[mid] <= nums[nums.length - 1]) { // mid 处于第二个递增区间，target 也处于第二个递增区间，向左移动
                    hi = mid - 1;
                } else if (target <= nums[nums.length - 1]) { // mid 处于第一个递增区间，target 处于第二个递增区间，向右移动
                    lo = mid + 1;
                } else { // mid 处于第一个递增区间，target 也处于第一个递增区间，向左移动
                    hi = mid - 1;
                }
            }
        }
        return -1;
    }

    /************* Solution 2: Optimized Solution1 - Binary Search and Move based on Pattern ***************/
    /**
     * index    0 1 2 3 4 5 6
     * sample1  4 5 6 7 1 2 3
     * sample2  1 2 3 4 5 6 7
     *
     * nums[mid] 和 target 同时和 nums[0] 比较
     * 如果都比 nums[0] 大，或者 都比 nums[0] 小，则在pivot的同一边，
     * 否则分别在 pivot 两边，同样适用于没有pivot的original array
     *
     * Time: O(logN)   Space: O(1)
     */
    public int search(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            boolean diffSide = (nums[mid] >= nums[0] ^ target >= nums[0]);
            if (nums[mid] < target) {
                if (diffSide) {
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            } else if (nums[mid] > target) {
                if (diffSide) {
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            } else {
                return mid;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        SearchInRotatedSortedArray solution = new SearchInRotatedSortedArray();
        System.out.println(solution.search(new int[]{4,5,6,7,0,1,2}, 0)); // 4
        System.out.println(solution.search(new int[]{4,5,6,7,0,1,2}, 3)); // -1
        System.out.println(solution.search(new int[]{1}, 0)); // -1
        System.out.println(solution.search(new int[]{1,2,3,4,5,6,7}, 3)); // 2
        System.out.println(solution.search(new int[]{3,5,1}, 3)); // 0
    }
}
