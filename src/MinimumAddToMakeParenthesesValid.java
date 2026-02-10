public class MinimumAddToMakeParenthesesValid {

    /************ Solution 1: Greedy + Stack replaced by Counter **************/
    /**
     * Time: O(N)   Space: O(1)
     */
    public int minAddToMakeValid(String s) {
        int unmatchedLeft = 0, add = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                unmatchedLeft++;
            } else if (unmatchedLeft == 0) {
                add++;
            } else {
                unmatchedLeft--;
            }
        }
        return add + unmatchedLeft;
    }
}
