import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 364. Nested List Weight Sum II (https://leetcode.com/problems/nested-list-weight-sum-ii/description/)
 */
public class NestedListWeightSumII {

    /************ Solution 1: One Pass 参见同名.png文件 NestedListWeightSumII.png ************/
    /**
     * Time: O(N) each int goes into&outof queue once
     * Space: O(N) worst case, each int are on the same level
     */
    public int depthSumInverse1(List<NestedInteger> nestedList) {
        int sum = 0, depthSum = 0, depth = 1;
        while (!nestedList.isEmpty()) {
            List<NestedInteger> nextLevel = new ArrayList<>();
            for (NestedInteger n : nestedList) {
                if (n.isInteger()) {
                    sum += n.getInteger();
                    depthSum += depth * n.getInteger();
                } else {
                    nextLevel.addAll(n.getList());
                }
            }
            nestedList = nextLevel;
            depth++;
        }
        // 小心！！！depth最后多加了1
        return depth * sum - depthSum;
    }

    /************ Solution 2: One Pass Recur DFS ************/
    /**
     * Time: O(# of recur call + # of loop in each recur call) < O(2N) = O(N)
     * Space: O(N) for max call stack in worst case (all int are nested into depth N)
     */
    int maxDepth;

    public int depthSumInverse(List<NestedInteger> nestedList) {
        int[] sums = recur(nestedList, 1);
        return sums[1] * (maxDepth + 1) - sums[0];
    }

    private int[] recur(List<NestedInteger> currList, int depth) {
        if (currList == null || currList.isEmpty()) return new int[]{0, 0};
        maxDepth = Math.max(maxDepth, depth);
        int unweightedSum = 0, weightedSum = 0;
        for (NestedInteger curr : currList) {
            if (curr.isInteger()) {
                weightedSum += curr.getInteger() * depth;
                unweightedSum += curr.getInteger();
            } else {
                int[] sums = recur(curr.getList(), depth + 1);
                weightedSum += sums[0];
                unweightedSum += sums[1];
            }
        }
        return new int[]{weightedSum, unweightedSum};
    }

    static class NestedInteger {

        private Integer i;
        private List<NestedListWeightSumII.NestedInteger> l;

        public NestedInteger() {
            l = new ArrayList<>();
        }

        public NestedInteger(int value) {
            i = value;
        }

        public boolean isInteger() {
            return i != null;
        }

        public Integer getInteger() {
            return i;
        }

        public void setInteger(int value) {
            i = value;
        }

        public void add(NestedListWeightSumII.NestedInteger ni) {
            if (l == null) l = new ArrayList<>();
            l.add(ni);
        }

        public List<NestedListWeightSumII.NestedInteger> getList() {
            return isInteger() ? Collections.emptyList() : l;
        }
    }

    public static void main(String[] args) {
        NestedListWeightSumII solution = new NestedListWeightSumII();

        // build [[1,1],2,[1,1]] => expected result: 8
        NestedListWeightSumII.NestedInteger n1 = new NestedListWeightSumII.NestedInteger();
        n1.add(new NestedListWeightSumII.NestedInteger(1));
        n1.add(new NestedListWeightSumII.NestedInteger(1));
        NestedListWeightSumII.NestedInteger n2 = new NestedListWeightSumII.NestedInteger(2);
        NestedListWeightSumII.NestedInteger n3 = new NestedListWeightSumII.NestedInteger();
        n3.add(new NestedListWeightSumII.NestedInteger(1));
        n3.add(new NestedListWeightSumII.NestedInteger(1));
        List<NestedListWeightSumII.NestedInteger> l = Arrays.asList(n1, n2, n3);
        System.out.println(solution.depthSumInverse(l));
    }
}
