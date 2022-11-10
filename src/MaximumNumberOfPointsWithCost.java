import java.util.Arrays;
import java.util.Collections;

public class MaximumNumberOfPointsWithCost {

    // O(R * C^2)
    public long maxPoints(int[][] points) {
        int r = points.length, c = points[0].length;
        System.out.printf("totalRow: %d, totalCol %d\n", r, c);
        long[] prev = Arrays.stream(points[0]).mapToLong(i -> i).toArray();
        for (int currRow = 1; currRow < r; currRow++) {
            long[] curr = Arrays.stream(points[currRow]).mapToLong(i -> i).toArray();
            for (int currCol = 0; currCol < c; currCol++) {
                System.out.printf("currRow: %d, currCol %d\n", currRow, currCol);
                long maxVal = Long.MIN_VALUE;
                for (int prevCol = 0; prevCol < c; prevCol++) {
                    long curVal = prev[prevCol] - Math.abs(prevCol - currCol);
                    maxVal = Math.max(maxVal, curVal);
                }
                curr[currCol] += maxVal;
            }
            prev = curr;
            System.out.println(Arrays.toString(curr));
        }
        return Arrays.stream(prev).max().getAsLong();
    }

    // O(R * 3N)
    public long maxPoints1(int[][] points) {
        int r = points.length, c = points[0].length;
        long[] prev = Arrays.stream(points[0]).mapToLong(i -> i).toArray();
        for (int currRow = 1; currRow < r; currRow++) {
            // calculate prev move from left to right
            long[] left = new long[c];
            left[0] = prev[0];
            for (int j = 1; j < c; j++) {
                left[j] = Math.max(left[j-1]-1, prev[j]);
            }
            // calculate prev move from right to left
            long[] right = new long[c];
            right[c-1] = prev[c-1];
            for (int j = c-2; j >= 0; j--) {
                right[j] = Math.max(right[j+1]-1, prev[j]);
            }
            // calculate curr row
            long[] curr = Arrays.stream(points[currRow]).mapToLong(i -> i).toArray();
            for (int j = 0; j < c; j++) {
                curr[j] += Math.max(left[j], right[j]);
            }
            prev = curr;
        }
        return Arrays.stream(prev).max().getAsLong();
    }

    public static void main(String[] args) {
        MaximumNumberOfPointsWithCost solution = new MaximumNumberOfPointsWithCost();
        System.out.println(solution.maxPoints1(new int[][]{{1,2,3},{1,5,1},{3,1,1}}));
        System.out.println(solution.maxPoints1(new int[][]{{1,5},{2,3},{4,2}}));
    }
}
