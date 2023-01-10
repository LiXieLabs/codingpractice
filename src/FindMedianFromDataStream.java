import java.util.PriorityQueue;

/**
 * 295. Find Median from Data Stream (https://leetcode.com/problems/find-median-from-data-stream/)
 */
public class FindMedianFromDataStream {

    // minHeap holds larger elements
    // maxHeap holds smaller elements
    // keep 0 <= maxHeap.size() - minHeap.size() <= 1
    PriorityQueue<Integer> minHeap;
    PriorityQueue<Integer> maxHeap;

    public FindMedianFromDataStream() {
        minHeap = new PriorityQueue<>();
        maxHeap = new PriorityQueue<>((a, b) -> (b - a));
    }

    // Time: O(logN)
    public void addNum(int num) {
        if (maxHeap.size() == 0 || num <= maxHeap.peek()) {
            maxHeap.offer(num);
            if (maxHeap.size() - minHeap.size() > 1) {
                minHeap.offer(maxHeap.poll());
            }
        } else {
            minHeap.offer(num);
            if (minHeap.size() > maxHeap.size()) {
                maxHeap.offer(minHeap.poll());
            }
        }
    }

    // Time: O(1)
    public double findMedian() {
        if ((maxHeap.size() + minHeap.size()) % 2 == 0) {
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        } else {
            return maxHeap.peek();
        }
    }

    public static void main(String[] args) {
        FindMedianFromDataStream solution = new FindMedianFromDataStream();
        solution.addNum(1);
        System.out.println(solution.findMedian());
        solution.addNum(2);
        System.out.println(solution.findMedian());
        solution.addNum(3);
        System.out.println(solution.findMedian());
    }
}
