import java.util.HashMap;
import java.util.Map;

public class DesignUndergroundSystem {

    static class UndergroundSystem {
        Map<String, Integer> ids;
        int currId = 0;
        Map<String, int[]> stats;
        Map<Integer, int[]> onTrain;

        public UndergroundSystem() {
            ids = new HashMap<>();
            currId = 0;
            stats = new HashMap<>();
            onTrain = new HashMap<>();
        }

        public void checkIn(int id, String stationName, int t) {
            if (!ids.containsKey(stationName)) {
                ids.put(stationName, currId++);
            }
            int sid = ids.get(stationName);
            onTrain.put(id, new int[]{sid, t});
        }

        public void checkOut(int id, String stationName, int t) {
            if (!ids.containsKey(stationName)) {
                ids.put(stationName, currId++);
            }
            int sid = ids.get(stationName);
            int[] start = onTrain.remove(id);
            String key = start[0] + "," + sid;
            int[] curr = stats.getOrDefault(key, new int[]{0, 0});
            curr[0] += t - start[1];
            curr[1] += 1;
            stats.put(key, curr);
        }

        public double getAverageTime(String startStation, String endStation) {
            int[] stat = stats.get(ids.get(startStation) + "," + ids.get(endStation));
            return stat[0] * 1.0 / stat[1];
        }
    }

    public static void main(String[] args) {
        UndergroundSystem solution = new UndergroundSystem();
        solution.checkIn(10,"Leyton",3);
        solution.checkOut(10,"Paradise",8);
        System.out.println(solution.getAverageTime("Leyton","Paradise"));

        solution.checkIn(5,"Leyton",10);
        solution.checkOut(5,"Paradise",16);
        System.out.println(solution.getAverageTime("Leyton","Paradise"));

        solution.checkIn(2,"Leyton",21);
        solution.checkOut(2,"Paradise",30);
        System.out.println(solution.getAverageTime("Leyton","Paradise"));
    }
}
