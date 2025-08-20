import java.util.Arrays;

/**
 * 443. String Compression (https://leetcode.com/problems/string-compression/description/)
 */
public class StringCompression {

    /********************* Solution 1: Two Pointers *******************/
    /**
     * pre 指向下一个要被写入结果的位置
     * cur 指向当前同一 char 的起始位置
     * end 指向当前同一 char 遍历到的位置
     *
     * Time: O(N)   Space: O(1)
     */
    public int compress(char[] chars) {
        int pre = 0, cur = 0;
        while (cur < chars.length) {
            int end = cur + 1;
            while (end < chars.length && chars[end] == chars[cur]) {
                end++;
            }
            chars[pre++] = chars[cur];
            if (end - cur > 1) {
//                for (Character c : intToCharArray(end - cur)) {
//                    chars[pre++] = c;
//                }
                for (Character c : String.valueOf(end - cur).toCharArray()) {
                    chars[pre++] = c;
                }
            }
            cur = end;
        }
        return pre;
    }

    private char[] intToCharArray(int n) {
        StringBuilder sb = new StringBuilder();
        while (n > 0) {
            sb.append(n % 10);
            n /= 10;
        }
        sb.reverse();
        return sb.toString().toCharArray();
    }

    public static void main(String[] args) {
        StringCompression solution = new StringCompression();

        // TC1
        char[] input1 = "aabbccc".toCharArray();
        System.out.println(Arrays.copyOfRange(input1, 0, solution.compress(input1)));
        // a2b2c3

        // TC2
        char[] input2 = "a".toCharArray();
        System.out.println(Arrays.copyOfRange(input2, 0, solution.compress(input2)));
        // a

        // TC3
        char[] input3 = "abbbbbbbbbbbb".toCharArray();
        System.out.println(Arrays.copyOfRange(input3, 0, solution.compress(input3)));
        // ab12
    }
}
