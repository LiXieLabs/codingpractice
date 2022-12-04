import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * 272. Closest Binary Search Tree Value II (https://leetcode.com/problems/closest-binary-search-tree-value-ii/)
 */
public class ClosestBinarySearchTreeValueII {

    /***************** Solution 1: Inorder Traversal + K-size Max Heap *******************/
    /**
     * Time: O(NlogK)   Space: O(H + K)
     */
    public List<Integer> closestKValues1(TreeNode root, double target, int k) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.comparingDouble(n -> -Math.abs(n - target)));
        Deque<TreeNode> stack = new ArrayDeque<>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            maxHeap.offer(root.val);
            if (maxHeap.size() > k) maxHeap.poll();
            root = root.right;
        }
        List<Integer> res = new ArrayList<>();
        while (!maxHeap.isEmpty()) {
            res.add(maxHeap.poll());
        }
        return res;
    }

    /***************** Solution 2: Inorder Traversal + Quick Select **********************/
    /**
     * Time: O(N) worst O(N^2)   Space: O(N) to store A
     */
    Random rand;
    List<Integer> A;
    double target;
    int k;
    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        rand = new Random();
        A = new ArrayList<>();
        inorder(root);
        this.target = target;
        this.k = k;
        quickSelect(0, A.size() - 1);
        return A.subList(0, k);
    }

    private void inorder(TreeNode curr) {
        if (curr == null) return;
        inorder(curr.left);
        A.add(curr.val);
        inorder(curr.right);
    }

    private void quickSelect(int start, int end) {
        if (start >= end) return;
        int index = rand.nextInt(end - start + 1) + start;
        double pivot = Math.abs(A.get(index) - target);
        int left = start, right = end;
        while (left <= right) {
            while (left <= right && Math.abs(A.get(left) - target) < pivot) left++;
            while (left <= right && Math.abs(A.get(right) - target) > pivot) right--;
            if (left <= right) {
                int temp = A.get(left);
                A.set(left, A.get(right));
                A.set(right, temp);
                left++;
                right--;
            }
        }
        if (k < left) {
            quickSelect(start, right);
        } else if (k > left) {
            quickSelect(left, end);
        }
    }

    public static void main(String[] args) {
        ClosestBinarySearchTreeValueII solution = new ClosestBinarySearchTreeValueII();

        TreeNode root1 = new TreeNode(4,
                new TreeNode(2,
                        new TreeNode(1),
                        new TreeNode(3)),
                new TreeNode(5));
        System.out.println(solution.closestKValues(root1, 3.714286, 2));

        TreeNode root2 = new TreeNode(1);
        System.out.println(solution.closestKValues(root2, 0.000000, 1));
    }
}
