/**
 * 72. Edit Distance (https://leetcode.com/problems/edit-distance/description/)
 */
public class EditDistance {

    /********************** Solution 1: Bottom-up 2D DP ***************************/
    /**
     * dp[i][j] 表示 word1[0:i] 变成 word2[0:j] (inclusive) 最少多少步
     *
     * if word1[i] == word2[j], 则 dp[i][j] = dp[i-1][j-1]
     * else, 则 dp[i][[j] = min(dp[i-1][j-1] + 1, dp[i-1][j] + 1, dp[i][j-1])
     *
     * dp[i-1][j] + 1 对应 word1[0:i] 删除 word1[i] 变成 word2[0:j]
     * dp[i][j-1] + 1 对应 word1[0:i] 增加 word2[j] 变成 word2[0:j]
     *
     * 另外，word1 变 word2 和 word2 变 word1，步数一样，操作相反
     * 此题也可以用 top-down recursive 解:
     * https://zxi.mytechroad.com/blog/dynamic-programming/leetcode-72-edit-distance/
     *
     * Time: O(L1 X L2)   Space: O(L1 X L2)
     */
    public int minDistance1(String word1, String word2) {
        if (word1 == null || word1.isEmpty()) return word2 == null ? 0 : word2.length();
        if (word2 == null || word2.isEmpty()) return word1.length();
        word1 = " " + word1;
        word2 = " " + word2;
        int l1 = word1.length(), l2 = word2.length();
        int[][] dp = new int[l1][l2];
        for (int i = 1; i < l1; i++) dp[i][0] = i; // 初始值为 i，不都是 1
        for (int j = 1; j < l2; j++) dp[0][j] = j; // 初始值为 j，不都是 1
        for (int i = 1; i < l1; i++) {
            for (int j = 1; j < l2; j++) {
                dp[i][j] = Math.min(
                        dp[i - 1][j - 1] + (word1.charAt(i) == word2.charAt(j) ? 0 : 1), // 必须加括号！
                        Math.min(dp[i][j - 1] + 1, dp[i - 1][j] + 1)
                );
            }
        }
        return dp[l1 - 1][l2 - 1];
    }

    /********************** Solution 2: 优化为 1D DP ***************************/
    /**
     * Time: O(L1 X L2)   Space: O(min(L1,L2))
     */
    public int minDistance(String word1, String word2) {
        if (word1 == null || word1.isEmpty()) return word2 == null ? 0 : word2.length();
        if (word2 == null || word2.isEmpty()) return word1.length();
        word1 = " " + word1;
        word2 = " " + word2;
        // for Space Complexity = min(L1, L2)
        if (word2.length() < word1.length()) {
            String temp = word1;
            word1 = word2;
            word2 = temp;
        }
        int l1 = word1.length(), l2 = word2.length();
        int[] dp = new int[l2];
        for (int j = 1; j < l2; j++) dp[j] = j; // 第一行初始值为 j，不都是 1
        for (int i = 1; i < l1; i++) {
            int prev = dp[0]; // prev 是左上角
            dp[0] = i; // 这个是第一列初始值
            for (int j = 1; j < l2; j++) {
                int temp = dp[j];
                dp[j] = Math.min(
                        prev + (word1.charAt(i) == word2.charAt(j) ? 0 : 1), // 必须加括号！
                        Math.min(dp[j - 1] + 1, dp[j] + 1)
                );
                prev = temp;
            }
        }
        return dp[l2 - 1];
    }

    public static void main(String[] args) {
        EditDistance solution = new EditDistance();
        // TC1: 3
        System.out.println(solution.minDistance("horse", "ros"));
        // TC2: 5
        System.out.println(solution.minDistance("intention", "execution"));
        // TC3: 6
        System.out.println(solution.minDistance("plasma", "altruism"));
    }
}
