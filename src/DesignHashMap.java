import java.util.Arrays;

/**
 * 706. Design HashMap (https://leetcode.com/problems/design-hashmap/)
 */
public class DesignHashMap {

    /************** Solution 1: 直接寻址法 *******************/
    /**
     * 直接寻址法 (direct addressing)
     * hash function：h(k) = k
     * 定义：每个元素的关键字k就是数组的下标，比如一个元素的关键字为2，则T[2]就是元素存放的位置。被寻址的表称为直接寻址表。
     * 前提:
     * (1)关键字的全域U较小，不然机器创建一个很大很大的数组不太可能。
     * (2)关键字都是自然数，不然不能直接寻址。
     * (3)没有两个元素的关键字相同，即不能“碰撞”。
     * 优点：简单，方便。并且没有冲突。
     * 缺点：
     * (1)关键字的全域很大时，如2^64，则计算机不能创建这么大的数组。
     * (2)如果关键字不是自然数，则不能直接寻址，比如关键字是string，则不能T[string]。
     * (3)如果实际使用的关键字只有少数，但是全域很大，则会造成空间浪费。比如，全域为100000，实际只使用了2个关键字。
     *
     * 算法：
     * 因为限制了 All keys and values will be in the range of [0, 1000000].
     * 可以定义一个长度1000001的数组，并且hash(key) == key == Array Index
     *
     * Time Complexity: O(1)
     * Space Complexity: O(keys size) == O(1000001)
     */
    int[] map1;

    public DesignHashMap() {
        map1 = new int[(int) 1e6 + 1];
        Arrays.fill(map1, -1);
    }

    public void put(int key, int value) {
        map1[key] = value;
    }

    public int get(int key) {
        return map1[key];
    }

    public void remove(int key) {
        map1[key] = -1;
    }

    /************** Solution 2: 除法散列法 *******************/
    /**
     * 除法散列法 (Division Hashing)
     * hash function：h(k)= k mod m
     * 注意点：
     * (1)m不能太小，m不能是2的幂次，m一定是质数。
     * (2)这个方法的优点是简单。
     *
     * 链接解决碰撞 (Resolving collision by Chaining)
     *
     * 定义：用双向链表来链接哈希值相同的元素。这种方法能够有良好性能的前提是满足近似简单一致散列。
     * 简单一致散列：如果任何一个关键字k，他映射到m个槽的任何一个的可能性都相同，并且k映射到哪个槽与其他关键字独立无关，则称为简单一致散列。
     * 装载因子(load factor)：n/m，即一个槽的链表的平均长度。
     * 结论：
     * INSERT操作的最坏情况时间为O(1)
     * DELETE操作的最坏情况时间为O(1)
     * 查找不成功的SEARCH操作的期望运行时间为O(1+n/m)，最坏情况时间为O(n)，当n=O(m)时，期望时间为O(1)
     * 查找成功的SEARCH操作的期望运行时间为O(1+n/m)，最坏情况时间为O(n)，当n=O(m)时，期望时间为O(1)
     * 具体分析参见<<算法导论11-2>>
     *
     * Hash Table 知识点总结见：https://app.gitbook.com/o/-LEzdBqqw8FawHA3mleT/s/-LEzdBqr7mNEEfIbMstL/design/706.-design-hashmap
     *
     * Time: O(load factor) = O(N/M)
     * Space: O(M + K)
     * N total # of possible keys, M slot counts, K unique keys that have been inserted.
     */
    ListNode706[] map2;

    public DesignHashMap(boolean solution2) {
        map2 = new ListNode706[1000];
        for (int i = 0; i < map2.length; i++) {
            map2[i] = new ListNode706(-1, -1);
        }
    }

    public void put2(int key, int value) {
        ListNode706 curr = map2[getIdx(key)];
        while (curr.next != null && curr.next.key != key) {
            curr = curr.next;
        }
        if (curr.next != null) {
            curr.next.val = value;
        } else {
            curr.next = new ListNode706(key, value);
        }
    }

    public int get2(int key) {
        ListNode706 curr = map2[getIdx(key)];
        while (curr != null && curr.key != key) {
            curr = curr.next;
        }
        return curr != null ? curr.val : -1;
    }

    public void remove2(int key) {
        ListNode706 curr = map2[getIdx(key)];
        while (curr.next != null && curr.next.key != key) {
            curr = curr.next;
        }
        if (curr.next != null) {
            curr.next = curr.next.next;
        }
    }

    private int getIdx(int key) {
        return Integer.hashCode(key) % map2.length; // h(k) = k % m
    }

    public static void main(String[] args) {

        // direct addressing
        DesignHashMap solution1 = new DesignHashMap();
        solution1.put(1, 1);
        solution1.put(2, 2);
        System.out.println(solution1.get(1));
        System.out.println(solution1.get(3));
        solution1.put(2, 1);
        System.out.println(solution1.get(2));
        solution1.remove(2);
        System.out.println(solution1.get(2));

        // division hashing
        DesignHashMap solution2 = new DesignHashMap(true);
        solution2.put2(1, 1);
        solution2.put2(2, 2);
        System.out.println(solution2.get2(1));
        System.out.println(solution2.get2(3));
        solution2.put2(2, 1);
        System.out.println(solution2.get2(2));
        solution2.remove2(2);
        System.out.println(solution2.get2(2));
    }
}

class ListNode706 {

    int key, val;
    ListNode706 next;

    public ListNode706(int key, int val) {
        this.key = key;
        this.val = val;
    }

}
