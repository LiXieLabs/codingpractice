import java.util.ArrayDeque;
import java.util.Deque;

public class TrappingRainWater {

    /****************** Solution 1: Descending Monotonic Stack **********************/
    /**
     * Time: O(N)   Space: O(N)
     */
    public int trap1(int[] height) {
        int res = 0;
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < height.length; i++) {
            // 维护单调栈，把低于当前height的都作为bottom populate出来，并和栈顶&当前height中较小的求面积累加入结果
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
     * lHighest 标记左墙高度，rHighest 标记右墙高度
     * l, r 分别标记当前位置
     * 每次取左右墙中较矮的一个，这样保证两侧不断向中间最高墙推进，左右墙即为短板，决定了水位
     * 如果当前高度比墙矮，则高度差为这一位置的水位
     * 如果当前高度比墙高，则刷新墙高
     *
     * Time: O(N)   Space: O(1)
     */
    public int trap(int[] height) {
        int res = 0;
        int l = 0, r = height.length - 1, lHighest = 0, rHighest = 0;
        while (l <= r) { // 注意！！！要有等号！！！最后汇合处可能也有积水！！！
            if (lHighest < rHighest) {
                if (height[l] < lHighest) {
                    res += lHighest - height[l];
                } else if (height[l] > lHighest) {
                    lHighest = height[l];
                }
                l++;
            } else {
                if (height[r] < rHighest) {
                    res += rHighest - height[r];
                } else if (height[r] > rHighest) {
                    rHighest = height[r];
                }
                r--;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        TrappingRainWater solution = new TrappingRainWater();
        System.out.println(solution.trap(new int[]{0,1,0,2,1,0,1,3,2,1,2,1}));
        System.out.println(solution.trap(new int[]{4,2,0,3,2,5}));
    }

}
