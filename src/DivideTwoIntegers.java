public class DivideTwoIntegers {

    /**************************** Solution 1: TLE!!!!! *******************************/
    /**
     * 先找到临界点times, 使得 2^times * B < A 且 2^(times+1) * B >= A
     * 再从 [2^times，2^(times+1)), 每次+1线性查找小于A到大于A的临界点，则为最后的倍数
     */
    public int divide1(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && divisor == -1) return Integer.MAX_VALUE;
        if (dividend == Integer.MIN_VALUE && divisor == 1) return Integer.MIN_VALUE;
        int a = Math.abs(dividend);
        int b = Math.abs(divisor);
        if (a == 0 || a < b) return 0;
        if (b == 1) return divisor > 0 ? dividend : -dividend;
        int times = 1, total = b;
        while (total + total < a) {
            total += total;
            times += times;
        }
        if (total + total == a) {
            times += times;
        } else {
            while (total + b <= a) {
                total += b;
                times += 1;
            }
        }
        return (dividend > 0) == (divisor > 0) ? times : -times;
    }

    /**************************** Solution 2: 改良 *******************************/
    /**
     * 循环找临界点times, 使得 2^times * B < A 且 2^(times+1) * B >= A
     * 每次times都从B的1倍开始查找
     */
    public int divide(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && divisor == -1) return Integer.MAX_VALUE;
        int a = Math.abs(dividend); // Math.abs(Integer.MIN_VALUE) == Integer.MIN_VALUE
        int b = Math.abs(divisor);
        int totalTimes = 0;
        while (a - b >= 0) {
            int times = 1, total = b;
            while (a - (total << 1) >= 0) { // a >= (total << 1) 不可以!!! 要依赖 2147483647 - (-2147483648)
                total <<= 1;
                times <<= 1;
            }
            a -= total; // 必须从a里面刨出去，新赋一个值累加上去和a比较会在最后一个<<1溢出
            totalTimes += times;
        }
        return (dividend > 0) == (divisor > 0) ? totalTimes : -totalTimes;
    }

    public static void main(String[] args) {
        DivideTwoIntegers solution = new DivideTwoIntegers();
        System.out.println(solution.divide(2, 2));
        System.out.println(solution.divide(2147483647, 1));
        System.out.println(solution.divide(2147483647, 2));
        System.out.println(solution.divide(10, 3));
        System.out.println(solution.divide(7, -3));
        System.out.println(solution.divide(-2147483648, 1));
        System.out.println(solution.divide(-2147483648, -1));
        System.out.println(solution.divide(1, -1));
        System.out.println(solution.divide(-1, 1));
        System.out.println(solution.divide(-1, -1));
        System.out.println(solution.divide(1, 1));

        System.out.println(Integer.toBinaryString(Integer.MIN_VALUE));
        System.out.println(Integer.toBinaryString(Integer.MAX_VALUE));
        System.out.println(-Integer.MIN_VALUE);
    }
}
