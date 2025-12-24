/**
 * 162. Find Peak Element (https://leetcode.com/problems/find-peak-element/description/)
 */
public class FindPeakElement {

    /******************* Solution 1: Binary Search Intuitive ***********************/
    /**
     * Time Complexity: O(N)   Space Complexity: O(1)
     */
    public int findPeakElement1(int[] nums) {
        int lo = 0, hi = nums.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            boolean largerThanLeft = strictLarger(mid, mid - 1, nums);
            boolean largerThanRight = strictLarger(mid, mid + 1, nums);
            if (largerThanLeft && largerThanRight) {
                return mid;
            } else if (largerThanLeft) { // 比左边大，向右走！
                lo = mid + 1;
            } else { // 比右边大，向左走！
                hi = mid - 1;
            }
        }
        return lo;
    }

    private boolean strictLarger(int i, int j, int[] nums) {
        return j < 0 || j == nums.length || nums[i] > nums[j];
    }

    /******************* Solution 2: Binary Search Simpler Code ***********************/
    /**
     * 利用了 mid 偏向 lo，且 lo == hi 已经退出循环，
     * 也就是说 mid == nums.length - 1 不可能出现，nums[mid + 1]永远不可能出现！
     *
     * Time Complexity: O(N)   Space Complexity: O(1)
     */
    public int findPeakElement(int[] nums) {
        int lo = 0, hi = nums.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] > nums[mid+1]) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }

    public static void main(String[] args) {
        FindPeakElement solution = new FindPeakElement();
        System.out.println(solution.findPeakElement(new int[]{1,2,3,1})); // 2
        System.out.println(solution.findPeakElement(new int[]{1,2,1,3,5,6,4})); // 5
        System.out.println(solution.findPeakElement(new int[]{5,1,4})); // 2
    }
}
