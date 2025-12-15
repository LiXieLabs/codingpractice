import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 42. Trapping Rain Water (https://leetcode.com/problems/trapping-rain-water/description/)
 */
public class TrappingRainWater {

    /**
     * 类似的问题
     * 11. Container With Most Water (https://leetcode.com/problems/container-with-most-water/description/)
     * 42. Trapping Rain Water (https://leetcode.com/problems/trapping-rain-water/description/)
     * 84. Largest Rectangle in Histogram (https://leetcode.com/problems/largest-rectangle-in-histogram/description/)
     */

    /****************** Solution 1: Descending Monotonic Stack **********************/
    /**
     * Time: O(N)   Space: O(N)
     *
     * 累加横向积水
     */
    public int trap1(int[] height) {
        int res = 0;
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < height.length; i++) {
            // 维护单调递增栈，把低于当前height的都作为bottom populate出来，并和栈顶&当前height中较小的求面积累加入结果
            while (!stack.isEmpty() && height[stack.peek()] <= height[i]) {
                int bottom = height[stack.pop()];
                if (!stack.isEmpty()) {
                    // rectangle area = width * height
                    res += (i - stack.peek() - 1) * (Math.min(height[stack.peek()], height[i]) - bottom);
                }
            }
            stack.push(i);
        }
        return res;
    }

    /************************ Solution 2: Two Pointers **************************/
    /**
     * lh 标记左墙最高高度，rh 标记右墙最高高度
     * l, r 分别标记当前位置
     * 每次取左右位置中较矮的一个，这样保证两侧不断向中间最高墙推进，左右墙即为短板，决定了水位
     * 如果当前高度比墙矮，则高度差为这一位置的水位
     * 如果当前高度比墙高，则刷新墙高
     *
     * Time: O(N)   Space: O(1)
     *
     * 累加纵向积水
     */
    public int trap(int[] height) {
        // lh = left highest wall, l = current left wall, r = current right wall, rh = right highest wall
        // (l, h) move the lower one to middle
        // before move, if it's equals or higher than the corresponding highest wall (lh, rh), no water trapped vertically with it as bottom, then we just need to reset the (lh, rh).
        // if it's lower than the highest wall, water trapped veritically with it as bottom, and amount is the height difference.
        int lh = 0, l = 0, r = height.length - 1, rh = height.length - 1, res = 0;
        while (l <= r) {
            if (height[l] < height[r]) {
                if (height[l] < height[lh]) {
                    res += height[lh] - height[l];
                } else {
                    lh = l;
                }
                l++;
            } else {
                if (height[r] < height[rh]) {
                    res += height[rh] - height[r];
                } else {
                    rh = r;
                }
                r--;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        TrappingRainWater solution = new TrappingRainWater();
        System.out.println(solution.trap(new int[]{0,1,0,2,1,0,1,3,2,1,2,1})); // 6
        System.out.println(solution.trap(new int[]{4,2,0,3,2,5})); // 9
    }

}
