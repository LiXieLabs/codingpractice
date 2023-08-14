/**
 * 35. Search Insert Position (https://leetcode.com/problems/search-insert-position/description/)
 */
public class SearchInsertPosition {

    /************** Solution 1: Binary Search *******************/
    /**
     * First Occurrence & Bisect 结合的变种
     *
     * Time: O(logN)   Space: O(1)
     */
    public int searchInsert(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1;
        while (lo <= hi) { // 一定要有=号！！！
            int mid = (lo + hi) / 2;
            if (nums[mid] == target) return mid;
            if (nums[mid] < target) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return lo;
    }

    public static void main(String[] args) {
        SearchInsertPosition solution = new SearchInsertPosition();

        System.out.println(solution.searchInsert(new int[]{1,3,5,6}, 5)); // 2
        System.out.println(solution.searchInsert(new int[]{1,3,5,6}, 2)); // 1
        System.out.println(solution.searchInsert(new int[]{1,3,5,6}, 7)); // 4
    }
}
