/**
 * 3163. String Compression III (https://leetcode.com/problems/string-compression-iii/description/)
 */
public class StringCompressionIII {

    /**************** Solution 1: String + 2 Pointers ****************/
    /**
     * Time: O(N)   Space: comp takes O(N) space or else O(1)
     */
    public String compressedString(String word) {
        StringBuilder comp = new StringBuilder();
        int start = 0;
        while (start < word.length()) {
            int end = start + 1;
            while (end < word.length() && word.charAt(end) == word.charAt(start) && end - start < 9) {
                end++;
            }
            // ⚠️注意⚠️可以直接 append 数字，且可以连续 append ！！！
            comp.append(end - start).append(word.charAt(start));
            start = end;
        }
        return comp.toString();
    }

    public static void main(String[] args) {
        StringCompressionIII solution = new StringCompressionIII();
        System.out.println(solution.compressedString("abcde")); // 1a1b1c1d1e
        System.out.println(solution.compressedString("aaaaaaaaaaaaaabb")); // 9a5a2b
    }
}
