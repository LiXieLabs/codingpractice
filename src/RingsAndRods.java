import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class RingsAndRods {

    public int countPoints(String rings) {
        Map<Character, Integer> masks = new HashMap<>(); // bit mask r = 100; g = 010; b = 001;
        masks.put('R', 4);
        masks.put('G', 2);
        masks.put('B', 1);
        int[] rods = new int[10];
        for (int i = 0; i < rings.length(); i += 2) {
            // Integer.valueOf(char) 或者 (int) char 返回的是ascii码！！！
            // Integer.valueOf(String.valueOf(char)) 返回的是Integer，不能做array index
            int r = Integer.parseInt(String.valueOf(rings.charAt(i+1)));
            rods[r] |= masks.get(rings.charAt(i));
        }
        // stream count 返回的是 long！！！需要cast to int！！！
        // return (int) Stream.of(rods).flatMapToInt(Arrays::stream).filter(rod -> rod == 7).count();
        return (int) Arrays.stream(rods).filter(rod -> rod == 7).count();
    }

    public static void main(String[] args) {
        RingsAndRods solution = new RingsAndRods();
        System.out.println(solution.countPoints("B0B6G0R6R0R6G9"));
        System.out.println(solution.countPoints("B0R0G0R9R0B0G0"));
        System.out.println(solution.countPoints("G4"));
    }
}
