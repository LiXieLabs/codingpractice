import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PerfectRectangle {

    /******** Solution 1: Build all corners + Sort + Check boundary area && Check mismatch corners *********************/
    /**
     * 1. calculate all corners coordinates - O(N)
     * 2. secondary sort corners - O(4Nlog4N) = O(NlogN)
     * 3. check total area == boundary area - O(1)
     * 4. iterate over corners, and find all mismatch corners (odd occurrence) - O(N)
     * 5. check corners form rectangle and matches with boundary - O(1)
     *
     * Time: O(NlogN)  Space: O(N)
     */
    public boolean isRectangleCover1(int[][] rectangles) {
        int[][] corners = new int[rectangles.length * 4][2];
        int i = 0, minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE, minY = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE;
        long totalArea = 0;
        for (int[] rec : rectangles) {
            int x1 = rec[0], y1 = rec[1], x2 = rec[2], y2 = rec[3];

            // add area + update boundary
            totalArea += 1L * (x2 - x1) * (y2 - y1);
            minX = Math.min(minX, x1);
            maxX = Math.max(maxX, x2);
            minY = Math.min(minY, y1);
            maxY = Math.max(maxY, y2);

            corners[i][0] = x1;
            corners[i++][1] = y1;
            corners[i][0] = x2;
            corners[i++][1] = y2;
            corners[i][0] = x1;
            corners[i++][1] = y2;
            corners[i][0] = x2;
            corners[i++][1] = y1;
        }
        long boundArea = 1L * (maxX - minX) * (maxY - minY);
        if (totalArea != boundArea) return false;

        Comparator<int[]> first = Comparator.comparingInt(a -> a[0]);
        Comparator<int[]> second = Comparator.comparingInt(a -> a[1]);
        Arrays.sort(corners, first.thenComparing(second));

        List<int[]> mismatch = new ArrayList<>();
        i = 0;
        while (i < corners.length) {
            int j = i + 1;
            while (j < corners.length && isSame(corners, i, j)) j++;
            if ((j - i) % 2 == 1) mismatch.add(corners[i]);
            i = j;
        }
        return isRectangle(mismatch, minX, maxX, minY, maxY);
    }

    private boolean isSame(int[][] corners, int i, int j) {
        return 0 <= i && i < corners.length
                && 0 <= j && j < corners.length
                && corners[i][0] == corners[j][0]
                && corners[i][1] == corners[j][1];
    }

    private boolean isRectangle(List<int[]> corners, int minX, int maxX, int minY, int maxY) {
        if (corners.size() != 4) return false;
        int[] c1 = corners.get(0), c2 = corners.get(1), c3 = corners.get(2), c4 = corners.get(3);
        return c1[0] == minX && c1[1] == minY
                && c2[0] == minX && c2[1] == maxY
                && c3[0] == maxX && c3[1] == minY
                && c4[0] == maxX && c4[1] == maxY;
    }

    /************** Solution 2: Replace Sort by HashSet *********************/
    /**
     * Sort is time-consuming, replace it by HashSet + "x;y" as key
     * Then, it's valid rectangle if
     * (1) totalArea == boundaryArea
     * (2) remaining set contains mismatch corners, make sure:
     *     - size is 4
     *     - set contains 4 boundary corners
     *
     * Time: O(N)   Space: O(N)
     */
    public boolean isRectangleCover(int[][] rectangles) {
        int minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE, minY = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE;
        long totalArea = 0;
        Set<String> set = new HashSet<>();
        for (int[] rec : rectangles) {
            int x1 = rec[0], y1 = rec[1], x2 = rec[2], y2 = rec[3];

            // add area + update boundary
            totalArea += 1L * (x2 - x1) * (y2 - y1);
            minX = Math.min(minX, x1);
            maxX = Math.max(maxX, x2);
            minY = Math.min(minY, y1);
            maxY = Math.max(maxY, y2);

            String k1 = getKey(x1, y1), k2 = getKey(x2, y2), k3 = getKey(x1, y2), k4 = getKey(x2, y1);
            if (!set.add(k1)) set.remove(k1);
            if (!set.add(k2)) set.remove(k2);
            if (!set.add(k3)) set.remove(k3);
            if (!set.add(k4)) set.remove(k4);
        }

        // check area
        long boundArea = 1L * (maxX - minX) * (maxY - minY);
        if (totalArea != boundArea) return false;

        // check boundary
        return set.size() == 4
                && set.contains(getKey(minX, minY))
                && set.contains(getKey(maxX, maxY))
                && set.contains(getKey(minX, maxY))
                && set.contains(getKey(maxX, minY));
    }

    private String getKey(int x, int y) {
        return x + ";" + y;
    }
}
