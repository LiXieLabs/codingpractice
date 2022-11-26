public class ReverseString {

    /********* Solution 1: Two Pointers **************/
    /**
     * Time: O(N)   Space: O(1)
     */
    public void reverseString(char[] s) {
        int l = 0, r = s.length - 1;
        while (l < r) {
            char temp = s[l];
            s[l++] = s[r];
            s[r--] = temp;
        }
    }

    public static void main(String[] args) {
        ReverseString solution = new ReverseString();

        char[] s1 = new char[]{'h','e','l','l','o'};
        solution.reverseString(s1);
        System.out.println(s1);

        char[] s2 = new char[]{};
        solution.reverseString(s2);
        System.out.println(s2);

        char[] s3 = new char[]{'a'};
        solution.reverseString(s3);
        System.out.println(s3);

        char[] s4 = new char[]{'H','a','n','n','a','h'};
        solution.reverseString(s4);
        System.out.println(s4);
    }
}
