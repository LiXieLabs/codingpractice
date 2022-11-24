import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class IntervalListIntersections {

    /***************** Solution 1: Intersection of Intervals by Two Pointers ****************/
    /**
     * Time: O(M + N)   Space: O(1)
     */
    public int[][] intervalIntersection1(int[][] firstList, int[][] secondList) {
        int p1 = 0, p2 = 0;
        List<int[]> res = new ArrayList<>();
        while (p1 < firstList.length && p2 < secondList.length) {
            // e1 < s2 || e2 < s1 无交集，向前移动
            if (firstList[p1][1] < secondList[p2][0]) {
                p1++;
            } else if (secondList[p2][1] < firstList[p1][0]) {
                p2++;
            } else { // e1 >= s2 && e2 >= s1 有交集，计入结果，并向前移动
                res.add(new int[]{
                        Math.max(firstList[p1][0], secondList[p2][0]),
                        Math.min(firstList[p1][1], secondList[p2][1])
                });
                if (firstList[p1][1] > secondList[p2][1]) {
                    p2++;
                } else {
                    p1++;
                }
            }
        }
        return res.toArray(new int[res.size()][2]);
    }

    /***************** Solution 2: Intersection of Intervals by Two Pointers 优化 ****************/
    /**
     * Solution 1 的代码优化
     */
    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        int p1 = 0, p2 = 0;
        List<int[]> res = new ArrayList<>();
        while (p1 < firstList.length && p2 < secondList.length) {
            int s1 = firstList[p1][0], e1 = firstList[p1][1];
            int s2 = secondList[p2][0], e2 = secondList[p2][1];
            int maxStart = Math.max(s1, s2);
            int minEnd = Math.min(e1, e2);
            // e1 >= s2 && e2 >= s1
            if (minEnd >= maxStart) res.add(new int[]{maxStart, minEnd});
            // e1 == e2 可以两个都向前移动，因为两个list都是disjoint的
            if (e1 >= e2) p2++;
            if (e2 >= e1) p1++;
        }
        return res.toArray(new int[res.size()][2]);
    }

    private static void print(int[][] input) {
        System.out.println("["
                + Arrays.stream(input).map(p -> "[" + p[0] + "," + p[1] + "]").collect(Collectors.joining(","))
                + "]");
    }

    public static void main(String[] args) {
        IntervalListIntersections solution = new IntervalListIntersections();

        // TC1
        print(solution.intervalIntersection(
                new int[][]{{0,2},{5,10},{13,23},{24,25}},
                new int[][]{{1,5},{8,12},{15,24},{25,26}}
        )); // [[1,2],[5,5],[8,10],[15,23],[24,24],[25,25]]

        // TC2
        print(solution.intervalIntersection(
                new int[][]{{1,3},{5,9}},
                new int[][]{}
        )); // []
    }


}
