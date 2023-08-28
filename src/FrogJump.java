import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 403. Frog Jump (https://leetcode.com/problems/frog-jump/description/)
 */
public class FrogJump {

    /********* Solution 1: Bottom-up 2D DP ******************/
    /**
     * steps is DP array
     * steps[i] 标记了 stone[i] 是否可以到达，且从 stone[i] 开始可以走多少步
     *
     * Time: O(N^2)     Space: O(N^2)
     * max is each stone can be reached by all its previous stones
     * 1 + 2 + 3 ... + N = O(N^2)
     */
    public boolean canCross(int[] stones) {
        // Iterate stones once to build
        // (1) map stone unit number to index in stone array
        // (2) init dp array => steps
        Map<Integer, Integer> stoneToIdx = new HashMap<>();
        List<Set<Integer>> steps = new ArrayList<>();
        for (int i = 0; i < stones.length; i++) {
            stoneToIdx.put(stones[i], i);
            steps.add(new HashSet<>());
        }
        // Init first step from stone[0] is 1
        steps.get(0).add(1);

        // Iterate stones again
        // for each stone, check if there is stone after each available step option
        // if so, update the next stone steps options
        for (int i = 0; i < stones.length; i++) {
            int curStone = stones[i];
            for (int step : steps.get(i)) {
                int nexStone = curStone + step;
                if (stoneToIdx.containsKey(nexStone)) {
                    int j = stoneToIdx.get(nexStone);
                    // nexStep = curStep - 1, curStep, curStep + 1;
                    if (step - 1 > 0) steps.get(j).add(step - 1);
                    steps.get(j).add(step);
                    steps.get(j).add(step + 1);
                }
            }
        }
        return !steps.get(steps.size() - 1).isEmpty();
    }

    public static void main(String[] args) {
        FrogJump solution = new FrogJump();

        System.out.println(solution.canCross(new int[]{0,1,3,5,6,8,12,17})); // true
        System.out.println(solution.canCross(new int[]{0,1,2,3,4,8,9,11})); // false
    }
}