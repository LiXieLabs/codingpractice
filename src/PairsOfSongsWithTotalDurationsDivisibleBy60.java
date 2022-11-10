import java.util.HashMap;
import java.util.Map;

public class PairsOfSongsWithTotalDurationsDivisibleBy60 {

    /****************************** Solution 1 **************************/
    /**
     * (A + B) % 60 == 0 <====> (A % 60) + (B % 60) == 60 除非本身可以整除60
     * Time: O(N) Space: O(1) max 60 modulo
     */
    public int numPairsDivisibleBy60(int[] time) {
        int res = 0;
        Map<Integer, Integer> counter = new HashMap<>();
        for (int t : time) {
            int mod = t % 60;
            // 必须 (60 - mod) % 60, 否则0就会去找60，而不是0了
            res += counter.getOrDefault((60 - mod) % 60, 0);
            counter.put(mod, counter.getOrDefault(mod, 0) + 1);
        }
        return res;
    }

    public static void main(String[] args) {
        PairsOfSongsWithTotalDurationsDivisibleBy60 solution = new PairsOfSongsWithTotalDurationsDivisibleBy60();
        System.out.println(solution.numPairsDivisibleBy60(new int[]{30,20,150,100,40}));
        System.out.println(solution.numPairsDivisibleBy60(new int[]{60,60,60}));
    }
}
