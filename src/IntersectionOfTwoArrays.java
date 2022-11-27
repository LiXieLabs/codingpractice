import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class IntersectionOfTwoArrays {

    /***************** Solution 1: Sort + Two Pointers ***********************/
    /**
     * Time: O(MlogM + NlogN)   Space: O(1)
     */
    public int[] intersection1(int[] nums1, int[] nums2) {
        List<Integer> res = new ArrayList<>();
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int p1 = 0, p2 = 0;
        while (p1 < nums1.length && p2 < nums2.length) {
            if (nums1[p1] == nums2[p2]) {
                res.add(nums1[p1]);
                do {
                    p1++;
                } while (p1 < nums1.length && nums1[p1] == nums1[p1 - 1]);
                do {
                    p2++;
                } while (p2 < nums2.length && nums2[p2] == nums2[p2 - 1]);
            } else if (nums1[p1] < nums2[p2]) {
                do {
                    p1++;
                } while (p1 < nums1.length && nums1[p1] == nums1[p1 - 1]);
            } else {
                do {
                    p2++;
                } while (p2 < nums2.length && nums2[p2] == nums2[p2 - 1]);
            }
        }
        return res.stream().mapToInt(i -> i).toArray();
    }

    /***************** Solution 2: Set + Set Intersection Operation ***********************/
    /**
     * Time: O(M + N)   Space: O(M + N)
     */
    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> set1 = new HashSet<>();
        for (int n : nums1) set1.add(n);
        Set<Integer> set2 = new HashSet<>();
        for (int n : nums2) set2.add(n);

        // Java Set Built-in Method of Intersection
//        set1.retainAll(set2); // Time: Average O(M + N) Worst O(M X N)
//        return set1.stream().mapToInt(Integer::intValue).toArray();

        List<Integer> res = new ArrayList<>();
        if (set2.size() < set1.size()) {
            Set<Integer> tmp = set1;
            set1 = set2;
            set2 = tmp;
        }
        for (int n : set1) {
            if (set2.contains(n)) res.add(n);
        }
        return res.stream().mapToInt(i -> i).toArray();
    }

    private static void print(int[] input) {
        System.out.println("["
                + Arrays.stream(input).mapToObj(String::valueOf).collect(Collectors.joining(","))
                + "]");
    }

    public static void main(String[] args) {
        IntersectionOfTwoArrays solution = new IntersectionOfTwoArrays();
        print(solution.intersection(new int[]{1,2,2,1}, new int[]{2,2}));
        print(solution.intersection(new int[]{4,9,5}, new int[]{9,4,9,8,4}));
    }
}
