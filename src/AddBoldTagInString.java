import java.util.Arrays;

public class AddBoldTagInString {

    /*************** Solution 1: Iterate over substring words ******************/
    /**
     * 完全一样 758. Bold Words in String (https://leetcode.com/problems/bold-words-in-string/description/)
     *
     * Time: O(N X M X W)
     * N is words.length
     * M is s.length
     * W is average word length
     *
     * Space: O(M)
     */
    public String addBoldTag(String s, String[] words) {
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

    public static void main(String[] args) {
        AddBoldTagInString solution = new AddBoldTagInString();
        System.out.println(solution.addBoldTag("abcxyz123", new String[]{"abc","123"}));
        System.out.println(solution.addBoldTag("aaabbb", new String[]{"aa","b"}));
    }
}
