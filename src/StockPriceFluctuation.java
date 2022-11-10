import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class StockPriceFluctuation {

    // ⭐️⭐️⭐️ NOTE ⭐️⭐️⭐️
    // 1. TreeMap Java doc: https://docs.oracle.com/javase/8/docs/api/java/util/TreeMap.html
    //    containsKey, get, put, remove, lastKey, firstKey => O(logN)
    // 2. 此处TreeMap实际上是Black-Red Tree实现的Binary Search Tree, 并非MinMaxHeap
    //    前者可排序外还可以random access任意key更强大，但是后者O(1)拿到最大最小值
    //    MinMaxHeap的结构是 https://www.baeldung.com/java-min-max-heap

    // 如果不用 TreeMap，也可以用两个 PriorityQueue, 一个minHeap，一个maxHeap
    // heap按照price排序，但当对应的timestamp和timeToPrice的HashMap中记录不一致，则pop出去，不计入结果

    /********************************** Solution 1 ***************************************/
    private TreeMap<Integer, Integer> minMaxHeap;
    private Map<Integer, Integer> timeToPrice;
    private Integer latestTime;

    public StockPriceFluctuation() {
        this.minMaxHeap = new TreeMap<>(); // stockPrice => count of timestamps
        this.timeToPrice = new HashMap<>(); // timestamp => stockPrice
        this.latestTime = 0;
    }

    public void update(int timestamp, int price) {
        // 存在且没变，直接退出
        if (this.timeToPrice.getOrDefault(timestamp, 0) == price) return;
        // 原来价格的heap entry更新
        if (this.timeToPrice.containsKey(timestamp)) {
            Integer prePrice = this.timeToPrice.get(timestamp);
            int count = this.minMaxHeap.get(prePrice) - 1;
            if (count == 0) {
                this.minMaxHeap.remove(prePrice);
            } else {
                this.minMaxHeap.replace(prePrice, count);
            }
        }
        // 新价格的map和heap更新
        this.timeToPrice.put(timestamp, price);
        this.minMaxHeap.put(price, this.minMaxHeap.getOrDefault(price, 0) + 1);
        // 更新最近价格
        if (timestamp >= this.latestTime) {
            this.latestTime = timestamp;
        }
    }

    public int current() {
        return this.timeToPrice.get(this.latestTime);
    }

    public int maximum() {
        return this.minMaxHeap.lastKey();
    }

    public int minimum() {
        return this.minMaxHeap.firstKey();
    }

    public static void main(String[] args) {
        StockPriceFluctuation solution = new StockPriceFluctuation();
        solution.update(1, 10);
        solution.update(2, 5);
        System.out.println(solution.current());
        System.out.println(solution.maximum());
        solution.update(1, 3);
        System.out.println(solution.maximum());
        solution.update(4, 2);
        System.out.println(solution.minimum());
    }
}
