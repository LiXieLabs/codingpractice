import java.util.Arrays;

public class BoldWordsInString {

    /*************** Solution 1: Iterate over substring words ******************/
    /**
     * 完全一样 616. Add Bold Tag in String (https://leetcode.com/problems/add-bold-tag-in-string/description/)
     *
     * Time: O(N X M X W)
     * N is words.length
     * M is s.length
     * W is average word length
     *
     * Space: O(M)
     */
    public String boldWords(String[] words, String s) {
        // bold[0] 和 bold[-1] 是 border guardian
        boolean[] bold = new boolean[s.length() + 2];
        // 对于每个substring dict里面的word，在s中找到所有位置，将对应的位置bold标为true
        for (String word : words) {
            for (int i = s.indexOf(word); i != -1; i = s.indexOf(word, i + 1)) {
                Arrays.fill(bold, i + 1, i + 1 + word.length(), true);
            }
        }
        // build result string
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (!bold[i] && bold[i + 1]) res.append("<b>");
            res.append(s.charAt(i));
            if (bold[i + 1] && !bold[i + 2]) res.append("</b>");
        }
        return res.toString();
    }

    // TODO: KMP
    // TODO: MERGE INTERVALS

}
