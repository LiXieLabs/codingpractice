import java.util.HashMap;
import java.util.Map;

public class ReconstructOriginalDigitsFromEnglish {

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
                sb.append(String.valueOf(n));
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        ReconstructOriginalDigitsFromEnglish solution = new ReconstructOriginalDigitsFromEnglish();
        System.out.println(solution.originalDigits("owoztneoer"));
        System.out.println(solution.originalDigits("fviefuro"));
    }
}
