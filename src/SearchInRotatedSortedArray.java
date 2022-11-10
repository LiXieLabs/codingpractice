public class SearchInRotatedSortedArray {

    /************************* Solution 1: ************************/
    public int search(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
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
        System.out.println(solution.search(new int[]{4,5,6,7,0,1,2}, 0));
        System.out.println(solution.search(new int[]{4,5,6,7,0,1,2}, 3));
        System.out.println(solution.search(new int[]{1}, 0));
        System.out.println(solution.search(new int[]{1,2,3,4,5,6,7}, 3));
        System.out.println(solution.search(new int[]{3,5,1}, 3));
    }
}
