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
        int i = 0, cnt = 0;
        while (i < word.length()) {
            int j = i;
            while (j < word.length() && word.charAt(j) == word.charAt(i) && cnt < 9) {
                j++;
                cnt++;
            }
            comp.append(cnt).append(word.charAt(i));
            i = j;
            cnt = 0;
        }
        return comp.toString();
    }

    public static void main(String[] args) {
        StringCompressionIII solution = new StringCompressionIII();
        System.out.println(solution.compressedString("abcde")); // 1a1b1c1d1e
        System.out.println(solution.compressedString("aaaaaaaaaaaaaabb")); // 9a5a2b
    }
}
