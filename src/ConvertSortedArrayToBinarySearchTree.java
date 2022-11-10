import java.util.Random;

public class ConvertSortedArrayToBinarySearchTree {

    int[] nums;
    Random rand = new Random();

    /***** Solution 1: Recursive build tree *****/
    /**
     * nums is actually pre-order traversal of a BST
     *
     * Time: O(N) visit each node once
     * Space: O(logN) by stack
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        this.nums = nums;
        return build(0, nums.length - 1);
    }

    private TreeNode build(int left, int right) {
        if (left > right) return null;
        if (left == right) return new TreeNode(nums[left]);
        int mid = (left + right) / 2;
        // Optional logic: for even number of node, randomly pick left or right middle as root.
        if ((left + right) % 2 == 1) mid += rand.nextInt(2);
        TreeNode root = new TreeNode(nums[mid]);
        root.left = build(left, mid - 1);
        root.right = build(mid + 1, right);
        return root;
    }

    public static void main(String[] args) {
        ConvertSortedArrayToBinarySearchTree solution = new ConvertSortedArrayToBinarySearchTree();
        Utils.print(solution.sortedArrayToBST(new int[]{-10,-3,0,5,9}));
        Utils.print(solution.sortedArrayToBST(new int[]{1,3}));
    }
}
