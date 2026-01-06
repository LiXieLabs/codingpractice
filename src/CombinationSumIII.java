import java.util.ArrayList;
import java.util.List;

public class CombinationSumIII {

    /************** Solution 1: Backtracking *****************/
    /**
     * Time: O(deepcopy res list X combination count) = O(K X C(9,K))
     *     = O(K X 9!/K!(9-K)!)
     * Space: O(K) for recur stack
     */
    List<List<Integer>> res;
    int k;

    public List<List<Integer>> combinationSum3(int k, int n) {
        res = new ArrayList<>();
        this.k = k;
        recur(new ArrayList<>(), 1, n);
        return res;
    }

    private void recur(List<Integer> path, int start, int remain) {
        if (path.size() == k) {
            if (remain == 0) res.add(new ArrayList<>(path));
            return;
        }
        for (int i = start; i <= 9 && i <= remain; i++) {
            path.add(i);
            recur(path, i + 1, remain - i);
            path.remove(path.size() - 1);
        }
    }
}
