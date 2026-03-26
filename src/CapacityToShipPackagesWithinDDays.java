/**
 * 1011. Capacity To Ship Packages Within D Days (https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/description/)
 */
public class CapacityToShipPackagesWithinDDays {

    /****************** Solution 1: Binary Search in Possible Results *****************/
    /**
     * 类似:
     * 410. Split Array Largest Sum (https://leetcode.com/problems/split-array-largest-sum/)
     * 378. Kth Smallest Element in a Sorted Matrix (https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/)
     * 875. Koko Eating Bananas (https://leetcode.com/problems/koko-eating-bananas/description/)
     * 878. Nth Magical Number (https://leetcode.com/problems/nth-magical-number/)
     * 1011. Capacity To Ship Packages Within D Days (https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/description/)
     * 1231. Divide Chocolate (https://leetcode.com/problems/divide-chocolate/)
     *
     * 将 weights 切 days - 1 刀，使得 subarray sum 的最大值最小
     * 即尽量平均的切割 days - 1 刀
     *
     * 在可行解中 binary search
     * initial lo = max(weights), hi = sum(weights)
     *
     * Time: O(N * logS)
     * where N is weights length, S is sum of weights, i.e. result space size.
     *
     * Space: O(1)
     *
     */
    public int shipWithinDays(int[] weights, int days) {
        int total = 0, max = 0;
        for (int w : weights) {
            total += w;
            max = Math.max(max, w);
        }
        int lo = max, hi = total;
        if (days > weights.length) return lo;
        if (days == 1) return total;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            int curDays = ship(weights, mid);
            if (curDays > days) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo;
    }

    private int ship(int[] weights, int capacity) {
        int days = 1, total = 0;
        for (int w : weights) {
            if (total + w > capacity) {
                days++;
                total = 0;
            }
            total += w;
        }
        return days;
    }

    public static void main(String[] args) {
        CapacityToShipPackagesWithinDDays solution = new CapacityToShipPackagesWithinDDays();
        System.out.println(solution.shipWithinDays(new int[]{1,2,3,4,5,6,7,8,9,10}, 5)); // 15
        System.out.println(solution.shipWithinDays(new int[]{3,2,2,4,1,4}, 3)); // 6
        System.out.println(solution.shipWithinDays(new int[]{1,2,3,1,1}, 4)); // 3
    }
}
