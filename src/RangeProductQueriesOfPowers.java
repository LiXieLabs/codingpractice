public class RangeProductQueriesOfPowers {

    /*********** Solution 1: Prefix Product + Inverse by modpow **************/
    /**
     * m = number of set bits in n (≤ 31)
     * Q = number of queries
     * 1. Extract set bits: O(m)
     * 2. Build prefix products: O(m)
     * 3. For each query:
     *      One modular inverse via modPow: O(\log MOD)
     *      One multiplication: O(1)
     * Time: O(m + Q x log(MOD)) = O(QlogMOD)   Space: O(m)
     */
    // 不要 Math.pow(10, 9) + 7!!! 这是 double！！！
    // 可以 private static final long MOD = (long) 1e9 + 7;
    private static final long MOD1 = 1_000_000_007L;

    public int[] productQueries1(int n, int[][] queries) {
        // calculate powers[]
        // 15 = 1 + 2 + 4 + 8 = 2^0 + 2^1 + 2^2 + 2^4 = [1,2,4,8]
        long[] powers = new long [31];
        int idx = 0;
        for (int i = 0; i < 31; i++, n >>= 1) {
            if ((n & 1) == 1) {
                powers[idx++] = 1L << i;
            }
        }

        // calculate pre[] (prefix products)
        // pre[0] == 1 && pre[i + 1] = pre[i] * powers[i] % MOD
        long[] pre = new long[idx + 1];
        pre[0] = 1;
        for (int i = 0; i < idx; i++) {
            pre[i + 1] = (pre[i] * (powers[i] % MOD1)) % MOD1;
        }

        // powers[l]...powers[r]
        // = pre[r + 1] / pre[l]
        // = pre[r + 1] * inverse(pre[l]) % MOD
        // = pre[r + 1] * modpow(pre[l], MOD - 2) % MOD
        // based on inverse(x)= x^(MOD-2) % MOD where MOD is prime number
        int[] res = new int[queries.length];
        for (int i = 0; i < res.length; i++) {
            int l = queries[i][0], r = queries[i][1];
            long numerator = pre[r + 1];
            long denomInv = modpow(pre[l], MOD1 - 2);
            res[i] = (int) (numerator * denomInv % MOD1);
        }
        return res;
    }

    /********** Solution 2: Prefix Sum + modpow ***************/
    /**
     * m = number of set bits in n (≤ 31)
     * Q = number of queries
     * 1. Extract set bits of n: O(m)
     * 2. Build prefix sum array: O(m)
     * 3. For each query:
     *    Compute exponent sum: O(1)
     *    Compute modPow(2, exp): O(log exp)
     * Time: O(m + Q x log(exp)) = O(Q)   Space: O(m)
     */
    // 不要 Math.pow(10, 9) + 7!!! 这是 double！！！
    // 可以 private static final int MOD = 1e9 + 7;
    private static final int MOD = 1_000_000_007;

    public int[] productQueries(int n, int[][] queries) {
        // calculate powers[] 只记录指数！！！不记录指数幂！！！
        // 15 = 1 + 2 + 4 + 8 = 2^0 + 2^1 + 2^2 + 2^4 = [0,1,2,4]
        int[] powers = new int [31];
        int idx = 0;
        for (int i = 0; i < 31; i++, n >>= 1) {
            if ((n & 1) == 1) {
                powers[idx++] = i;
            }
        }

        // calculate pre[] (prefix sum instead of prefix product)
        // pre[0] == 0 && pre[i + 1] = pre[i] + powers[i]
        // ⚠️注意⚠️ pre[] 和 modpow 仍需 long 处理！！！
        long[] pre = new long[idx + 1];
        pre[0] = 0;
        for (int i = 0; i < idx; i++) {
            pre[i + 1] = pre[i] + powers[i];
        }

        // 2^powers[l]...2^powers[r]
        // = 2^pre[r + 1] / 2^pre[l]
        // = 2^(pre[r + 1] - pre[l])
        // = powmod(2, pre[r + 1] - pre[l])
        int[] res = new int[queries.length];
        for (int i = 0; i < res.length; i++) {
            int l = queries[i][0], r = queries[i][1];
            res[i] = (int) modpow(2, pre[r + 1] - pre[l]);
        }
        return res;
    }

    private long modpow(long base, long exp) {
        long res = 1;
        while (exp > 0) {
            if ((exp & 1) == 1) res = res * base % MOD;
            base = base * base % MOD;
            exp >>= 1;
        }
        return res;
    }
}
