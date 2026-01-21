public class PowXN {

    /****************** Solution1: Iterative ****************/
    /**
     * 经典的 binary exponentiation 算法！！！当模板背下来！！！
     * res = 3 ^ 13 = base ^ exp
     *     = 3 ^ (1 + 4 + 8)
     *     = 3 ^ 1 * 3 ^ 4 * 3 ^ 8
     *     = 3 ^ (2^0) * 3 ^ (2^2) * 3 ^ (2^4)
     * res = 所有 exp 的二进制位为 1 的那些 base^(2^k) 的乘积
     *
     * modPow 的本质：
     * - base：当前 2^k 次幂
     * - exp：决定要不要用这个幂
     * - 每轮 (k++)：
     *     - exp 奇数 → res *= base
     *     - base 自己平方
     *     - exp 右移
     *
     * Time: O(logN) where logN = number of bits in n
     * Space: O(1)
     */
    public double myPow1(double x, int n) {
        if (n < 0) {
            // 否则 x=2.0, n=Integer.MIN_VALUE会溢出
            // 也可以直接用 long exp hold 住 n，见下面👇
            if (n == Integer.MIN_VALUE) {
                x *= x;
                n >>= 1;
            }
            x = 1 / x;
            n = -n;
        }
        double cur = x, res = 1;
        while (n > 0) {
            if (n % 2 == 1) res *= cur;
            cur *= cur;
            n >>= 1;
        }
        return res;
    }

    public double myPow2(double x, int n) {
        long exp = n;
        if (exp < 0) {
            x = 1.0 / x;
            exp = -exp;
        }
        double res = 1;
        while (exp > 0) {
            if ((exp & 1) == 1) res *= x;
            x *= x;
            exp >>= 1;
        }
        return res;
    }

    /****************** Solution2: Recursive ****************/
    public double myPow(double x, int n) {
        if (n == 0) return 1;
        if (n < 0) {
            return 1 / x * myPow(1 / x, -(n + 1));
        }
        return (n % 2 == 1 ? x : 1) * myPow(x * x, n >> 1);
    }

    public static void main(String[] args) {
        PowXN solution = new PowXN();
        System.out.println(solution.myPow(2.0, 10));
        System.out.println(solution.myPow(2.1, 3));
        System.out.println(solution.myPow(2.0, -2));
        System.out.println(solution.myPow(2.0, Integer.MIN_VALUE));
    }
}
