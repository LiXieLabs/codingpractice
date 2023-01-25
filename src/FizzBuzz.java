import java.util.ArrayList;
import java.util.List;

/**
 * 412. Fizz Buzz (https://leetcode.com/problems/fizz-buzz/description/)
 */
public class FizzBuzz {

    /************ Solution 1: StringBuilder append **************/
    /**
     * 优点：适用于条件比整除3和5多很多的情况
     * 缺点：有点慢，因为每个都要重新append结果
     *
     * Time: O(N)  Space: O(1)
     */
    public List<String> fizzBuzz1(int n) {
        List<String> res = new ArrayList<>();
        StringBuilder sb;
        for (int i = 1; i <= n; i++) {
            sb = new StringBuilder();
            if (i % 3 == 0) sb.append("Fizz");
            if (i % 5 == 0) sb.append("Buzz");
            res.add(sb.length() == 0 ? String.valueOf(i) : sb.toString());
        }
        return res;
    }

    /************ Solution 2: Hash by Array **************/
    /**
     * 整除 3 => 累计 1，否则 0
     * 整除 5 => 累计 2，否则 0
     * 整除 3 & 5 => 累计 1 + 2 = 3
     * 都不能整除 => 累计 0 + 0 = 0
     * 累计得分对应 array hash map 的 index
     *
     * Time: O(N)  Space: O(1)
     */
    public List<String> fizzBuzz(int n) {
        List<String> res = new ArrayList<>();
        String[] map = new String[]{"", "Fizz", "Buzz", "FizzBuzz"};
        for (int i = 1; i <= n; i++) {
            String s = map[(i % 3 == 0 ? 1 : 0) + (i % 5 == 0 ? 2 : 0)];
            res.add(s.isEmpty() ? String.valueOf(i) : s);
        }
        return res;
    }

    public static void main(String[] args) {
        FizzBuzz solution = new FizzBuzz();
        System.out.println(solution.fizzBuzz(3)); // [1, 2, Fizz]
        System.out.println(solution.fizzBuzz(5)); // [1, 2, Fizz, 4, Buzz]
        System.out.println(solution.fizzBuzz(15)); // [1, 2, Fizz, 4, Buzz, Fizz, 7, 8, Fizz, Buzz, 11, Fizz, 13, 14, FizzBuzz]
    }
}
