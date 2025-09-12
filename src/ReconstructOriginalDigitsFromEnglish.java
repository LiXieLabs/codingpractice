import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 423. Reconstruct Original Digits from English (https://leetcode.com/problems/reconstruct-original-digits-from-english/description/)
 */
public class ReconstructOriginalDigitsFromEnglish {


    private static final char[] FIND = new char[]{'z', 'x', 's', 'v', 'f', 'r', 'w', 'o', 't', 'e'};
    private static final char[][] REMOVE = new char[][]{{'e', 'r', 'o'}, {'s', 'i'}, {'e', 'v', 'e', 'n'}, {'f', 'i', 'e'}, {'o', 'u', 'r'}, {'t', 'h', 'e', 'e'}, {'t', 'o'}, {'n', 'e'}, {'e', 'i', 'g', 'h'}, {'n', 'i', 'n'}};
    private static final int[] DIGIT = new int[]{0, 6, 7, 5, 4, 3, 2, 1, 8, 9};

    /**************** Solution 1: 按一定顺序，用 unique char 确定 digit 个数，减去其他 char **************/
    /**
     * Time: O(N + 10 + NlogN) = O(NlogN)   Space: O(1)
     */
    public String originalDigits1(String s) {
        Map<Character, Integer> counter = new HashMap<>();
        for (char c : s.toCharArray()) {
            counter.put(c, counter.getOrDefault(c, 0) + 1);
        }

        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            // 该 digit 的 unique char 有几个
            int count = counter.getOrDefault(FIND[i], 0);
            if (count == 0) continue;
            // 就在结果里加几个该 digit
            for (int n = count; n > 0; n--) {
                res.add(DIGIT[i]);
            }
            // unique char 减去相应数量
            counter.put(FIND[i], counter.get(FIND[i]) - count);
            // 该 digit 的其他 char 减去相应数量
            for (char remove : REMOVE[i]) {
                counter.put(remove, counter.get(remove) - count);
            }
        }
        Collections.sort(res);
        StringBuilder sb = new StringBuilder();
        for (int n : res) {
            sb.append(n);
        }
        return sb.toString();
    }

    /**************** Solution 2: Solution 1 的优化 **************/
    /**
     * 优化 1:
     * 实际上不用更新 counter！！！
     * 只需要把其他已知数量的 digit 中该 char 的数量减去即可！！！
     *
     * 优化 2:
     * 最后按照 0 - 9 build 结果，不用 sort！！！
     *
     * Time: O(N)   Space: O(1)
     */
    public String originalDigits(String s) {
        Map<Character, Integer> count = new HashMap<>();
        for (Character c : s.toCharArray()) {
            count.put(c, count.getOrDefault(c, 0) + 1);
        }
        int[] digit = new int[10];
        digit[0] = count.getOrDefault('z', 0);
        digit[2] = count.getOrDefault('w', 0);
        digit[4] = count.getOrDefault('u', 0);
        digit[6] = count.getOrDefault('x', 0);
        digit[8] = count.getOrDefault('g', 0);
        digit[3] = count.getOrDefault('h', 0) - digit[8];
        digit[5] = count.getOrDefault('f', 0) - digit[4];
        digit[7] = count.getOrDefault('s', 0) - digit[6];
        digit[9] = count.getOrDefault('i', 0) - digit[5] - digit[6] - digit[8];
        digit[1] = count.getOrDefault('n', 0) - digit[7] - 2 * digit[9];

        StringBuilder sb = new StringBuilder();
        for (int n = 0; n < 10; n++) {
            for (int i = 0; i < digit[n]; i++) {
                sb.append(n);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        ReconstructOriginalDigitsFromEnglish solution = new ReconstructOriginalDigitsFromEnglish();
        System.out.println(solution.originalDigits("owoztneoer")); // 012
        System.out.println(solution.originalDigits("fviefuro")); // 45
    }
}
