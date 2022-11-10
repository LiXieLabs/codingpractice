import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SwapAdjacentInLRString {

    /***************** Solution 1: Recursive DFS *********************/
    public boolean canTransform(String start, String end) {
        if (count(start, 'R') != count(end, 'R')) return false;
        if (count(start, 'L') != count(end, 'L')) return false;

        return search(start, end, new HashSet<String>());
    }

    public boolean search(String curr, String target, Set<String> visited) {
        if (curr.equals(target)) return true;
        if (visited.contains(curr)) return false;
        for (int i = 0; i < curr.length() - 1; i++) {
            if (curr.substring(i, i+2).equals("XL")) {
                String next = curr.substring(0, i) + "LX" + curr.substring(i+2);
                if (search(next, target, visited)) return true;
            }
            if (curr.substring(i, i+2).equals("RX")) {
                String next = curr.substring(0, i) + "XR" + curr.substring(i+2);
                if (search(next, target, visited)) return true;
            }
        }
        visited.add(curr);
        return false;
    }

    public int count(String s, Character c) {
        int res = 0;
        for (Character chr : s.toCharArray()) {
            if (chr.equals(c)) res++;
        }
        return res;
    }

    /****************** Solution 2: 参见同名.png文件 SwapAdjacentInLRString.png ********************/
    public boolean canTransform2(String start, String end) {
        String startRemoveX = start.replace("X", ""), endRemoveX = end.replace("X", "");
        if (!startRemoveX.equals(endRemoveX)) return false;
        List<Integer> startIndices = getIndices(start), endIndices = getIndices(end);
        for (int i = 0; i < startRemoveX.length(); i++) {
            if (startRemoveX.charAt(i) == 'L' && startIndices.get(i) < endIndices.get(i)) return false;
            if (startRemoveX.charAt(i) == 'R' && startIndices.get(i) > endIndices.get(i)) return false;
        }
        return true;
    }

    public List<Integer> getIndices(String s) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != 'X') res.add(i);
        }
        return res;
    }

    public static void main(String[] args) {
        SwapAdjacentInLRString solution = new SwapAdjacentInLRString();
        System.out.println(solution.canTransform2("RXXLRXRXL", "XRLXXRRLX"));
        System.out.println(solution.canTransform2("X", "L"));
        System.out.println(solution.canTransform2("LXXLXRLXXL", "XLLXRXLXLX"));
    }
}
