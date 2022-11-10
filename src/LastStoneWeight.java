import java.util.PriorityQueue;

public class LastStoneWeight {

    /********* Solution 1: Simulation by maxHeap to keep 1st & 2nd largest ************/
    /**
     * Time: build maxHeap + loop to smash X 3 poll() = O(NlogN) + O(N) X O(3logN) = O(NlogN)
     * 第二个loop，每进行一次，至少减少一个stone，最多N-1次
     *
     * Space: O(N) by maxHeap
     */
    public int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>();
        for (int stone : stones) {
            maxHeap.offer(-stone);
        }
        while (maxHeap.size() > 1) {
            int newStone = -maxHeap.poll() + maxHeap.poll();
            if (newStone != 0) {
                maxHeap.offer(-newStone);
            }
        }
        return maxHeap.isEmpty() ? 0 : -maxHeap.poll();
    }

    /******** TODO: Solution 2 counting sort ***********/

    public static void main(String[] args) {
        LastStoneWeight solution = new LastStoneWeight();
        System.out.println(solution.lastStoneWeight(new int[]{2,7,4,1,8,1}));
        System.out.println(solution.lastStoneWeight(new int[]{1}));
    }
}
