import java.util.Comparator;
import java.util.PriorityQueue;

public class KthSmallestElementInASortedMatrix {

    /************ Solution 1: minHeap to find Kth smallest in R sorted array ****************/
    /**
     * Time: O(KlogR)
     * Space: O(R)
     */
    public int kthSmallest1(int[][] matrix, int k) {
        int row = matrix.length, col = matrix[0].length;
        int[] p = new int[row];
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(Comparator.comparingInt(i -> matrix[i][p[i]]));
        for (int r = 0; r < row; r++) {
            minHeap.offer(r);
        }
        for (int i = 0; i < k - 1; i++) {
            int curRow = minHeap.poll();
            if (p[curRow] + 1 < col) {
                p[curRow]++;
                minHeap.offer(curRow);
            }
        }
        int i = minHeap.poll();
        return matrix[i][p[i]];
    }

    int[][] mat;
    int row, col;

    /******************* Solution 2: 在可行解中Binary Search + Row&Col sorted 2D matrix 找 bisectRight ********************/
    /**
     * 类似:
     * 410. Split Array Largest Sum (https://leetcode.com/problems/split-array-largest-sum/)
     * 378. Kth Smallest Element in a Sorted Matrix (https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/)
     * 1011. Capacity To Ship Packages Within D Days (https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/description/)
     * 1231. Divide Chocolate (https://leetcode.com/problems/divide-chocolate/)
     *
     * Time: O((R+C) X log(maxV - minV))
     * Space: O(1)
     */
    public int kthSmallest(int[][] matrix, int k) {
        mat = matrix;
        row = matrix.length;
        col = matrix[0].length;
        int lo = matrix[0][0], hi = matrix[row - 1][col -1];
        while (lo < hi) {
            int mid = (lo + hi) >> 1; // 右移一位向下取整，除2向0取整，主要防止负数endless loop，for TC3
            if (findLessThanOrEqual(mid) < k) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo;
    }

    private int findLessThanOrEqual(int n) {
        int c = col - 1, count = 0;
        for (int r = 0; r < row; r++) {
            while (c >= 0 && mat[r][c] > n) c--;
            count += (c + 1);
        }
        return count;
    }

    public static void main(String[] args) {
        KthSmallestElementInASortedMatrix solution = new KthSmallestElementInASortedMatrix();
        // TC1
        System.out.println(solution.kthSmallest(new int[][]{{1,5,9},{10,11,13},{12,13,15}}, 8));
        // TC2
        System.out.println(solution.kthSmallest(new int[][]{{-5}}, 1));
        // TC3
        System.out.println(solution.kthSmallest(new int[][]{{-5,-4},{-5,-4}}, 2));
    }
}