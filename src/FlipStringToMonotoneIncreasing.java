public class FlipStringToMonotoneIncreasing {

    /************ Solution 1: 1D DP ********************/
    /**
     * dp0[i] 记录了到 s[i] 为止，维护一个ending with 0的递增String需要的最少步数
     * dp1[i] 记录了到 s[i] 为止，维护一个ending with 1的递增String需要的最少步数
     *
     * dp0[i+1] = dp0[i] + (s[i] == '0' ? 0 : 1);
     * dp1[i+1] = Math.min(dp0[i], dp1[i]) + (s[i] == '1' ? 0 : 1);
     *
     * 用 endWithZero 代替 dp[0]
     * 用 endWithOne 代替 dp[1]
     *
     * 最后 min(endWithZero, endWithOne) 为结果
     *
     * Time: O(N)   Space: O(N)
     */
    public int minFlipsMonoIncr(String s) {
        int endWithZero = 0, endWithOne = 0;
        for (int i = 0; i < s.length(); i++) {
            // 小心运算优先级！！！需要括号！！！不然+优先于==优先于?:
            int newEndWithZero = endWithZero + (s.charAt(i) == '0' ? 0 : 1);
            int newEndWithOne = Math.min(endWithZero, endWithOne) + (s.charAt(i) == '1' ? 0 : 1);
            endWithZero = newEndWithZero;
            endWithOne = newEndWithOne;
        }
        return Math.min(endWithZero, endWithOne);
    }

    public static void main(String[] args) {
        FlipStringToMonotoneIncreasing solution = new FlipStringToMonotoneIncreasing();
        System.out.println(solution.minFlipsMonoIncr("00110"));
        System.out.println(solution.minFlipsMonoIncr("010110"));
        System.out.println(solution.minFlipsMonoIncr("00011000"));
    }
}