import java.util.PriorityQueue;

public class LongestHappyString {

    /************ Solution 1: maxHeap每次找出最大和次大的两个计入结果 **************/
    /**
     * 类似 https://leetcode.com/problems/reorganize-string/
     *
     * 每次最多的字母放两个
     * 每次次多的字母放一个或者两个
     *
     * totalRemain 标记剩余要放置的字母的总个数 => 剩余的abc
     * first[1] 标记当前剩余最多的字母的个数 => 剩余的a
     *
     * 假设 a 最多
     * totalRemain => 剩余的abc
     * first[1] => 剩余的a
     * 如果 first[1] >= 2 * (totalRemain - first[1]),
     * 即 剩余的a > 2 * 剩余的bc,
     * 则最多的字母和其他的字母悬殊太大，次多只放置一个，才可以使最   多的字母尽可能多的被隔开放置
     *
     * Time: O(Nlog26)   Space: O(26)
     */
    public String longestDiverseString(int a, int b, int c) {
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((p1, p2) -> (p2[1] - p1[1]));
        if (a != 0) maxHeap.offer(new int[]{0, a});
        if (b != 0) maxHeap.offer(new int[]{1, b});
        if (c != 0) maxHeap.offer(new int[]{2, c});
        int totalRemain = a + b + c;
        StringBuilder sb = new StringBuilder();
        while (!maxHeap.isEmpty() &&
                (sb.length() == 0 || maxHeap.peek()[0] != sb.charAt(sb.length() - 1) - 'a')) {
            int[] first = maxHeap.poll();
            // 注意！！！count的逻辑主要针对TC1 => 如果最多和次多相差过多，次多只能放一个！！！
            int count = first[1] >= 2 * (totalRemain - first[1]) ? 1 : 2;
            for (int i = 0; i < 2 && first[1] > 0; i++, first[1]--, totalRemain--) {
                sb.append((char) ('a' + first[0]));;
            }
            int[] second = null;
            if (!maxHeap.isEmpty()) {
                second = maxHeap.poll();
                for (int i = 0; i < count && second[1] > 0; i++, second[1]--, totalRemain--) {
                    sb.append((char) ('a' + second[0]));
                }
            }
            if (first[1] != 0) maxHeap.offer(first);
            if (second != null && second[1] != 0) maxHeap.offer(second);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        LongestHappyString solution = new LongestHappyString();
        // TC1
        System.out.println(solution.longestDiverseString(0,8,11));
        // TC2
        System.out.println(solution.longestDiverseString(1,1,7));
        // TC3
        System.out.println(solution.longestDiverseString(7,1,0));

    }
}