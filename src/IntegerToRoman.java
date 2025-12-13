import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 12. Integer to Roman (https://leetcode.com/problems/integer-to-roman/description/)
 */
public class IntegerToRoman {

    private static final String[][] map = new String[][]{
            {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"},
            {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"},
            {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"},
            {"", "M", "MM", "MMM"}
    };

    private static final Integer[] values = new Integer[]{
            1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1
    };
    private static final String[] symbols = new String[]{
            "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"
    };

    /********** Solution 1: Hardcode Digits **********/
    /**
     * Time: O(N)   Space: O(1)
     */
    public String intToRoman1(int num) {
        List<String> res = new ArrayList<>();
        int i = 0;
        while (num > 0) {
            res.add(map[i++][num % 10]);
            num /= 10;
        }
        Collections.reverse(res);
        return String.join("", res);
    }

    /************** Solution 2: Greedy *************/
    /**
     * Time: O(N)   Space: O(1)
     */
    public String intToRoman2(int num) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (num > 0) {
            if (num / values[i] > 0) {
                sb.append(symbols[i]);
                num -= values[i];
            } else {
                i++;
            }
        }
        return sb.toString();
    }

    /************** Solution 3: Another Greedy *************/
    /**
     * Time: O(N)   Space: O(1)
     */
    public String intToRoman(int num) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (num > 0) {
            sb.append(symbols[i].repeat(num / values[i]));
            // 👆 相当于 👇
//            for (int j = 0; j < num / values[i]; j++) {
//                sb.append(symbols[i]);
//            }
            num %= values[i++];
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        IntegerToRoman solution = new IntegerToRoman();
        System.out.println(solution.intToRoman(3)); // III
        System.out.println(solution.intToRoman(58)); // LVIII
        System.out.println(solution.intToRoman(1994)); // MCMXCIV
    }
}
