public class FlipStringToMonotoneIncreasing {

    /************ Solution 1: 1D DP + Min Sum ************/
    /**
     * allZero[i] 表示 l 向右到 i 位置全变成 0 需要的 flip 次数
     * allOne[i] 表示 r 向左到 i 位置全变成 1 需要的 flip 次数
     *
     * 则最后结果是：
     * (1) allZero[r - l] => 全变 0
     * (2) allOne[0] => 全变 1
     * (3) allZero[i] + allOne[i + 1] for all 0 <= i && i + 1 <= r - l => i (包含 i) 之前是 0，i 之后是 1
     * 中的最小值！
     *
     * Time: O(N)   Space: O(N)
     */
    public int minFlipsMonoIncr1(String s) {
        // l 从左至右找到第一个不是 0 的位置
        // r 从右往左找到第一个不是 1 的位置
        int l = 0, r = s.length() - 1;
        while (l < s.length() && s.charAt(l) == '0') l++;
        while (r >= l && s.charAt(r) == '1') r--;
        if (r < l) return 0; // 说明已经monotone string了

        int[] allZero = new int[r - l + 1], allOne = new int[r - l + 1];
        allZero[0] = 1;
        allOne[r - l] = 1;
        for (int i = 1; l + i <= r; i++) {
            allZero[i] = allZero[i - 1] + (s.charAt(l + i) - '0');
            allOne[r - l - i] = allOne[r - l - i + 1] + (1 - (s.charAt(r - i) - '0'));
        }
        int minOp = Math.min(allZero[r - l], allOne[0]);
        for (int i = 0; i + 1 <= r - l; i++) {
            minOp = Math.min(minOp, allZero[i] + allOne[i + 1]);
        }
        return minOp;
    }

    /************ Solution 2: 1D DP ********************/
    /**
     * dp0[i] 记录了到 s[i] 为止，维护一个ending with 0的递增String需要的最少步数
     * dp1[i] 记录了到 s[i] 为止，维护一个ending with 1的递增String需要的最少步数
     *
     * dp0[i+1] = dp0[i] + (s[i] == '0' ? 0 : 1);
     * dp1[i+1] = Math.min(dp0[i], dp1[i]) + (s[i] == '1' ? 0 : 1);
     *
     * 用 endWithZero 代替 dp0
     * 用 endWithOne 代替 dp1
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