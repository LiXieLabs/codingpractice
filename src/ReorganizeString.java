import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class ReorganizeString {

    /************ Solution 1: maxHeap每次找出最大和次大的两个计入结果 **************/
    /**
     * 类似 https://leetcode.com/problems/longest-happy-string/
     *
     * Time: O(Nlog26)   Space: O(26)
     */
    public String reorganizeString1(String s) {
        Map<Character, Integer> counter = new HashMap<>();
        for (char c : s.toCharArray()) {
            counter.put(c, counter.getOrDefault(c, 0) + 1);
            if (counter.get(c) > (s.length() + 1) / 2) return "";
        }
        PriorityQueue<Character> maxHeap = new PriorityQueue<>((c1, c2) -> counter.get(c2) - counter.get(c1));
        for (char c : counter.keySet()) {
            maxHeap.offer(c);
        }
        StringBuilder res = new StringBuilder();
        while (!maxHeap.isEmpty()) {
            char first = maxHeap.poll();
            res.append(first);
            counter.put(first, counter.get(first) - 1);

            Character second = null;
            if (!maxHeap.isEmpty()) {
                second = maxHeap.poll();
                res.append(second);
                counter.put(second, counter.get(second) - 1);
            }

            // 小心！！！PriorityQueue排序相同情况下，谁先进去谁在顶端！！！
            // 所以要保证出来和进去顺序相同，不然会导致两轮重复字母相邻 => 参见 TC5
            if (counter.get(first) != 0) maxHeap.offer(first);
            if (second != null && counter.get(second) != 0) maxHeap.offer(second);
        }

        return res.toString();
    }

    /************* Solution 2: 先将最多的放置在偶数位上 + 再每隔一个安排剩下的 **************/
    /**
     * Time: O(N)   Space: O(26)
     */
    public String reorganizeString(String s) {
        int[] counter = new int[26];
        int max = 0;
        for (char c : s.toCharArray()) {
            int i = c - 'a';
            counter[i]++;
            // ⚠️注意⚠️ 可以提早结束！！！
            if (counter[i] > (s.length() + 1) / 2) return "";
            if (counter[i] > counter[max]) max = i;
        }
        char[] res = new char[s.length()];
        int i = 0;
        while (counter[max]-- > 0) {
            res[i] = (char) (max + 'a');
            i += 2;
        }
        for (int c = 0; c < counter.length; c++) {
            while (counter[c]-- > 0) {
                if (i >= res.length) i = 1;
                res[i] = (char) (c + 'a');
                i += 2;
            }
        }
        // ⚠️注意⚠️ char[] -> String!!!
        return String.valueOf(res);
    }

    public static void main(String[] args) {
        ReorganizeString solution = new ReorganizeString();
        System.out.println(solution.reorganizeString("aab")); // TC1
        System.out.println(solution.reorganizeString("aaabc")); // TC2
        System.out.println(solution.reorganizeString("aaab")); // TC3
        System.out.println(solution.reorganizeString("aaaac")); // TC4
        System.out.println(solution.reorganizeString("abbabbaaab")); // TC5
    }
}