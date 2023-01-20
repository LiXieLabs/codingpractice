import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 536. Construct Binary Tree from String (https://leetcode.com/problems/construct-binary-tree-from-string/description/)
 */
public class ConstructBinaryTreeFromString {

    /************* Solution 1: Parse by Pattern + Stack ************/
    /**
     * s.charAt(i)
     * (1) '-' => update sign
     * (2) Character.isDigit() => update n
     *     if end, build TreeNode & add in stack
     * (3) ')' => stack.pop() is child, stack.peek() is parent
     *
     * TODO: Recursive solution
     *
     * Time: O(N)   Space: O(H) ~ O(logN)
     */
    public TreeNode str2tree(String s) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        Integer n = null;
        int sign = 1;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '-') {
                sign = -1;
            } else if (Character.isDigit(s.charAt(i))) {
                if (n == null) n = 0;
                n = n * 10 + (s.charAt(i) - '0');
                if (i + 1 == s.length() || !Character.isDigit(s.charAt(i + 1))) {
                    stack.push(new TreeNode(n * sign));
                    n = null;
                    sign = 1;
                }
            } else if (s.charAt(i) == ')') {
                TreeNode curr = stack.pop();
                TreeNode prev = stack.peek();
                if (prev.left == null) {
                    prev.left = curr;
                } else {
                    prev.right = curr;
                }
            }
        }
        return stack.peek();
    }

    private static void print(TreeNode root) {
        SerializeAndDeserializeBinaryTree printer = new SerializeAndDeserializeBinaryTree();
        System.out.println(printer.serialize(root));
    }

    public static void main(String[] args) {
        ConstructBinaryTreeFromString solution = new ConstructBinaryTreeFromString();
        print(solution.str2tree("4(2(3)(1))(6(5))")); // 4,2,6,3,1,5,#
        print(solution.str2tree("-4(2(-3)(1))(-6(5)(-7))")); // -4,2,-6,-3,1,5,-7
        print(solution.str2tree("-4")); // -4
    }
}
