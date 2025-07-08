import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 34. Find First and Last Position of Element in Sorted Array
 * (https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/description/)
 */
public class    FindFirstAndLastPositionOfElementInSortedArray {

    /***************** Solution 1: Bisect Left & Right ************************/
    /**
     * Time: O(logN)   Space: O(1)
     */
    public int[] searchRange1(int[] nums, int target) {
        int l = bisectLeft(nums, target), r = bisectRight(nums, target);
        return l == r ? new int[]{-1, -1} : new int[]{l, r - 1};
    }

    private int bisectLeft(int[] nums, int target) {
        int lo = 0, hi = nums.length;
        while (lo < hi) {
            int mid = (lo + hi) >> 1;
            if (nums[mid] < target) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo;
    }

    private int bisectRight(int[] nums, int target) {
        int lo = 0, hi = nums.length;
        while (lo < hi) {
            int mid = (lo + hi) >> 1;
            if (nums[mid] > target) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }

    /***************** Solution 2: Find First & Last Occurrence ************************/
    /**
     * Time: O(logN)   Space: O(1)
     */
    public int[] searchRange(int[] nums, int target) {
        int l = findFirstOccurrence(nums, target);
        if (l == -1) return new int[]{-1, -1};
        return new int[]{l, findLastOccurrence(nums, target)};
    }

    private int findFirstOccurrence(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1;
        while (lo <= hi) {
            int mid = (lo + hi) >> 1;
            if (nums[mid] == target) {
                if (mid == 0 || nums[mid - 1] != target) return mid;
                hi = mid - 1;
            } else if (nums[mid] < target) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return -1;
    }

    private int findLastOccurrence(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1;
        while (lo <= hi) {
            int mid = (lo + hi) >> 1;
            if (nums[mid] == target) {
                if (mid == nums.length - 1 || nums[mid + 1] != target) return mid;
                lo = mid + 1;
            } else if (nums[mid] < target) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return -1;
    }

    private static void print(int[] input) {
        System.out.println("["
                + Arrays.stream(input).mapToObj(String::valueOf).collect(Collectors.joining(","))
                + "]");
    }

    public static void main(String[] args) {
        FindFirstAndLastPositionOfElementInSortedArray solution = new FindFirstAndLastPositionOfElementInSortedArray();
        print(solution.searchRange(new int[]{5,7,7,8,8,10},8)); // [3,4]
        print(solution.searchRange(new int[]{5,7,7,8,8,10},6)); // [-1,-1]
        print(solution.searchRange(new int[]{},0)); // [-1,-1]
    }
}