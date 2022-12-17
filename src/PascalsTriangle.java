import java.util.ArrayList;
import java.util.List;

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
                    cur.add(res.get(res.size() - 1).get(i - 1) + res.get(res.size() - 1).get(i));
                }
            }
            res.add(cur);
        }
        return res;
    }

    public static void main(String[] args) {
        PascalsTriangle solution = new PascalsTriangle();
        System.out.println(solution.generate(1));
        System.out.println(solution.generate(2));
        System.out.println(solution.generate(3));
        System.out.println(solution.generate(5));
    }
}
