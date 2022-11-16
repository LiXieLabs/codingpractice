import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class LongestConsecutiveSequence {

    /***************** Solution 1: Union Find *******************/
    /**
     * Time: O(N)
     * init => O(N); find/union => amortized ~ O(N)
     *
     * Space: O(N) by maps
     */
    public int longestConsecutive1(int[] nums) {
        UnionFind128 uf = new UnionFind128(nums);
        for (int n : nums) {
            if (uf.id.containsKey(n - 1)) uf.union(n, n - 1);
            if (uf.id.containsKey(n + 1)) uf.union(n + 1, n);
        }
        int maxLen = 0;
        for (int n : nums) {
            maxLen = Math.max(maxLen, uf.sz.get(n));
        }
        return maxLen;
    }

    /***************** Solution 2: Set + 找连续的第一个 *******************/
    /**
     * Time: O(N)
     * init => O(N); find/union => amortized ~ O(N)
     *
     * Space: O(N) by maps
     */
    public int longestConsecutive(int[] nums) {
        Set<Integer> numSet = Arrays.stream(nums).boxed().collect(Collectors.toSet());
        int maxLen = 0;
        for (int n : nums) {
            if (!numSet.contains(n - 1)) {
                int curLen = 1;
                while (numSet.contains(++n)) {
                    curLen++;
                }
                maxLen = Math.max(maxLen, curLen);
            }
        }
        return maxLen;
    }


    public static void main(String[] args) {
        LongestConsecutiveSequence solution = new LongestConsecutiveSequence();
        System.out.println(solution.longestConsecutive(new int[]{}));
        System.out.println(solution.longestConsecutive(new int[]{200, 4, 100, 1, 3, 2}));
        System.out.println(solution.longestConsecutive(new int[]{0,3,7,2,5,8,4,6,0,1}));
    }
}

class UnionFind128 {

    Map<Integer, Integer> id;
    Map<Integer, Integer> sz;

    public UnionFind128(int[] nums) {
        id = new HashMap<>();
        sz = new HashMap<>();
        for (int n : nums) {
            id.put(n, n);
            sz.put(n, 1);
        }
    }

    public int find(int p) {
        while (id.get(p) != p) {
            id.put(p, id.get(id.get(p)));
            p = id.get(p);
        }
        return p;
    }

    public void union(int p1, int p2) {
        int r1 = find(p1), r2 = find(p2);
        if (r1 == r2) return;
        if (sz.get(r1) > sz.get(r2)) {
            id.put(r2, r1);
            sz.put(r1, sz.get(r1) + sz.get(r2));
        } else {
            id.put(r1, r2);
            sz.put(r2, sz.get(r1) + sz.get(r2));
        }
    }
}
