import java.util.Arrays;

public class CountUniqueCharactersOfAllSubstringsOfAGivenString {

    public int uniqueLetterString(String s) {
        int[][] dp = new int[26][2];
        int res = 0;
        for (int i = 0; i < 26; i++) Arrays.fill(dp[i], -1);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            res += (dp[c - 'A'][1] - dp[c - 'A'][0]) * (i - dp[c - 'A'][1]);
            dp[c - 'A'] = new int[]{dp[c - 'A'][1], i};
        }
        for (int i = 0; i < 26; i++) {
            res += (dp[i][1] - dp[i][0]) * (s.length() - dp[i][1]);
        }
        return res;
    }

    public static void main(String[] args) {
        CountUniqueCharactersOfAllSubstringsOfAGivenString solution = new CountUniqueCharactersOfAllSubstringsOfAGivenString();
        System.out.println(solution.uniqueLetterString("ABC")); //10
        System.out.println(solution.uniqueLetterString("ABA")); //8
        System.out.println(solution.uniqueLetterString("LEETCODE")); //92
    }
}