import java.util.HashMap;
import java.util.Map;

public class SentenceScreenFitting {

    /******************** Solution 1: 超时！！！*****************************/
    public static int wordsTyping(String[] sentence, int rows, int cols) {
        int i = 0, start = 0, k = 0, count = 0;
        Map<Integer, Integer> memo = new HashMap<>();
        while (k < rows * cols) {
            if (k % cols != 0) k++;
            int c = k % cols;
            if (i == 0) {
                start = k;
                if (memo.containsKey(c)) {
                    if (k + memo.get(c) <= rows * cols) {
                        count++;
                        k += memo.get(c);
                        continue;
                    } else {
                        return count;
                    }
                }
            }
            if (cols - c < sentence[i].length()) {
                k += cols - c;
            } else {
                k += sentence[i].length();
                if (i == sentence.length - 1) {
                    count++;
                    i = 0;
                    memo.put(start % cols, k - start);
                } else {
                    i++;
                }
            }
        }
        return count;
    }

    /******************** Solution 2 *****************************/
    public static int wordsTyping1(String[] sentence, int rows, int cols) {
        int filled = 0; // 既是number of char in s that has been filled, 也是index of char in s to be filled
        String s = String.join(" ", sentence) + " ";
        for (int i = 0; i < rows; i++) {
            filled += cols;
            if (s.charAt(filled % s.length()) == ' ') {
                filled++;
            } else {
                // filled > 0 for case rows = 1000, cols = 1, sentence = {"hello"}
                while (filled > 0 && s.charAt((filled - 1) % s.length()) != ' ') {
                    filled--;
                }
            }
        }
        return filled / s.length();
    }

    public static void main(String[] args) {
        System.out.println(SentenceScreenFitting.wordsTyping1(new String[]{"hello","world"}, 2, 8));
        System.out.println(SentenceScreenFitting.wordsTyping1(new String[]{"a", "bcd", "e"}, 3, 6));
        System.out.println(SentenceScreenFitting.wordsTyping1(new String[]{"i","had","apple","pie"}, 4, 5));
        System.out.println(SentenceScreenFitting.wordsTyping1(new String[]{"a", "bcd", "e"}, 4, 6));
    }

}
