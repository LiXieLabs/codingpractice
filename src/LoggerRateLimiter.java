import java.util.HashMap;
import java.util.Map;

public class LoggerRateLimiter {

    private static int THRESHOLD = 10;

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
        System.out.println(logger.shouldPrintMessage(1, "foo"));
        System.out.println(logger.shouldPrintMessage(2, "bar"));
        System.out.println(logger.shouldPrintMessage(3, "foo"));
        System.out.println(logger.shouldPrintMessage(8, "bar"));
        System.out.println(logger.shouldPrintMessage(10, "foo"));
        System.out.println(logger.shouldPrintMessage(11, "foo"));
    }
}
