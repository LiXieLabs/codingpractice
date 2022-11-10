import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class FindAndReplaceInString {

    // Indices里面的index是乱序的，方法一：HashMap
    public String findReplaceString(String s, int[] indices, String[] sources, String[] targets) {
        // build map
        Map<Integer, String[]> map = new HashMap<>();
        for (int i = 0; i < indices.length; i++) {
            map.put(indices[i], new String[]{sources[i], targets[i]});
        }

        // iterate over original string and replace
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < s.length()) {
            if (map.containsKey(i)) {
                String source = map.get(i)[0], target = map.get(i)[1];
                if (i + source.length() <= s.length() && source.equals(s.substring(i, i + source.length()))) {
                    sb.append(target);
                    i += source.length();
                    continue;
                }
            }
            sb.append(s.charAt(i));
            i += 1;
        }
        return sb.toString();
    }

    // Indices里面的index是乱序的，方法二：Array + Sort
    public String findReplaceString1(String s, int[] indices, String[] sources, String[] targets) {
        // build array & sort
        int[][] sorted = new int[indices.length][2];
        for (int i = 0; i < indices.length; i++) {
            sorted[i][0] = indices[i];
            sorted[i][1] = i; // 记录原来的i，方便查询sources&targets
        }
        Arrays.sort(sorted, Comparator.comparing(i -> i[0]));

        // iterate over original string and replace
        StringBuilder sb = new StringBuilder();
        int i = 0, j = 0;
        while (i < s.length()) {
            if (j < sorted.length && i == sorted[j][0]) {
                String source = sources[sorted[j][1]], target = targets[sorted[j][1]];
                j += 1;
                if (i + source.length() <= s.length() && source.equals(s.substring(i, i + source.length()))) {
                    sb.append(target);
                    i += source.length();
                    continue;
                }
            }
            sb.append(s.charAt(i));
            i += 1;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        FindAndReplaceInString solution = new FindAndReplaceInString();
        System.out.println(solution.findReplaceString1("abcd", new int[]{0, 2}, new String[]{"a", "cd"}, new String[]{"eee", "ffff"}));
        System.out.println(solution.findReplaceString1("abcd", new int[]{0, 2}, new String[]{"ab", "ec"}, new String[]{"eee", "ffff"}));
    }
}
