import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 39. Combination Sum (https://leetcode.com/problems/combination-sum/description/)
 */
public class CombinationSum {

    /**
     * List of similar questions
     * 78. Subsets
     * 90. Subsets II
     * 46. Permutations
     * 47. Permutations II
     * 77. Combinations
     * 39. Combination Sum
     * 40. Combination Sum II
     * 216. Combination Sum III
     * 377. Combination Sum IV
     * 131. Palindrome Partition
     */

    /****************** Solution 1: Recursive Backtracking *****************/
    /**
     * N == the number of candidates
     * T == the target value
     * M == the min value among the candidates
     *
     * Backtracking is actually doing DFS of a N-ary tree.
     * Total # of nodes in an N-ary tree with height T/M is N^(T/M+1)
     * Each leave will have a List copy, which takes T/M
     * Initial sort is NlogN
     *
     * Time: O(N^(T/M+1))  Space: O(T/M) by recur stack.
     */
    List<List<Integer>> res;
    int[] candidates;

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        res = new ArrayList<>();
        // ⚠️注意⚠️ sort is not required, but highly recommended!!!
        Arrays.sort(candidates);
        this.candidates = candidates;
        recur(0, target, new ArrayList<>());
        return res;
    }

    private void recur(int start, int remain, List<Integer> curPath) {
        if (remain == 0) {
            res.add(new ArrayList<>(curPath));
            return;
        }
        // ⚠️注意⚠️ with sort, we can do early pruning by candidates[i] <= remain!!!
        for (int i = start; i < candidates.length && candidates[i] <= remain; i++) {
            curPath.add(candidates[i]);
            recur(i, remain - candidates[i], curPath);
            curPath.remove(curPath.size() - 1);
        }
    }

    public static void main(String[] args) {
        CombinationSum solution = new CombinationSum();
        System.out.println(solution.combinationSum(new int[]{2, 3, 6, 7}, 7)); // [[2, 2, 3], [7]]
        System.out.println(solution.combinationSum(new int[]{2, 3, 5}, 8)); // [[2, 2, 2, 2], [2, 3, 3], [3, 5]]
        System.out.println(solution.combinationSum(new int[]{2}, 1)); // []
    }
}
