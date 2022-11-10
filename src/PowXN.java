public class PowXN {

    /****************** Solution1: Iterative ****************/
    public double myPow1(double x, int n) {
        if (n < 0) {
            // 否则 x=2.0, n=Integer.MIN_VALUE会溢出
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

    /****************** Solution2: Recursive ****************/
    public double myPow(double x, int n) {
        if (n == 0) return 1;
        if (n < 0) {
            return 1 / x * myPow(1 / x, -(n + 1));
        }
        return n % 2 == 1 ? x * myPow(x, n - 1) : myPow(x * x, n >> 1);
    }

    public static void main(String[] args) {
        PowXN solution = new PowXN();
        System.out.println(solution.myPow(2.0, 10));
        System.out.println(solution.myPow(2.1, 3));
        System.out.println(solution.myPow(2.0, -2));
        System.out.println(solution.myPow(2.0, Integer.MIN_VALUE));
    }
}
