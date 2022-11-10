import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class NumberOfMatchingSubsequences {

    // HashMap + Deque to Check All Words in Parallel
    public int numMatchingSubseq(String s, String[] words) {
        if (s == null || s.length() == 0 || words == null || words.length == 0) return 0;
        Map<Character, Deque<int[]>> waitingLst = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            waitingLst.putIfAbsent(words[i].charAt(0), new ArrayDeque<>());
            waitingLst.get(words[i].charAt(0)).add(new int[]{i, 0});
        }
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            if (!waitingLst.containsKey(s.charAt(i))) continue;
            int total = waitingLst.get(s.charAt(i)).size();
            for (int j = 0; j < total; j++) {
                int[] cur = waitingLst.get(s.charAt(i)).poll();
                if (cur[1] + 1 == words[cur[0]].length()) {
                    res += 1;
                } else {
                    Character nextChar = words[cur[0]].charAt(cur[1]+1);
                    waitingLst.putIfAbsent(nextChar, new ArrayDeque<>());
                    waitingLst.get(nextChar).add(new int[]{cur[0], cur[1]+1});
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        NumberOfMatchingSubsequences solution = new NumberOfMatchingSubsequences();
        System.out.println(solution.numMatchingSubseq("abcde", new String[]{"a","bb","acd","ace"}));
        System.out.println(solution.numMatchingSubseq("dsahjpjauf", new String[]{"ahjpjau","ja","ahbwzgqnuk","tnmlanowax"}));
    }
}
