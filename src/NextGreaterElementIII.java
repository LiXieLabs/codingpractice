import java.util.ArrayList;
import java.util.List;

/**
 * 556. Next Greater Element III (https://leetcode.com/problems/next-greater-element-iii/)
 */
public class NextGreaterElementIII {

    /************ Solution 1: Integer <=> List of Digits & Next Permutation **************/
    /**
     * Integer <=> Array of Digits 也可以借助 String:
     * char[] number = (n + "").toCharArray();
     * Arrays.sort(number, i, number.length);
     * long val = Long.parseLong(new String(number));
     *
     * Next Permutation 解法参见
     * 31. Next Permutation (https://leetcode.com/problems/next-permutation/)
     *
     * Time: O(N)   Space: O(N) where N is number digit in n.
     */
    public int nextGreaterElement1(int n) {
        // split number into digit list (in reversed order: 12345 => [5,4,3,2,1])
        List<Integer> lst = splitNum(n);

        // solve with next permutation solution
        int i = 0;
        while (i < lst.size() && (i == 0 || lst.get(i) >= lst.get(i - 1))) {
            i++;
        }
        if (i == lst.size()) return -1;
        int l = 0, r = i - 1;
        while (l <= r) {
            int tmp = lst.get(l);
            lst.set(l, lst.get(r));
            lst.set(r, tmp);
            l++; r--;
        }
        int j = 0;
        while (j < i && lst.get(j) > lst.get(i)) {
            j++;
        }
        int tmp = lst.get(i);
        lst.set(i, lst.get(j - 1));
        lst.set(j - 1, tmp);

        // join digit list into number and return
        return joinNum(lst);
    }

    private List<Integer> splitNum(int n) {
        List<Integer> res = new ArrayList<>();
        while (n > 0) {
            res.add(n % 10);
            n /= 10;
        }
        return res;
    }

    private int joinNum(List<Integer> lst) {
        // 注意！！！先用 long 接住，最后超过返回 -1
        long res = 0;
        for (int i = lst.size() - 1; i >= 0; i--) {
            res = res * 10 + lst.get(i);
        }
        return res > Integer.MAX_VALUE ? -1 : (int) res;
    }

    /************ Solution 2: Integer split & join 的另一种方法 **************/
    /**
     * Time: O(N)   Space: O(N) where N is number digit in n.
     */
    public int nextGreaterElement(int n) {
        // 快速 split integer to digits
        char[] arr = String.valueOf(n).toCharArray();

        // 解决 next permutation
        int i = arr.length - 2;
        while (i >= 0 && arr[i] >= arr[i+1]) {
            i--;
        }
        if (i == -1) return -1;

        int l = i + 1, r = arr.length - 1;
        while (l < r) {
            char temp = arr[l];
            arr[l++] = arr[r];
            arr[r--] = temp;
        }

        int j = i + 1;
        while (arr[j] <= arr[i]) {
            j++;
        }
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;

        // 跟 Integer.MAX_VALUE 逐位比较 并返回结果！！！
        char[] maxInt = String.valueOf(Integer.MAX_VALUE).toCharArray();
        return larger(arr, maxInt) ? -1 : Integer.parseInt(new String(arr));

    }

    private boolean larger(char[] num, char[] base) {
        if (num.length > base.length) return true;
        if (num.length < base.length) return false;
        for (int i = 0; i < num.length; i++) {
            if (num[i] > base[i]) return true;
            if (num[i] < base[i]) return false;
        }
        return false;
    }

    public static void main(String[] args) {
        NextGreaterElementIII solution = new NextGreaterElementIII();

        System.out.println(solution.nextGreaterElement(12)); // 21
        System.out.println(solution.nextGreaterElement(21)); // -1
        System.out.println(solution.nextGreaterElement(34521)); // 35124
        System.out.println(solution.nextGreaterElement(2147483476)); // 2147483647
        System.out.println(solution.nextGreaterElement(1999999999)); // -1
    }
}
