public class SuperPow {

    private static int MOD = 1337;

    /************* Solution 1: ModPower by exp power ****************/
    /**
     *    3 ^ 85
     *  = 3 ^ (80 + 5)
     *  = 3^80 * 3^5
     *  = ((3^8)^10 * 3^5) % MOD
     *
     * Time: O(len(b))
     * modpow(res, 10) → O(log 10) ≈ constant
     * modpow(a, digit) where digit ∈ [0..9] → O(log 9) ≈ constant
     * Multiplications + modulo → constant
     * So each loop iteration is O(1).
     * len(b) iterations
     */
    public int superPow(int a, int[] b) {
        // 3 ^ 85
        // 3 ^ (80 + 5)
        // 3^80 * 3^5
        // ((3^8)^10 * 3^5) % MOD
        a %= MOD;
        int res = 1;
        for (int digit : b) {
            res = (int) ((long) (modpow(res, 10) * modpow(a, digit)) % MOD);
        }
        return res;
    }

    private int modpow(int x, int exp) {
        int res = 1;
        long base = x % MOD;
        while (exp > 0) {
            if ((exp & 1) == 1) res = (int) (res * base % MOD);
            base = base * base % MOD;
            exp >>= 1;
        }
        return res;
    }
}
