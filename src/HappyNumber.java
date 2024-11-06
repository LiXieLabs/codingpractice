import java.util.HashSet;
import java.util.Set;

/**
 * 202. Happy Number (https://leetcode.com/problems/happy-number/description/)
 */
public class HappyNumber {

    /********** Solution 1: Simulate *************/
    /**
     * recur check until 1 (true) or cycle (false)
     *
     * Time: O(logN)
     * 分析见 https://leetcode.com/problems/happy-number/solutions/421162/happy-number/?orderBy=most_votes
     * Space: O(logN) by hashSet
     */
    Set<Integer> seen;
    public boolean isHappy1(int n) {
        seen = new HashSet<>();
        return recurCheck(n);
    }

    private boolean recurCheck(int n) {
        if (n == 1) return true;
        if (!seen.add(n)) return false;
        return recurCheck(getNext(n));
    }

    /********** Solution 2: Floyd's Tortoise & Hare (Cycle Detection) Algo *************/
    /**
     * Two Pointers to detect cycle
     * 142. Linked List Cycle II (https://leetcode.com/problems/linked-list-cycle-ii/)
     *
     * Time: O(logN)
     * Space: O(1)
     */
    public boolean isHappy(int n) {
        int slow = n, fast = n;
        do {
            slow = getNext(slow);
            fast = getNext(getNext(fast));
        } while (slow != fast && fast != 1);
        return fast == 1;
    }

    private int getNext(int n) {
        int next = 0;
        while (n > 0) {
            int digit = n % 10;
            next += digit * digit;
//            next += (int) Math.pow(n % 10, 2);
            n /= 10;
        }
        return next;
    }

    public static void main(String[] args) {
        HappyNumber solution = new HappyNumber();
        System.out.println(solution.isHappy(19)); // true
        System.out.println(solution.isHappy(2)); // false
    }
}
