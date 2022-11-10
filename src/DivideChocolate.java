import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DivideChocolate {

    // Similar as 410. Split Array Largest Sum

    private int[] sweetness;
    private Map<String, Integer> memo;

    /************ Solution 1: Recur + Memo => TLE :( *****************/
    public int maximizeSweetness1(int[] sweetness, int k) {
        this.sweetness = sweetness;
        memo = new HashMap<>();
        return recur(0, k+1);
    }

    public int recur(int start, int remain) {
        int total = 0, maxSweet = Integer.MIN_VALUE;
        if (remain == 1) {
            while (start < sweetness.length) {
                total += sweetness[start++];
            }
            return total;
        }
        String key = start + "," + remain;
        if (!memo.containsKey(key)) {
            for (int i = start; i < sweetness.length - (remain - 1); i++) {
                total += sweetness[i];
                if (total < maxSweet) continue;
                int nextMin = recur(i + 1, remain - 1);
                maxSweet = Math.max(maxSweet, Math.min(total, nextMin));
            }
            memo.put(key, maxSweet);
        }
        return memo.get(key);
    }

    /*********** Solution 2: Binary Search *******************/
    /**
     * N denotes # of chunks, S denotes total sweetness
     * Time: O(N * log(S/(k+1)))
     * Space: O(1)
     */
    public int maximizeSweetness(int[] sweetness, int k) {
        this.sweetness = sweetness;
        int lo = Arrays.stream(sweetness).min().getAsInt();
        int hi = Arrays.stream(sweetness).sum() / (k+1);
        while (lo < hi) {
            int mid = (lo + hi + 1) / 2;
            if (canDivide(mid, k + 1)) {
                lo = mid;
            } else {
                hi = mid - 1;
            }
        }
        return lo;
    }

    public boolean canDivide(int throttle, int k) {
        int curSum = 0, i = 0;
        while (k > 0) {
            if (i == sweetness.length) return false;
            curSum += sweetness[i++];
            if (curSum >= throttle) {
                curSum = 0;
                k--;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        DivideChocolate solution = new DivideChocolate();
        System.out.println(solution.maximizeSweetness(new int[]{1,2,3,4,5,6,7,8,9}, 5));
        System.out.println(solution.maximizeSweetness(new int[]{5,6,7,8,9,1,2,3,4}, 8));
        System.out.println(solution.maximizeSweetness(new int[]{1,2,2,1,2,2,1,2,2}, 2));

    }
}
