/**
 * 67. Add Binary (https://leetcode.com/problems/add-binary/description/)
 */
public class AddBinary {

    /***************** Solution 1: StringBuilder ******************/
    /**
     * Time:O(max(A,B))  Space: O(max(A,B))
     */
    // NOTE!!! In Java, char和int可以自由cast，char对应的int即为起ASCII序号
    public String addBinary(String a, String b) {
        int ia = a.length() - 1, ib = b.length() - 1, carry = 0;
        StringBuilder sb = new StringBuilder();
        while (ia >= 0 || ib >= 0 || carry > 0) {
            if (ia >= 0) carry += a.charAt(ia--) - '0';
            if (ib >= 0) carry += b.charAt(ib--) - '0';
            // 也可以：
//            if (ia >= 0 && a.charAt(ia--) == '1') carry++;
//            if (ib >= 0 && b.charAt(ib--) == '1') carry++;
            sb.append(carry % 2);
            carry /= 2;
        }
        return sb.reverse().toString();
    }

    public static void main(String[] args) {
        AddBinary solution = new AddBinary();
        System.out.println(solution.addBinary("11", "1")); // 100
        System.out.println(solution.addBinary("1010", "1011")); // 10101
    }
}