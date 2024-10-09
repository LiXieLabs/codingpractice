/**
 * 29. Divide Two Integers (https://leetcode.com/problems/divide-two-integers/description/)
 */
public class DivideTwoIntegers {

    /**************************** Solution 1: TLE!!!!! *******************************/
    /**
     * 先找到临界点times, 使得 2^times * B =< A 且 2^(times+1) * B > A
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
     * 循环找临界点times, 使得 2^times * B <= A 且 2^(times+1) * B > A
     * 每次times都从B的1倍开始查找
     *
     * e.g. divide(93706, 157)
     *
     * 1st Loop:
     * 1 157
     * 2 314
     * 4 628
     * 8 1256
     * 16 2512
     * 32 5024
     * 64 10048
     * 128 20096
     * 256 40192
     * 512 80384
     * total times: 512; dividend: 13322
     *
     * 2nd Loop
     * 1 157
     * 2 314
     * 4 628
     * 8 1256
     * 16 2512
     * 32 5024
     * 64 10048
     * total times: 576; dividend: 3274
     *
     * 3rd Loop
     * 1 157
     * 2 314
     * 4 628
     * 8 1256
     * 16 2512
     * total times: 592; dividend: 762
     *
     * 4th Loop
     * 1 157
     * 2 314
     * 4 628
     * total times: 596; dividend: 134
     *
     * Time: logN
     */
    public int divide(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && divisor == -1) return Integer.MAX_VALUE; // 必须单独处理，不然totalTimes会溢出!!!
        int a = Math.abs(dividend); // Math.abs(Integer.MIN_VALUE) == Integer.MIN_VALUE
        int b = Math.abs(divisor);
        int totalTimes = 0;
        while (a - b >= 0) { // a >= b 不可以!!! Integer.MIN_VALUE - 1 = Integer.MAX_VALUE
            int times = 1, total = b;
            System.out.println(times + " " + total);
            while (a - (total << 1) >= 0) { // a >= (total << 1) 不可以!!! 要依赖 2147483647 - (-2147483648)
                total <<= 1;
                times <<= 1;
//                System.out.println(times + " " + total);
            }
            a -= total; // 必须从a里面刨出去，新赋一个值累加上去和a比较会在最后一个<<1溢出
            totalTimes += times;
//            System.out.println("total times: " + totalTimes + "; dividend: " + a);
        }
        return (dividend > 0) == (divisor > 0) ? totalTimes : -totalTimes;
    }

    public static void main(String[] args) {
        DivideTwoIntegers solution = new DivideTwoIntegers();
        System.out.println(solution.divide(93706, 157)); // 596
        System.out.println(solution.divide(2, 2)); // 1
        System.out.println(solution.divide(2147483647, 1)); // 2147483647
        System.out.println(solution.divide(2147483647, 2)); // 1073741823
        System.out.println(solution.divide(10, 3)); // 3
        System.out.println(solution.divide(7, -3)); // -2
        System.out.println(solution.divide(-2147483648, 1)); // -2147483648
        System.out.println(solution.divide(-2147483648, -1)); // 2147483647
        System.out.println(solution.divide(-2147483648, -2)); // 1073741824
        System.out.println(solution.divide(1, -1)); // -1
        System.out.println(solution.divide(-1, 1)); // -1
        System.out.println(solution.divide(-1, -1)); // 1
        System.out.println(solution.divide(1, 1)); // 1

        System.out.println(Integer.toBinaryString(Integer.MIN_VALUE)); // 10000000000000000000000000000000
        System.out.println(Integer.toBinaryString(Integer.MAX_VALUE)); //  1111111111111111111111111111111
        System.out.println(-Integer.MIN_VALUE); // -2147483648
        System.out.println(Math.abs(Integer.MIN_VALUE)); // -2147483648
        System.out.println(Integer.MIN_VALUE - 1); // 2147483647
    }
}
