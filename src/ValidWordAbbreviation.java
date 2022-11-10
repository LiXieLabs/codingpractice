public class ValidWordAbbreviation {

    public boolean validWordAbbreviation(String word, String abbr) {
        int i = 0, j = 0, num = 0;
        while (i < word.length()) {
            // 小心0开头的数字invalid情况！！！
            if (j < abbr.length() && abbr.charAt(j) == '0') return false;
            // 注意如何判断数字！！！
            while (j < abbr.length() && Character.isDigit(abbr.charAt(j))) {
                // 小心char变int是ascii码！！！
                num = num * 10 + Integer.valueOf(abbr.charAt(j++) - '0');
            }
            if (num > 0) {
                while (i < word.length() && num > 0) {
                    i++;
                    num--;
                }
            } else if (j == abbr.length() || word.charAt(i) != abbr.charAt(j)) {
                return false;
            } else {
                i++;
                j++;
            }
        }
        return j == abbr.length() && num == 0;
    }

    public static void main(String[] args) {
        ValidWordAbbreviation solution = new ValidWordAbbreviation();
        System.out.println(solution.validWordAbbreviation("internationalization", "i12iz4n"));
        System.out.println(solution.validWordAbbreviation("apple", "a2e"));
    }
}
