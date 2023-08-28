import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 895. Maximum Frequency Stack (https://leetcode.com/problems/maximum-frequency-stack/)
 */
public class MaximumFrequencyStack {

    /********* For Solution1 ***********/
    TreeMap<Integer, Deque<Integer>> freqToVals1;
    Map<Integer, Integer> valToFreq1;

    /********* For Solution2 ***********/
    Map<Integer, Deque<Integer>> freqToVals;
    Map<Integer, Integer> valToFreq;
    int maxFreq;

    public MaximumFrequencyStack() {
        /********* For Solution1 ***********/
        freqToVals1 = new TreeMap<>();
        valToFreq1 = new HashMap<>();

        /********* For Solution2 ***********/
        freqToVals = new HashMap<>();
        valToFreq = new HashMap<>();
        maxFreq = 0;
    }

    /********** Solution 1: TreeMap freq + Stack vals && HashMap counter *************/
    /**
     * 	needs
     * 	1. freq -> vals get latest & remove any or lazy removal
     * 	2. heap to add freq & get max freq & remove freq if no vals
     * 	3. val -> freq counter
     *
     * 	1 + 2 => TreeMap<Integer, Deque<Integer>> freqToVals
     * 	TreeMap 维护 key freq 的 order
     * 	Deque 维护一个 vals 的 stack
     * 	3 => Map<Integer, Integer> valToFreq
     *
     *  Time:
     *  push => O(logN) by TreeMap.put
     *  pop => O(logN) by TreeMap.remove + amortized O(1) by Deque.pop() => O(logN)
     *
     *  Space: O(N)
     */
    public void push1(int val) {
        valToFreq1.put(val, valToFreq1.getOrDefault(val, 0) + 1);

        int freq = valToFreq1.get(val);

        freqToVals1.putIfAbsent(freq, new ArrayDeque<>());
        freqToVals1.get(freq).push(val);
    }

    public int pop1() {
        int resVal = -1;
        while (resVal == -1 && !freqToVals1.isEmpty()) {
            int maxFreq = freqToVals1.lastKey();
            Deque<Integer> vals = freqToVals1.get(maxFreq);
            while (!vals.isEmpty() && valToFreq1.getOrDefault(vals.peekFirst(), 0) != maxFreq) {
                vals.pop();
            }
            if (!vals.isEmpty()) {
                resVal = vals.pop();
            }
            if (vals.isEmpty()) {
                freqToVals1.remove(maxFreq);
            }
        }
        if (resVal != -1) {
            int freq = valToFreq1.get(resVal);
            if (freq == 1) {
                valToFreq1.remove(resVal);
            } else {
                valToFreq1.put(resVal, freq - 1);
            }

        }
        return resVal;
    }

    /********** Solution 2: Optimize Solution 1 *************/
    /**
     * 	Optimizations:
     * 	(1) turn TreeMap to HashMap + constant, only keep records of cur max freq (实际上，freq是连续的，5出现4次，pop一个，一定还有3次)
     *
     *  Time:
     *  push => O(1)
     *  pop => O(1)
     *
     *  Space: O(N)
     */
    public void push(int val) {
        valToFreq.put(val, valToFreq.getOrDefault(val, 0) + 1);

        int freq = valToFreq.get(val);

        freqToVals.putIfAbsent(freq, new ArrayDeque<>());
        freqToVals.get(freq).push(val);

        maxFreq = Math.max(maxFreq, freq);
    }

    public int pop() {
        int resVal = freqToVals.get(maxFreq).pop();
        valToFreq.put(resVal, valToFreq.get(resVal) - 1);
        if (freqToVals.get(maxFreq).isEmpty()) {
            freqToVals.remove(maxFreq--);
        }
        return resVal;
    }

    public static void main(String[] args) {
        // TC1
//        MaximumFrequencyStack solution = new MaximumFrequencyStack();
//        solution.push(5);
//        solution.push(7);
//        solution.push(5);
//        solution.push(7);
//        solution.push(4);
//        solution.push(5);
//        System.out.println(solution.pop()); // 5
//        System.out.println(solution.pop()); // 7
//        System.out.println(solution.pop()); // 5
//        System.out.println(solution.pop()); // 4

        // TC2
        MaximumFrequencyStack solution2 = new MaximumFrequencyStack();
        solution2.push(4);
        solution2.push(0);
        solution2.push(9);
        solution2.push(3);
        solution2.push(4);
        solution2.push(2);
        System.out.println(solution2.pop()); // 4
        solution2.push(6);
        System.out.println(solution2.pop()); // 6
        solution2.push(1);
        System.out.println(solution2.pop()); // 1
        solution2.push(1);
        System.out.println(solution2.pop()); // 1
        solution2.push(4);
        System.out.println(solution2.pop()); // 4
        System.out.println(solution2.pop()); // 2
        System.out.println(solution2.pop()); // 3
        System.out.println(solution2.pop()); // 9
        System.out.println(solution2.pop()); // 0
        System.out.println(solution2.pop()); // 4
    }
}
