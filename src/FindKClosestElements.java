import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * 658. Find K Closest Elements (https://leetcode.com/problems/find-k-closest-elements/description/)
 */
public class FindKClosestElements {

    /************** Solution 1: Bisect Left 找到插入 x 的位置 + Two Pointers 向两边扩展 **************/
    /**
     * Time: O(logN + k)   Space: O(1)
     */
    public List<Integer> findClosestElements1(int[] arr, int k, int x) {
        int pos = bisectLeft(arr, x);
        int l = pos - 1, r = pos;
        Deque<Integer> res = new ArrayDeque<>();
        while (res.size() < k) {
            int left = l >= 0 ? Math.abs(arr[l] - x) : Integer.MAX_VALUE;
            int right = r < arr.length ? Math.abs(arr[r] - x) : Integer.MAX_VALUE;
            if (left <= right) {
                res.push(arr[l--]);
            } else {
                res.offer(arr[r++]);
            }
        }
        return new ArrayList<>(res);
    }

    private int bisectLeft(int[] arr, int target) {
        int lo = 0, hi = arr.length;
        while (lo < hi) {
            int mid = lo + (hi - lo >> 1);
            if (arr[mid] < target) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo;
    }

    /************** Solution 2: Binary Search Left Bound of the K-size Window **************/
    /**
     * 左边界的范围 [0, arr.length()-k] (inclusive)
     * arr[mid] 和 arr[mid+k] 肯定不在同一个k-size里
     * 如果 arr[mid+k] 离 x 更近，需向右移动window
     * 反之，结果在当前边界及其左边中
     *
     * Time: O(log(N-k) + k)   Space: O(1)
     */
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        int lo = 0, hi = arr.length - k;
        while (lo < hi) {
            int mid = lo + (hi - lo >> 1);
            // 注意！！！Math.abs(arr[mid + k] - x) < Math.abs(arr[mid] - x) 不行！！！否则 TC1 会 fail！！！
            // x 一定在 arr[mid] 和 arr[mid + k] 之间！！！
            if (x - arr[mid] <= arr[mid + k] - x) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        List<Integer> res = new ArrayList<>();
        while (res.size() < k) res.add(arr[lo++]);
        return res;
    }

    public static void main(String[] args) {
        FindKClosestElements solution = new FindKClosestElements();
        // TC1 => [2, 3, 3]
        System.out.println(solution.findClosestElements(new int[]{1,1,2,2,2,2,2,3,3}, 3, 3));
        // TC2 => [1, 2, 3, 4]
        System.out.println(solution.findClosestElements(new int[]{1,2,3,4,5}, 4, 3));
        // TC3 => [1, 2, 3, 4]
        System.out.println(solution.findClosestElements(new int[]{1,2,3,4,5}, 4, -1));
        // TC4 => [2, 3, 4, 5]
        System.out.println(solution.findClosestElements(new int[]{1,2,3,4,5}, 4, 20));
    }
}
