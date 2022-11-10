import java.util.PriorityQueue;
import java.util.Random;

public class KthLargestElementInAnArray {

    /************ Solution 1: K size Min-Heap ****************/
    /**
     * Space: O(K)
     * Time: O(NlogK)
     */
    public int findKthLargest1(int[] nums, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int n : nums) {
            heap.offer(n);
            if (heap.size() > k) {
                heap.poll();
            }
        }
        return heap.peek();
    }

    /*********** Solution 2: Quick Select ********************/
    /**
     * Time: O(N) worst O(N^2)
     * Space: O(1)
     */
    Random rand;
    public int findKthLargest(int[] nums, int k) {
        rand = new Random();
        if (nums == null || nums.length == 0 || k < 1 || k > nums.length) return -1;
        return quickSelect(nums, 0, nums.length - 1, nums.length - k);
    }

    private int quickSelect(int[] nums, int start, int end, int k) {
        if (start >= end) return nums[k];
        int index = rand.nextInt(end - start + 1) + start;
        int pivot = nums[index];
        int left = start, right = end;
        while (left <= right) {
            while (left <= right && nums[left] < pivot) left++;
            while (left <= right && nums[right] > pivot) right--;
            if (left <= right) {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
                left++;
                right--;
            }
        }
        if (k <= right) {
            return quickSelect(nums, start, right, k);
        }
        if (k >= left) {
            return quickSelect(nums, left, end, k);
        }
        return nums[k];
    }

    public static void main(String[] args) {
        KthLargestElementInAnArray solution = new KthLargestElementInAnArray();
        System.out.println(solution.findKthLargest(new int[]{3,2,1,5,6,4}, 2));
        System.out.println(solution.findKthLargest(new int[]{3,2,3,1,2,4,5,5,6}, 4));
    }
}
