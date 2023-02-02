import java.util.Arrays;

/**
 * 1220. Count Vowels Permutation (https://leetcode.com/problems/count-vowels-permutation/description/)
 */
public class CountVowelsPermutation {

    private static final int MOD = (int) 1e9 + 7; // (int) Math.pow(10, 9) + 7; 也行

    /*********************** Solution 1: Bottom Up 2D DP 最优解 *************************/
    /**
     * map is built based on the rules:
     * 'a':{'e'}, 'e':{'a','i'}, 'i':{'a','e','o','u'}, 'o':{'e','u'}, 'u':{'a'}
     * 'a','e','i','o','u' => 0,1,2,3,4
     * 则 map 转化为
     * new int[][]{{1}, {0, 2}, {0, 1, 3, 4}, {2, 4}, {0}};
     *
     * dp[i][j] 表示长度为 i 时，以 j 结尾的 String 有多少个
     * 比如 dp[1][2] = 1，表示长度为 1 时，以 'i' 结尾的 String 有 1 个
     *
     * 则遍历 i = 1 -> N, 对于每一个 j，通过 map[j] 遍历下一个字符，将当前个数累加给下一个字符
     * 类似于一个状态机转化问题
     *
     * Time: O(N)   Space: O(1)
     */
    public int countVowelPermutation1(int n) {
        int[][] map = new int[][]{{1}, {0, 2}, {0, 1, 3, 4}, {2, 4}, {0}};
        int[] curr = new int[5];
        Arrays.fill(curr, 1);
        for (int i = 1; i < n; i++) {
            int[] next = new int[5];
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < map[j].length; k++) {
                    next[map[j][k]] = (next[map[j][k]] + curr[j]) % MOD;
                }
            }
            curr = next;
        }
        int total = 0;
        for (int i = 0; i < 5; i++) {
            total = (total + curr[i]) % MOD;
        }
        return total;
    }

    /*********************** Solution 2: Top Down Recursive *************************/
    /**
     * map 是 Solution 1 map 翻过来
     * 即哪些字母可以到达该字母，而非该字母可以去往哪些字母
     *
     * Time: O(N)   Space: O(5N) = O(N)
     */
    int[][] memo;
    private static final int[][] map = new int[][]{{1, 2, 4}, {0, 2}, {1, 3}, {2}, {2, 3}};
    public int countVowelPermutation(int n) {
        memo = new int[n][5];
        Arrays.fill(memo[0], 1);
        int total = 0;
        for (int vowel = 0; vowel < 5; vowel++) {
            total = (total + recur(n - 1, vowel)) % MOD;
        }
        return total;
    }

    private int recur(int n, int vowel) {
        if (memo[n][vowel] == 0) {
            int res = 0;
            for (int i = 0; i < map[vowel].length; i++) {
                res = (res + recur(n - 1, map[vowel][i])) % MOD;
            }
            memo[n][vowel] = res;
        }
        return memo[n][vowel];
    }

    public static void main(String[] args) {
        CountVowelsPermutation solution = new CountVowelsPermutation();
        System.out.println(solution.countVowelPermutation(1)); // 5
        System.out.println(solution.countVowelPermutation(2)); // 10
        System.out.println(solution.countVowelPermutation(5)); // 68
        System.out.println(solution.countVowelPermutation(20000)); // 759959057
    }
}
