import java.util.Comparator;
import java.util.PriorityQueue;

public class LastStoneWeight {

    /********* Solution 1: Simulation by maxHeap to keep 1st & 2nd largest ************/
    /**
     * Time: build maxHeap + loop to smash X 3 poll() = O(NlogN) + O(N) X O(3logN) = O(NlogN)
     * 第二个loop，每进行一次，至少减少一个stone，最多N-1次
     *
     * Space: O(N) by maxHeap
     */
    public int lastStoneWeight1(int[] stones) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        for (int stone : stones) {
            maxHeap.offer(stone);
        }
        while (maxHeap.size() > 1) {
            int x = maxHeap.poll(), y = maxHeap.poll();
            if (x > y) {
                maxHeap.offer(x - y);
            }
        }
        return maxHeap.size() > 0 ? maxHeap.poll() : 0;
    }

    /*****************  Solution 2 counting sort / bucket sort *******************/
    /**
     * Only if maxWeight is small!
     * bucket by weight
     *
     * Time: O(N + W)   Space: O(W)
     */
    public int lastStoneWeight(int[] stones) {
        int maxWeight = 0;
        for (int w : stones) maxWeight = Math.max(maxWeight, w);

        int[] cnt = new int[maxWeight + 1];
        for (int w : stones) cnt[w]++;

        int i = maxWeight;
        while (true) { // ⚠️注意⚠️ 只有这样才不要求过高 while 之外的 return statement
            while (i > 0 && cnt[i] == 0) i--;
            if (i == 0) return 0; // no stone at all
            int x = i;
            cnt[i]--;

            while (i > 0 && cnt[i] == 0) i--;
            if (i == 0) return x; // only 1 stone left
            int y = i;
            cnt[i]--;

            int d = x - y;
            if (d > 0) {
                cnt[d]++;
                if (d > i) i = d; // ⚠️注意⚠️ 不然 TC3 会错！！！
            }
        }
    }

    public static void main(String[] args) {
        LastStoneWeight solution = new LastStoneWeight();
        System.out.println(solution.lastStoneWeight(new int[]{2,7,4,1,8,1}));
        System.out.println(solution.lastStoneWeight(new int[]{1}));
        System.out.println(solution.lastStoneWeight(new int[]{1,3}));
    }
}
