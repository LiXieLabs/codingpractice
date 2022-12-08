/**
 * 1041. Robot Bounded In Circle (https://leetcode.com/problems/robot-bounded-in-circle/description/)
 */
public class RobotBoundedInCircle {

    private static final int[][] DIREC = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    private int di, x, y;

    /************** Solution 1: Simulation *****************/
    /**
     * The robot stays in the circle iff final status
     * changes direction or it moves 0.
     * 不用考虑具体路径，实际上是旋转对称的，只要方向变了，1或3次总能回到原点
     *
     * Time: O(N)   Space: O(1)
     */
    public boolean isRobotBounded(String instructions) {
        di = x = y = 0;
        for (char c : instructions.toCharArray()) {
            if (c == 'G') {
                x += DIREC[di][0];
                y += DIREC[di][1];
            } else if (c == 'R') {
                di = (di + 1) % 4;
            } else { // c == 'L'
                di = (di + 3) % 4; // (di - 1 + len) % 4
            }
        }
        return x == 0 && y == 0 || di != 0;
    }

    public static void main(String[] args) {
        RobotBoundedInCircle solution = new RobotBoundedInCircle();
        System.out.println(solution.isRobotBounded("GGLLGG"));
        System.out.println(solution.isRobotBounded("GG"));
        System.out.println(solution.isRobotBounded("GL"));
    }
}
