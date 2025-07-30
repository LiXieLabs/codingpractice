import java.util.ArrayList;
import java.util.List;

/**
 * 118. Pascal's Triangle (https://leetcode.com/problems/pascals-triangle/description/)
 */
public class PascalsTriangle {

    /*************** Solution 1: DP ******************/
    /**
     * Time: (1 + numRows) X numRows / 2 = O(numRows^2)
     * Space: O(1)
     */
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> res = new ArrayList<>();
        for (int r = 1; r <= numRows; r++) {
            List<Integer> cur = new ArrayList<>();
            for (int i = 0; i < r; i++) {
                if (i == 0 || i == r - 1) {
                    cur.add(1);
                } else {
                    // ⚠️⚠️⚠️注意⚠️⚠️⚠️ res.get(res.size() - 1) 不能提到前面！因为有 res.size() == 0 的情况！！！
                    cur.add(res.get(res.size() - 1).get(i - 1) + res.get(res.size() - 1).get(i));
                }
            }
            res.add(cur);
        }
        return res;
    }

    public static void main(String[] args) {
        PascalsTriangle solution = new PascalsTriangle();
        System.out.println(solution.generate(1)); // [[1]]
        System.out.println(solution.generate(2)); // [[1], [1, 1]]
        System.out.println(solution.generate(3)); // [[1], [1, 1], [1, 2, 1]]
        System.out.println(solution.generate(5)); // [[1], [1, 1], [1, 2, 1], [1, 3, 3, 1], [1, 4, 6, 4, 1]]
    }
}
