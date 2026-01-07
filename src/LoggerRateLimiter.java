import java.util.HashMap;
import java.util.Map;

/**
 * 359. Logger Rate Limiter (https://leetcode.com/problems/logger-rate-limiter/description/)
 */
public class LoggerRateLimiter {

    /********** Solution 1: Msg to Last Print Timestamp HashMap *********************/
    /**
     * Time: O(1)   Space: O(N)
     */
    private static final int THRESHOLD = 10;

    private Map<String, Integer>  msgTimeMap;

    public LoggerRateLimiter() {
        msgTimeMap = new HashMap<>();
    }

    public boolean shouldPrintMessage(int timestamp, String message) {
        if (!msgTimeMap.containsKey(message) || timestamp - msgTimeMap.get(message) >= THRESHOLD) {
            msgTimeMap.put(message, timestamp);
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        LoggerRateLimiter logger = new LoggerRateLimiter();
        System.out.println(logger.shouldPrintMessage(1, "foo")); // true
        System.out.println(logger.shouldPrintMessage(2, "bar")); // true
        System.out.println(logger.shouldPrintMessage(3, "foo")); // false
        System.out.println(logger.shouldPrintMessage(8, "bar")); // false
        System.out.println(logger.shouldPrintMessage(10, "foo")); // false
        System.out.println(logger.shouldPrintMessage(11, "foo")); // true
    }
}
