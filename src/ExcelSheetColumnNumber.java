public class ExcelSheetColumnNumber {

    /************* Solution 1: Right to Left *******************/
    /**
     * BZY = 2 x 26^2 + 26 x 26^1 + 25 x 26^0
     *    = n3 x base0 x 26 x 26 + n2 x base0 x 26 + n1 x base0
     *    <----------------------------------------------------
     *    (base0 == 1)
     * Time: O(N)   Space: O(1)
     */
    public int titleToNumber1(String columnTitle) {
        int res = 0, base = 1, l = columnTitle.length();
        for (int i = 0; i < l; i++) {
            int n = columnTitle.charAt(l - i - 1) - 'A' + 1;
            res += n * base;
            base *= 26;
        }
        return res;
    }

    /************* Solution 2: Left to Right *******************/
    /**
     * BZY = 2 x 26^2 + 26 x 26^1 + 25 x 26^0
     *    = n1 x 26 x 26 + n2 x 26 + n3
     *    = ((n1) x 26 + n2) x 26 + n3
     *    ----------------------------->
     * Time: O(N)   Space: O(1)
     */
    public int titleToNumber(String columnTitle) {
        int res = 0, l = columnTitle.length();
        for (int i = 0; i < l; i++) {
            res *= 26;
            res += columnTitle.charAt(i) - 'A' + 1;
        }
        return res;
    }
}
