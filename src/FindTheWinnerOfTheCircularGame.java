import java.util.ArrayDeque;
import java.util.Deque;

public class FindTheWinnerOfTheCircularGame {

    /********* Solution 1: Simulation by Circular Queue *************/
    /**
     * 约瑟夫环问题 https://en.wikipedia.org/wiki/Josephus_problem
     * Simulation solution => Time: O(N X K)
     * TODO: Math solution => Time: O(N)
     */
    public int findTheWinner(int n, int k) {
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 1; i <= n; i++) {
            queue.offer(i);
        }
        while (queue.size() > 1) {
            for (int i = 0; i < k - 1; i++) {
                queue.offer(queue.poll());
            }
            queue.poll();
        }
        return queue.poll();
    }

    public static void main(String[] args) {
        FindTheWinnerOfTheCircularGame solution = new FindTheWinnerOfTheCircularGame();
        System.out.println(solution.findTheWinner(1, 1));
        System.out.println(solution.findTheWinner(5, 2));
        System.out.println(solution.findTheWinner(6, 5));
    }
}