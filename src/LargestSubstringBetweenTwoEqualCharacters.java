import java.util.HashMap;
import java.util.Map;

public class LargestSubstringBetweenTwoEqualCharacters {

    public int maxLengthBetweenEqualCharacters(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int result = -1;
        for (int i = 0; i < s.length(); i++) {
            if (map.containsKey(s.charAt(i))) {
                result = Math.max(i - map.get(s.charAt(i)) - 1, result);
            } else {
                map.put(s.charAt(i), i);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        LargestSubstringBetweenTwoEqualCharacters solution = new LargestSubstringBetweenTwoEqualCharacters();
        System.out.println(solution.maxLengthBetweenEqualCharacters("aa"));
        System.out.println(solution.maxLengthBetweenEqualCharacters("abca"));
        System.out.println(solution.maxLengthBetweenEqualCharacters("cbzxy"));
    }
}
