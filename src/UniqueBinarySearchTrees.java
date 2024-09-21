/**
 * 96. Unique Binary Search Trees (https://leetcode.com/problems/unique-binary-search-trees/description/)
 */
public class UniqueBinarySearchTrees {

    /****************** Solution 1: Recursive ******************/
    /**
     * 遍历每个数为root，左右两侧recur计算，轴对称只计算左半边，乘以2即可
     * EVEN => f(6) = f(5)Xf(0)X2 + f(4)Xf(1)X2 + f(3)Xf(2)X2
     * ODD  => f(7) = f(6)Xf(0)X2 + f(5)Xf(1)X2 + f(4)Xf(2)X2 + f(3)Xf(3)
     *
     * T(N)   = T(N-1) + T(N-2) + ... + T(0)
     * T(N-1) =          T(N-2) + ... + T(0)
     * T(N)   = 2 x T(N-1) = 2^k x T(N-k) = O(2^N)
     *
     * 可以加上Memo => 那么实际就是DP了 => 见solution2！！！
     */
    public int numTrees1(int n) {
        if (n <= 1) return 1;

        int cnt = 0;
        for (int i = 0; i < n / 2; i++) {
            // i is root => int left = i, right = n - i - 1;
            cnt += numTrees1(i) * numTrees1(n - i - 1) * 2;
        }
        if (n % 2 == 1) cnt += Math.pow(numTrees1(n / 2), 2);

        return cnt;

    }

    /****************** Solution 2: 1D DP ******************/
    /**
     * dp[c] = Sum(dp[i] x dp[c - i - 1]) for i = [0, c]
     * dp[c] 表示 c 个 node 时，有 dp[c] 种排列组合
     * i 表示当前 root 左侧有多少个 node
     * c - i - 1 表示当前 root 右侧有多少个 node
     *
     * Time: O(1 + 2 + ... + N) = O(N^2)   Space: O(N)
     */
    public int numTrees(int n) {
        if (n <= 1) return 1;
        int[] dp = new int[n + 1];
        dp[0] = dp[1] = 1;
        for (int c = 2; c < n + 1; c++) {
            for (int i = 0; i < c / 2; i++) {
                dp[c] += dp[i] * dp[c - i - 1] * 2;
            }
            if (c % 2 == 1) dp[c] += Math.pow(dp[c / 2], 2);
        }
        return dp[n];
    }

    /*************** TODO: Solution 3: Catalan Number 递推公式 O(N) ***************/
    // https://leetcode.com/problems/unique-binary-search-trees/solution/
    // https://zh.wikipedia.org/wiki/%E5%8D%A1%E5%A1%94%E5%85%B0%E6%95%B0

    public static void main(String[] args) {
        UniqueBinarySearchTrees solution = new UniqueBinarySearchTrees();
        System.out.println(solution.numTrees(0)); // 1
        System.out.println(solution.numTrees(1)); // 1
        System.out.println(solution.numTrees(2)); // 2
        System.out.println(solution.numTrees(3)); // 5
        System.out.println(solution.numTrees(4)); // 14
        System.out.println(solution.numTrees(5)); // 42
    }
}
