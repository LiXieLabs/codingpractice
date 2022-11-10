import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IntegerToEnglishWords {

    static String[] Ones = new String[]{
            "Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
            "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen",
            "Eighteen", "Nineteen"
    };
    static String[] Tens = new String[]{
            "", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"
    };
    static String[] Units = new String[]{
            "", "Thousand", "Million", "Billion"
    };

    public String numberToWords(int num) {
        if (num == 0) return Ones[num];
        List<String> res = new ArrayList<>();
        int i = 0;
        while (num > 0) {
            if (num % 1000 > 0) {
                if (i > 0) res.add(Units[i]); // 有trim可以不用if判断
                res.add(build(num % 1000));
            }
            num /= 1000;
            i++;
        }
        Collections.reverse(res);
        return String.join(" ", res).trim();
    }

    public String build(int n) {
        List<String> res = new ArrayList<>();
        if (n / 100 > 0) {
            res.add(Ones[n/100]);
            res.add("Hundred");
            n %= 100;
        }
        if (n >= 20) {
            res.add(Tens[n/10]);
            n %= 10;
        }
        if (n > 0) {
            res.add(Ones[n]);
        }
        return String.join(" ", res).trim();
    }

    public static void main(String[] args) {
        IntegerToEnglishWords solution = new IntegerToEnglishWords();
        System.out.println(solution.numberToWords(0));
        System.out.println(solution.numberToWords(123));
        System.out.println(solution.numberToWords(12345678));
        System.out.println(solution.numberToWords(1123123123));
        System.out.println(solution.numberToWords(1000));
        System.out.println(solution.numberToWords(123000123));
    }
}
