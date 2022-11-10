public class JumpGameII {

    /*********** Solution 1: 1D Greedy Algo **************/
    /**
     * 与 55. Jump Game (https://leetcode.com/problems/jump-game/)
     * 区别是需要count记录步数
     *
     * 类似
     *   1024. Video Stitching (https://leetcode.com/problems/video-stitching/)：
     *   1326. Minimum Number of Taps to Open to Water a Garden (https://leetcode.com/problems/minimum-number-of-taps-to-open-to-water-a-garden/)
     *
     * Time: O(N)   Space: O(1)
     */
    public int jump(int[] nums) {
        int count = 0, curCanReach = 0, nexCanReach = 0;
        for (int i = 0; curCanReach < nums.length - 1; curCanReach = nexCanReach, count++) {
            while (i <= curCanReach) {
                nexCanReach = Math.max(nexCanReach, i + nums[i++]);
            }
            if (nexCanReach == curCanReach) return -1; // Optional if it is guaranteed to reach the end.
        }
        return count;
    }

    public static void main(String[] args) {
        JumpGameII solution = new JumpGameII();
        System.out.println(solution.jump(new int[]{2,3,1,1,4})); // 2
        System.out.println(solution.jump(new int[]{2,3,0,1,4})); // 2
    }
}
