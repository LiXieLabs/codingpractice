public class RepeatedSubstringPattern {

    // 27ms 53.9MB
    public boolean repeatedSubstringPattern(String s) {
        if (s.length() == 1) return false;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(0) && (s.length() - i) % i == 0) {
                int start = i;
                while (start < s.length() && s.substring(0, i).equals(s.substring(start, start + i))) {
                    start += i;
                }
                if (start == s.length()) return true;
            }
        }
        return false;
    }

    // 7ms 42.9MB - BEST
    public boolean repeatedSubstringPattern1(String s) {
        for (int i = 1; i <= s.length() / 2; i++) {
            if (s.charAt(i) == s.charAt(0) && s.length() % i == 0) {
                String pattern = s.substring(0, i);
                int start = i;
                while (start < s.length() && pattern.equals(s.substring(start, start + i))) {
                    start += i;
                }
                if (start == s.length()) return true;
            }
        }
        return false;
    }

    // 23ms 54.2MB
    public boolean repeatedSubstringPattern2(String s) {
        for (int i = 1; i <= s.length() / 2; i++) {
            if (s.charAt(i) == s.charAt(0) && s.length() % i == 0) {
                String pattern = s.substring(0, i);
                StringBuilder sb = new StringBuilder(pattern + pattern);
                while (sb.length() < s.length()) {
                    sb.append(pattern);
                }
                if (sb.toString().equals(s)) return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        RepeatedSubstringPattern solution = new RepeatedSubstringPattern();
        System.out.println(solution.repeatedSubstringPattern2("a"));
        System.out.println(solution.repeatedSubstringPattern2("abab"));
        System.out.println(solution.repeatedSubstringPattern2("aba"));
        System.out.println(solution.repeatedSubstringPattern2("abcabcabcabc"));
    }
}
