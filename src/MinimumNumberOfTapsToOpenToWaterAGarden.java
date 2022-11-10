public class MinimumNumberOfTapsToOpenToWaterAGarden {

    /*************** Solution 1: 1D Greedy Algo ***************/
    /**
     * 类似
     *   45. Jump Game II (https://leetcode.com/problems/jump-game-ii/)
     *   1024. Video Stitching (https://leetcode.com/problems/video-stitching/)
     * 通用解法参见：https://leetcode.com/problems/minimum-number-of-taps-to-open-to-water-a-garden/solutions/506853/java-a-general-greedy-solution-to-process-similar-problems/
     *
     * 对于每个tap能到达的left和right，首先限制在0~n范围内，其次reach[left]=max(reach[left],right)
     * reach[i]表示从i开始最远能覆盖的距离
     *
     * curCanReach = 0表示起始最远能覆盖到的为0
     * 从i=0开始，在当前能到达的范围curCanReach内增加i，找到能将nexCanReach刷新最远的那一个tap的左侧点
     * 如果nexCanReach没有被刷新，则表明至少curCanReach后面那一段无法被覆盖，返回-1
     * 一旦curCanReach到达n，结束返回tap count
     *
     * Time: O(N)  Space: O(N)
     *
     */
    public int minTaps(int n, int[] ranges) {
        int[] reach = new int[n + 1];
        for (int i = 0; i < ranges.length; i++) {
            if (ranges[i] == 0) continue; // optional & optimization
            int left = Math.max(0, i - ranges[i]);
            int right = Math.min(n, i + ranges[i]);
            reach[left] = Math.max(reach[left], right);
        }
        int curCanReach = 0, nexCanReach = 0, count = 0;
        for (int i = 0; curCanReach < n; curCanReach = nexCanReach, count++) {
            while (i <= curCanReach) {
                nexCanReach = Math.max(nexCanReach, reach[i++]);
            }
            if (nexCanReach == curCanReach) return -1;
        }
        return count;
    }

    public static void main(String[] args) {
        MinimumNumberOfTapsToOpenToWaterAGarden solution = new MinimumNumberOfTapsToOpenToWaterAGarden();
        System.out.println(solution.minTaps(5, new int[]{3,4,1,1,0,0})); // 1
        System.out.println(solution.minTaps(3, new int[]{0,0,0,0})); // -1
        System.out.println(solution.minTaps(3, new int[]{0,1,0,0})); // -1

    }
}