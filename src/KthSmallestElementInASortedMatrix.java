import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 378. Kth Smallest Element in a Sorted Matrix (https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/description/)
 */
public class KthSmallestElementInASortedMatrix {

    /************ Solution 1: k-size maxHeap + break in advance **************/
    /**
     * Iterate over matrix and break early.
     *
     * Time: O(R x C x logK)   Space: O(K) by maxHeap
     */
    public int kthSmallest1(int[][] matrix, int k) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((n1, n2) -> (n2 - n1));
        int r = matrix.length, c = matrix[0].length;
        for (int i = 0; i < r; i++) {
            if (maxHeap.size() == k && maxHeap.peek() <= matrix[i][0]) break;
            for (int j = 0; j < c; j++) {
                if (maxHeap.size() == k && maxHeap.peek() <= matrix[i][j]) break;
                maxHeap.offer(matrix[i][j]);
                if (maxHeap.size() > k) maxHeap.poll();
            }
        }
        return maxHeap.peek();
    }

    /************ Solution 2: minHeap to find Kth smallest in R sorted array ****************/
    /**
     * 每行一个指针，放在 minHeap 里面，取 k 次当前最小的，最后一个就是解！
     *
     * Time: O(Klog(min(K, R)))   Space: O(min(K, R))
     */
    public int kthSmallest2(int[][] matrix, int k) {
        int r = matrix.length, c = matrix[0].length;
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> matrix[a[0]][a[1]]));
        for (int i = 0; i < r && i < k; i++) {
            minHeap.offer(new int[]{i, 0});
        }
        for (int i = 0; i < k - 1; i++) { // 如果 k=1, 当前堆顶就是解，故循环到 k-1
            int[] s = minHeap.poll();
            if (s[1] < c - 1) {
                minHeap.offer(new int[]{s[0], s[1] + 1});
            }
        }
        int[] s = minHeap.poll();
        return matrix[s[0]][s[1]];
    }

    int[][] mat;
    int row, col;

    /******************* Solution 3: 在可行解中Binary Search + Row&Col sorted 2D matrix 找 bisectLeft ********************/
    /**
     * lo = matrix[0][0], hi = matrix[-1][-1]，可行解在 [lo, hi] 中。
     * 找到最小的可行解 x，使得 matrix 中 <= x 的 elements 个数恰好 == k。
     * 因为是 bisectLeft, 保证了是所有满足 (使得 matrix 中 <= x 的 elements 个数恰好 == k) 的可行解中的最小值，
     * 所以该可行解一定在 matrix 中！！！
     *
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
        System.out.println(solution.kthSmallest(new int[][]{{1,5,9},{10,11,13},{12,13,15}}, 8)); // 13
        // TC2
        System.out.println(solution.kthSmallest(new int[][]{{-5}}, 1)); // -5
        // TC3
        System.out.println(solution.kthSmallest(new int[][]{{-5,-4},{-5,-4}}, 2)); // -5
    }
}