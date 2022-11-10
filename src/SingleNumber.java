public class SingleNumber {

    public int singleNumber(int[] nums) {
        int filter = 0;
        for (int n : nums) {
            filter ^= n;
        }
        return filter;
    }

    public static void main(String[] args) {
        SingleNumber solution = new SingleNumber();
        System.out.println(solution.singleNumber(new int[]{4,2,1,2,1}));
        System.out.println(solution.singleNumber(new int[]{-4,2,-11,2,-11}));
    }
}
