import java.util.ArrayDeque;
import java.util.Deque;

public class MovingAverageFromDataStream {

    /************************* Solution 1: Queue **************************/
    /**
     * Time: O(1) Space: O(N)
     */
//    Deque<Integer> queue;
//    int sum;
//    int maxSize;
//
//    public MovingAverageFromDataStream(int size) {
//        this.queue = new ArrayDeque<>();
//        this.sum = 0;
//        this.maxSize = size;
//    }
//
//    public double next(int val) {
//        this.queue.offer(val);
//        this.sum += val;
//        if (this.queue.size() > this.maxSize) {
//            this.sum -= this.queue.poll();
//        }
//        return (double) this.sum / this.queue.size();
//    }

    /************************* Solution 2: Array + Mod Rotating Index **************************/
    /**
     * Time: O(1) Space: O(N)
     */
    int[] queue;
    int sum;
    int i;
    int n;

    public MovingAverageFromDataStream(int size) {
        this.queue = new int[size];
        this.sum = 0;
        this.i = 0;
        this.n = 0;
    }

    public double next(int val) {
        if (this.n < this.queue.length) this.n++;
        this.sum -= this.queue[this.i];
        this.queue[this.i] = val;
        this.sum += this.queue[this.i++];
        this.i %= this.queue.length;
        return (double) this.sum / n;
    }


    public static void main(String[] args) {
        MovingAverageFromDataStream solution = new MovingAverageFromDataStream(3);
        System.out.println(solution.next(1));
        System.out.println(solution.next(10));
        System.out.println(solution.next(3));
        System.out.println(solution.next(5));
    }
}
