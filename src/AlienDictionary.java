import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlienDictionary {

    /********* Solution 1: Topological Sort *************/
    /**
     * Let N be the total number of strings in the input list.
     * Let C be the total length of all the words in the input list, added together.
     * Let U be the total number of unique letters in the alien alphabet -> 26.
     *
     * Time: O(C) + O(V + E) = O(C) + min(U^2, N) = O(C)
     * if U^2 < N, as N < C, => O(C)
     * if U^2 > N, as N < C, => O(C)
     *
     * Space: in + out = O(V + E) = O(U + min(U^2, N)) ===(U is 26)===> O(1)
     */
    public String alienOrder(String[] words) {
        // Build adj list
        Map<Integer, List<Integer>> out = new HashMap<>();
        Map<Integer, Integer> in = new HashMap<>();
        String prev = null;
        for (String curr : words) {
            for (Character c : curr.toCharArray()) {
                out.putIfAbsent(c - 'a', new ArrayList<>());
                in.putIfAbsent(c - 'a', 0);
            }
            if (prev != null) {
                int i = 0;
                while (i < prev.length() && i < curr.length() && prev.charAt(i) == curr.charAt(i)) {
                    i++;
                }
                if (i < prev.length() && i < curr.length()) {
                    int s = prev.charAt(i) - 'a';
                    int e = curr.charAt(i) - 'a';
                    out.get(s).add(e);
                    in.put(e, in.get(e) + 1);
                } else if (i < prev.length()) { // TC4
                    return "";
                }
            }
            prev = curr;
        }

        // Init node with zero inbound edges
        List<Integer> zeroIn = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            if (in.containsKey(i) && in.get(i) == 0) {
                zeroIn.add(i);
            }
        }

        // Populate nodes level by level
        StringBuilder sb = new StringBuilder();
        while (!zeroIn.isEmpty()) {
            List<Integer> nextZeroIn = new ArrayList<>();
            for (int cur : zeroIn) {
                // sb.append((char) (i + 'a'));
                sb.append((char) (cur + 'a'));
                for (int nex : out.get(cur)) {
                    in.put(nex, in.get(nex) - 1);
                    if (in.get(nex) == 0) {
                        nextZeroIn.add(nex);
                    }
                }
            }
            zeroIn = nextZeroIn;
        }
        // TC5
        return sb.length() == in.size() ? sb.toString() : "";
    }

    public static void main(String[] args) {
        AlienDictionary solution = new AlienDictionary();
        // TC1
        System.out.println(solution.alienOrder(new String[]{"wrt","wrf","er","ett","rftt"}));
        // TC2
        System.out.println(solution.alienOrder(new String[]{"z","x"}));
        // TC3
        System.out.println(solution.alienOrder(new String[]{"z","x","z"}));
        // TC4
        System.out.println(solution.alienOrder(new String[]{"abc","ab"}));
        // TC5
        System.out.println(solution.alienOrder(new String[]{"z","x","a","zb","zx"}));
    }
}
