public class SearchInRotatedSortedArray {

    /************* Solution 1: Binary Search and Move based on Pattern ***************/
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
