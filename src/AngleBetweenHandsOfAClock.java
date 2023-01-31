/**
 * 1344. Angle Between Hands of a Clock (https://leetcode.com/problems/angle-between-hands-of-a-clock/description/)
 */
public class AngleBetweenHandsOfAClock {

    /************ Solution 1: Math ***************/
    /**
     * 全都转化为12点位置顺时针到该时/分针位置的角度
     * 相减的绝对值 和 补角 取较小值即为结果
     *
     * Time: O(1)   Space: O(1)
     */
    public double angleClock(int hour, int minutes) {
        int oneMinAngle = 6, oneHrAngle = 30;
        double hourAngle = (hour % 12 + minutes / 60.0) * oneHrAngle;
        double minAngle = minutes * oneMinAngle;
        double angle = Math.abs(hourAngle - minAngle);
        return angle > 180 ? 360 - angle : angle;
    }

    public static void main(String[] args) {
        AngleBetweenHandsOfAClock solution = new AngleBetweenHandsOfAClock();
        System.out.println(solution.angleClock(12, 30));
        System.out.println(solution.angleClock(3, 30));
        System.out.println(solution.angleClock(3, 15));
        System.out.println(solution.angleClock(2, 38));
    }

}
