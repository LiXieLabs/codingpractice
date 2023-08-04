import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 151. Reverse Words in a String (https://leetcode.com/problems/reverse-words-in-a-string/)
 */
public class ReverseWordsInAString {

    /************ Solution 1: Trim + Split => Reverse => Join *************/
    /**
     * Time: O(N)     Space: O(N)
     */
    public String reverseWords(String s) {
        // Remove all spaces and build list of words
        List<String> words = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                if (sb.length() > 0) {
                    words.add(sb.toString());
                    sb = new StringBuilder();
                }
            } else {
                sb.append(s.charAt(i));
            }
        }
        if (sb.length() > 0) {
            words.add(sb.toString());
            sb = new StringBuilder();
        }
//        // 👆🏻can be replaced by
//        // remove leading spaces
//        s = s.trim();
//        // split by multiple spaces
//        List<String> words = Arrays.asList(s.split("\\s+"));

        // Reverse list of words and join with space as delimiter
        Collections.reverse(words);
        return String.join(" ", words);
    }

    public static void main(String[] args) {
        ReverseWordsInAString solution = new ReverseWordsInAString();
        System.out.println(solution.reverseWords("  the    sky is    blue   ")); // "blue is sky the"
        System.out.println(solution.reverseWords("  hello world  ")); // "world hello"
        System.out.println(solution.reverseWords("a good   example")); // "a good   example"
    }
}
