import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.stream.Collectors;

public class RevealCardsInIncreasingOrder {

    /********** Solution 1: Simulate the reverse process *************/
    /**
     * [2,13,3,11,5,17,7]
     *
     * sorted => [2,3,5,7,11,13,17]
     *
     * [17]
     * [13,17]
     * deque+enqueue 17 [17,13] -> enque 11 [11,17,13]
     * deque+enqueue 13 [13,11,17] -> enque 7 [7,13,11,17]
     * deque+enqueue 17 [17,7,13,11] -> enque 5 [5,17,7,13,11]
     * deque+enqueue 11 [11,5,17,7,13] -> enque 3 [3,11,5,17,7,13]
     * deque+enqueue 13 [13,3,11,5,17,7] -> enque 2 [2,13,3,11,5,17,7]
     *
     * Time: O(NlogN + N)   Space: O(N)
     */
    public int[] deckRevealedIncreasing(int[] deck) {
        Deque<Integer> queue = new ArrayDeque<>();
        int l = deck.length;
        Arrays.sort(deck);
        for (int i = l - 1; i >= 0; i--) {
            if (queue.size() >= 2) {
                queue.offer(queue.poll());
            }
            queue.offer(deck[i]);
        }
        int[] res = new int[l];
        for (int i = l - 1; i >= 0; i--) {
            res[i] = queue.poll();
        }
        return res;
    }

    /********** Solution 2: Recursive version of Solution 1 *************/
    int[] deck;
    int l;
    Deque<Integer> queue;

    public int[] deckRevealedIncreasing2(int[] deck) {
        Arrays.sort(deck);
        this.deck = deck;
        l = deck.length;
        queue = new ArrayDeque<>();
        recur(l - 1);

        int[] res = new int[l];
        for (int i = l - 1; i >= 0; i--) {
            res[i] = queue.poll();
        }
        return res;
    }

    private void recur(int i) {
        if (i < 0) return;

        if (queue.size() >= 2) {
            queue.offer(queue.poll());
        }

        queue.offer(deck[i]);

        recur(i - 1);
    }

    private static void print(int[] input) {
        System.out.println("[" + Arrays.stream(input).mapToObj(String::valueOf).collect(Collectors.joining(",")) + "]");
    }

    public static void main(String[] args) {
        RevealCardsInIncreasingOrder solution = new RevealCardsInIncreasingOrder();
        print(solution.deckRevealedIncreasing(new int[]{17,13,11,2,3,5,7}));
        print(solution.deckRevealedIncreasing(new int[]{}));
        print(solution.deckRevealedIncreasing(new int[]{1}));
        print(solution.deckRevealedIncreasing(new int[]{1,10}));
    }
}