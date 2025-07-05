/**
 * 162. Find Peak Element (https://leetcode.com/problems/find-peak-element/description/)
 */
public class FindPeakElement {

    /******************* Solution 1: Binary Search Intuitive ***********************/
    public int findPeakElement1(int[] nums) {
        int lo = 0, hi = nums.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            // if it is peak (比左右都大)
            if ((mid == 0 || nums[mid] > nums[mid-1]) && (mid == nums.length-1 || nums[mid] > nums[mid+1])) return mid;
            if (mid != nums.length-1 && nums[mid] < nums[mid+1]) {
                // 如果右边大，向右走
                lo = mid + 1;
            } else {
                // 如果左边大，向左走
                hi = mid - 1;
            }
        }
        return lo;
    }

    /******************* Solution 2: Binary Search Simpler Code ***********************/
    public int findPeakElement(int[] nums) {
        int lo = 0, hi = nums.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            // if it is peak (比左右都大)
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
