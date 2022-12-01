/**
 * 278. First Bad Version (https://leetcode.com/problems/first-bad-version/)
 */
public class FirstBadVersion {

    private int[] versions;

    /************* Solution 1: Binary Search - First Occurrence *************/
    /**
     * Time: O(logN)   Space: O(1)
     */
    public int firstBadVersion(int n) {
        int lo = 1, hi = n;
        while (lo < hi) {
            int mid = lo + ((hi - lo) >> 1);
            if (isBadVersion(mid)) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }

    private boolean isBadVersion(int n) {
        return versions[n] == 1;
    }

    public static void main(String[] args) {
        FirstBadVersion solution = new FirstBadVersion();
        solution.versions = new int[]{0,0,0,0,1,1};
        System.out.println(solution.firstBadVersion(5));
        solution.versions = new int[]{0,1};
        System.out.println(solution.firstBadVersion(1));
    }


}
