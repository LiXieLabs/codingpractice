public class BinarySearch {

    /*************** Solution 1: Binary Search **************/
    public int search(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            if (nums[mid] < target) {
                lo = mid + 1;
            } else if (nums[mid] > target) {
                hi = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        BinarySearch solution = new BinarySearch();
        System.out.println(solution.search(new int[]{-1,0,3,5,9,12}, 9));
        System.out.println(solution.search(new int[]{-1,0,3,5,9,12}, 2));
    }
}
