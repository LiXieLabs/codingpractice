import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class SingleThreadedCPU {

    /*********************** Solution 1: Line Sweep by Time + MinHeap + SecondarySort **************************/
    public int[] getOrder(int[][] tasks) {
        // MinHeap - enqueue time in ascendant order
        PriorityQueue<int[]> remainingTasks = new PriorityQueue<>(Comparator.comparingInt(t -> t[0]));
        for (int i = 0; i < tasks.length; i++) {
            remainingTasks.offer(new int[]{tasks[i][0], tasks[i][1], i});
        }

        // MinHeap + SecondarySort - process time and then index in ascendant order
        Comparator<int[]> first = Comparator.comparingInt(t -> t[1]);
        Comparator<int[]> second = Comparator.comparingInt(t -> t[2]);
        PriorityQueue<int[]> availableTasks = new PriorityQueue<>(first.thenComparing(second));

        int[] execOrder = new int[tasks.length];
        int currTime = 0, taskCount = 0;
        while (!remainingTasks.isEmpty() || !availableTasks.isEmpty()) {
            // 跳过idle时间
            if (availableTasks.isEmpty() && remainingTasks.peek()[0] > currTime) {
                int[] nextTask = remainingTasks.poll();
                availableTasks.offer(nextTask);
                currTime = nextTask[0];
            }
            // 将所有当前可执行但是还未执行的task从remainingTasks加入availableTasks
            while (!remainingTasks.isEmpty() && remainingTasks.peek()[0] <= currTime) {
                availableTasks.offer(remainingTasks.poll());
            }
            // 执行processTime最短，或者同样processTime下index最小的
            int[] currTask = availableTasks.poll();
            execOrder[taskCount++] = currTask[2];
            currTime += currTask[1];
        }
        return execOrder;
    }

    public void print(int[] result) {
        String s = "[" + Arrays.stream(result).mapToObj(String::valueOf).collect(Collectors.joining(",")) + "]";
        System.out.println(s);
    }


    public static void main(String[] args) {
        SingleThreadedCPU solution = new SingleThreadedCPU();
        solution.print(solution.getOrder(new int[][]{{1,2}, {2,4}, {3,2}, {4,1}}));
        solution.print(solution.getOrder(new int[][]{{7,10}, {7,12}, {7,5}, {7,4}, {7,2}}));
        solution.print(solution.getOrder(new int[][]{{1,2}, {3,4}, {2,3}, {4,1}}));
    }
}
