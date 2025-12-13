import java.util.HashMap;
import java.util.Map;

/**
 * 13. Roman to Integer (https://leetcode.com/problems/roman-to-integer/description/)
 */
public class RomanToInteger {

    static Map<Character, Integer> map = new HashMap<>();
    static {
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);
    }

    /************ Solution 1: Greedy - Right to Left ****************/
    /**
     * Time: O(N) Space: O(1)
     */
    public int romanToInt1(String s) {
        int res = 0, i = s.length() - 1;
        while (i >= 0) {
            if (i + 1 < s.length() && map.get(s.charAt(i)) < map.get(s.charAt(i+1))) {
                res -= map.get(s.charAt(i--));
            } else {
                res += map.get(s.charAt(i--));
            }
        }
        return res;
    }

    /************ Solution 2: Greedy - Left to Right ****************/
    /**
     * Time: O(N) Space: O(1)
     */
    public int romanToInt(String s) {
        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            int sign = i + 1 < s.length() && map.get(s.charAt(i)) < map.get(s.charAt(i + 1)) ? -1 : 1;
            result += sign * map.get(s.charAt(i));
        }
        return result;
    }

    public static void main(String[] args) {
        RomanToInteger solution = new RomanToInteger();
        System.out.println(solution.romanToInt("III")); // 3
        System.out.println(solution.romanToInt("LVIII")); // 58
        System.out.println(solution.romanToInt("MCMXCIV")); // 1994
    }
}
