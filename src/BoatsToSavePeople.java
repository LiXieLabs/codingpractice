import java.util.Arrays;

public class BoatsToSavePeople {

    /*************** Solution 1: Greedy Algo by Two Pointers ****************/
    /**
     * 如果 (l != r && people[l] + people[r] <= limit) => 凑成一对
     * 否则:
     * (1) l == r 相遇只剩一个人
     * (2) l != r && people[l] + people[r] > limit, 只能people[r]自己坐一艘船
     * (3) 不管 l, r 的相对关系，people[r] == limit，只能people[r]自己坐一艘船
     *
     * Time: O(NlogN)
     * Space: O(1)
     * In Java, Arrays.sort() is implemented using a variant of the Quick Sort algorithm
     * which has a space complexity of O(logN).
     */
    public int numRescueBoats2(int[] people, int limit) {
        Arrays.sort(people);
        int l = 0, r = people.length - 1, res = 0;
        while (l <= r) {
            res++;
            if (l != r && people[l] + people[r] <= limit) {
                l++;
                r--;
            } else { // l == r || people[l] + people[r] > limit 包括 people[r] == limit
                r--;
            }
        }
        return res;
    }

    /*************** Solution 2: Greedy Algo by Two Pointers 优化 ****************/
    /**
     * Solution 1 的代码优化
     *
     * Time: O(NlogN)
     * Space: O(1)
     * In Java, Arrays.sort() is implemented using a variant of the Quick Sort algorithm
     * which has a space complexity of O(logN).
     */
    public int numRescueBoats(int[] people, int limit) {
        Arrays.sort(people);
        int res = 0;
        for (int l = 0, r = people.length - 1; l <= r; r--, res++) {
            if (l != r && people[l] + people[r] <= limit) l++;
        }
        return res;
    }

    public static void main(String[] args) {
        BoatsToSavePeople solution = new BoatsToSavePeople();
        System.out.println(solution.numRescueBoats(new int[]{1,2}, 3));
        System.out.println(solution.numRescueBoats(new int[]{3,2,2,1}, 3));
        System.out.println(solution.numRescueBoats(new int[]{3,5,3,4}, 5));
        System.out.println(solution.numRescueBoats(new int[]{5,5,5,5}, 5));
    }

}
