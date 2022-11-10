import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LetterCombinationsOfAPhoneNumber {

    // can be optimized to
    // String[] mapping = new String[] {"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
    static Map<Character, String[]> map = new HashMap<>();

    static {
        map.put('2', new String[]{"a", "b", "c"});
        map.put('3', new String[]{"d", "e", "f"});
        map.put('4', new String[]{"g", "h", "i"});
        map.put('5', new String[]{"j", "k", "l"});
        map.put('6', new String[]{"m", "n", "o"});
        map.put('7', new String[]{"p", "q", "r", "s"});
        map.put('8', new String[]{"t", "u", "v"});
        map.put('9', new String[]{"w", "x", "y", "z"});
    }

    /********************* Solution 1: Iterative **********************/
    /**
     * Time: O(4^N X N), N is length of digits, 4^N combinations, and each combination took max N time to build
     * Space: O(4^N) for result
     */
    public List<String> letterCombinations1(String digits) {
        // Note: Edge Case!!!
        if (digits == null || digits.length() == 0) return new ArrayList<>();
        List<String> curr = Arrays.asList("");
        for (Character d : digits.toCharArray()) {
            List<String> next = new ArrayList<>();
            for (String c : map.get(d)) {
                for (String o : curr) {
                    next.add(o + c);
                }
            }
            curr = next;
        }
        return curr;
    }

    /********************* Solution 2: Recursive *********************/
    /**
     * Time: O(4^N X N), N is length of digits, 4^N combinations, and each combination took max N time to build
     * Space: O(4^N) for result, O(N) for recursive call stack
     */
    public List<String> letterCombinations(String digits) {
        // Note: Edge Case!!!
        if (digits == null || digits.length() == 0) return new ArrayList<>();
        return recur(digits, 0);
    }

    private List<String> recur(String digits, int i) {
        if (i >= digits.length()) return Collections.singletonList("");
        List<String> next = recur(digits, i + 1);
        List<String> curr = new ArrayList<>();
        for (String c : map.get(digits.charAt(i))) {
            for (String n : next) {
                curr.add(c + n);
            }
        }
        return curr;
    }

    public static void print(List<String> lst) {
        System.out.println("[" + String.join(",", lst) + "]");
    }

    public static void main(String[] args) {
        LetterCombinationsOfAPhoneNumber solution = new LetterCombinationsOfAPhoneNumber();
        print(solution.letterCombinations("23"));
        print(solution.letterCombinations(""));
        print(solution.letterCombinations(null));
    }


}
