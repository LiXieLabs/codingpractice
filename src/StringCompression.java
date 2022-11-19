import java.util.Arrays;

public class StringCompression {

    /********************* Solution 1: Two Pointers *******************/
    /**
     * pre 指向下一个要被写入结果的位置
     * start 指向当前同一 char 的起始位置
     * end 指向当前同一 char 遍历到的位置
     *
     * Time: O(N)   Space: O(1)
     */
    public int compress(char[] chars) {
        int pre = 0, start = 0, end = -1;
        while (++end < chars.length) {
            // chars结尾或者下一个char不等于当前char的话，结束当前这个char遍历，写入结果
            if (end == chars.length - 1 || chars[end + 1] != chars[start]) {
                chars[pre++] = chars[start];
                if (end - start > 0) {
                    for (char c : String.valueOf(end - start + 1).toCharArray()) {
                        chars[pre++] = c;
                    }
                }
                start = end + 1;
            }
        }
        return pre;
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
