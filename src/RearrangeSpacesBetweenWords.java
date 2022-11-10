import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RearrangeSpacesBetweenWords {

//    Time & space: O(n), where n = text.length().

    public String reorderSpaces(String text) {
        // m spaces, n words
        // m // (n-1)
        // m % (n-1)

        // count word and spaces
        int i = 0;
        int start = 0;
        int spaces = 0;
        List<String> words = new ArrayList<>();
        while (i < text.length()) {
            while (i < text.length() && text.charAt(i) == ' ') {
                spaces++;
                i++;
            }
            start = i;
            while (i < text.length() && text.charAt(i) != ' ') {
                i++;
            }
            if (start != i) {
                words.add(text.substring(start, i));
            }
        }
//        System.out.println(spaces + " " + Arrays.toString(words.toArray()));
//        9 [this, is, a, sentence]
//        7 [practice, makes, perfect]

        // rearrange
//        StringBuilder result = new StringBuilder("\"");
        StringBuilder result = new StringBuilder();
        int gaps = words.size() <= 1 ? 0 : words.size() - 1;
        for (int k = 0; k < words.size(); k++) {
            result.append(words.get(k));
            if (k != words.size() - 1) {
                for (int l = 0; l < gaps; l++) {
                    result.append(" ");
                }
            }
        }
        // 小心 words.size()-1==0 情况！！！divisor cannot be ZERO!!!
        // dividend / divisor = quotient && dividend % divisor = remainder
        int trailingSpaces = words.size() == 1 ? spaces : spaces % (words.size() - 1);
        for (int k = 0; k < trailingSpaces; k++) {
            result.append(" ");
        }
//        result.append("\"");
        return result.toString();
    }

    public String reorderSpaces1(String text) {

        String[] words = text.trim().split("\\s+");
//        int spaces = text.chars().map(c -> c == ' ' ? 1 : 0).sum();
        int spaces = (int) text.chars().filter(c -> c == ' ').count();
        int gaps = words.length <= 1 ? 0 : words.length - 1;
        int trailingSpaces = words.length == 1 ? spaces : spaces % (words.length - 1);

//        str.repeat() only available in Java11...
//        return String.join(" ".repeat(gaps), words) + " ".repeat(trailingSpaces);

        StringBuilder result = new StringBuilder();
        for (int k = 0; k < words.length; k++) {
            result.append(words[k]);
            int spacesToAdd = k != words.length - 1 ? gaps : trailingSpaces;
            while (spacesToAdd > 0) {
                result.append(" ");
                spacesToAdd--;
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        RearrangeSpacesBetweenWords rearranger = new RearrangeSpacesBetweenWords();
        System.out.println(rearranger.reorderSpaces1("  this   is  a sentence "));
        System.out.println(rearranger.reorderSpaces1(" practice   makes   perfect"));
        System.out.println(rearranger.reorderSpaces1("a"));
    }
}
