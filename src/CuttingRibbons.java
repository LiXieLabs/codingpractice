/**
 * 1891. Cutting Ribbons (https://leetcode.com/problems/cutting-ribbons/description/)
 */
public class CuttingRibbons {

    /************** Solution 1: Binary Search in 可行解 ****************/
    /**
     * 可行解 [0, max(ribbons)]
     *
     * Time: O(NlogM)
     * N denotes ribbons.length, M denotes max(ribbons)
     * Space: O(1)
     */
    public int maxLength(int[] ribbons, int k) {
        int lo = 0, hi = 0;
        for (int l : ribbons) {
            hi = Math.max(hi, l);
        }
        while (lo < hi) {
            int mid = (lo + hi + 1) >> 1; // lo = mid，向上取整，不然会endless loop
            if (tryCut(ribbons, mid) >= k) {
                lo = mid;
            } else {
                hi = mid - 1;
            }
        }
        return lo;
    }

    private int tryCut(int[] ribbons, int l) {
        int total = 0;
        for (int ribbon : ribbons) {
            total += ribbon / l;
        }
        return total;
    }

    public static void main(String[] args) {
        CuttingRibbons solution = new CuttingRibbons();
        System.out.println(solution.maxLength(new int[]{9,7,5}, 3));
        System.out.println(solution.maxLength(new int[]{9,7,5}, 4));
        System.out.println(solution.maxLength(new int[]{9,7,5}, 22));
    }
}
