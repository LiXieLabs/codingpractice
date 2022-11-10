import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.stream.Collectors;

public class BuildingsWithAnOceanView {

    public int[] findBuildings(int[] heights) {
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < heights.length; i++) {
            while (!stack.isEmpty() && heights[stack.peek()] <= heights[i]) {
                stack.pop();
            }
            stack.push(i);
        }
        int[] res = new int[stack.size()];
        int i = res.length - 1;
        while (!stack.isEmpty()) res[i--] = stack.pop();
        return res;
    }

    public static String toStr(int[] res) {
        return Arrays.stream(res).mapToObj(String::valueOf).collect(Collectors.joining(","));
    }
    public static void main(String[] args) {
        BuildingsWithAnOceanView solution = new BuildingsWithAnOceanView();
        System.out.println("[" + toStr(solution.findBuildings(new int[]{4,2,3,1})) + "]");
        System.out.println("[" + toStr(solution.findBuildings(new int[]{4,3,2,1})) + "]");
        System.out.println("[" + toStr(solution.findBuildings(new int[]{1,3,2,4})) + "]");
    }
}
