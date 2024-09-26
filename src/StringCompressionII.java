import java.util.Arrays;

/**
 * 1531. String Compression II (https://leetcode.com/problems/string-compression-ii/description/)
 */
public class StringCompressionII {

    char[] chars;

    /******************** Solution 1: 4D DP (TLE) *************************/
    /**
     * i - current index
     * k - # of chars to remove (i.e. at most delete k chars)
     * p - previous char
     * l - encoded run length of previous char from beginning to s[i]
     *
     * dp[i][k][p][l] denotes min encoded run length of s[i:] given the current state
     *
     * dp[i][k][p][l] =
     * ---> delete s[i] ---> dp[i+1,k-1,p,l]
     * ---> keep s[i] ---> s[i] == p ---> dp[i+1,k,p,l+1] + carry
     *                ---> s[i] != p ---> dp[i+1,k,s[i],1] + 1
     *
     * Time: O(N^2 X K X 26)
     * Space: O(N^2 X K X 26)
     */
    int[][][][] dp1 = new int[101][101][27][101];
    public int getLengthOfOptimalCompression1(String s, int k) {
        chars = s.toCharArray();
        for (int[][][] threeDimArr : dp1) {
            for (int[][] twoDimArr : threeDimArr) {
                for (int[] oneDimArr : twoDimArr) {
                    Arrays.fill(oneDimArr, -1);
                }
            }
        }
        return recur1(0, k, (char) ('a' + 26), 0);
    }

    private int recur1(int i, int k, char p, int l) {
        if (k < 0) return chars.length;
        if (i + k >= chars.length) return 0;
        int res = dp1[i][k][p - 'a'][l];
        if (res == -1) {
            if (chars[i] == p) { // s[i] is same as previous char, keep
                int carry = (l == 1 || l == 9 || l == 99) ? 1 : 0;
                res = recur1(i + 1, k, p, l + 1) + carry;
            } else {
                res = Math.min(
                        recur1(i + 1, k - 1, p, l), // delete s[i]
                        recur1(i + 1, k, chars[i], 1) + 1 // keep s[i]
                );
            }
            dp1[i][k][p - 'a'][l] = res;
        }
        return dp1[i][k][p - 'a'][l];
    }

    /******************** Solution 2: 2D DP *************************/
    /**
     * 花花酱: https://www.youtube.com/watch?v=UIK00l_AiPQ
     * i - current index
     * k - # of chars to remove (i.e. at most delete k chars)
     *
     * dp[i][k] denotes min encoded run length of s[i:] with at most k deletions
     *
     * dp[i][k] =
     * ---> delete s[i] ---> dp[i+1,k-1]
     * ---> keep s[i] ---> min(encoded_len(same_in_s[i:j]) + dp[j+1,k-diff]) for j in [i:] (inclusive)
     *                     encoded_len(same_in_s[i:j]) denotes encoded run len of s[i:j] with all non s[i] removed
     *
     * Time: O(N^2 X K)
     * Space: O(N^2 X K)
     */
    int[][] dp2 = new int[101][101];
    public int getLengthOfOptimalCompression(String s, int k) {
        chars = s.toCharArray();
        for (int[] arr : dp2) {
            Arrays.fill(arr, -1);
        }
        return recur2(0, k);
    }

    private int recur2(int i, int k) {
        if (k < 0) return chars.length;
        if (i + k >= chars.length) return 0;
        int res = dp2[i][k];
        if (res == -1) {
            res = recur2(i + 1, k - 1); // delete s[i]
            int same = 0, diff = 0, len = 0;
            for (int j = i; j < chars.length && k - diff >= 0; j++) {
                if (chars[j] == chars[i]) {
                    same++;
                    if (same <= 2 || same == 10 || same == 100) len++;
                } else {
                    diff++;
                }
                res = Math.min(res, recur2(j + 1, k - diff) + len);
            }
            dp2[i][k] = res;
        }
        return dp2[i][k];
    }

    public static void main(String[] args) {
        StringCompressionII solution = new StringCompressionII();
        System.out.println(solution.getLengthOfOptimalCompression("aaabcccd", 2)); // 4
        System.out.println(solution.getLengthOfOptimalCompression("aabbaa", 2)); // 2
        System.out.println(solution.getLengthOfOptimalCompression("aaaaaaaaaaa", 0)); // 3
    }
}
