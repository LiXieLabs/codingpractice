import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 339. Nested List Weight Sum (https://leetcode.com/problems/nested-list-weight-sum/description/)
 */
public class NestedListWeightSum {

    /****************** Solution 1: Level order BFS ******************/
    /**
     * Time: O(N) each int goes in & out of queue once
     * Space: O(N) worst case, each int are on the same level
     */
    public int depthSum1(List<NestedInteger> nestedList) {
        int res = 0, d = 1;
        while (!nestedList.isEmpty()) {
            List<NestedInteger> nextLevel = new ArrayList<>();
            for (NestedInteger ni : nestedList) {
                if (ni.isInteger()) {
                    res += d * ni.getInteger();
                } else {
                    nextLevel.addAll(ni.getList());
                }
            }
            nestedList = nextLevel;
            d += 1;
        }
        return res;
    }

    /****************** Solution 2: optimized recur DFS *********************/
    /**
     * Time: O(# of recur call + # of loop in each recur call) < O(2N) = O(N)
     * Space: O(N) for max call stack in worst case (all int are nested into depth N)
     */
    public int depthSum(List<NestedInteger> nestedList) {
        return dfs(nestedList, 1);
    }

    public int dfs(List<NestedInteger> l, int d) {
        int res = 0;
        for (NestedInteger ni : l) {
            if (ni.isInteger()) {
                res += d * ni.getInteger();
            } else {
                res += dfs(ni.getList(), d + 1);
            }
        }
        return res;
    }

    static class NestedInteger {

        private Integer i;
        private List<NestedInteger> l;

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

        public void add(NestedInteger ni) {
            if (l == null) l = new ArrayList<>();
            l.add(ni);
        }

        public List<NestedInteger> getList() {
            return isInteger() ? Collections.emptyList() : l;
        }
    }

    public static void main(String[] args) {
        NestedListWeightSum solution = new NestedListWeightSum();

        // build [[1,1],2,[1,1]] => expected result: 10
        NestedInteger n1 = new NestedInteger();
        n1.add(new NestedInteger(1));
        n1.add(new NestedInteger(1));
        NestedInteger n2 = new NestedInteger(2);
        NestedInteger n3 = new NestedInteger();
        n3.add(new NestedInteger(1));
        n3.add(new NestedInteger(1));
        List<NestedInteger> l = Arrays.asList(n1, n2, n3);
        System.out.println(solution.depthSum(l));
    }
}
