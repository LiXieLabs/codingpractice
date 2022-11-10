public class SingleNumberII {

    public int singleNumber(int[] nums) {
        int x1 = 0, x2 = 0, mask = 0;
        for (int n : nums) {
            x2 ^= (x1 & n);
            x1 ^= n;
            mask = ~(x1 & x2);
            x1 &= mask;
            x2 &= mask;
        }
        return x1;
    }

    public static void main(String[] args) {
        SingleNumberII solution = new SingleNumberII();
        System.out.println(solution.singleNumber(new int[]{2,2,3,2}));
        System.out.println(solution.singleNumber(new int[]{0,1,0,1,0,1,99}));
    }
}
