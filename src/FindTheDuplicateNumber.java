import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FindTheDuplicateNumber {

    /************** 🚫 Solution 1: Sort + prev/curr Two Pointers *******************/
    /**
     * 不满足要求：NOT modify original nums array
     *
     * Time: O(NlogN)
     * Space: O(logN)
     * In Java, Arrays.sort() for primitives is implemented using a variant of the Quick Sort algorithm,
     * which has a space complexity of O(logN)
     */
    public int findDuplicate1(int[] nums) {
        Arrays.sort(nums);
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) return nums[i];
        }
        return -1;
    }

    /************** 🚫 Solution 2: HashSet *******************/
    /**
     * 不满足要求：constant space
     *
     * Time: O(N)   Space: O(N)
     */
    public int findDuplicate2(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int n : nums) {
            if (!set.add(n)) return n;
        }
        return -1;
    }

    /************** 🚫 Solution 3: Negative Marking *******************/
    /**
     * 不满足要求：NOT modify original nums array
     *
     * 类似：
     * 41. First Missing Positive (https://leetcode.com/problems/first-missing-positive/)
     *
     * Time: O(N)   Space: O(1)
     */
    public int findDuplicate3(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int idx = Math.abs(nums[i]);
            if (nums[idx] < 0) return idx;
            nums[idx] *= -1;
        }
        return -1;
    }

    /************** ✅ Solution 4: Binary Search *******************/
    /**
     * binary search 找到最小的一个 mid in [1,n]，
     * 使得 nums 中 <= mid 的个数 <= mid 本身
     *
     * 比如 [1,2,3,3,4]
     * <= 1 有一个
     * <= 2 有两个
     * <= 3 有四个 => 解
     * <= 4 有五个
     *
     * Time: O(NlogN)   Space: O(1)
     */
    public int findDuplicate4(int[] nums) {
        int l = 1, h = nums.length - 1;
        while (l < h) {
            int mid = (l + h) / 2;
            int cnt = 0;
            for (int n : nums) {
                if (n <= mid) cnt++;
            }
            if (cnt <= mid) {
                l = mid + 1;
            } else {
                h = mid;
            }
        }
        return l;
    }

    /************** ✅ Solution 5: Bit Manipulation *******************/
    /**
     * [1,2,3,4] 的 base count 是
     * 1 => binary [0,0,1]
     * 2 => binary [0,1,0]
     * 3 => binary [0,1,1]
     * 4 => binary [1,0,0]
     * total =>    [1,2,2]
     *
     * [1,2,3,3] 的 actual count 是
     * 1 => binary [0,0,1]
     * 2 => binary [0,1,0]
     * 3 => binary [0,1,1] X 2
     * total =>    [0,3,3]
     *
     * diff = actual count - base count = [-1,1,1] => positive [0,1,1] => duplicate is 3
     *
     * 如果duplicate个数多于2个，则missing number被duplicate代替，相应的bit count只会更多
     *
     * Time: O(NlogN)   Space: O(1)
     */
    public int findDuplicate5(int[] nums) {
        int duplicate = 0;
        int maxBit = maxBit(maxNum(nums));
        for (int bit = 0; bit < maxBit; bit++) {
            int mask = (1 << bit);
            int baseCount = 0, actualCount = 0;
            for (int i = 0; i < nums.length; i++) {
                baseCount += (i & mask);
                actualCount += (nums[i] & mask);
            }
            if (actualCount > baseCount) {
                duplicate ^= mask;
            }
        }
        return duplicate;
    }

    /**
     * 找到int[]中最大的一个number
     */
    private int maxNum(int[] nums) {
        int res = nums[0];
        for (int n : nums) {
            res = Math.max(res, n);
        }
        return res;
    }

    /**
     * 计算有number二进制有几位
     */
    private int maxBit(int num) {
        int res = 0;
        while (num > 0) {
            num >>= 1;
            res++;
        }
        return res;
    }

    /************** ✅ Solution 6: Floyd's Tortoise & Hare (Cycle Detection) Algo *******************/
    /**
     *  0,1,2,3,4,5  -> index
     * [3,2,4,5,1,2] -> nums
     *
     * nums[0](3) -> nums[3](5) -> nums[5](2) -> nums[2](4) -> nums[4](1)
     *                                  ^                           |
     *                                  |__________________________ |
     *
     * 完全相同:
     * 142. Linked List Cycle II (https://leetcode.com/problems/linked-list-cycle-ii/)
     *
     * Time: O(N)   Space: O(1)
     */
    public int findDuplicate(int[] nums) {
        int slow = 0, fast = 0;
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (nums[slow] != nums[fast]);
        slow = 0;
        while (nums[slow] != nums[fast]) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return nums[slow];
    }

    public static void main(String[] args) {
        FindTheDuplicateNumber solution = new FindTheDuplicateNumber();
        System.out.println(solution.findDuplicate(new int[]{1,3,4,2,2}));
        System.out.println(solution.findDuplicate(new int[]{2,2,2,2,2}));
        System.out.println(solution.findDuplicate(new int[]{1,3,3,3,4}));
    }
}
