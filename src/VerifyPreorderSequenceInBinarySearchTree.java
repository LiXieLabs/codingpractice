import java.util.ArrayDeque;
import java.util.Deque;

public class VerifyPreorderSequenceInBinarySearchTree {

    /********** Solution 1:  Iterative Simulate Preorder Traversal by Monotonic Descending Stack ************/
    /**
     * 到某一点A[i],将stack中比它小的都pop出去，并更新最小值lo，此时stack顶端则为其第一个作为left-subtree的subtree的root
     * pop出来的顺序正好是ascending sorted，跟inorder taversal结果一样
     *
     * Time: O(N)   Space: O(H) by stack
     */
    public boolean verifyPreorder1(int[] preorder) {
        int lo = Integer.MIN_VALUE;
        Deque<Integer> stack = new ArrayDeque<>();
        for (int p : preorder) {
            if (p < lo) return false;
            while (!stack.isEmpty() && stack.peek() < p) {
                lo = stack.pop();
            }
            stack.push(p);
        }
        return true;
    }

    /************* Solution 2: O(1) Space Solution 1 ************/
    /**
     * 跟solution 1一样，但是把preorder当作stack
     *
     * Time: O(N)   Space: O(1)
     */
    public boolean verifyPreorder2(int[] preorder) {
        int lo = Integer.MIN_VALUE, i = -1;
        for (int p : preorder) {
            if (p < lo) return false;
            while (i >= 0 && preorder[i] < p) {
                lo = preorder[i--];
            }
            preorder[++i] = p;
        }
        return true;
    }

    /********** Solution 3:  Recursive to check if follow pattern [r][--- <r ---][--- >r ---] ************/
    /**
     * Time: O(N)   Space: O(H) by recur call stack
     */
    int[] A;
    int i;
    public boolean verifyPreorder(int[] preorder) {
        A = preorder;
        i = 0;
        return recurCheck(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean recurCheck(int lo, int hi) {
        // A[i] > hi 是 section 1 & 2 的分界线
        if (i == A.length || A[i] > hi) return true;
        int root = A[i++];
        return root > lo
                && recurCheck(lo, root) // section 1: [--- <r ---]
                && recurCheck(root, hi); // section 2: [--- >r ---]
    }

    public static void main(String[] args) {
        VerifyPreorderSequenceInBinarySearchTree solution = new VerifyPreorderSequenceInBinarySearchTree();
        System.out.println(solution.verifyPreorder(new int[]{5,2,1,3,6}));
        System.out.println(solution.verifyPreorder(new int[]{5,2,6,1,3}));
    }
}
