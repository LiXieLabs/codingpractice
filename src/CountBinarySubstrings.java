public class CountBinarySubstrings {

    /*************** Solution 1: Two Pointers ***************/
    /**
     * pre指向前一个block的一开头
     * preCnt标记前一个block长度
     * cur指向目前block的当前位置
     *
     * 当cur是最后一个或者cur的下一个要开始新的block，结算pre和cur两个block:
     * (1) 取较短的一个的长度累加到结果
     * (2) pre移动到当前block开头
     * (3) preCnt标记为当前block长度
     *
     * Time: O(N)  Space: O(1)
     */
    public int countBinarySubstrings1(String s) {
        int pre = 0, cur = 0, res = 0;
        while (cur < s.length() && s.charAt(cur) == s.charAt(pre)) {
            cur++;
        }
        int preCnt = cur;
        while (cur < s.length()) {
            if (cur == s.length() - 1 || s.charAt(cur + 1) != s.charAt(cur)) {
                res += Math.min(preCnt, cur - (pre + preCnt) + 1);
                pre += preCnt;
                preCnt = cur - pre + 1;
            }
            cur++;
        }
        return res;
    }

    /*************** Solution 1: Two Pointers 优化 ***************/
    /**
     * pre记录前一个block的长度，起始为0
     * cur记录当前block的长度，起始为1
     *
     * 每次遇到新的block，结算pre和cur:
     * (1) 取较短的一个的长度累加到结果
     * (2) pre标记为当前block长度
     * (3) cur重置为1，因为当前i已经在新block了
     */
    public int countBinarySubstrings(String s) {
        int pre = 0, cur = 1, res = 0;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                cur++;
            } else {
                res += Math.min(pre, cur);
                pre = cur;
                cur = 1;
            }
        }
        return res + Math.min(pre, cur);
    }

    public static void main(String[] args) {
        CountBinarySubstrings solution = new CountBinarySubstrings();
        System.out.println(solution.countBinarySubstrings("00110011"));
        System.out.println(solution.countBinarySubstrings("10101"));
        System.out.println(solution.countBinarySubstrings(""));
        System.out.println(solution.countBinarySubstrings("1"));
    }
}