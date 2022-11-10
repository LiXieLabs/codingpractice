import java.util.*;

public class BusRoutes {

    /******************* Solution 1: BFS ********************/
    /**
     * 对于每个当前队列中的station，找到经过它的所有bus route，
     * 对于每个bus，将其能到达的所有station加入next level queue。
     *
     * Time: O(N) Space: O(N)
     * N denotes number of unique stations in graph.
     */
    public int numBusesToDestination(int[][] routes, int source, int target) {
        Map<Integer, List<Integer>> stationToBus = new HashMap<>();
        for (int i = 0; i < routes.length; i++) {
            for (int station: routes[i]) {
                stationToBus.putIfAbsent(station, new ArrayList<>());
                stationToBus.get(station).add(i);
            }
        }
        Set<Integer> visitedBus = new HashSet<>();
        Set<Integer> visitedStation = new HashSet<>();
        Deque<Integer> queue = new ArrayDeque<>();
        queue.push(source);
        visitedStation.add(source);
        int tookBus = 0;
        while (queue.size() != 0) {
            Deque<Integer> nextQ = new ArrayDeque<>();
            for (int station : queue) {
                // 不能放在next station的if里面，不然起点即终点的情况无解！！！
                if (station == target) return tookBus;
                for (int bus : stationToBus.get(station)) {
                    if (!visitedBus.contains(bus)) {
                        for (int nextStation : routes[bus]) {
                            if (!visitedStation.contains(nextStation)) {
                                nextQ.offer(nextStation);
                                visitedStation.add(nextStation);
                            }
                        }
                        visitedBus.add(bus);
                    }
                }
            }
            queue = nextQ;
            tookBus++;
        }
        return -1;
    }

    public static void main(String[] args) {
        BusRoutes solution = new BusRoutes();
        System.out.println(solution.numBusesToDestination(new int[][]{{1,2,7},{3,6,7}}, 1, 6));
        System.out.println(solution.numBusesToDestination(new int[][]{{7,12},{4,5,15},{6},{15,19},{9,12,13}}, 15, 12));
        System.out.println(solution.numBusesToDestination(new int[][]{{1,5},{3,5}}, 5, 5));
    }

}
