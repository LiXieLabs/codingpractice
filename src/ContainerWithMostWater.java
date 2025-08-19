/**
 *  11. Container With Most Water (https://leetcode.com/problems/container-with-most-water/description/)
 */
public class ContainerWithMostWater {

    /**
     * 类似的问题
     * 11. Container With Most Water (https://leetcode.com/problems/container-with-most-water/description/)
     * 42. Trapping Rain Water (https://leetcode.com/problems/trapping-rain-water/description/)
     * 84. Largest Rectangle in Histogram (https://leetcode.com/problems/largest-rectangle-in-histogram/description/)
     */

    /************** Solution 1: Two Pointers ***************/
    /**
     * 两个 pointers 向中间移动，移动较矮的那个，
     * 因为矮的那个更有潜力变高，使得最终值更大
     *
     * Time: O(N)   Space: O(1)
     */
    public int maxArea(int[] height) {
        int l = 0, r = height.length - 1, res = 0;
        while (l < r) {
            int lh = height[l], rh = height[r];
            if (lh < rh) {
                res = Math.max(res, lh * (r - l++));
            } else {
                res = Math.max(res, rh * (r-- - l));
            }
        }
        return res;
    }

    public static void main(String[] args) {
        ContainerWithMostWater solution = new ContainerWithMostWater();
        System.out.println(solution.maxArea(new int[]{1,8,6,2,5,4,8,3,7}));
        System.out.println(solution.maxArea(new int[]{1,1}));
    }
}
