/**
 * 33. Search in Rotated Sorted Array (https://leetcode.com/problems/search-in-rotated-sorted-array/description/)
 */
public class SearchInRotatedSortedArray {

    /************* Solution 1: Binary Search and Move based on Pattern ***************/
    /**
     * 比 Solution 2 更好理解
     *    mid < target
     *      start             pivot mid target end -> move to right
     *      start  target     pivot mid        end -> move to left -> condition: mid <= end && target > end
     *      start  mid target pivot            end -> move to right
     *    mid > target
     *      start             pivot target mid end -> move to left
     *      start   mid       pivot target     end -> move to right -> condition: mid > end && target <= end
     *      start target mid  pivot            end -> move to left
     *
     * 注意 condition 里面要有等号！不然 mid 或 target 正好是 end 情况会 fail！
     *
     * Time: O(logN)   Space: O(1)
     */
    public int search1(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1, end = nums[nums.length - 1];
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] == target) return mid;
            if (nums[mid] < target) {
                if (nums[mid] <= end && target > end) {
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            } else {
                if (nums[mid] > end && target <= end) {
                    lo = mid + 1;
                } else {
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
