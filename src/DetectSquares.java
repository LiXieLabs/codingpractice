import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class DetectSquares {

    // 也可以不用map, 直接2D array做counter map = new int[1001][1001];
    Map<String, Integer> counter;
    // ⭐️⭐️⭐️ 注意: 不能用list，不然乘以倍数相当于重复计算 ⭐️⭐️⭐️
    Map<Integer, Set<Integer>> xToY;

    public DetectSquares() {
        this.counter = new HashMap<>();
        this.xToY = new HashMap<>();
    }

    public void add(int[] point) {
        String pointHash = toStr(point);
        this.counter.put(pointHash, this.counter.getOrDefault(pointHash, 0) + 1);
        this.xToY.putIfAbsent(point[0], new HashSet<>());
        this.xToY.get(point[0]).add(point[1]);
    }

    public int count(int[] point) {
        int total = 0;
        int x0 = point[0], y0 = point[1];
        if (this.xToY.containsKey(x0)) {
            for (int y1 : this.xToY.get(x0)) {
                // ⭐️⭐️⭐️ 注意: 面积为0的可能性 ⭐️⭐️⭐️
                if (y0 == y1) continue;
                int a = Math.abs(y0 - y1);
                // (x0, y0), (x0, y1), (x0-|y0-y1|, y0), (x0-|y0-y1|, y1)
                total += this.counter.getOrDefault(toStr(new int[]{x0, y1}), 0)
                        * this.counter.getOrDefault(toStr(new int[]{x0 - a, y0}), 0)
                        * this.counter.getOrDefault(toStr(new int[]{x0 - a, y1}), 0);
                // (x0, y0), (x0, y1), (x0+|y0-y1|, y0), (x0+|y0-y1|, y1)
                total += this.counter.getOrDefault(toStr(new int[]{x0, y1}), 0)
                        * this.counter.getOrDefault(toStr(new int[]{x0 + a, y0}), 0)
                        * this.counter.getOrDefault(toStr(new int[]{x0 + a, y1}), 0);
            }
        }
        return total;
    }

    // ⭐️⭐️⭐️ 注意: int array 不能直接做 key 是地址, Array.hashCode 有conflict ⭐️⭐️⭐️
    private String toStr(int[] array) {
        return Arrays.stream(array).mapToObj(String::valueOf).collect(Collectors.joining(","));
    }

    public static void main(String[] args) {
        DetectSquares detectSquares = new DetectSquares();
//        detectSquares.add(new int[]{3, 10});
//        detectSquares.add(new int[]{11, 2});
//        detectSquares.add(new int[]{3, 2});
//        System.out.println(detectSquares.count(new int[]{11, 10})); // return 1. You can choose:
//                                                                    //   - The first, second, and third points
//        System.out.println(detectSquares.count(new int[]{14, 8}));  // return 0. The query point cannot form a square with any points in the data structure.
//        detectSquares.add(new int[]{11, 2});    // Adding duplicate points is allowed.
//        System.out.println(detectSquares.count(new int[]{11, 10})); // return 2. You can choose:
//                                                                    //   - The first, second, and third points
//                                                                    //   - The first, third, and fourth points

        detectSquares.add(new int[]{933,325});
        detectSquares.add(new int[]{336,922});
        detectSquares.add(new int[]{336,325});
        detectSquares.count(new int[]{933,922});

        detectSquares.add(new int[]{697,201});
        detectSquares.add(new int[]{697,845});
        detectSquares.add(new int[]{53,845});
        detectSquares.count(new int[]{53,201});

        detectSquares.add(new int[]{856,14});
        detectSquares.add(new int[]{85,785});
        detectSquares.add(new int[]{856,785});
        detectSquares.count(new int[]{85,14});

        detectSquares.add(new int[]{361,55});
        detectSquares.add(new int[]{361,678});
        detectSquares.add(new int[]{984,678});
        detectSquares.count(new int[]{984,55});

        detectSquares.add(new int[]{343,689});
        detectSquares.add(new int[]{587,445});
        detectSquares.add(new int[]{343,445});
        detectSquares.count(new int[]{587,689});

        detectSquares.add(new int[]{198,967});
        detectSquares.add(new int[]{946,219});
        detectSquares.add(new int[]{198,219});
        detectSquares.count(new int[]{946,967});

        detectSquares.add(new int[]{998,893});
        detectSquares.add(new int[]{138,893});
        detectSquares.add(new int[]{998,33});
        detectSquares.count(new int[]{138,33});

        detectSquares.add(new int[]{860,875});
        detectSquares.add(new int[]{183,198});
        detectSquares.add(new int[]{183,875});
        detectSquares.count(new int[]{860,198});

        detectSquares.add(new int[]{127,18});
        detectSquares.add(new int[]{943,18});
        detectSquares.add(new int[]{127,834});
        detectSquares.count(new int[]{943,834});

        detectSquares.add(new int[]{703,347});
        detectSquares.add(new int[]{636,280});
        detectSquares.add(new int[]{703,280});
        detectSquares.count(new int[]{636,347});

        detectSquares.add(new int[]{805,799});
        detectSquares.add(new int[]{213,207});
        detectSquares.add(new int[]{213,799});
        detectSquares.count(new int[]{805,207});

        detectSquares.add(new int[]{555,301});
        detectSquares.add(new int[]{555,722});
        detectSquares.add(new int[]{976,301});
        detectSquares.count(new int[]{976,722});

        detectSquares.add(new int[]{983,928});
        detectSquares.add(new int[]{63,8});
        detectSquares.add(new int[]{63,928});
        detectSquares.count(new int[]{983,8});

        detectSquares.add(new int[]{410,195});
        detectSquares.add(new int[]{369,236});
        detectSquares.add(new int[]{369,195});
        detectSquares.count(new int[]{410,236});

        detectSquares.add(new int[]{665,249});
        detectSquares.add(new int[]{665,848});
        detectSquares.add(new int[]{66,848});
        detectSquares.count(new int[]{66,249});

        detectSquares.add(new int[]{491,722});
        detectSquares.add(new int[]{127,358});
        detectSquares.add(new int[]{491,358});
        detectSquares.count(new int[]{127,722});

        detectSquares.add(new int[]{961,933});
        detectSquares.add(new int[]{961,54});
        detectSquares.add(new int[]{82,54});
        detectSquares.count(new int[]{82,933});

        detectSquares.add(new int[]{879,166});
        detectSquares.add(new int[]{46,166});
        detectSquares.add(new int[]{879,999});
        detectSquares.count(new int[]{46,999});

        detectSquares.add(new int[]{28,5});
        detectSquares.add(new int[]{995,972});
        detectSquares.add(new int[]{995,5});
        detectSquares.count(new int[]{28,972});

        detectSquares.add(new int[]{973,452});
        detectSquares.add(new int[]{562,863});
        detectSquares.add(new int[]{973,863});
        detectSquares.count(new int[]{562,452});

        detectSquares.add(new int[]{916,135});
        detectSquares.add(new int[]{482,569});
        detectSquares.add(new int[]{916,569});
        detectSquares.count(new int[]{482,135});

        detectSquares.add(new int[]{38,712});
        detectSquares.add(new int[]{38,61});
        detectSquares.add(new int[]{689,712});
        detectSquares.count(new int[]{689,61});

        detectSquares.add(new int[]{875,823});
        detectSquares.add(new int[]{242,823});
        detectSquares.add(new int[]{875,190});
        detectSquares.count(new int[]{242,190});

        detectSquares.add(new int[]{660,506});
        detectSquares.add(new int[]{660,277});
        detectSquares.add(new int[]{889,506});
        detectSquares.count(new int[]{889,277});

        detectSquares.add(new int[]{294,129});
        detectSquares.add(new int[]{294,755});
        detectSquares.add(new int[]{920,755});
        detectSquares.count(new int[]{920,129});

        detectSquares.add(new int[]{247,117});
        detectSquares.add(new int[]{789,117});
        detectSquares.add(new int[]{789,659});
        detectSquares.count(new int[]{247,659});

        detectSquares.add(new int[]{918,686});
        detectSquares.add(new int[]{488,686});
        detectSquares.add(new int[]{918,256});
        detectSquares.count(new int[]{488,256});

        detectSquares.add(new int[]{95,438});
        detectSquares.add(new int[]{548,891});
        detectSquares.add(new int[]{548,438});
        detectSquares.count(new int[]{95,891});

        detectSquares.add(new int[]{102,416});
        detectSquares.add(new int[]{459,416});
        detectSquares.add(new int[]{102,773});
        detectSquares.count(new int[]{459,773});

        detectSquares.add(new int[]{245,999});
        detectSquares.add(new int[]{863,381});
        detectSquares.add(new int[]{863,999});
        detectSquares.count(new int[]{245,381});

        detectSquares.add(new int[]{60,971});
        detectSquares.add(new int[]{79,971});
        detectSquares.add(new int[]{60,952});
        detectSquares.count(new int[]{79,952});

        detectSquares.add(new int[]{255,511});
        detectSquares.add(new int[]{255,14});
        detectSquares.add(new int[]{752,511});
        detectSquares.count(new int[]{752,14});

        detectSquares.add(new int[]{404,775});
        detectSquares.add(new int[]{404,589});
        detectSquares.add(new int[]{590,775});
        detectSquares.count(new int[]{590,589});

        detectSquares.add(new int[]{734,924});
        detectSquares.add(new int[]{165,924});
        detectSquares.add(new int[]{165,355});
        detectSquares.count(new int[]{734,355});

        detectSquares.add(new int[]{613,71});
        detectSquares.add(new int[]{613,444});
        detectSquares.add(new int[]{240,444});
        detectSquares.count(new int[]{240,71});

        detectSquares.add(new int[]{888,984});
        detectSquares.add(new int[]{664,760});
        detectSquares.add(new int[]{888,760});
        detectSquares.count(new int[]{664,984});

        detectSquares.add(new int[]{798,486});
        detectSquares.add(new int[]{449,835});
        detectSquares.add(new int[]{798,835});
        detectSquares.count(new int[]{449,486});

        detectSquares.add(new int[]{826,348});
        detectSquares.add(new int[]{207,348});
        detectSquares.add(new int[]{826,967});
        detectSquares.count(new int[]{207,967});

        detectSquares.add(new int[]{830,993});
        detectSquares.add(new int[]{830,495});
        detectSquares.add(new int[]{332,993});
        detectSquares.count(new int[]{332,495});
    }
}
