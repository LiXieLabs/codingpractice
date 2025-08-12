/**
 * 43. Multiply Strings (https://leetcode.com/problems/multiply-strings/description/)
 */
public class MultiplyStrings {

    /**************** Solution 1: All Pairs of Digits from 2 Numbers *****************/
    /**
     *  num1[i] * num2[j] will be placed at indices res[i + j, i + j + 1]
     *
     *  num1 有 M 位，num2 有 N 位，
     *  则 M + 1 位的最小数字 1000...00 M 个 0，乘以 N + 1 位的最小数字 1000...00 N 个 0，
     *  有 M + N + 1 位，1 后面 M + N 个 0
     *  num1 * num2 一定比他小，最多 M + N 位！！！
     *  => int[] res = new int[l1 + l2];
     *
     *  Time: O(M x N)  Space: O(M + N)
     */
    public String multiply(String num1, String num2) {
        if ("0".equals(num1) || "0".equals(num2)) return "0";
        int l1 = num1.length(), l2 = num2.length();
        int[] res = new int[l1 + l2];
        for (int i1 = l1 - 1; i1 >= 0; i1--) {
            for (int i2 = l2 - 1; i2 >= 0; i2--) {
                int p1 = i1 + i2, p2 = i1 + i2 + 1;
                int n = (num1.charAt(i1) - '0') * (num2.charAt(i2) - '0') + res[p2];
                res[p2] = n % 10;
                res[p1] += n / 10;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int n : res) {
            if (sb.length() == 0 && n == 0) continue;
            sb.append((char) ('0' + n));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        MultiplyStrings solution = new MultiplyStrings();
        System.out.println(solution.multiply("2", "3")); // 6
        System.out.println(solution.multiply("123", "456")); // 56088
        System.out.println(solution.multiply("123456789", "987654321")); // 121932631112635269
        System.out.println(solution.multiply("881095803", "61")); // 53746843983
    }
}
