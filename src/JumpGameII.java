/**
 * 45. Jump Game II (https://leetcode.com/problems/jump-game-ii/description/)
 */
public class JumpGameII {

    /*********** Solution 1: 1D Greedy Algo (本质是 BFS!!!) **************/
    /**
     * 与 55. Jump Game (https://leetcode.com/problems/jump-game/)
     * 区别是需要steps记录步数
     *
     *  本质是 BFS!!!
     *  比如 [2,3,1,1,4]，root 是 2，可以到达 [3,1]，maxReach 为下一层需要遍历的范围
     *  steps 本质是记录 BFS 层数！！！
     *
     * 类似
     *   1024. Video Stitching (https://leetcode.com/problems/video-stitching/)：
     *   1326. Minimum Number of Taps to Open to Water a Garden (https://leetcode.com/problems/minimum-number-of-taps-to-open-to-water-a-garden/)
     *
     * Time: O(N)   Space: O(1)
     */
    public int jump(int[] nums) {
        int steps = 0, maxReach = 0, i = 0;
        while (maxReach < nums.length - 1) {
            int nexReach = maxReach;
            while (i <= maxReach) {
                nexReach = Math.max(nexReach, i + nums[i++]);
            }
            if (nexReach == maxReach) return -1; // Optional if it is guaranteed to reach the end.
            maxReach = nexReach;
            steps++;
        }
        return steps;
    }

    public static void main(String[] args) {
        JumpGameII solution = new JumpGameII();
        System.out.println(solution.jump(new int[]{2,3,1,1,4})); // 2
        System.out.println(solution.jump(new int[]{2,3,0,1,4})); // 2
        System.out.println(solution.jump(new int[]{2,2,0,0,4})); // -1
    }
}
