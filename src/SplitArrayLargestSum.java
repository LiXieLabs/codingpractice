import java.util.Arrays;

class SplitArrayLargestSum {

    // Similar as 1231. Divide Chocolate

    private int[] nums;

    /*********** Solution 1: Binary Search *******************/
    /**
     * N denotes # of subarrays, S denotes sum of sums
     * Time: O(N * log(S))
     * Space: O(1)
     */
    public int splitArrayLargestSum(int[] nums, int m) {
        this.nums = nums;
        int lo = Arrays.stream(nums).max().getAsInt();
        int hi = Arrays.stream(nums).sum();

        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (canDivide(mid, m)) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }

    // i < nums.length  m == 0  => throttle too low => false
    // i == nums.length  m > 0  => throttle too high => true
    // i == nums.length  m == 0 => throttle works => true
    private boolean canDivide(int throttle, int m) {
        int curSum = 0, i = 0;
        while (i < nums.length) {
            if (m == 0) return false;
            if (curSum + nums[i] <= throttle) {
                curSum += nums[i++];
            } else {
                curSum = 0;
                m--;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        SplitArrayLargestSum solution = new SplitArrayLargestSum();
        System.out.println(solution.splitArrayLargestSum(new int[]{7,2,5,10,8}, 2));
        System.out.println(solution.splitArrayLargestSum(new int[]{1,2,3,4,5}, 2));
        System.out.println(solution.splitArrayLargestSum(new int[]{1,4,4}, 3));

    }
}